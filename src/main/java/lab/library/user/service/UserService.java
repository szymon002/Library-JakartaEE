package lab.library.user.service;

import lab.library.user.entity.User;
import lab.library.user.repository.api.UserRepository;

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
}
