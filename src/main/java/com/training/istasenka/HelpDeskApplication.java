package com.training.istasenka;

import com.training.istasenka.provider.kafkatopic.TopicProvider;
import com.training.istasenka.provider.specification.ticket.role.RoleSpecificationProvider;
import com.training.istasenka.util.UserRole;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.training.istasenka.util.KafkaTopicType.ENGINEER_RATING_TOPIC;
import static com.training.istasenka.util.KafkaTopicType.FEEDBACK_TOPIC;

@SpringBootApplication
@EnableScheduling
@EnableCaching
@EnableKafka
@EnableKafkaStreams
public class HelpDeskApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelpDeskApplication.class, args);
    }

    @Autowired
    private List<RoleSpecificationProvider> specificationProviders;

    @Autowired
    private TopicProvider topicProvider;

    @Bean
    public ConfigurableServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addConnectorCustomizers(connector -> connector.setProperty("relaxedQueryChars", "|{}[]"));
        return factory;
    }

    @Bean
    public Map<UserRole, RoleSpecificationProvider> getSpecificationProviders() {
        return specificationProviders.stream().collect(Collectors.toMap(RoleSpecificationProvider::getUserRole, Function.identity()));
    }

    @Bean
    public NewTopic topic1() {
        return topicProvider.getTopicByTopicType(FEEDBACK_TOPIC);
    }

    @Bean
    public NewTopic topic2() {
        return topicProvider.getTopicByTopicType(ENGINEER_RATING_TOPIC);
    }
}
