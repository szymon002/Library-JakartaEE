package lab.library.book.entity;

import jakarta.persistence.*;
import lab.library.user.entity.User;
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
@Entity
@Table(name = "books")
public class Book implements Serializable {
    @Id
    private UUID id;

    private String title;
    private String author;

    private LocalDate dateOfPublication;

    @Enumerated(EnumType.ORDINAL)
    private State state;

    @ManyToOne
    @JoinColumn(name = "publisher")
    private Publisher publisher;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public enum State {
        NEW,
        USED
    }

    @Version
    private int version;
}
