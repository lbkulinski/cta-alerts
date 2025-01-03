package app.cta4j.model;

import app.cta4j.model.serialization.AlertUrlDeserializer;
import app.cta4j.model.serialization.StringToInstantConverter;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.time.Instant;
import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Alert(
    @JsonAlias("AlertId")
    String id,

    @JsonAlias("Headline")
    String headline,

    @JsonAlias("ShortDescription")
    String shortDescription,

    @JsonAlias("Impact")
    String impact,

    @JsonAlias("EventStart")
    @JsonDeserialize(converter = StringToInstantConverter.class)
    Instant start,

    @JsonAlias("EventEnd")
    @JsonDeserialize(converter = StringToInstantConverter.class)
    Instant end,

    @JsonAlias("AlertURL")
    @JsonDeserialize(using = AlertUrlDeserializer.class)
    String url
) {
}
