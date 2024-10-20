package com.zionique.invoiceapp.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
@Setter
public class UserDetailsImpl implements UserDetails {
    private final User user;
//    private String mobile;
//    private String name;
//    private String password;
//    private Collection<? extends GrantedAuthority> authorities;

//    public UserDetailsImpl(String mobile, String name, String password, Collection<? extends GrantedAuthority> authorities) {
//        this.mobile = mobile;
//        this.name = name;
//        this.password = password;
//        this.authorities = authorities;
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getName().name()));
    }
//        return authorities;


    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getMobile();
    }

    public String getName() { return user.getName(); }

    // Other UserDetails methods (isAccountNonExpired, isAccountNonLocked, etc.)
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

    public String getRole() {
        return user.getRole().getName().toString();
    }
}
