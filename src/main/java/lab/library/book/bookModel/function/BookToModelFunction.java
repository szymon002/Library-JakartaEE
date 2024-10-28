package lab.library.book.bookModel.function;

import lab.library.book.bookModel.BookModel;
import lab.library.book.entity.Book;

import java.io.Serializable;
import java.util.function.Function;

public class BookToModelFunction implements Function<Book, BookModel>, Serializable {
    @Override
    public BookModel apply(Book book) {
        return BookModel.builder()
                .title(book.getTitle())
                .author(book.getAuthor())
                .dateOfPublication(book.getDateOfPublication())
                .state(book.getState())
                .publisher(book.getPublisher().getName())
                .build();
    }
}
