package com.seoplog.domain.user;

import com.seoplog.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String account;

    private String name;

    private String password;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private final List<Session> sessions = new ArrayList<>();

    @Builder
    private User(String account, String name, String password) {
        this.account = account;
        this.name = name;
        this.password = password;
    }
}
