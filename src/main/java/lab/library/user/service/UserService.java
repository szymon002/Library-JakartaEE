package lab.library.user.service;

import lab.library.user.entity.User;
import lab.library.user.repository.api.UserRepository;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> find(UUID id) {
        return userRepository.find(id);
    }

    public List<User> findAllByBirthDate(LocalDate birthDate) {
        return userRepository.findAllByBirthDate(birthDate);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void create(User user) {
        userRepository.create(user);
    }

    public void updateAvatar(UUID id, InputStream avatar) {
        userRepository.find(id).ifPresent(user -> {
            try {
                user.setAvatar(avatar.readAllBytes());
                userRepository.update(user);
            } catch (IOException ex) {
                throw new IllegalStateException(ex);
            }
        });
    }

    public void deleteAvatar(UUID id) {
        userRepository.find(id).ifPresent(user -> {
            user.setAvatar(null);
            userRepository.update(user);
        });
    }
}
