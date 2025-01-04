package app.cta4j.provider;

import com.google.inject.Provider;
import io.github.redouane59.twitter.TwitterClient;
import io.github.redouane59.twitter.signature.TwitterCredentials;

public final class TwitterClientProvider implements Provider<TwitterClient> {
    @Override
    public TwitterClient get() {
        String accessToken = System.getenv("TWITTER_ACCESS_TOKEN");

        if (accessToken == null) {
            throw new RuntimeException("TWITTER_ACCESS_TOKEN is not set");
        }

        String accessTokenSecret = System.getenv("TWITTER_ACCESS_TOKEN_SECRET");

        if (accessTokenSecret == null) {
            throw new RuntimeException("TWITTER_ACCESS_TOKEN_SECRET is not set");
        }

        String apiKey = System.getenv("TWITTER_API_KEY");

        if (apiKey == null) {
            throw new RuntimeException("TWITTER_API_KEY is not set");
        }

        String apiKeySecret = System.getenv("TWITTER_API_KEY_SECRET");

        if (apiKeySecret == null) {
            throw new RuntimeException("TWITTER_API_KEY_SECRET is not set");
        }

        TwitterCredentials credentials = TwitterCredentials.builder()
                                                           .accessToken(accessToken)
                                                           .accessTokenSecret(accessTokenSecret)
                                                           .apiKey(apiKey)
                                                           .apiSecretKey(apiKeySecret)
                                                           .build();

        TwitterClient client = new TwitterClient(credentials);

        client.setAutomaticRetry(false);

        return client;
    }
}
