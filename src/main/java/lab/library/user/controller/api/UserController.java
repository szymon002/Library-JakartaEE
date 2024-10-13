package lab.library.user.controller.api;

import lab.library.user.dto.GetUserResponse;
import lab.library.user.dto.GetUsersResponse;

import java.io.InputStream;
import java.util.UUID;

public interface UserController {

    GetUsersResponse getUsers();

    GetUserResponse getUser(UUID id);

    byte[] getUserAvatar(UUID id);

    void putUserAvatar(UUID id, InputStream avatar);

    void deleteAvatar(UUID id);
}
