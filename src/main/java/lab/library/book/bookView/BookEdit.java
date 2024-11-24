package lab.library.book.bookView;

import jakarta.ejb.EJB;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lab.library.book.bookModel.BookEditModel;
import lab.library.book.entity.Book;
import lab.library.book.service.BookService;
import lab.library.component.ModelFunctionFactory;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@Named
@ViewScoped
public class BookEdit implements Serializable {
    private BookService service;

    private final ModelFunctionFactory factory;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private BookEditModel book;

    @Inject
    public BookEdit(
                    ModelFunctionFactory factory
    ) {
        this.factory = factory;
    }

    @EJB
    public void setService(BookService bookService) {
        this.service = bookService;
    }

    public void init() throws IOException {
        Optional<Book> book = service.find(id);
        if (book.isPresent()) {
            this.book = factory.bookToEditModel().apply(book.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Book not found");
        }
    }

    public String saveAction() {
        service.update(factory.updateBook().apply(service.find(id).orElseThrow(), book));
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true&includeViewParams=true";
    }
}
