package lab.library.book.controller.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lab.library.book.dto.GetBookResponse;
import lab.library.book.dto.GetBooksResponse;
import lab.library.book.dto.PatchBookRequest;
import lab.library.book.dto.PutBookRequest;

import java.util.UUID;

@Path("")
public interface BookController {
    @GET
    @Path("/books")
    @Produces(MediaType.APPLICATION_JSON)
    GetBooksResponse getBooks();

    GetBooksResponse getUserBooks(UUID id);

    @GET
    @Path("/publishers/{id}/books")
    @Produces(MediaType.APPLICATION_JSON)
    GetBooksResponse getPublisherBooks(@PathParam("id") UUID id);

    @GET
    @Path("/books/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetBookResponse getBook(@PathParam("id") UUID id);

    @PUT
    @Path("/publishers/{publisherId}/books/{bookId}")
    @Consumes({MediaType.APPLICATION_JSON})
    void putBook(
            @PathParam("publisherId") UUID publisherId,
            @PathParam("bookId") UUID bookId,
            PutBookRequest request
    );

    @PATCH
    @Path("/books/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    void patchBook(@PathParam("id") UUID id, PatchBookRequest request);


    @DELETE
    @Path("/books/{id}")
    void deleteBook(@PathParam("id") UUID id);
}
