package app.cta4j.provider;

import com.google.inject.Provider;
import work.socialhub.kbsky.Bluesky;
import work.socialhub.kbsky.BlueskyFactory;
import work.socialhub.kbsky.domain.Service;

public final class BlueskyClientProvider implements Provider<Bluesky> {
    @Override
    public Bluesky get() {
        String uri = Service.BSKY_SOCIAL.getUri();

        return BlueskyFactory.INSTANCE.instance(uri);
    }
}
