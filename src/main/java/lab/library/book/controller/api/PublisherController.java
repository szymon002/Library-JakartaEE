package lab.library.book.controller.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lab.library.book.dto.GetPublisherResponse;
import lab.library.book.dto.GetPublishersResponse;
import lab.library.book.dto.PatchPublisherRequest;
import lab.library.book.dto.PutPublisherRequest;

import java.util.UUID;

@Path("")
public interface PublisherController {

    @GET
    @Path("/publishers/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetPublisherResponse getPublisher(@PathParam("id") UUID id);

    @GET
    @Path("/publishers")
    @Produces(MediaType.APPLICATION_JSON)
    GetPublishersResponse getPublishers();

    @PUT
    @Path("/publishers/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    void putPublisher(@PathParam("id") UUID id, PutPublisherRequest request);

    @PATCH
    @Path("/publishers/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void patchPublisher(@PathParam("id") UUID id, PatchPublisherRequest request);

    @DELETE
    @Path("/publishers/{id}")
    void deletePublisher(@PathParam("id") UUID id);
}
