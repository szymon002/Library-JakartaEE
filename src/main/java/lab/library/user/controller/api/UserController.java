package lab.library.user.controller.api;

import lab.library.user.dto.GetUserResponse;
import lab.library.user.dto.GetUsersResponse;

import java.util.UUID;

public interface UserController {

    GetUsersResponse getUsers();

    GetUserResponse getUser(UUID id);
}
