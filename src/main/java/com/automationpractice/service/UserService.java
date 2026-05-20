package com.automationpractice.service;

import com.automationpractice.model.AppUser;
import com.automationpractice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<AppUser> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<AppUser> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public AppUser createUser(AppUser user) {
        return userRepository.save(user);
    }

    public AppUser updateUser(Long id, AppUser updated) {
        return userRepository.findById(id).map(user -> {
            user.setFirstName(updated.getFirstName());
            user.setLastName(updated.getLastName());
            user.setEmail(updated.getEmail());
            user.setPhoneNumber(updated.getPhoneNumber());
            user.setDateOfBirth(updated.getDateOfBirth());
            user.setGender(updated.getGender());
            user.setCountry(updated.getCountry());
            user.setCity(updated.getCity());
            user.setAddress(updated.getAddress());
            user.setBio(updated.getBio());
            user.setActive(updated.isActive());
            user.setNewsletterSubscribed(updated.isNewsletterSubscribed());
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found: " + id));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public long count() {
        return userRepository.count();
    }
}
