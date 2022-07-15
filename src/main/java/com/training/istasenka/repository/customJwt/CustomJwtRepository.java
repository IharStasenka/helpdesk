package com.training.istasenka.repository.customJwt;

import com.training.istasenka.model.customjwt.CustomJwt;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface CustomJwtRepository {
    Optional<CustomJwt> findByJwtTokenData(String jwtTokenBody);
    Optional<Long> addCustomJwt(CustomJwt customJwt);
    Optional<List<CustomJwt>> deleteByExpirationDate(Date expiryDate);

}
