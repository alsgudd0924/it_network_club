package com.it_network.it_network.auth;

import com.it_network.it_network.user.AddUserRequestDto;
import com.it_network.it_network.user.User;
import com.it_network.it_network.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/my/test")
    public String myTest() {
        return "JWT 인증 성공!";
    }


    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody AddUserRequestDto request) {
        userService.save(request);
        return ResponseEntity.ok("회원가입 성공");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String pw = request.get("password");


        User user = userService.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));


        if (!bCryptPasswordEncoder.matches(pw, user.getUser_pw())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }


        String token = jwtTokenProvider.generateToken(user.getEmail());


        return ResponseEntity.ok(Map.of("token", token));
    }
}