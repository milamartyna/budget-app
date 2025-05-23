package pk.edu.budget_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pk.edu.budget_app.dto.UserLoginDto;
import pk.edu.budget_app.security.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDto dto) {
        String token = authService.login(dto);
        return ResponseEntity.ok(token);
    }
}

