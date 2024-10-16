package lab.library.book.repository.memory;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import lab.library.book.entity.Publisher;
import lab.library.book.repository.api.PublisherRepository;
import lab.library.datastore.component.DataStore;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class PublisherInMemoryRepository implements PublisherRepository {
    private final DataStore store;

    @Inject
    public PublisherInMemoryRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<Publisher> find(UUID id) {
        return store.findAllPublishers().stream()
                .filter(publisher -> publisher.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Publisher> findAll() {
        return store.findAllPublishers();
    }

    @Override
    public void create(Publisher entity) {
        store.createPublisher(entity);
    }

    @Override
    public void delete(Publisher entity) {
        store.deletePublisher(entity.getId());
    }

    @Override
    public void update(Publisher entity) {
        store.updatePublisher(entity);
    }
}
