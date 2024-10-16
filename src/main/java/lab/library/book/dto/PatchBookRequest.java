package lab.library.book.dto;

import lab.library.book.entity.Book;
import lombok.*;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PatchBookRequest {
    private String title;
    private String author;

    private Book.State state;
}
