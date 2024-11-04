package lab.library.book.repository.persistence;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lab.library.book.entity.Publisher;
import lab.library.book.repository.api.PublisherRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class PublisherPersistenceRepository implements PublisherRepository {
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Publisher> find(UUID id) {
        return Optional.ofNullable(em.find(Publisher.class, id));
    }

    @Override
    public List<Publisher> findAll() {
        return em.createQuery("SELECT p FROM Publisher p", Publisher.class).getResultList();
    }

    @Override
    public void create(Publisher entity) {
        em.persist(entity);
    }

    @Override
    public void delete(Publisher entity) {
        em.refresh(entity);
        em.remove(em.find(Publisher.class, entity.getId()));
    }

    @Override
    public void update(Publisher entity) {
        em.merge(entity);
    }
}
