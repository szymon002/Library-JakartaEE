package lab.library.user.controller.impl;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import jakarta.ws.rs.NotFoundException;
import lab.library.component.DtoFunctionFactory;
import lab.library.user.controller.api.UserController;
import lab.library.user.dto.GetUserResponse;
import lab.library.user.dto.GetUsersResponse;
import lab.library.user.entity.User;
import lab.library.user.service.UserService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@RequestScoped
public class UserDefaultController implements UserController {
    private final UserService service;

    private final DtoFunctionFactory factory;

    @Inject
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

    @Override
    public void getUserAvatar(UUID id, HttpServletResponse response) throws IOException {
        User user = service.find(id).orElseThrow(NotFoundException::new);

        if (user.getAvatar() == null) {
            throw new NotFoundException();
        }

        System.out.println(user.getAvatar());

        Path photoPath = Path.of(user.getAvatar());

        String filename = photoPath.getFileName().toString().toLowerCase();

        if (filename.endsWith(".png")) {
            response.setContentType("image/png");
        } else if (filename.endsWith(".jpg")) {
            response.setContentType("image/jpeg");
        } else {
            response.setContentType("application/octet-stream");
        }

        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");

        OutputStream out = response.getOutputStream();
        Files.copy(photoPath, out);
        out.close();

        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    public void putUserAvatar(UUID id, Part photoPart) throws IOException {
        User user = service.find(id).orElseThrow(NotFoundException::new);
        String fileName = photoPart.getSubmittedFileName();

        InputStream is = photoPart.getInputStream();
        service.updateAvatar(id, is, fileName);
        is.close();
    }

    @Override
    public void deleteAvatar(UUID id) {
        service.find(id).ifPresentOrElse(
                user -> {
                    if (user.getAvatar() == null) {
                        throw new NotFoundException();
                    }
                    service.deleteAvatar(id);
                },
                () -> {
                    throw new NotFoundException();
                }
        );
    }
}
