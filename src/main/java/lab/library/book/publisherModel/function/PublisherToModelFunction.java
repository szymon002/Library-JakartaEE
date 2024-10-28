package lab.library.book.publisherModel.function;

import lab.library.book.entity.Publisher;
import lab.library.book.publisherModel.PublisherModel;

import java.io.Serializable;
import java.util.function.Function;

public class PublisherToModelFunction implements Function<Publisher, PublisherModel>, Serializable {
    @Override
    public PublisherModel apply(Publisher publisher) {
        return PublisherModel.builder()
                .name(publisher.getName())
                .country(publisher.getCountry())
                .dateOfCreation(publisher.getDateOfCreation())
                .numOfWorkers(publisher.getNumOfWorkers())
                .build();
    }
}
