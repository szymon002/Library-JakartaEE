package lab.library.book.bookView;

import jakarta.enterprise.context.Conversation;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lab.library.book.bookModel.BookCreateModel;
import lab.library.book.entity.Book;
import lab.library.book.publisherModel.PublisherChooseModel;
import lab.library.book.service.BookService;
import lab.library.book.service.PublisherService;
import lab.library.component.ModelFunctionFactory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ConversationScoped
@Named
@Log
@NoArgsConstructor(force = true)
public class BookCreate implements Serializable {
    private final BookService bookService;
    private final PublisherService publisherService;
    private final ModelFunctionFactory factory;

    @Getter
    private BookCreateModel book;

    @Getter
    private List<PublisherChooseModel> publishers;

    private final Conversation conversation;

    @Inject
    public BookCreate(
            BookService bookService,
            PublisherService publisherService,
            ModelFunctionFactory factory,
            Conversation conversation
    ) {
        this.bookService = bookService;
        this.publisherService = publisherService;
        this.factory = factory;
        this.conversation = conversation;
    }

    public void init() {
        if (conversation.isTransient()) {
            publishers = publisherService.findAll().stream()
                    .map(factory.choosePublisher())
                    .collect(Collectors.toList());
            book = BookCreateModel.builder()
                    .id(UUID.randomUUID())
                    .build();
            conversation.begin();
        }
    }

    public String goToPublisherAction() {
        return "/book/book_create_publisher.xhtml?faces-redirect=true";
    }

    public String cancelAction() {
        conversation.end();
        return "/publisher/publisher_list.xhtml?faces-redirect=true";
    }

    public String goToBasicAction() {
        return "/book/book_create_basic.xhtml?faces-redirect=true";
    }

    public String goToConfirmAction() {
        return "/book/book_create_confirm.xhtml?faces-redirect=true";
    }

    public String saveAction() {
        bookService.create(factory.createBook().apply(book));
        conversation.end();
        return "/publisher/publisher_list.xhtml?faces-redirect=true";
    }

    public List<Book.State> getBookStates() {
        return Arrays.asList(Book.State.values());
    }
}
