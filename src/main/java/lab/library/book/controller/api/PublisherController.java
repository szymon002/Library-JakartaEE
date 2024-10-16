package lab.library.book.controller.api;

import lab.library.book.dto.GetPublisherResponse;
import lab.library.book.dto.GetPublishersResponse;
import lab.library.book.dto.PatchPublisherRequest;
import lab.library.book.dto.PutPublisherRequest;

import java.util.UUID;

public interface PublisherController {

    GetPublisherResponse getPublisher(UUID id);

    GetPublishersResponse getPublishers();

    void putPublisher(UUID id, PutPublisherRequest request);

    void patchPublisher(UUID id, PatchPublisherRequest request);

    void deletePublisher(UUID id);
}
