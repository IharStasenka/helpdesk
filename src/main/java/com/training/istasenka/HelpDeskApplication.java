package com.training.istasenka;

import com.training.istasenka.provider.kafkatopic.TopicProvider;
import com.training.istasenka.provider.specification.mailrecepients.MailTemplateRecipientSpecificationProvider;
import com.training.istasenka.provider.specification.ticket.role.RoleSpecificationProvider;
import com.training.istasenka.util.MailTemplateType;
import com.training.istasenka.util.UserRole;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.apache.kafka.clients.admin.NewTopic;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.training.istasenka.util.KafkaTopicType.ENGINEER_RATING_TOPIC;
import static com.training.istasenka.util.KafkaTopicType.FEEDBACK_TOPIC;
import static org.keycloak.OAuth2Constants.CLIENT_CREDENTIALS;

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
    private List<RoleSpecificationProvider> userRoleSpecificationProviders;

    @Autowired
    private List<MailTemplateRecipientSpecificationProvider> mailTemplateRecipientSpecificationProviders;

    @Autowired
    private TopicProvider topicProvider;

    @Bean
    public ConfigurableServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addConnectorCustomizers(connector -> connector.setProperty("relaxedQueryChars", "|{}[]"));
        return factory;
    }

    @Bean
    public Map<MailTemplateType, MailTemplateRecipientSpecificationProvider> getMailTemplateRecipientSpecificationProvider() {
        return mailTemplateRecipientSpecificationProviders
                .stream()
                .collect(Collectors.toMap(MailTemplateRecipientSpecificationProvider::getMailTemplateType, Function.identity()));
    }

    @Bean
    public Map<UserRole, RoleSpecificationProvider> getUserRoleSpecificationProviders() {
        return userRoleSpecificationProviders
                .stream()
                .collect(Collectors.toMap(RoleSpecificationProvider::getUserRole, Function.identity()));
    }

    @Bean
    public NewTopic topic1() {
        return topicProvider.getTopicByTopicType(FEEDBACK_TOPIC);
    }

    @Bean
    public NewTopic topic2() {
        return topicProvider.getTopicByTopicType(ENGINEER_RATING_TOPIC);
    }

    @Bean
    public WebClient webClientWithTimeout(@Value("${keycloak.auth-server-url}") String baseUrl) {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .responseTimeout(Duration.ofMillis(5000))
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS)));

        return WebClient.builder()
                .baseUrl(baseUrl)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    @Bean
    public Keycloak getKeycloak(
            @Value("${keycloak.auth-server-url}") String url,
            @Value("${keycloak.resource}") String clientId,
            @Value("${keycloak.realm}") String realm,
            @Value("${keycloak.credentials.secret}") String secret) {
        return KeycloakBuilder.builder()
                .grantType(CLIENT_CREDENTIALS)
                .serverUrl(url)
                .realm(realm)
                .clientId(clientId)
                .clientSecret(secret)
                .build();
    }
}
