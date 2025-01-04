package app.cta4j.service;

import app.cta4j.client.AlertClient;
import app.cta4j.model.Alert;
import com.google.inject.Inject;
import io.github.redouane59.twitter.TwitterClient;
import io.github.redouane59.twitter.dto.tweet.Tweet;
import redis.clients.jedis.UnifiedJedis;
import social.bigbone.MastodonClient;
import social.bigbone.api.entity.Status;
import social.bigbone.api.exception.BigBoneRequestException;
import work.socialhub.kbsky.Bluesky;
import work.socialhub.kbsky.api.entity.app.bsky.feed.FeedPostRequest;
import work.socialhub.kbsky.api.entity.com.atproto.server.ServerCreateSessionRequest;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public final class AlertService {
    private final AlertClient client;

    private final UnifiedJedis jedis;

    private final TwitterClient twitterClient;

    private final MastodonClient mastodonClient;

    private final Bluesky blueskyClient;

    @Inject
    public AlertService(AlertClient client, UnifiedJedis jedis, TwitterClient twitterClient,
                        MastodonClient mastodonClient, Bluesky blueskyClient) {
        this.client = Objects.requireNonNull(client);

        this.jedis = Objects.requireNonNull(jedis);

        this.twitterClient = Objects.requireNonNull(twitterClient);

        this.mastodonClient = Objects.requireNonNull(mastodonClient);

        this.blueskyClient = Objects.requireNonNull(blueskyClient);
    }

    private String getPostText(Alert alert) {
        Objects.requireNonNull(alert);

        String impact = alert.impact();

        String headline = alert.headline();

        ZoneId chicagoId = ZoneId.of("America/Chicago");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd hh:mm a")
                                                       .withZone(chicagoId);

        Instant start = alert.start();

        String startString = formatter.format(start);

        Instant end = alert.end();

        String endString;

        if (end == null) {
            endString = "TBD";
        } else {
            endString = formatter.format(end);
        }

        String url = alert.url();

        return "[%s] %s %s - %s %s".formatted(impact, headline, startString, endString, url);
    }

    private String postSkeet(String text) {
        Objects.requireNonNull(text);

        String handle = System.getenv("BLUESKY_HANDLE");

        if (handle == null) {
            throw new RuntimeException("BLUESKY_HANDLE is not set");
        }

        String appPassword = System.getenv("BLUESKY_APP_PASSWORD");

        if (appPassword == null) {
            throw new RuntimeException("BLUESKY_APP_PASSWORD is not set");
        }

        ServerCreateSessionRequest serverCreateSessionRequest = new ServerCreateSessionRequest();

        serverCreateSessionRequest.setIdentifier(handle);

        serverCreateSessionRequest.setPassword(appPassword);

        String accessJwt = this.blueskyClient.server()
                                             .createSession(serverCreateSessionRequest)
                                             .getData().accessJwt;

        FeedPostRequest feedPostRequest = new FeedPostRequest(accessJwt);

        feedPostRequest.setText(text);

        return this.blueskyClient.feed()
                                 .post(feedPostRequest)
                                 .getData()
                                 .getCid();
    }

    private Map<String, String> postAlert(Alert alert) {
        Objects.requireNonNull(alert);

        String text = this.getPostText(alert);

        Tweet tweet = this.twitterClient.postTweet(text);

        String tweetId = tweet.getId();

        Map<String, String> postIds = new HashMap<>();

        if (tweetId != null) {
            postIds.put("twitter", tweetId);
        }

        try {
            Status status = this.mastodonClient.statuses()
                                               .postStatus(text)
                                               .execute();

            String mastodonId = status.getId();

            postIds.put("mastodon", mastodonId);
        } catch (BigBoneRequestException e) {
            e.printStackTrace();
        }

        String skeetId = this.postSkeet(text);

        postIds.put("bluesky", skeetId);

        return Map.copyOf(postIds);
    }

    public void postAlerts() {
        List<Alert> alerts = this.client.getAlerts()
                                        .alertBody()
                                        .alerts();

        for (Alert alert : alerts) {
            String id = alert.id();

            if (this.jedis.exists(id)) {
                continue;
            }

            Map<String, String> postIds = this.postAlert(alert);

            if (!postIds.isEmpty()) {
                this.jedis.hset(id, postIds);
            }
        }
    }
}
