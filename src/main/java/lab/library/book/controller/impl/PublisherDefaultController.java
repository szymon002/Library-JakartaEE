package lab.library.book.controller.impl;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import lab.library.book.controller.api.PublisherController;
import lab.library.book.dto.GetPublisherResponse;
import lab.library.book.dto.GetPublishersResponse;
import lab.library.book.dto.PatchPublisherRequest;
import lab.library.book.dto.PutPublisherRequest;
import lab.library.book.service.PublisherService;
import lab.library.component.DtoFunctionFactory;
import lab.library.controller.servlet.exception.BadRequestException;
import lab.library.controller.servlet.exception.NotFoundException;

import java.util.UUID;

@RequestScoped
public class PublisherDefaultController implements PublisherController {

    private final PublisherService service;

    private final DtoFunctionFactory factory;

    @Inject
    public PublisherDefaultController(PublisherService service, DtoFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    @Override
    public GetPublisherResponse getPublisher(UUID id) {
        return service.find(id)
                .map(factory.publisherToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public GetPublishersResponse getPublishers() {
        return factory.publishersToResponse().apply(service.findAll());
    }

    @Override
    public void putPublisher(UUID id, PutPublisherRequest request) {
        try {
            service.create(factory.requestToPublisher().apply(id, request));
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException();
        }
    }

    @Override
    public void patchPublisher(UUID id, PatchPublisherRequest request) {
        service.find(id).ifPresentOrElse(
                entity -> service.update(factory.updatePublisher().apply(entity, request)),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void deletePublisher(UUID id) {
        service.find(id).ifPresentOrElse(
                entity -> service.delete(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }
}
