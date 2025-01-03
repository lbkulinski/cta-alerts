package app.cta4j.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AlertResponse(@JsonAlias("CTAAlerts") AlertBody alertBody) {
}
