package lab.library.book.publisherModel.function;

import lab.library.book.entity.Publisher;
import lab.library.book.publisherModel.PublisherChooseModel;

import java.io.Serializable;
import java.util.function.Function;

public class PublisherToModelChooseFunction implements Function<Publisher, PublisherChooseModel>, Serializable {
    @Override
    public PublisherChooseModel apply(Publisher publisher) {
        return PublisherChooseModel.builder()
                .id(publisher.getId())
                .name(publisher.getName())
                .build();
    }
}
