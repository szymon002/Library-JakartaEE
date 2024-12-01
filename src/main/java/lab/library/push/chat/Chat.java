package lab.library.push.chat;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.SecurityContext;
import lab.library.push.context.PushMessageContext;
import lab.library.push.dto.Message;
import lombok.Getter;
import lombok.Setter;

@Named
@RequestScoped
public class Chat {
    private final PushMessageContext pushMessageContext;

    private final SecurityContext securityContext;

    @Inject
    public Chat(
            @SuppressWarnings("CdiInjectionPointsInspection") SecurityContext securityContext,
            PushMessageContext pushMessageContext
    ) {
        this.pushMessageContext = pushMessageContext;
        this.securityContext = securityContext;
    }

    @Getter
    @Setter
    private String message;

    @Getter
    @Setter
    private String recipient;

    public void sendMessage() {
        Message chatMessage = Message.builder()
                .from(securityContext.getCallerPrincipal().getName())
                .content(message)
                .build();

        if (recipient == null || recipient.isEmpty()) {
            pushMessageContext.notifyAll(chatMessage);
        } else {
            pushMessageContext.notifyUser(chatMessage, recipient);
        }

        message = "";
    }
}
