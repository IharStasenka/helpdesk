package com.training.istasenka.provider.kafkatopic;

import com.training.istasenka.util.KafkaTopicType;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.stereotype.Component;

@Component
public class TopicProviderImpl implements TopicProvider {

    @Override
    public NewTopic getTopicByTopicType(KafkaTopicType kafkaTopicType) {
        return TopicBuilder
                .name(kafkaTopicType.getTopicName())
                .partitions(kafkaTopicType.getNumberOfPartitions())
                .replicas(kafkaTopicType.getReplicationFactor())
                .build();
    }
}
