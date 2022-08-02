package com.training.istasenka.provider.specification.ticket.role;

import com.training.istasenka.util.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
@Component
public class UserRoleSpecificationProviders {

    @Autowired
    private List<RoleSpecificationProvider> userRoleSpecificationProviderList;

    public Map<UserRole, RoleSpecificationProvider> getUserRoleSpecificationProviders() {
        return userRoleSpecificationProviderList
                .stream()
                .collect(Collectors.toMap(RoleSpecificationProvider::getUserRole, Function.identity()));
    }
}
