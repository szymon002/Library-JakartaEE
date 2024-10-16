package lab.library.book.dto.function;

import lab.library.book.dto.GetBooksResponse;
import lab.library.book.entity.Book;

import java.util.List;
import java.util.function.Function;

public class BooksToResponseFunction implements Function<List<Book>, GetBooksResponse> {
    @Override
    public GetBooksResponse apply(List<Book> books) {
        return GetBooksResponse.builder()
                .books(books.stream()
                        .map(book -> GetBooksResponse.Book.builder()
                                .id(book.getId())
                                .title(book.getTitle())
                                .build())
                        .toList())
                .build();
    }
}
