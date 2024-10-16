package lab.library.book.dto.function;

import lab.library.book.dto.PutBookRequest;
import lab.library.book.entity.Book;
import lab.library.book.entity.Publisher;

import java.time.LocalDate;
import java.util.UUID;
import java.util.function.BiFunction;

public class RequestToBookFunction implements BiFunction<UUID, PutBookRequest, Book> {
    @Override
    public Book apply(UUID id, PutBookRequest putBookRequest) {
        return Book.builder()
                .id(id)
                .title(putBookRequest.getTitle())
                .author(putBookRequest.getAuthor())
                .state(putBookRequest.getState())
                .dateOfPublication(LocalDate.now())
                .publisher(Publisher.builder()
                        .id(putBookRequest.getPublisher())
                        .build())
                .build();
    }
}
