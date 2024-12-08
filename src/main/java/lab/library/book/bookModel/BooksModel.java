package lab.library.book.bookModel;

import lab.library.book.dto.GetBooksResponse;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class BooksModel implements Serializable {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Book {
        private UUID id;

        private String title;

        private String author;

        private int version;

        private LocalDateTime creationDateTime;

        private LocalDateTime lastUpdateTime;
    }

    @Singular
    List<Book> books;
}
