package by.mmb.controllers;

import by.mmb.dto.UserDTO;
import by.mmb.exception.AppsException;
import by.mmb.model.User;
import by.mmb.sevice.SecurityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Slf4j
public class TestController {

    private final SecurityService securityService;

    @Autowired
    public TestController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @GetMapping("/getUserDetail")
    public UserDTO avoid() throws AppsException {
        User currentUser = securityService.getCurrentUser();
        return UserDTO.builder()
                .userName(currentUser.getName())
                .build();
    }


    @GetMapping("/info")
    public String avoidWithUser() {
        return "Hello user";
    }

}
