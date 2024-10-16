package lab.library.book.repository.memory;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import lab.library.book.entity.Book;
import lab.library.book.entity.Publisher;
import lab.library.book.repository.api.BookRepository;
import lab.library.datastore.component.DataStore;
import lab.library.user.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestScoped
public class BookInMemoryRepository implements BookRepository {
    private final DataStore store;

    @Inject
    public BookInMemoryRepository(
            DataStore store
    ) {
        this.store = store;
    }
    @Override
    public List<Book> findAllByUser(User user) {
        return store.findAllBooks().stream()
                .filter(book -> user.equals(book.getUser()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findAllByPublisher(Publisher publisher) {
        return store.findAllBooks().stream()
                .filter(book -> publisher.equals(book.getPublisher()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Book> find(UUID id) {
        return store.findAllBooks().stream()
                .filter(book -> book.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Book> findAll() {
        return store.findAllBooks();
    }

    @Override
    public void create(Book entity) {
        store.createBook(entity);
    }

    @Override
    public void delete(Book entity) {
        store.deleteBook(entity.getId());
    }

    @Override
    public void update(Book entity) {
        store.updateBook(entity);
    }
}
