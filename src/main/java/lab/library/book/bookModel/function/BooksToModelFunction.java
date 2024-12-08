package lab.library.book.bookModel.function;

import lab.library.book.bookModel.BooksModel;
import lab.library.book.entity.Book;

import java.util.List;
import java.util.function.Function;

public class BooksToModelFunction implements Function<List<Book>, BooksModel> {
    @Override
    public BooksModel apply(List<Book> books) {
        return BooksModel.builder()
                .books(books.stream()
                        .map(book -> BooksModel.Book.builder()
                                .id(book.getId())
                                .title(book.getTitle())
                                .author(book.getAuthor())
                                .version(book.getVersion())
                                .creationDateTime(book.getCreationDateTime())
                                .lastUpdateTime(book.getLastUpdateTime())
                                .build())
                        .toList()
                ).build();
    }
}
