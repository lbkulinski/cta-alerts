package app.cta4j.model.serialization;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public final class AlertUrlDeserializer extends StdDeserializer<String> {
    public AlertUrlDeserializer(Class<?> vc) {
        super(vc);
    }

    public AlertUrlDeserializer() {
        this(null);
    }

    @Override
    public String deserialize(JsonParser parser, DeserializationContext context) throws IOException, JacksonException {
        JsonNode node = parser.getCodec()
                              .readTree(parser);

        return node.get("#cdata-section")
                   .asText();
    }
}
