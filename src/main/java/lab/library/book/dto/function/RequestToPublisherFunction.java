package lab.library.book.dto.function;

import lab.library.book.dto.PutPublisherRequest;
import lab.library.book.entity.Publisher;

import java.util.UUID;
import java.util.function.BiFunction;

public class RequestToPublisherFunction implements BiFunction<UUID, PutPublisherRequest, Publisher> {
    @Override
    public Publisher apply(UUID uuid, PutPublisherRequest putPublisherRequest) {
        return Publisher.builder()
                .id(uuid)
                .name(putPublisherRequest.getName())
                .country(putPublisherRequest.getCountry())
                .dateOfCreation(putPublisherRequest.getDateOfCreation())
                .numOfWorkers(putPublisherRequest.getNumOfWorkers()).build();
    }
}
