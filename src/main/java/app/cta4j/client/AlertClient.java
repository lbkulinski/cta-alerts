package app.cta4j.client;

import app.cta4j.model.AlertResponse;
import feign.RequestLine;

public interface AlertClient {
    @RequestLine("GET /alerts.aspx")
    AlertResponse getAlerts();
}
