package lab.library.controller.servlet;

import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import lab.library.book.controller.api.BookController;
import lab.library.book.controller.api.PublisherController;
import lab.library.book.dto.PatchBookRequest;
import lab.library.book.dto.PatchPublisherRequest;
import lab.library.book.dto.PutBookRequest;
import lab.library.book.dto.PutPublisherRequest;
import lab.library.user.controller.api.UserController;
import lombok.SneakyThrows;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(urlPatterns = {
        ApiServlet.Paths.API + "/*"
})
@MultipartConfig(maxFileSize = 200 * 1024)
public class ApiServlet extends HttpServlet {

    private final UserController userController;

    private final BookController bookController;

    private final PublisherController publisherController;

    public static final class Paths {
        public static final String API = "/api";
    }

    public static final class Patterns {
        private static final Pattern UUID = Pattern.compile("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");

        public static final Pattern USER = Pattern.compile("/users/(%s)".formatted(UUID.pattern()));

        public static final Pattern USERS = Pattern.compile("/users/?");

        public static final Pattern BOOK = Pattern.compile("/books/(%s)".formatted(UUID.pattern()));

        public static final Pattern BOOKS = Pattern.compile("/books/?");

        public static final Pattern PUBLISHER = Pattern.compile("/publishers/(%s)".formatted(UUID.pattern()));

        public static final Pattern PUBLISHERS = Pattern.compile("/publishers/?");

        public static final Pattern PUBLISHER_BOOKS = Pattern.compile("/publishers/(%s)/books/?".formatted(UUID.pattern()));

        public static final Pattern USER_BOOKS = Pattern.compile("/users/(%s)/books/?".formatted(UUID.pattern()));

        public static final Pattern USER_AVATAR = Pattern.compile("/users/(%s)/avatar".formatted(UUID.pattern()));
    }

    private final Jsonb jsonb = JsonbBuilder.create();

    @Inject
    public ApiServlet(
            UserController userController,
            BookController bookController,
            PublisherController publisherController
    ) {
        this.userController = userController;
        this.bookController = bookController;
        this.publisherController = publisherController;
    }
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getMethod().equals("PATCH")) {
            doPatch(request, response);
        } else {
            super.service(request, response);
        }

    }

    @SuppressWarnings("RedundantThrows")
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();

        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.USERS.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(userController.getUsers()));
                return;
            } else if (path.matches(Patterns.USER.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.USER, path);
                response.getWriter().write(jsonb.toJson(userController.getUser(uuid)));
                return;
            } else if (path.matches(Patterns.USER_AVATAR.pattern())) {
                UUID uuid = extractUuid(Patterns.USER_AVATAR, path);
                userController.getUserAvatar(uuid, response);
                return;
            } else if (path.matches(Patterns.BOOK.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.BOOK, path);
                response.getWriter().write(jsonb.toJson(bookController.getBook(uuid)));
                return;
            } else if (path.matches(Patterns.BOOKS.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(bookController.getBooks()));
                return;
            } else if (path.matches(Patterns.PUBLISHER.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.PUBLISHER, path);
                response.getWriter().write(jsonb.toJson(publisherController.getPublisher(uuid)));
                return;
            } else if (path.matches(Patterns.PUBLISHERS.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(publisherController.getPublishers()));
                return;
            } else if (path.matches(Patterns.USER_BOOKS.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.USER_BOOKS, path);
                response.getWriter().write(jsonb.toJson(bookController.getUserBooks(uuid)));
                return;
            } else if (path.matches(Patterns.PUBLISHER_BOOKS.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.PUBLISHER_BOOKS, path);
                response.getWriter().write(jsonb.toJson(bookController.getPublisherBooks(uuid)));
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.USER_AVATAR.pattern())) {
                UUID uuid = extractUuid(Patterns.USER_AVATAR, path);
                Part photopart = request.getPart("avatar");
                userController.putUserAvatar(uuid, photopart);
                return;
            } else if (path.matches(Patterns.BOOK.pattern())) {
                UUID uuid = extractUuid(Patterns.BOOK, path);
                bookController.putBook(uuid, jsonb.fromJson(request.getReader(), PutBookRequest.class));
                response.addHeader("Location", createUrl(request, Paths.API, "books", uuid.toString()));
                return;
            } else if (path.matches(Patterns.PUBLISHER.pattern())) {
                UUID uuid = extractUuid(Patterns.PUBLISHER, path);
                publisherController.putPublisher(uuid, jsonb.fromJson(request.getReader(), PutPublisherRequest.class));
                response.addHeader("Location", createUrl(request, Paths.API, "publishers", uuid.toString()));
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.USER_AVATAR.pattern())) {
                UUID uuid = extractUuid(Patterns.USER_AVATAR, path);
                userController.deleteAvatar(uuid);
                return;
            } else if (path.matches(Patterns.PUBLISHER.pattern())) {
                UUID uuid = extractUuid(Patterns.PUBLISHER, path);
                publisherController.deletePublisher(uuid);
                return;
            } else if (path.matches(Patterns.BOOK.pattern())) {
                UUID uuid = extractUuid(Patterns.BOOK, path);
                bookController.deleteBook(uuid);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @SuppressWarnings("RedundantThrows")
    protected void doPatch(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.BOOK.pattern())) {
                UUID uuid = extractUuid(Patterns.BOOK, path);
                bookController.patchBook(uuid, jsonb.fromJson(request.getReader(), PatchBookRequest.class));
                return;
            } else if (path.matches(Patterns.PUBLISHER.pattern())) {
                UUID uuid = extractUuid(Patterns.PUBLISHER, path);
                publisherController.patchPublisher(uuid, jsonb.fromJson(request.getReader(), PatchPublisherRequest.class));
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);

    }



    private String parseRequestPath(HttpServletRequest request) {
        String path = request.getPathInfo();
        path = path != null ? path : "";
        return path;
    }

    private static UUID extractUuid(Pattern pattern, String path) {
        Matcher matcher = pattern.matcher(path);
        if (matcher.matches()) {
            return UUID.fromString(matcher.group(1));
        }
        throw new IllegalArgumentException("No UUID in path.");
    }

    public static String createUrl(HttpServletRequest request, String... paths) {
        StringBuilder builder = new StringBuilder();
        builder.append(request.getScheme())
                .append("://")
                .append(request.getServerName())
                .append(":")
                .append(request.getServerPort())
                .append(request.getContextPath());
        for (String path : paths) {
            builder.append("/")
                    .append(path, path.startsWith("/") ? 1 : 0, path.endsWith("/") ? path.length() - 1 : path.length());
        }
        return builder.toString();
    }

}
