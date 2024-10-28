package lab.library.book.publisherModel;

import lab.library.book.dto.GetPublishersResponse;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PublishersModel implements Serializable {
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Publisher {
        private UUID id;

        private String name;
    }

    @Singular
    List<Publisher> publishers;
}
