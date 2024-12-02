package uz.pdp.giftcertificate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.giftcertificate.domain.dto.request.LoginDTO;
import uz.pdp.giftcertificate.domain.dto.response.UserResponse;
import uz.pdp.giftcertificate.domain.views.UserView;
import uz.pdp.giftcertificate.service.UserService;

import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;


    @GetMapping("/{id}")
    public UserView findById(@PathVariable(name = "id") UUID id) {
        return userService.findByIdView(id);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginDTO loginDTO) {
        return userService.login(loginDTO);
    }

    @GetMapping("/me")
    public UserResponse me(@RequestHeader("auth") String authToken) {
        return userService.userInfo(authToken);
    }

}
