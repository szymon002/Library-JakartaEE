package lab.library.book.dto.function;

import lab.library.book.dto.PatchPublisherRequest;
import lab.library.book.entity.Publisher;

import java.util.function.BiFunction;

public class UpdatePublisherWithRequestFunction implements BiFunction<Publisher, PatchPublisherRequest, Publisher> {
    @Override
    public Publisher apply(Publisher publisher, PatchPublisherRequest patchPublisherRequest) {
        return Publisher.builder()
                .id(publisher.getId())
                .name(patchPublisherRequest.getName())
                .numOfWorkers(patchPublisherRequest.getNumOfWorkers())
                .country(publisher.getCountry())
                .dateOfCreation(publisher.getDateOfCreation()).build();
    }
}
