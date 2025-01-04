/*
package app.cta4j.provider;

import com.google.inject.Provider;
import social.bigbone.MastodonClient;

public final class MastodonClientProvider implements Provider<MastodonClient> {
    @Override
    public MastodonClient get() {
        String instance = System.getenv("MASTODON_INSTANCE");

        if (instance == null) {
            throw new RuntimeException("MASTODON_INSTANCE is not set");
        }

        String accessToken = System.getenv("MASTODON_ACCESS_TOKEN");

        if (accessToken == null) {
            throw new RuntimeException("MASTODON_ACCESS_TOKEN is not set");
        }

        MastodonClient.Builder builder = new MastodonClient.Builder(instance);

        return builder.accessToken(accessToken)
                      .build();
    }
}
*/
