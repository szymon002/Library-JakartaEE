package lab.library.user.service;

import lab.library.user.entity.User;
import lab.library.user.repository.api.UserRepository;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserService {

    private final UserRepository userRepository;
    private final Path path;

    public UserService(UserRepository userRepository, Path path) {
        this.userRepository = userRepository;
        this.path = path;
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

    public void updateAvatar(UUID id, InputStream avatar, String filename) {
        userRepository.find(id).ifPresent(user -> {
            try {
                Path photoPath = path.resolve(filename);
                Files.copy(avatar, photoPath, StandardCopyOption.REPLACE_EXISTING);
                user.setAvatar(photoPath.toString());
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
