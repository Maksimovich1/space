package by.mmb.controllers.registration;

import by.mmb.controllers.advice.annotation.ExceptionHandlerProcessing;
import by.mmb.dto.UserDTO;
import by.mmb.exception.AppsException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/")
public class NoAuthController {

    @ExceptionHandlerProcessing
    @GetMapping(path = "/logout")
    public String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return "/";
    }

    @ExceptionHandlerProcessing
    @GetMapping("/test")
    public UserDTO avoid() throws AppsException {

        throw new AppsException("wwwwwwwwwwwwwwwwwwww", -10111);
    }

    @ExceptionHandlerProcessing
    @GetMapping("/test1")
    public UserDTO avoidwe() {
        String s = null;
        s.split("");
        return null;
    }
}
