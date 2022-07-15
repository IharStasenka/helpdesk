package com.training.istasenka.repository.customJwt;

import com.training.istasenka.model.customjwt.CustomJwt;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class CustomJwtRepositoryImpl implements CustomJwtRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<CustomJwt> findByJwtTokenData(String jwtTokenBody) {
        try {
            CustomJwt customJwt = entityManager.createQuery(
                    "select cj from CustomJwt cj where cj.jwtTokenData = :jwtTokenData", CustomJwt.class)
                    .setParameter("jwtTokenData", jwtTokenBody)
                    .getSingleResult();
            entityManager.detach(customJwt);
            return Optional.of(customJwt);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Long> addCustomJwt(CustomJwt customJwt) {
        try {
            entityManager.merge(customJwt);
            entityManager.detach(customJwt);
            return Optional.of(customJwt.getId());
        } catch (Exception exception) {
            return Optional.empty();
        }
    }

    public Optional<List<CustomJwt>> deleteByExpirationDate(Date expiryDate) {
        try{
            List<CustomJwt> listCustomJwt = entityManager.createQuery(
                    "select cj from CustomJwt cj where cj.expiryDate < :expiryDate ", CustomJwt.class)
                    .setParameter("expiryDate", expiryDate)
                    .getResultList();
            if (!listCustomJwt.isEmpty()) {
                for (CustomJwt customJwt:listCustomJwt
                     ) {
                    entityManager.remove(customJwt);
                }
                entityManager.flush();
                entityManager.clear();
            }
            return Optional.of(listCustomJwt);
        } catch (NoResultException e){
            return Optional.empty();
        }


    }

}
