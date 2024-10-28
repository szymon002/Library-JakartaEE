package lab.library.book.publisherModel;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PublisherModel {
    private String name;
    private String country;

    private LocalDate dateOfCreation;

    private int numOfWorkers;
}
