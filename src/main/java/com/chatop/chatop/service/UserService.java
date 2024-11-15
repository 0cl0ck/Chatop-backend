package com.chatop.chatop.service;


import com.chatop.chatop.model.User;
import com.chatop.chatop.repository.UserRepository;
import org.springframework.stereotype.Service;
import com.chatop.chatop.dtos.UserDto;


import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> allUsers() {
        List<User> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }

    public UserDto getUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
                
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getFullName());
        dto.setEmail(user.getEmail());
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        dto.setCreated_at(dateFormat.format(user.getCreatedAt()));
        dto.setUpdated_at(dateFormat.format(user.getUpdatedAt()));
        
        return dto;
    }
}