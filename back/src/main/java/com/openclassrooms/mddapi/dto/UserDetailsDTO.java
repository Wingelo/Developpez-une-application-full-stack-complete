package com.openclassrooms.mddapi.dto;

import com.openclassrooms.mddapi.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsDTO {

    private Integer id;

    private String username;
    private String email;

    private String createdAt;
    private String updatedAt;


    public static UserDetailsDTO fromEntity(User user) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return new UserDetailsDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                Optional.ofNullable(user.getCreatedAt())
                        .map(date -> date.format(formatter))
                        .orElse(null),
                Optional.ofNullable(user.getUpdatedAt())
                        .map(date -> date.format(formatter))
                        .orElse(null)
        );
    }

}
