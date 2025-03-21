package com.openclassrooms.mddapi.configuration;

import com.openclassrooms.mddapi.entity.User;
import com.openclassrooms.mddapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class ToolsUtils {

    private final UserService userService;

    public static boolean verifyStringWithRegex(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public User getUserLogin(String login) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return ToolsUtils.verifyStringWithRegex(login, emailRegex) ? userService.findByEmail(login) : userService.findByUsername(login);
    }


}
