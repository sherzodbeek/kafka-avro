package com.epam.config;

import com.epam.avro.User;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDeserializer implements Deserializer<User> {

    private static final Logger log = LoggerFactory.getLogger(UserDeserializer.class);


    @Override
    public User deserialize(String s, byte[] data) {
        try {
            if (data != null) {
                DatumReader<User> reader = new SpecificDatumReader<>(User.getClassSchema());
                Decoder decoder = DecoderFactory.get().binaryDecoder(data, null);
                return reader.read(null, decoder);
            }
        } catch (Exception e) {
            log.error("Error occurred: {}", e.getMessage());
        }
        return null;
    }
}
