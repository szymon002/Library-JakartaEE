package lab.library.book.bookModel;

import lab.library.book.entity.Book;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class BookEditModel {
    private String title;
    private String author;

    private Book.State state;
}
