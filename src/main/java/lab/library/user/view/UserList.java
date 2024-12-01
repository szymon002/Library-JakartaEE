package lab.library.user.view;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.SecurityContext;
import lab.library.component.ModelFunctionFactory;
import lab.library.user.model.UsersModel;
import lab.library.user.service.UserService;

import java.util.stream.Collectors;

@RequestScoped
@Named
public class UserList {

    private final UserService service;

    private UsersModel users;

    private final SecurityContext securityContext;

    private final ModelFunctionFactory factory;

    @Inject
    public UserList(UserService service,
                    ModelFunctionFactory factory,
                    @SuppressWarnings("CdiInjectionPointsInspection") SecurityContext securityContext) {
        this.service = service;
        this.factory = factory;
        this.securityContext = securityContext;
    }

    public UsersModel getUsers() {
        String currentUser = securityContext.getCallerPrincipal().getName();
        if (users == null) {
            users = factory.usersToModel().apply(
                    service.findAll().stream()
                            .filter(user -> !user.getLogin().equals(currentUser))
                            .collect(Collectors.toList())
            );
        }
        return users;
    }

}

