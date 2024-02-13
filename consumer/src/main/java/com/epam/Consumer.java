package com.epam;

import com.epam.avro.User;
import com.epam.config.UserDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.IntegerDeserializer;

import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

public class Consumer {

    public static void main(String[] args) {

        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "localhost:29092");
        props.setProperty("group.id", UUID.randomUUID().toString());
        props.setProperty("enable.auto.commit", "true");
        props.setProperty("auto.commit.interval.ms", "1000");
        props.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        KafkaConsumer<Integer, User> consumer = new KafkaConsumer<>(props, new IntegerDeserializer(), new UserDeserializer());
        consumer.subscribe(List.of("user_topic"));

        while (true) {
            ConsumerRecords<Integer, User> records = consumer.poll(Duration.ofMillis(100));

            for (ConsumerRecord<Integer, User> rec : records) {
                System.out.println("Received message: (" + rec.key() + ", " + rec.value().toString() + ") at offset " + rec.offset());
            }
        }
    }
}
