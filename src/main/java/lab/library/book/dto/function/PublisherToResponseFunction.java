package lab.library.book.dto.function;

import lab.library.book.dto.GetPublisherResponse;
import lab.library.book.entity.Publisher;

import java.util.function.Function;

public class PublisherToResponseFunction implements Function<Publisher, GetPublisherResponse> {
    @Override
    public GetPublisherResponse apply(Publisher publisher) {
        return GetPublisherResponse.builder()
                .id(publisher.getId())
                .name(publisher.getName())
                .dateOfCreation(publisher.getDateOfCreation())
                .build();
    }
}
