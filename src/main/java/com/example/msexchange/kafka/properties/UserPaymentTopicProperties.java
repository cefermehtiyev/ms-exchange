package com.example.msexchange.kafka.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "kafka.topics.user-payment")
@Getter
@Setter
public class UserPaymentTopicProperties {
    private String topicName;
    private int partitionCount;
    private short replicationFactor;
    private String retentionInMs;
}
