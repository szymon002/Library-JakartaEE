package lab.library.book.bookView;

import jakarta.ejb.EJB;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lab.library.book.bookModel.BookModel;
import lab.library.book.entity.Book;
import lab.library.book.service.BookService;
import lab.library.component.ModelFunctionFactory;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@ViewScoped
@Named
public class BookView implements Serializable {
    private BookService bookService;

    private final ModelFunctionFactory factory;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private BookModel book;
    @Inject
    public BookView(
                    ModelFunctionFactory factory
    ) {
        this.factory = factory;
    }

    @EJB
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    public void init() throws IOException {
        Optional<Book> book = bookService.find(id);
        if (book.isPresent()) {
            this.book = factory.bookToModel().apply(book.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Book not found");
        }
    }
}
