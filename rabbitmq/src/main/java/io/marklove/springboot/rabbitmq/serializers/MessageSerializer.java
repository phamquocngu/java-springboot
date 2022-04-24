package io.marklove.springboot.rabbitmq.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import io.marklove.springboot.rabbitmq.model.Message;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class MessageSerializer extends StdSerializer<Message> {
    private static final String FORMAT_ZONE_DATE_TIME = "MM/dd/yyyy - HH:mm:ss Z";

    public MessageSerializer() {
        this(null);
    }

    public MessageSerializer(Class<Message> t) {
        super(t);
    }

    @Override
    public void serialize(Message value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {

        jgen.writeStartObject();
        jgen.writeStringField("id", value.getId());

        ZonedDateTime dateTime = value.getDateTime();
        String dateTimeStr = "";
        if(dateTime != null) {
            dateTimeStr = dateTime.format(DateTimeFormatter.ofPattern(FORMAT_ZONE_DATE_TIME));
        }

        jgen.writeStringField("dateTime", dateTimeStr);
        jgen.writeStringField("content", value.getContent());
        jgen.writeEndObject();
    }
}
