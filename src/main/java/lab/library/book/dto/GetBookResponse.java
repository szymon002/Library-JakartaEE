package lab.library.book.dto;

import lab.library.book.entity.Book;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetBookResponse {
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Publisher {
        private UUID id;

        private String name;
    }

    private UUID id;

    private String title;
    private String author;

    private LocalDate dateOfPublication;

    private Book.State state;

    private Publisher publisher;

}
