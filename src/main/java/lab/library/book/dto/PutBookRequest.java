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
public class PutBookRequest {
    private String title;
    private String author;

    private Book.State state;

    private UUID publisher;

    private int version;
}
