package lab.library.configuration.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lab.library.user.entity.User;
import lab.library.user.service.UserService;
import lombok.SneakyThrows;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

@WebListener
public class InitializedData implements ServletContextListener {
    private UserService userService;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        userService = (UserService) event.getServletContext().getAttribute("userService");
        init();
    }

    @SneakyThrows
    private void init() {
        User user1 = User.builder()
                .id(UUID.fromString("3214f86a-d656-4021-ac1e-1d562b69034a"))
                .login("user1")
                .firstName("Szymon")
                .lastName("Malecki")
                .birthDate(LocalDate.of(2002, 2, 18))
                .email("szymon.test@gmail.com")
                .avatar(getResourceAsByteArray("/avatar/user1.png"))
                .build();

        User user2 = User.builder()
                .id(UUID.fromString("698ae485-51b2-4629-958f-ac0aa5679b32"))
                .login("user2")
                .firstName("Anna")
                .lastName("Kowalska")
                .birthDate(LocalDate.of(1998, 5, 12))
                .email("anna.test@gmail.com")
                .avatar(getResourceAsByteArray("/avatar/user2.png"))
                .build();

        User user3 = User.builder()
                .id(UUID.fromString("5b04df4d-21a3-4693-b3b2-abaab7f3dd83"))
                .login("user3")
                .firstName("Piotr")
                .lastName("Nowak")
                .birthDate(LocalDate.of(1995, 11, 23))
                .email("piotr.test@gmail.com")
                .avatar(getResourceAsByteArray("/avatar/user3.png"))
                .build();

        User user4 = User.builder()
                .id(UUID.fromString("fb9de5f7-87e5-4281-9390-75ded8e3bcff"))
                .login("user4")
                .firstName("Ewa")
                .lastName("Zielińska")
                .birthDate(LocalDate.of(2001, 7, 15))
                .email("ewa.test@gmail.com")
                .avatar(getResourceAsByteArray("/avatar/user4.png"))
                .build();

        userService.create(user1);
        userService.create(user2);
        userService.create(user3);
        userService.create(user4);

    }

    @SneakyThrows
    private byte[] getResourceAsByteArray(String name) {
        Path path = Paths.get("/Users/szymonmalecki/Desktop/LABJAKARTA/Library-JakartaEE/src/main/resources" + name);
        System.out.println(path);
        if (Files.exists(path)) {
            return Files.readAllBytes(path);
        } else {
            throw new IllegalStateException("Unable to get resource %s".formatted(name));
        }
    }
}
