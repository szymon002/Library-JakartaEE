package lab.library.configuration.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lab.library.user.entity.User;
import lab.library.user.service.UserService;
import lombok.SneakyThrows;

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
                .id(UUID.fromString("a04cfc47-7ec7-4b9a-8780-a222f89a08d7"))
                .login("user1")
                .firstName("Szymon")
                .lastName("Malecki")
                .birthDate(LocalDate.of(2002, 2, 18))
                .email("szymon.test@gmail.com")
                .build();

        User user2 = User.builder()
                .id(UUID.fromString("a04cfc47-6de8-4c8a-9812-b333g99b09d8"))
                .login("user2")
                .firstName("Anna")
                .lastName("Kowalska")
                .birthDate(LocalDate.of(1998, 5, 12))
                .email("anna.test@gmail.com")
                .build();

        User user3 = User.builder()
                .id(UUID.fromString("c25ehh99-5bc9-4d7b-8921-c444h10c10e9"))
                .login("user3")
                .firstName("Piotr")
                .lastName("Nowak")
                .birthDate(LocalDate.of(1995, 11, 23))
                .email("piotr.test@gmail.com")
                .build();

        User user4 = User.builder()
                .id(UUID.fromString("d36fii00-4ad0-4e6c-8734-d555i21d21f0"))
                .login("user4")
                .firstName("Ewa")
                .lastName("Zielińska")
                .birthDate(LocalDate.of(2001, 7, 15))
                .email("ewa.test@gmail.com")
                .build();

        userService.create(user1);
        userService.create(user2);
        userService.create(user3);
        userService.create(user4);

    }
}
