package lab.library.user.repository.persistence;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lab.library.user.entity.User;
import lab.library.user.repository.api.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class UserPersistenceRepository implements UserRepository {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<User> find(UUID id) {
        return Optional.ofNullable(em.find(User.class, id));
    }

    @Override
    public List<User> findAll() {
        return em.createQuery("SELECT u FROM User u ", User.class).getResultList();
    }

    @Override
    public void create(User entity) {
        em.persist(entity);
    }

    @Override
    public void delete(User entity) {
        em.remove(em.find(User.class, entity));
    }

    @Override
    public void update(User entity) {
        em.merge(entity);
    }

    @Override
    public List<User> findAllByBirthDate(LocalDate birthDate) {
        return em.createQuery("SELECT u FROM User u WHERE u.birthDate = :birthDate", User.class)
                .setParameter("birthDate", birthDate)
                .getResultList();
    }
}
