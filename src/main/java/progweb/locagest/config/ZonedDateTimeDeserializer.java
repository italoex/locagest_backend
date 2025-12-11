package progweb.locagest.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ZonedDateTimeDeserializer extends JsonDeserializer<ZonedDateTime> {

    @Override
    public ZonedDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String dateString = p.getValueAsString();
        if (dateString == null || dateString.isEmpty()) {
            return null;
        }

        try {
            return ZonedDateTime.parse(dateString, DateTimeFormatter.ISO_DATE_TIME);
        } catch (Exception e1) {
            try {
                return ZonedDateTime.parse(dateString, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            } catch (Exception e2) {
                throw new IOException("Não foi possível deserializar a data: " + dateString, e2);
            }
        }
    }
}
