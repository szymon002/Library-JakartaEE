package lab.library.book.publisherModel.converter;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.FacesConverter;
import jakarta.faces.convert.Converter;
import jakarta.inject.Inject;
import lab.library.book.entity.Publisher;
import lab.library.book.publisherModel.PublisherChooseModel;
import lab.library.book.service.PublisherService;
import lab.library.component.ModelFunctionFactory;

import java.util.Optional;
import java.util.UUID;

@FacesConverter(forClass = PublisherChooseModel.class, managed = true)
public class PublisherModelConverter implements Converter<PublisherChooseModel> {
    private final PublisherService service;

    private final ModelFunctionFactory factory;

    @Inject
    public PublisherModelConverter(PublisherService service,
                                   ModelFunctionFactory factory
    ) {
        this.service = service;
        this.factory = factory;
    }

    @Override
    public PublisherChooseModel getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        Optional<Publisher> publisher = service.find(UUID.fromString(value));
        return publisher.map(factory.choosePublisher()).orElse(null);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, PublisherChooseModel value) {
        return value == null ? "" : value.getId().toString();
    }
}
