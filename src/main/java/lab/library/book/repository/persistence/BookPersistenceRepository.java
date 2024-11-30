package lab.library.book.repository.persistence;

import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lab.library.book.entity.Book;
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
        return em.createQuery("SELECT b FROM Book b WHERE b.user = :user", Book.class)
                .setParameter("user", user)
                .getResultList();
    }

    @Override
    public List<Book> findAllByPublisher(Publisher publisher) {
        return em.createQuery("SELECT b FROM Book b WHERE b.publisher = :publisher", Book.class)
                .setParameter("publisher", publisher)
                .getResultList();
    }

    @Override
    public List<Book> findAllByPublisherAndUser(Publisher publisher, User user) {
        return em.createQuery("SELECT b FROM Book b WHERE b.publisher = :publisher AND b.user = :user", Book.class)
                .setParameter("publisher", publisher)
                .setParameter("user", user)
                .getResultList();
    }

    @Override
    public Optional<Book> find(UUID id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public List<Book> findAll() {
        return em.createQuery("SELECT b FROM Book b", Book.class).getResultList();
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
