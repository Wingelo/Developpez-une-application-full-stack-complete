package com.openclassrooms.mddapi.dto;

import com.openclassrooms.mddapi.entity.Theme;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThemeDTO {

    private Integer id;
    private String name;
    private String description;

    private String createdAt;
    private String updatedAt;

    public static ThemeDTO fromEntity(Theme theme) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return new ThemeDTO(
                theme.getId(),
                theme.getName(),
                theme.getDescription(),
                Optional.ofNullable(theme.getCreatedAt())
                        .map(date -> date.format(formatter))
                        .orElse(null),
                Optional.ofNullable(theme.getUpdatedAt())
                        .map(date -> date.format(formatter))
                        .orElse(null)
        );
    }
}
