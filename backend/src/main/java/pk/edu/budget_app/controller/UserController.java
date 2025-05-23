package pk.edu.budget_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pk.edu.budget_app.dto.UserDto;
import pk.edu.budget_app.dto.UserLoginDto;
import pk.edu.budget_app.dto.UserMapper;
import pk.edu.budget_app.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserDto> getUsers() {
        return userService.findAll().stream().map(UserMapper::toDto).toList();
    }

    @GetMapping("/{userName}")
    public UserDto getUserByName(@PathVariable String userName) {
        var user = userService.getUserOrThrow(userName);
        return UserMapper.toDto(user);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody UserLoginDto dto) {
        var user = UserMapper.toDto(userService.createUser(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

}
