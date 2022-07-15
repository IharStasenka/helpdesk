package com.training.istasenka.model.customjwt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.training.istasenka.model.user.User;
import com.training.istasenka.util.TokenType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@JsonIgnoreProperties({"id", "user", "numberChildToken", "expiryDate"})
@Table(name = "Jwt_Token")
public class CustomJwt {
    @Id
    @GeneratedValue(generator = "ID_GENERATOR")
    @GenericGenerator(name = "ID_GENERATOR",
            strategy = "increment",
            parameters = {
//                    @org.hibernate.annotations.Parameter(
//                            name = "initial_value",
//                            value = "100"),
                    @org.hibernate.annotations.Parameter(
                            name = "sequence_name",
                            value = "HD_SEQUENCE"
                    )
            })
    @Column(name = "token_id", nullable = false, unique = true, updatable = false)
    private Long id;
    @Column(name = "token_data", nullable = false, updatable = false, unique = true)
    private String jwtTokenData;
    @Temporal(TemporalType.TIME)
    @Column(name = "exp_time", nullable = false, updatable = false)
    private Date expiryDate;
    @Column(name = "child_token")
    private Long numberChildToken;
    @Column(name = "token_type")
    private TokenType tokenType;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;


    public CustomJwt() {
    }

    public CustomJwt(Long id, String jwtTokenData, Date expiryDate, TokenType tokenType, Long numberChildToken, User user) {
        this.id = id;
        this.jwtTokenData = jwtTokenData;
        this.expiryDate = expiryDate;
        this.tokenType = tokenType;
        this.numberChildToken = numberChildToken;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJwtTokenData() {
        return jwtTokenData;
    }

    public void setJwtTokenData(String jwtTokenData) {
        this.jwtTokenData = jwtTokenData;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Long getNumberChildToken() {
        return numberChildToken;
    }

    public void setNumberChildToken(Long numberChildToken) {
        this.numberChildToken = numberChildToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    @Override
    public String toString() {
        return "CustomJwt{" +
                "id=" + id +
                ", jwtTokenData='" + jwtTokenData + '\'' +
                ", expiryDate=" + expiryDate +
                '}';
    }
}
