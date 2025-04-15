package com.openclassrooms.mddapi.dto;

import com.openclassrooms.mddapi.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsDTO {

    private Integer id;

    private String username;
    private String email;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public static UserDetailsDTO fromEntity(User user) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return new UserDetailsDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

}
