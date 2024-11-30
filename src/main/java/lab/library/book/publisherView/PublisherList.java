package lab.library.book.publisherView;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.annotation.View;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lab.library.book.publisherModel.PublishersModel;
import lab.library.book.service.PublisherService;
import lab.library.component.ModelFunctionFactory;

import java.io.Serializable;

@ViewScoped
@Named
public class PublisherList implements Serializable {
    private PublisherService publisherService;

    private PublishersModel publishers;

    private final ModelFunctionFactory factory;

    @Inject
    public PublisherList(
            ModelFunctionFactory factory
    ) {
        this.factory = factory;
    }

    @EJB
    public void setPublisherService(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    public PublishersModel getPublishers() {
        if (publishers == null) {
            publishers = factory.publishersToModel().apply(publisherService.findAll());
        }
        return publishers;
    }

    public void deleteAction(PublishersModel.Publisher publisher) {
        publisherService.delete(publisher.getId());
        publishers = null;
    }
}
