package lab.library.book.publisherView;

import jakarta.ejb.EJB;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lab.library.book.bookModel.BooksModel;
import lab.library.book.entity.Book;
import lab.library.book.entity.Publisher;
import lab.library.book.publisherModel.PublisherModel;
import lab.library.book.service.BookService;
import lab.library.book.service.PublisherService;
import lab.library.component.ModelFunctionFactory;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ViewScoped
@Named
public class PublisherView implements Serializable {
    private PublisherService service;
    private BookService bookService;

    private final ModelFunctionFactory factory;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private PublisherModel publisher;

    @Getter
    private BooksModel books;

    @Inject
    public PublisherView(
                         ModelFunctionFactory factory
    ) {
        this.factory = factory;
    }

    @EJB
    public void setService(PublisherService publisherService) {
        this.service = publisherService;
    }

    @EJB
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    public void init() throws IOException {
        Optional<Publisher> publisher = service.find(id);
        if (publisher.isPresent()) {
            List<Book> books = bookService.findAll(publisher.get());
            this.books = factory.booksToModel().apply(books);
            this.publisher = factory.publisherToModel().apply(publisher.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Publisher not found");
        }
    }

    public String deleteBook(UUID bookId) {
        bookService.delete(bookId);
        return "publisher_view.xhtml?id=" + id.toString() + "&faces-redirect=true";
    }
}
