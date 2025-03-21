package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.entity.Theme;
import com.openclassrooms.mddapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ThemeRepository extends JpaRepository<Theme, Integer> {

    List<Theme> findByUsers(User users);

    Theme findById(int id);
}
