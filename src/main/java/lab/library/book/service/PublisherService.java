package lab.library.book.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lab.library.book.entity.Book;
import lab.library.book.entity.Publisher;
import lab.library.book.repository.api.BookRepository;
import lab.library.book.repository.api.PublisherRepository;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class PublisherService {
    private final PublisherRepository publisherRepository;
    private final BookService bookService;
    private final BookRepository bookRepository;

    @Inject
    public PublisherService(PublisherRepository publisherRepository,
                            BookService bookService,
                            BookRepository bookRepository
    ) {
        this.publisherRepository = publisherRepository;
        this.bookService = bookService;
        this.bookRepository = bookRepository;
    }

    public Optional<Publisher> find(UUID id) {
        return publisherRepository.find(id);
    }

    public List<Publisher> findAll() {
        return publisherRepository.findAll();
    }

    public void create(Publisher publisher) {
        publisherRepository.create(publisher);
    }

    public void update(Publisher publisher) {
        publisherRepository.update(publisher);
    }

    public void delete(UUID id) {
        Publisher publisher = publisherRepository.find(id).orElseThrow();

        List<Book> books = bookRepository.findAllByPublisher(publisher);

        for (Book book : books) {
            bookService.delete(book.getId());
        }

        publisherRepository.delete(publisher);
    }
}
