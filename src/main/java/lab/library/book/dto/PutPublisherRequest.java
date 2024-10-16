package lab.library.book.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PutPublisherRequest {
    private String name;
    private String country;

    private LocalDate dateOfCreation;

    private int numOfWorkers;
}
