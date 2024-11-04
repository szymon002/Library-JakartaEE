package lab.library.book.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lab.library.book.entity.Book;
import lab.library.book.entity.Publisher;
import lab.library.book.repository.api.BookRepository;
import lab.library.book.repository.api.PublisherRepository;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
@Log
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
        Optional<Publisher> publisher = publisherRepository.find(id);
        return publisher;
    }

    public List<Publisher> findAll() {
        return publisherRepository.findAll();
    }

    @Transactional
    public void create(Publisher publisher) {
        publisherRepository.create(publisher);
    }

    public void update(Publisher publisher) {
        publisherRepository.update(publisher);
    }

    @Transactional
    public void delete(UUID id) {
        Publisher publisher = publisherRepository.find(id).orElseThrow();
        publisherRepository.delete(publisher);
    }
}
