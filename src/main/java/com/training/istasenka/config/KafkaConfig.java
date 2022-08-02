package com.training.istasenka.config;


import com.training.istasenka.provider.kafkatopic.TopicProvider;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;

import static com.training.istasenka.util.KafkaTopicType.ENGINEER_RATING_TOPIC;
import static com.training.istasenka.util.KafkaTopicType.FEEDBACK_TOPIC;


@EnableKafka
@EnableKafkaStreams
@Configuration
public class KafkaConfig {
    @Autowired
    private TopicProvider topicProvider;

    @Bean
    public NewTopic topic1() {
        return topicProvider.getTopicByTopicType(FEEDBACK_TOPIC);
    }

    @Bean
    public NewTopic topic2() {
        return topicProvider.getTopicByTopicType(ENGINEER_RATING_TOPIC);
    }
}
