package com.hms.Service;

import com.hms.Entity.User;
import com.hms.Payload.LoginDto;
import com.hms.Payload.UserDto;
import com.hms.Repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ===============Mapper Methods for Converting DTOs to Entity.=======================

    User mapToEntity(UserDto userDto){
        User user = new User();
        user.setName(userDto.getName());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setMobile(userDto.getMobile());
        user.setPassword(BCrypt.hashpw(userDto.getPassword(),BCrypt.gensalt(10))); // Use BCryptPasswordEncoder
        return user;
    }
    UserDto mapToDto(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setMobile(user.getMobile());
        return userDto;
    }
    // ===============Methods for User Operations===========================

    public UserDto addUser(UserDto userDto) {
        validateUser(userDto);
        User user = mapToEntity(userDto);
        User us = userRepository.save(user);
        UserDto userDto1 = mapToDto(us);
        return userDto1;
    }
    private void validateUser(UserDto userDto) {
        Optional<User> opUsername = userRepository.findByUsername(userDto.getUsername());
        if (opUsername.isPresent()) {
            throw new IllegalArgumentException("User with this Username already exists");
        }
        Optional<User> opEmail = userRepository.findByEmail(userDto.getEmail());
        if (opEmail.isPresent()) {
            throw new IllegalArgumentException("User with this Email already exists");
        }
        Optional<User> opMobile = userRepository.findByMobile(userDto.getMobile());
        if (opMobile.isPresent()) {
            throw new IllegalArgumentException("User with this Mobile already exists");
        }
    }


    public boolean verifyLogin(LoginDto loginDto) {
        Optional<User> opUser = userRepository.findByUsername(loginDto.getUsername());
        if(opUser.isPresent()) {
            User user = opUser.get();
            if(BCrypt.checkpw(loginDto.getPassword(),user.getPassword())){
                return true;
            }
        }
        return false;
    }
}
