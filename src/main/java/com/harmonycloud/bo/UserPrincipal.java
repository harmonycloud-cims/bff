package com.harmonycloud.bo;

import java.util.Collection;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String token;

    private String givenName;

    private String surName;

    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(Integer id,String token,String givenName,String surName,Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.token=token;
        this.surName=surName;
        this.givenName = givenName;
        this.authorities = authorities;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPrincipal that = (UserPrincipal) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }


    @Override
    public String getPassword() {
        return null;
    }


    @Override
    public String getUsername() {
        String username = givenName+","+surName;
        return username;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }


    public void setToken(String token) {
        this.token = token;
    }



}
