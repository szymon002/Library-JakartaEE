package lab.library.user.controller.impl;

import lab.library.component.DtoFunctionFactory;
import lab.library.user.controller.api.UserController;
import lab.library.user.dto.GetUserResponse;
import lab.library.user.dto.GetUsersResponse;
import lab.library.user.service.UserService;
import lab.library.controller.servlet.exception.NotFoundException;

import java.util.UUID;

public class UserDefaultController implements UserController {
    private final UserService service;

    private final DtoFunctionFactory factory;

    public UserDefaultController(UserService service, DtoFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }


    @Override
    public GetUsersResponse getUsers() {
        return factory.usersToResponse().apply(service.findAll());
    }

    @Override
    public GetUserResponse getUser(UUID id) {
        return service.find(id)
                .map(factory.userToResponse())
                .orElseThrow(NotFoundException::new);
    }
}
