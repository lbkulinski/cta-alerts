package app.cta4j.model.serialization;

import com.fasterxml.jackson.databind.util.StdConverter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public final class StringToInstantConverter extends StdConverter<String, Instant> {
    @Override
    public Instant convert(String string) {
        Objects.requireNonNull(string);

        ZoneId chicagoId = ZoneId.of("America/Chicago");

        try {
            return LocalDate.parse(string)
                            .atTime(0, 0)
                            .atZone(chicagoId)
                            .toInstant();
        } catch (DateTimeParseException ignored) {
        }

        try {
            return LocalDateTime.parse(string)
                                .atZone(chicagoId)
                                .toInstant();
        } catch (DateTimeParseException ignored) {
        }

        throw new IllegalArgumentException("\"%s\" is not a valid date-time".formatted(string));
    }
}
