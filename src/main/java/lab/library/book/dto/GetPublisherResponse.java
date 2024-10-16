package lab.library.book.dto;

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
public class GetPublisherResponse {
    private UUID id;

    private String name;

    private LocalDate dateOfCreation;
}
