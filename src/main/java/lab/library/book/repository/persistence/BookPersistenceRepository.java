package lab.library.book.repository.persistence;

import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lab.library.book.entity.Book;
import lab.library.book.entity.Book_;
import lab.library.book.entity.Publisher;
import lab.library.book.repository.api.BookRepository;
import lab.library.user.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
public class BookPersistenceRepository implements BookRepository {
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Book> findAllByUser(User user) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Book> query = cb.createQuery(Book.class);
        Root<Book> root = query.from(Book.class);
        query.select(root)
                .where(cb.equal(root.get(Book_.user), user));
        return em.createQuery(query).getResultList();
    }

    @Override
    public List<Book> findAllByPublisher(Publisher publisher) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Book> query = cb.createQuery(Book.class);
        Root<Book> root = query.from(Book.class);
        query.select(root)
                .where(cb.equal(root.get(Book_.publisher), publisher));
        return em.createQuery(query).getResultList();
    }

    @Override
    public List<Book> findAllByPublisherAndUser(Publisher publisher, User user) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Book> query = cb.createQuery(Book.class);
        Root<Book> root = query.from(Book.class);
        query.select(root)
                .where(cb.and(
                        cb.equal(root.get(Book_.publisher), publisher),
                        cb.equal(root.get(Book_.user), user)
                ));
        return em.createQuery(query).getResultList();
    }

    @Override
    public Optional<Book> find(UUID id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public List<Book> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Book> query = cb.createQuery(Book.class);
        Root<Book> root = query.from(Book.class);
        query.select(root);
        return em.createQuery(query).getResultList();
    }

    @Override
    public void create(Book entity) {
        em.persist(entity);
    }

    @Override
    public void delete(Book entity) {
        em.remove(em.find(Book.class, entity.getId()));
    }

    @Override
    public void update(Book entity) {
        em.merge(entity);
    }
}
