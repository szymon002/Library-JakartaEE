package lab.library.book.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lab.library.book.entity.Book;
import lab.library.book.entity.Publisher;
import lab.library.book.repository.api.BookRepository;
import lab.library.book.repository.api.PublisherRepository;
import lab.library.user.entity.User;
import lab.library.user.repository.api.UserRepository;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class BookService {
    private final BookRepository bookRepository;

    private final UserRepository userRepository;

    private final PublisherRepository publisherRepository;

    @Inject
    public BookService(
            BookRepository bookRepository,
            UserRepository userRepository,
            PublisherRepository publisherRepository
    ) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.publisherRepository = publisherRepository;
    }

    public Optional<Book> find(UUID id) {
        return bookRepository.find(id);
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public List<Book> findAll(User user) {
        return bookRepository.findAllByUser(user);
    }

    public List<Book> findAll(Publisher publisher) {
        return bookRepository.findAllByPublisher(publisher);
    }

    @Transactional
    public void create(Book book) {
        if (bookRepository.find(book.getId()).isPresent()) {
            throw new IllegalArgumentException("Book already exists");
        }

        if (publisherRepository.find(book.getPublisher().getId()).isEmpty()) {
            throw new IllegalArgumentException("Publisher doesn't exist");
        }
        bookRepository.create(book);
        publisherRepository.find(book.getPublisher().getId())
                .ifPresent(publisher -> publisher.getBooks().add(book));
    }

    @Transactional
    public void update(Book book) {
        bookRepository.update(book);
    }

    @Transactional
    public void delete(UUID id) {
        bookRepository.delete(bookRepository.find(id).orElseThrow());
    }

    public Optional<List<Book>> findAllByUser(UUID id) {
        return userRepository.find(id)
                .map(bookRepository::findAllByUser);
    }

    public Optional<List<Book>> findAllByPublisher(UUID id) {
        return publisherRepository.find(id)
                .map(bookRepository::findAllByPublisher);
    }
}
