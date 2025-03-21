package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.ThemeDTO;
import com.openclassrooms.mddapi.entity.Theme;
import com.openclassrooms.mddapi.entity.User;

import java.util.List;

public interface ThemeServiceImpl {

    List<ThemeDTO> findThemesByUser(User user);

    Theme findThemeById(Integer id);
}
