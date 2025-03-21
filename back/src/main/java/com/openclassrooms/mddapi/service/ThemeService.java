package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.ThemeDTO;
import com.openclassrooms.mddapi.entity.Theme;
import com.openclassrooms.mddapi.entity.User;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ThemeService implements ThemeServiceImpl {

    private final ThemeRepository themeRepository;

    public List<ThemeDTO> findThemesByUser(User user) {
        return themeRepository.findByUsers(user)
                .stream()
                .map(ThemeDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public List<ThemeDTO> getAllTheme() {
        List<Theme> themesList = themeRepository.findAll();
        return themesList.stream()
                .map(ThemeDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public Theme findThemeById(Integer id) {
        return themeRepository.findById(id).orElse(null);
    }
}
