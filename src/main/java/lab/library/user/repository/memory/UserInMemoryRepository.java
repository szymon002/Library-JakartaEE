package lab.library.user.repository.memory;

import lab.library.datastore.component.DataStore;
import lab.library.user.entity.User;
import lab.library.user.repository.api.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserInMemoryRepository implements UserRepository {
    private final DataStore datastore;

    public UserInMemoryRepository(DataStore datastore) {
        this.datastore = datastore;
    }

    @Override
    public Optional<User> find(UUID id) {
        return datastore.findAllUsers().stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<User> findAll() {
        return datastore.findAllUsers();
    }

    @Override
    public void create(User entity) {
        datastore.createUser(entity);
    }

    @Override
    public void delete(User entity) {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void update(User entity) {
        datastore.updateUser(entity);
    }

    @Override
    public List<User> findAllByBirthDate(LocalDate birthDate) {
        return datastore.findAllUsers().stream()
                .filter(user -> user.getBirthDate().equals(birthDate))
                .toList();
    }
}
