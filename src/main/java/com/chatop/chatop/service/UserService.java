package com.chatop.chatop.service;


import com.chatop.chatop.model.User;
import com.chatop.chatop.repository.UserRepository;
import org.springframework.stereotype.Service;
import com.chatop.chatop.dtos.UserDto;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<UserDto> getUser(Integer id) {
        return userRepository.findById(id)
            .map(user -> {
                UserDto dto = new UserDto();
                dto.setId(user.getId());
                dto.setName(user.getFullName());
                dto.setEmail(user.getEmail());
                dto.setCreated_at(user.getCreatedAt().toString());
                dto.setUpdated_at(user.getUpdatedAt().toString());
                return dto;
            });
    }

    public List<User> allUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
}