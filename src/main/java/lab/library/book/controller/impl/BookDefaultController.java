package lab.library.book.controller.impl;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.TransactionalException;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import lab.library.book.controller.api.BookController;
import lab.library.book.dto.GetBookResponse;
import lab.library.book.dto.GetBooksResponse;
import lab.library.book.dto.PatchBookRequest;
import lab.library.book.dto.PutBookRequest;
import lab.library.book.service.BookService;
import lab.library.component.DtoFunctionFactory;
;
import lab.library.user.entity.User;
import lab.library.user.entity.UserRoles;
import lombok.extern.java.Log;

import java.util.UUID;
import java.util.logging.Level;

@Path("")
@Log
@RolesAllowed(UserRoles.USER)
public class BookDefaultController implements BookController {

    private BookService service;

    private final DtoFunctionFactory factory;

    @Inject
    public BookDefaultController(DtoFunctionFactory factory) {
        this.factory = factory;
    }

    @EJB
    public void setService(BookService bookService) {
        this.service = bookService;
    }

    @Override
    public GetBooksResponse getBooks() {
        return factory.booksToResponse().apply(service.findAll());
    }

    @Override
    public GetBooksResponse getUserBooks(UUID id) {
        return service.findAllByUser(id)
                .map(factory.booksToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public GetBooksResponse getPublisherBooks(UUID id) {
        return service.findAllByPublisher(id)
                .map(factory.booksToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public GetBookResponse getBook(UUID id) {
        return service.find(id)
                .map(factory.bookToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void putBook(UUID publisherId, UUID bookId, PutBookRequest request) {
        try {
            request.setPublisher(publisherId);
            service.create(factory.requestToBook().apply(bookId, request));
        } catch (EJBException ex) {
            if (ex.getCause() instanceof IllegalArgumentException) {
                log.log(Level.WARNING, ex.getMessage(), ex);
                throw new BadRequestException(ex);
            }
            throw ex;
        }

    }

    @Override
    public void patchBook(UUID id, PatchBookRequest request) {
        service.find(id).ifPresentOrElse(
                entity -> service.update(factory.updateBook().apply(entity, request)),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void deleteBook(UUID id) {
        service.find(id).ifPresentOrElse(
                entity -> service.delete(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }
}
