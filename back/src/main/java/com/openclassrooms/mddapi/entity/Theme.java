package com.openclassrooms.mddapi.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "themes")
@EqualsAndHashCode(exclude = {"articles", "users"})
@ToString(exclude = {"articles", "users"})
public class Theme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String description;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "theme")
    private Set<Article> articles = new HashSet<>();

    @ManyToMany(mappedBy = "subscriptions")
    private Set<User> users = new HashSet<>();

}
