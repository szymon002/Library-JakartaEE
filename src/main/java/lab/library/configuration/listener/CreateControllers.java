package lab.library.configuration.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lab.library.component.DtoFunctionFactory;
import lab.library.user.controller.impl.UserDefaultController;
import lab.library.user.service.UserService;

@WebListener
public class CreateControllers implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        UserService userService = (UserService) event.getServletContext().getAttribute("userService");

        event.getServletContext().setAttribute("userController", new UserDefaultController(
                userService,
                new DtoFunctionFactory()
        ));
    }
}
