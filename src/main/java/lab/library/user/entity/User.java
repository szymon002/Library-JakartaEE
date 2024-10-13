package lab.library.user.entity;

import lab.library.book.entity.Book;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class User implements Serializable {
    private UUID id;

    private String login;
    private String firstName;
    private String lastName;
    private String password;
    private String email;

    private LocalDate birthDate;

    private List<Book> books;
}
