package lab.library.book.publisherModel.function;

import lab.library.book.entity.Publisher;
import lab.library.book.publisherModel.PublishersModel;

import java.util.List;
import java.util.function.Function;

public class PublishersToModelFunction implements Function<List<Publisher>, PublishersModel> {
    @Override
    public PublishersModel apply(List<Publisher> publishers) {
        return PublishersModel.builder()
                .publishers(publishers.stream()
                        .map(publisher -> PublishersModel.Publisher.builder()
                                .id(publisher.getId())
                                .name(publisher.getName())
                                .build())
                        .toList())
                .build();
    }
}
