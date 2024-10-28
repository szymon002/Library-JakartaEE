package lab.library.book.bookModel;

import lab.library.book.entity.Book;
import lab.library.book.entity.Publisher;
import lab.library.user.entity.User;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class BookModel {
    private String title;
    private String author;

    private LocalDate dateOfPublication;

    private Book.State state;

    private String publisher;
}
