package lab.library.configuration.singleton.logger;
import jakarta.annotation.Priority;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;

import java.util.logging.Logger;
import java.util.logging.Level;


@LogBinding
@Interceptor
@Priority(10)
public class LoggerInterceptor {

    private SecurityContext securityContext;
    static Logger logger = Logger.getLogger(LoggerInterceptor.class.getName());

    @Inject
    public LoggerInterceptor(SecurityContext securityContext) {
        this.securityContext = securityContext;
    }

    @AroundInvoke
    public Object invoke(InvocationContext context) throws Exception {
        String user = securityContext.getCallerPrincipal().getName();
        String operation = context.getMethod().getName();
        String resource = context.getParameters().length > 0 ? context.getParameters()[0].toString() : "Unknown";
        System.out.println("User '" + user + "' performed '" + operation + "' on resource with ID '" + resource + "'");
        logger.log(Level.INFO, "User '" + user + "' performed '" + operation + "' on resource with ID '" + resource + "'");
        return context.proceed();
    }
}
