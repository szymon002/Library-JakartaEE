package lab.library.book.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;
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
@Entity
@Table(name = "publishers")
public class Publisher implements Serializable {
    @Id
    private UUID id;

    private String name;
    private String country;

    private LocalDate dateOfCreation;

    private int numOfWorkers;

    @OneToMany(mappedBy = "publisher", cascade = CascadeType.REMOVE)
    List<Book> books;
}
