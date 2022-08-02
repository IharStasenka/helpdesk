package com.training.istasenka.util;

public enum KafkaTopicType {
    FEEDBACK_TOPIC("feedback", 1, (short) 1),
    ENGINEER_RATING_TOPIC("engineerRating", 1, (short) 1);

    private final String topicName;
    private final Integer numberOfPartitions;
    private final Short replicationFactor;

    KafkaTopicType(String topicName, Integer numberOfPartitions, Short replicationFactor) {
        this.topicName = topicName;
        this.numberOfPartitions = numberOfPartitions;
        this.replicationFactor = replicationFactor;
    }

    public String getTopicName() {
        return topicName;
    }

    public Integer getNumberOfPartitions() {
        return numberOfPartitions;
    }

    public Short getReplicationFactor() {
        return replicationFactor;
    }
}
