//package io.marklove.springboot.rabbitmq.serializers.deserializers;
//
//import com.fasterxml.jackson.core.JsonGenerator;
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.DeserializationContext;
//import com.fasterxml.jackson.databind.JsonDeserializer;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.SerializerProvider;
//import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
//import com.fasterxml.jackson.databind.node.IntNode;
//import com.fasterxml.jackson.databind.ser.std.StdSerializer;
//import io.marklove.springboot.rabbitmq.model.Message;
//import org.springframework.util.StringUtils;
//
//import java.io.IOException;
//import java.time.ZonedDateTime;
//import java.time.format.DateTimeFormatter;
//
//public class MessageDeserializer extends StdDeserializer<Message> {
//    private static final String FORMAT_ZONE_DATE_TIME = "MM/dd/yyyy - HH:mm:ss Z";
//
//    public MessageDeserializer() {
//        this(null);
//    }
//
//    public MessageDeserializer(Class<?> vc) {
//        super(vc);
//    }
//
//    @Override
//    public Message deserialize(JsonParser jp, DeserializationContext ctxt)
//            throws IOException, JsonProcessingException {
//        JsonNode node = jp.getCodec().readTree(jp);
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_ZONE_DATE_TIME);
//        String dateTimeStr = node.get("dateTime").asText();
//
//        ZonedDateTime dateTime = null;
//        if(!StringUtils.isEmpty(dateTimeStr)) {
//            dateTime = ZonedDateTime.parse(dateTimeStr, formatter);
//        }
//
//        String id = node.get("id").asText();
//        String content = node.get("content").asText();
//
//        return new Message(id, dateTime, content);
//    }
//}
