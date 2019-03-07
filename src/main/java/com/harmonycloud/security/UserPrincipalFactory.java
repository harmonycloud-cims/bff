package com.harmonycloud.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.harmonycloud.bo.UserPrincipal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserPrincipalFactory {

    public static UserPrincipal createUserPrincipal(String token,Map<String, Object> claims) {

        List<GrantedAuthority> authorities=new ArrayList<>();

        List<String> roles = (List<String>) claims.get("roles");
        for (int i = 0; i < roles.size(); i++) {
            authorities.add(new SimpleGrantedAuthority(roles.get(i)));
        }

        Integer userId = Integer.valueOf(claims.get("userId").toString());
        String givenName = claims.get("givenname").toString();
        String surName = claims.get("surname").toString();
        return new UserPrincipal(userId,token,givenName,surName,authorities);
    }
}
