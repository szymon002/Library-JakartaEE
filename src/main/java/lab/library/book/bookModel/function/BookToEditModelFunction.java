package lab.library.book.bookModel.function;

import lab.library.book.bookModel.BookEditModel;
import lab.library.book.entity.Book;

import java.io.Serializable;
import java.util.function.Function;

public class BookToEditModelFunction implements Function<Book, BookEditModel>, Serializable {
    @Override
    public BookEditModel apply(Book book) {
        return BookEditModel.builder()
                .title(book.getTitle())
                .author(book.getAuthor())
                .state(book.getState())
                .build();
    }
}
