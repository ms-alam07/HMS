package com.hms.Service;

import com.hms.Entity.User;
import com.hms.Payload.LoginDto;
import com.hms.Payload.UserDto;
import com.hms.Repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JWTService jwtService;

    public UserService(UserRepository userRepository, JWTService jwtService) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    // =============== Mapper Methods for Converting DTOs to Entity =======================
    User mapToEntity(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setMobile(userDto.getMobile());
        user.setPassword(BCrypt.hashpw(userDto.getPassword(), BCrypt.gensalt(10)));
        return user;
    }

    UserDto mapToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setMobile(user.getMobile());
        return userDto;
    }

    // =============== Methods for User Operations ============================
    public UserDto addUser(UserDto userDto) {
        validateUser(userDto);
        User user = mapToEntity(userDto);
        user.setRole("ROLE_USER");
        User savedUser = userRepository.save(user);
        return mapToDto(savedUser);
    }

    public UserDto addPropertyOwner(UserDto userDto) {
        validateUser(userDto);
        User user = mapToEntity(userDto);
        user.setRole("ROLE_OWNER");
        User savedUser = userRepository.save(user);
        return mapToDto(savedUser);
    }

    public UserDto addBlogManagerAccount(UserDto userDto) {
        validateUser(userDto);
        User user = mapToEntity(userDto);
        user.setRole("ROLE_BLOG-MANAGER");
        User savedUser = userRepository.save(user);
        return mapToDto(savedUser);
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

    public String verifyLogin(LoginDto loginDto) {
        Optional<User> opUser = userRepository.findByUsername(loginDto.getUsername());
        if (opUser.isPresent()) {
            User user = opUser.get();
            if (BCrypt.checkpw(loginDto.getPassword(), user.getPassword())) {
                 String token =jwtService.generateToken(user.getUsername());
                 return token;
            }
        }
        return null;
    }

    // =============== CRUD Methods for User ================================
    public UserDto getUserById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return mapToDto(user.get());
        }
        throw new IllegalArgumentException("User not found");
    }

    public UserDto updateUser(Long userId, UserDto userDto) {
        Optional<User> opUser = userRepository.findById(userId);
        if (opUser.isPresent()) {
            User existingUser = opUser.get();
            existingUser.setName(userDto.getName());
            existingUser.setUsername(userDto.getUsername());
            existingUser.setEmail(userDto.getEmail());
            existingUser.setMobile(userDto.getMobile());

            // Optionally, you can re-hash the password if it's being updated
            if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
                existingUser.setPassword(BCrypt.hashpw(userDto.getPassword(), BCrypt.gensalt(10)));
            }

            User updatedUser = userRepository.save(existingUser);
            return mapToDto(updatedUser);
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }

    public void deleteUser(Long userId) {
        Optional<User> opUser = userRepository.findById(userId);
        if (opUser.isPresent()) {
            userRepository.delete(opUser.get());
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }

    public Iterable<UserDto> getAllUsers() {
        Iterable<User> users = userRepository.findAll();
        return mapToDtoIterable(users);
    }

    private Iterable<UserDto> mapToDtoIterable(Iterable<User> users) {
        return () -> new Iterator<UserDto>() {
            private final Iterator<User> userIterator = users.iterator();

            @Override
            public boolean hasNext() {
                return userIterator.hasNext();
            }

            @Override
            public UserDto next() {
                return mapToDto(userIterator.next());
            }
        };
    }

}
