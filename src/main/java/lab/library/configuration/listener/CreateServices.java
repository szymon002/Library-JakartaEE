package lab.library.configuration.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lab.library.datastore.component.DataStore;
import lab.library.user.repository.api.UserRepository;
import lab.library.user.repository.memory.UserInMemoryRepository;
import lab.library.user.service.UserService;

import java.nio.file.Path;
import java.nio.file.Paths;

@WebListener
public class CreateServices implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        DataStore dataSource = (DataStore) event.getServletContext().getAttribute("datasource");

        UserRepository userRepository = new UserInMemoryRepository(dataSource);
        ServletContext servletContext = event.getServletContext();
        Path photosPath = Paths.get(servletContext.getInitParameter("AVATAR_PATH"));

        event.getServletContext().setAttribute("userService", new UserService(userRepository, photosPath));
    }

}
