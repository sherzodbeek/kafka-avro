package com.epam.config;

import com.epam.avro.User;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class UserSerializer implements Serializer<User> {

    private static final Logger log = LoggerFactory.getLogger(UserSerializer.class);


    @Override
    public byte[] serialize(String s, User user) {
        byte[] arr = new byte[100000];
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            BinaryEncoder binaryEncoder = EncoderFactory.get().binaryEncoder(outputStream, null);
            GenericDatumWriter<User> writer = new GenericDatumWriter<>(user.getSchema());
            writer.write(user, binaryEncoder);
            binaryEncoder.flush();
            arr = outputStream.toByteArray();
        } catch (IOException e) {
            log.error("Error occurred: {}", e.getMessage());
        }
        return arr;
    }
}
