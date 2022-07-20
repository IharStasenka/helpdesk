package com.training.istasenka.service.user;

import com.training.istasenka.dto.user.EngineerRatingDto;
import com.training.istasenka.exception.CustomIllegalArgumentException;
import com.training.istasenka.model.user.User;
import com.training.istasenka.repository.user.UserRepository;
import lombok.AllArgsConstructor;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final StreamsBuilderFactoryBean factoryBean;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "cache.users", key = "#email")
    public User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("There are no user with email %s", email)));
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "cache.users", allEntries = true)
    public Long addUser(User user) {
        return userRepository.save(user).getUserId();
    }

    @Override
    @Transactional
    @Cacheable(cacheNames = "cache.users")
    public List<User> getUsers(int amountOfUsers, Long startId) {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "cache.users", key = "#email")
    public void deleteUser(String email) {
        userRepository.deleteById(getUser(email).getUserId());
    }

    @Override
    @Transactional
    @CachePut(cacheNames = "cache.users", key = "#user.email")
    public void updateUser(User user, Long userId) {
        if (user.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Mismatch of user id, and id in path variable");
        }
        userRepository.save(user);
    }

    @Override
    @Transactional
    public EngineerRatingDto getEngineerRating(String engineerEmail) {
        getUser(engineerEmail);
        final KafkaStreams kafkaStreams =  factoryBean.getKafkaStreams();
        if (kafkaStreams != null) {
            final ReadOnlyKeyValueStore<String, EngineerRatingDto> rating = kafkaStreams.store(StoreQueryParameters.fromNameAndType("rating", QueryableStoreTypes.keyValueStore()));
            EngineerRatingDto engineerRatingDto = rating.get(engineerEmail);
            if (engineerRatingDto == null) {
                engineerRatingDto = new EngineerRatingDto(0L, BigDecimal.ZERO);
            }
            return engineerRatingDto;
        } else {
            throw new CustomIllegalArgumentException("KafkaStream is empty");
        }
    }
}
