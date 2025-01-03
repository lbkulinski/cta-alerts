package app.cta4j.service;

import app.cta4j.client.AlertClient;
import app.cta4j.model.Alert;
import com.google.inject.Inject;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public final class AlertService {
    private final AlertClient client;

    @Inject
    public AlertService(AlertClient client) {
        this.client = Objects.requireNonNull(client);
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

    public void postAlerts() {
        List<Alert> alerts = this.client.getAlerts()
                                        .alertBody()
                                        .alerts();

        for (Alert alert : alerts) {
            String postText = this.getPostText(alert);

            System.out.println(postText);
        }
    }
}
