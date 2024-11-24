package lab.library.component;

import jakarta.enterprise.context.ApplicationScoped;
import lab.library.book.dto.function.*;
import lab.library.user.dto.function.RequestToUserFunction;
import lab.library.user.dto.function.UserToResponseFunction;
import lab.library.user.dto.function.UsersToResponseFunction;

@ApplicationScoped
public class DtoFunctionFactory {

    public UsersToResponseFunction usersToResponse() {
        return new UsersToResponseFunction();
    }

    public UserToResponseFunction userToResponse() {
        return new UserToResponseFunction();
    }

    public RequestToUserFunction requestToUser() {
        return new RequestToUserFunction();
    }

    public BookToResponseFunction bookToResponse() {
        return new BookToResponseFunction();
    }

    public BooksToResponseFunction booksToResponse() {
        return new BooksToResponseFunction();
    }

    public PublisherToResponseFunction publisherToResponse() {
        return new PublisherToResponseFunction();
    }

    public PublishersToResponseFunction publishersToResponse() {
        return new PublishersToResponseFunction();
    }

    public RequestToBookFunction requestToBook() {
        return new RequestToBookFunction();
    }

    public UpdateBookWithRequestFunction updateBook() {
        return new UpdateBookWithRequestFunction();
    }

    public RequestToPublisherFunction requestToPublisher() {
        return new RequestToPublisherFunction();
    }

    public UpdatePublisherWithRequestFunction updatePublisher() {
        return new UpdatePublisherWithRequestFunction();
    }
}
