package lab.library.book.bookModel.function;

import lab.library.book.bookModel.BookEditModel;
import lab.library.book.entity.Book;

import java.io.Serializable;
import java.util.function.BiFunction;

public class UpdateBookWithModelFunction implements BiFunction<Book, BookEditModel, Book>, Serializable {
    @Override
    public Book apply(Book book, BookEditModel request) {
        return Book.builder()
                .id(book.getId())
                .title(request.getTitle())
                .author(request.getAuthor())
                .state(request.getState())
                .dateOfPublication(book.getDateOfPublication())
                .publisher(book.getPublisher())
                .user(book.getUser())
                .version(request.getVersion())
                .creationDateTime(book.getCreationDateTime())
                .build();
    }
}
