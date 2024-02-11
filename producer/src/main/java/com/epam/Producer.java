package com.epam;

import com.epam.avro.User;
import com.epam.config.UserSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;

import java.util.Properties;

public class Producer {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:29092");

        KafkaProducer<Integer, User> producer = new KafkaProducer<>(props, new IntegerSerializer(), new UserSerializer());
        for (int i = 1; i <= 100; i++){
            producer.send(new ProducerRecord<>("user_topic", 0, i, User.newBuilder().setId(i).setName(i + " avro value").build()));
        }
        producer.close();

    }
}
