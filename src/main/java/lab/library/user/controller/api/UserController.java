package lab.library.user.controller.api;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lab.library.user.dto.GetUserResponse;
import lab.library.user.dto.GetUsersResponse;
import lab.library.user.dto.PutUserRequest;

import java.io.IOException;
import java.util.UUID;

@Path("")
public interface UserController {
    @PUT
    @Path("/users/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    void putUser(
            @PathParam("id") UUID id,
            PutUserRequest request
    );

    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    GetUsersResponse getUsers();

    GetUserResponse getUser(UUID id);

    public void getUserAvatar(UUID id, HttpServletResponse response) throws IOException;

    void putUserAvatar(UUID id, Part photoPart) throws IOException;

    void deleteAvatar(UUID id);
}
