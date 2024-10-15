package lab.library.user.controller.api;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import lab.library.user.dto.GetUserResponse;
import lab.library.user.dto.GetUsersResponse;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public interface UserController {

    GetUsersResponse getUsers();

    GetUserResponse getUser(UUID id);

    public void getUserAvatar(UUID id, HttpServletResponse response) throws IOException;

    void putUserAvatar(UUID id, Part photoPart) throws IOException;

    void deleteAvatar(UUID id);
}
