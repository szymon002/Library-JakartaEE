package lab.library.user.service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.security.PermitAll;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import lab.library.user.entity.User;
import lab.library.user.repository.api.UserRepository;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class UserService {

    private final UserRepository userRepository;
    private Path path;

    private final Pbkdf2PasswordHash passwordHash;

    @Inject
    public UserService(UserRepository userRepository,
                       @SuppressWarnings("CdiInjectionPointsInspection") Pbkdf2PasswordHash passwordHash
    ) {
        this.userRepository = userRepository;
        this.passwordHash = passwordHash;
    }

    @PostConstruct
    private void initPath() {
        this.path = Paths.get("/Users/szymonmalecki/Downloads/Avatars/");
    }

    public Optional<User> find(UUID id) {
        return userRepository.find(id);
    }

    public List<User> findAllByBirthDate(LocalDate birthDate) {
        return userRepository.findAllByBirthDate(birthDate);
    }

    @PermitAll
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @PermitAll
    public void create(User user) {
        user.setPassword(passwordHash.generate(user.getPassword().toCharArray()));
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
