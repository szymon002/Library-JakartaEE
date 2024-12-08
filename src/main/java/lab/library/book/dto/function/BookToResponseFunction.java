package lab.library.book.dto.function;

import lab.library.book.dto.GetBookResponse;
import lab.library.book.entity.Book;

import java.util.function.Function;

public class BookToResponseFunction implements Function<Book, GetBookResponse> {
    @Override
    public GetBookResponse apply(Book book) {
        System.out.println("publisher " + book.getPublisher().getName());
        return GetBookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .dateOfPublication(book.getDateOfPublication())
                .state(book.getState())
                .publisher(GetBookResponse.Publisher.builder()
                        .id(book.getPublisher().getId())
                        .name(book.getPublisher().getName())
                        .build())
                .version(book.getVersion())
                .build();
    }
}
