package lab.library.datastore.component;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lab.library.book.entity.Book;
import lab.library.book.entity.Publisher;
import lab.library.serialization.component.CloningUtility;
import lab.library.user.entity.User;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Log
@ApplicationScoped
@NoArgsConstructor(force = true)
public class DataStore {

    private final Set<User> users = new HashSet<>();

    private final Set<Book> books = new HashSet<>();

    private final Set<Publisher> publishers = new HashSet<>();

    private final CloningUtility cloningUtility;

    @Inject
    public DataStore(CloningUtility cloningUtility) {
        this.cloningUtility = cloningUtility;
    }

    public synchronized List<User> findAllUsers() {
        return users.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized List<Book> findAllBooks() {
        return books.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized List<Publisher> findAllPublishers() {
        return publishers.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized void createUser(User value) throws IllegalArgumentException {
        if (users.stream().anyMatch(user -> user.getId().equals(value.getId()))) {
            throw new IllegalArgumentException("The user id \"%s\" is not unique".formatted(value.getId()));
        }
        users.add(cloningUtility.clone(value));
    }

    public synchronized void updateUser(User value) throws IllegalArgumentException {
        if (users.removeIf(user -> user.getId().equals(value.getId()))) {
            users.add(cloningUtility.clone(value));
        } else {
            throw new IllegalArgumentException("The user with id \"%s\" does not exist".formatted(value.getId()));
        }
    }

    public synchronized void createBook(Book value) throws IllegalArgumentException {
        if (books.stream().anyMatch(book -> book.getId().equals(value.getId()))) {
            throw new IllegalArgumentException("The Book id \"%s\" is not unique".formatted(value.getId()));
        }
        Book book = cloneWithRelationships(value);
        books.add(book);
    }

    public synchronized void updateBook(Book value) throws IllegalArgumentException {
        if (books.removeIf(book -> book.getId().equals(value.getId()))) {
            books.add(cloningUtility.clone(value));
        } else {
            throw new IllegalArgumentException("The Book with id \"%s\" does not exist".formatted(value.getId()));
        }
    }

    public synchronized void createPublisher(Publisher value) throws IllegalArgumentException {
        if (publishers.stream().anyMatch(publisher -> publisher.getId().equals(value.getId()))) {
            throw new IllegalArgumentException("The Publisher id \"%s\" is not unique".formatted(value.getId()));
        }

        publishers.add(cloningUtility.clone(value));
    }

    public synchronized void updatePublisher(Publisher value) throws IllegalArgumentException {
        if (publishers.removeIf(publisher -> publisher.getId().equals(value.getId()))) {
            publishers.add(cloningUtility.clone(value));
        } else {
            throw new IllegalArgumentException("The Publisher with id \"%s\" does not exist".formatted(value.getId()));
        }
    }

    public synchronized void deleteBook(UUID id) throws IllegalArgumentException {
        if (!books.removeIf(book -> book.getId().equals(id))) {
            throw new IllegalArgumentException("The book with id \"%s\" does not exist".formatted(id));
        }
    }

    public synchronized void deletePublisher(UUID id) throws IllegalArgumentException {
        if (!publishers.removeIf(publisher -> publisher.getId().equals(id))) {
            throw new IllegalArgumentException("The publisher with id \"%s\" does not exist".formatted(id));
        }
    }

    private Book cloneWithRelationships(Book value) {
        Book entity = cloningUtility.clone(value);

        if (entity.getUser() != null) {
            entity.setUser(users.stream()
                    .filter(user -> user.getId().equals(value.getUser().getId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No user with id \"%s\".".formatted(value.getUser().getId()))));
        }

        if (entity.getPublisher() != null) {
            entity.setPublisher(publishers.stream()
                    .filter(publisher -> publisher.getId().equals(value.getPublisher().getId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No publisher with id \"%s\".".formatted(value.getPublisher().getId()))));
        }

        return entity;
    }
}
