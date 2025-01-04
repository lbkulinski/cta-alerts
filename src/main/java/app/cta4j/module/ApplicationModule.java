package app.cta4j.module;

import app.cta4j.client.AlertClient;
import app.cta4j.provider.*;
import com.google.inject.AbstractModule;
import io.github.redouane59.twitter.TwitterClient;
import redis.clients.jedis.UnifiedJedis;
import social.bigbone.MastodonClient;
import work.socialhub.kbsky.Bluesky;

public final class ApplicationModule extends AbstractModule {
    @Override
    protected void configure() {
        this.bind(AlertClient.class)
            .toProvider(AlertClientProvider.class);

        this.bind(UnifiedJedis.class)
            .toProvider(JedisClientProvider.class);

        this.bind(TwitterClient.class)
            .toProvider(TwitterClientProvider.class);

        this.bind(Bluesky.class)
            .toProvider(BlueskyClientProvider.class);

        this.bind(MastodonClient.class)
            .toProvider(MastodonClientProvider.class);
    }
}
