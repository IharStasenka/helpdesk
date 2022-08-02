package com.training.istasenka.provider.kafkatopic;

import com.training.istasenka.util.KafkaTopicType;
import org.apache.kafka.clients.admin.NewTopic;

public interface TopicProvider {

    NewTopic getTopicByTopicType(KafkaTopicType kafkaTopicType);
}
