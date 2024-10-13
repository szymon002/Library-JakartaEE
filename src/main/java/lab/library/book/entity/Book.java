package lab.library.book.entity;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class Book implements Serializable {
    private UUID id;

    private String title;
    private String author;

    private LocalDate dateOfPublication;

    private State state;

    private Publisher publisher;

    enum State {
        NEW,
        Used
    }
}
