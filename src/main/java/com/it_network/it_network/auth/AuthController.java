package com.it_network.it_network.auth;

import com.it_network.it_network.user.AddUserRequestDto;
import com.it_network.it_network.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    @PostMapping("/user")
    public String signup(@RequestBody AddUserRequestDto request) {
        userService.save(request);
        return "redirect:/login";
    }

}
