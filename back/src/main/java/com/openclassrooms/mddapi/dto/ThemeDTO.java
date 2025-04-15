package com.openclassrooms.mddapi.dto;

import com.openclassrooms.mddapi.entity.Theme;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThemeDTO {

    private Integer id;
    private String name;
    private String description;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ThemeDTO fromEntity(Theme theme) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return new ThemeDTO(
                theme.getId(),
                theme.getName(),
                theme.getDescription(),
                theme.getCreatedAt(),
                theme.getUpdatedAt()
        );
    }
}
