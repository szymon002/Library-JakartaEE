package lab.library.book.repository.api;

import lab.library.book.entity.Book;
import lab.library.book.entity.Publisher;
import lab.library.repository.api.Repository;
import lab.library.user.entity.User;

import java.util.List;
import java.util.UUID;

public interface BookRepository extends Repository<Book, UUID> {

    List<Book> findAllByUser(User user);

    List<Book> findAllByPublisher(Publisher publisher);

    List<Book> findAllByPublisherAndUser(Publisher publisher, User user);
}
