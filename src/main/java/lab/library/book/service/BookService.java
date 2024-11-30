package lab.library.book.service;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotFoundException;
import lab.library.book.entity.Book;
import lab.library.book.entity.Publisher;
import lab.library.book.repository.api.BookRepository;
import lab.library.book.repository.api.PublisherRepository;
import lab.library.user.entity.User;
import lab.library.user.entity.UserRoles;
import lab.library.user.repository.api.UserRepository;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class BookService {
    private final BookRepository bookRepository;

    private final UserRepository userRepository;

    private final PublisherRepository publisherRepository;

    private final SecurityContext securityContext;

    @Inject
    public BookService(
            BookRepository bookRepository,
            UserRepository userRepository,
            PublisherRepository publisherRepository,
            @SuppressWarnings("CdiInjectionPointsInspection") SecurityContext securityContext) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.publisherRepository = publisherRepository;
        this.securityContext = securityContext;
    }

    public Optional<Book> find(UUID id) {
        Book book = bookRepository.find(id).orElseThrow();

        if (securityContext.isCallerInRole(UserRoles.ADMIN)) {
            return Optional.of(book);
        }

        User user = userRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                .orElseThrow(NotFoundException::new);

        if (user.getId() == book.getUser().getId()) {
            return Optional.of(book);
        } else {
            try {
                String contextPath = FacesContext.getCurrentInstance()
                        .getExternalContext()
                        .getRequestContextPath();
                FacesContext.getCurrentInstance()
                        .getExternalContext()
                        .redirect(contextPath + "/publisher/publisher_list.xhtml");
            } catch (IOException e) {
                throw new RuntimeException("Redirection failed", e);
            }
        }
        return Optional.empty();
    }

    @RolesAllowed(UserRoles.USER)
    public List<Book> findAll() {
        if (securityContext.isCallerInRole(UserRoles.ADMIN)) {
            return bookRepository.findAll();
        }

        User user = userRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                .orElseThrow(NotFoundException::new);

        return findAll(user);
    }

    @RolesAllowed(UserRoles.USER)
    public List<Book> findAll(User user) {
        return bookRepository.findAllByUser(user);
    }

    public List<Book> findAll(Publisher publisher) {
        if (securityContext.isCallerInRole(UserRoles.ADMIN)) {
            return bookRepository.findAllByPublisher(publisher);
        }

        User user = userRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                .orElseThrow(NotFoundException::new);

        return bookRepository.findAllByPublisherAndUser(publisher, user);
    }

    @RolesAllowed(UserRoles.USER)
    public void create(Book book) {
        if (bookRepository.find(book.getId()).isPresent()) {
            throw new IllegalArgumentException("Book already exists");
        }

        if (publisherRepository.find(book.getPublisher().getId()).isEmpty()) {
            throw new IllegalArgumentException("Publisher doesn't exist");
        }

        User user = userRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                .orElseThrow(NotFoundException::new);

        book.setUser(user);
        bookRepository.create(book);
        publisherRepository.find(book.getPublisher().getId())
                .ifPresent(publisher -> publisher.getBooks().add(book));
        userRepository.find(book.getUser().getId())
                .ifPresent(user1 -> user1.getBooks().add(book));
    }

    public void createForInitializer(Book book) {
        bookRepository.create(book);
    }

    @RolesAllowed(UserRoles.USER)
    public void update(Book book) {
        if (securityContext.isCallerInRole(UserRoles.ADMIN)) {
            bookRepository.update(book);
            return;
        }
        User user = userRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                .orElseThrow(NotFoundException::new);

        if (user.getId() == book.getUser().getId()) {
            bookRepository.update(book);
        } else {
            throw new ForbiddenException();
        }
    }

    @RolesAllowed(UserRoles.USER)
    public void delete(UUID id) {
        Book book = bookRepository.find(id).orElseThrow();
        if (securityContext.isCallerInRole(UserRoles.ADMIN)) {
            bookRepository.delete(book);
            return;
        }
        User user = userRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                .orElseThrow(NotFoundException::new);
        if (user.getId() == book.getUser().getId()) {
            bookRepository.delete(book);
        } else {
            throw new ForbiddenException();
        }
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
