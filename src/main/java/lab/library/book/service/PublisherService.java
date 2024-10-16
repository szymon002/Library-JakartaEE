package lab.library.book.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lab.library.book.entity.Publisher;
import lab.library.book.repository.api.PublisherRepository;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class PublisherService {
    private final PublisherRepository publisherRepository;

    @Inject
    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public Optional<Publisher> find(UUID id) {
        return publisherRepository.find(id);
    }

    public List<Publisher> findAll() {
        return publisherRepository.findAll();
    }

    public void create(Publisher publisher) {
        publisherRepository.create(publisher);
    }

    public void update(Publisher publisher) {
        publisherRepository.update(publisher);
    }

    public void delete(UUID id) {
        publisherRepository.delete(publisherRepository.find(id).orElseThrow());
    }
}
