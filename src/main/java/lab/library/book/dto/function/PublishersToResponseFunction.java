package lab.library.book.dto.function;

import lab.library.book.dto.GetPublishersResponse;
import lab.library.book.entity.Publisher;

import java.util.List;
import java.util.function.Function;

public class PublishersToResponseFunction implements Function<List<Publisher>, GetPublishersResponse> {
    @Override
    public GetPublishersResponse apply(List<Publisher> publishers) {
        return GetPublishersResponse.builder()
                .publishers(publishers.stream()
                        .map(publisher -> GetPublishersResponse.Publisher.builder()
                                .id(publisher.getId())
                                .name(publisher.getName())
                                .build())
                        .toList())
                .build();
    }
}
