package com.openclassrooms.mddapi.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(exclude = {"articles", "subscriptions", "comments"})
@ToString(exclude = {"articles", "subscriptions", "comments"})
public class User {

    @Id
    @GeneratedValue
    private Integer id;

    private String username;
    private String email;
    private String password;
    private String role;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany
    private Set<Article> articles = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "subscriptions",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "theme_id")
    )
    private Set<Theme> subscriptions = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Comment> comments = new HashSet<>();

}
