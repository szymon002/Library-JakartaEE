package lab.library.user.repository.api;

import lab.library.user.entity.User;
import lab.library.repository.api.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends Repository<User, UUID> {

    List<User> findAllByBirthDate(LocalDate birthDate);

    Optional<User> findByLogin(String login);

}
