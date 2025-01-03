package app.cta4j.module;

import app.cta4j.client.AlertClient;
import app.cta4j.provider.AlertClientProvider;
import com.google.inject.AbstractModule;

public final class ApplicationModule extends AbstractModule {
    @Override
    protected void configure() {
        this.bind(AlertClient.class)
            .toProvider(AlertClientProvider.class);
    }
}
