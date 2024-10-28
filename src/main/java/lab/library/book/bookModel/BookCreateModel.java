package lab.library.book.bookModel;

import lab.library.book.entity.Book;
import lab.library.book.publisherModel.PublisherChooseModel;
import lab.library.book.publisherModel.PublisherModel;
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
public class BookCreateModel {
    private UUID id;
    private String title;
    private String author;

    private Book.State state;

    private LocalDate dateOfPublication;

    private PublisherChooseModel publisher;
}
