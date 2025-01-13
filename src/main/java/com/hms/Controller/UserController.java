package com.hms.Controller;

import com.hms.Payload.LoginDto;
import com.hms.Payload.UserDto;
import com.hms.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/hms/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto) {
        try {
            UserDto dto = userService.addUser(userDto);
            return new ResponseEntity<>(dto, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDto loginDto) {
        String token = userService.verifyLogin(loginDto);
        if(token != null){
            return new ResponseEntity<>(token,HttpStatus.OK);
        }
        return new ResponseEntity<>("Invalid Credentials",HttpStatus.BAD_REQUEST);
    }
}
