package app.cta4j.provider;

import app.cta4j.client.AlertClient;
import com.google.inject.Provider;
import feign.Feign;
import feign.RequestInterceptor;
import feign.jackson.JacksonDecoder;

public final class AlertClientProvider implements Provider<AlertClient> {
    @Override
    public AlertClient get() {
        JacksonDecoder decoder = new JacksonDecoder();

        RequestInterceptor interceptor = template -> template.query("outputType", "JSON");

        return Feign.builder()
                    .decoder(decoder)
                    .requestInterceptor(interceptor)
                    .target(AlertClient.class, "https://www.transitchicago.com/api/1.0");
    }
}
