package com.training.directory.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.training.directory.constant.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Table(name = "users")
public class User extends BaseEntity implements UserDetails  {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private Date birthday;

    @NotNull
    @Email
    @Column(unique = true)
    private String email;

    @NotNull
    @Column(unique = true)
    private String username;

    @JsonIgnore
    @NotNull
    private String password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Image profilePicture;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return Boolean.TRUE;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return Boolean.TRUE;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return Boolean.TRUE;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return Boolean.TRUE;
    }
}
