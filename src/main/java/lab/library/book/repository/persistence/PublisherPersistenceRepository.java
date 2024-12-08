package lab.library.book.repository.persistence;

import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lab.library.book.entity.Publisher;
import lab.library.book.repository.api.PublisherRepository;
import lab.library.user.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
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
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Publisher> query = cb.createQuery(Publisher.class);
        Root<Publisher> root = query.from(Publisher.class);
        query.select(root);
        return em.createQuery(query).getResultList();
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
