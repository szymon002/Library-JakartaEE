package lab.library.book.entity;

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
public class Publisher implements Serializable {
    private UUID id;

    private String name;
    private String country;

    private LocalDate dateOfCreation;

    private int numOfWorkers;

    List<Book> books;
}
