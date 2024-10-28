package lab.library.component;

import jakarta.enterprise.context.ApplicationScoped;
import lab.library.book.bookModel.function.*;
import lab.library.book.publisherModel.function.PublisherToModelChooseFunction;
import lab.library.book.publisherModel.function.PublisherToModelFunction;
import lab.library.book.publisherModel.function.PublishersToModelFunction;

@ApplicationScoped
public class ModelFunctionFactory {

    public PublishersToModelFunction publishersToModel() {
        return new PublishersToModelFunction();
    }

    public PublisherToModelFunction publisherToModel() {
        return new PublisherToModelFunction();
    }

    public BooksToModelFunction booksToModel() {
        return new BooksToModelFunction();
    }

    public BookToModelFunction bookToModel() {
        return new BookToModelFunction();
    }

    public BookToEditModelFunction bookToEditModel() {
        return new BookToEditModelFunction();
    }

    public UpdateBookWithModelFunction updateBook() {
        return new UpdateBookWithModelFunction();
    }

    public PublisherToModelChooseFunction choosePublisher() {
        return new PublisherToModelChooseFunction();
    }

    public ModelToBookFunction createBook() {
        return new ModelToBookFunction();
    }
}
