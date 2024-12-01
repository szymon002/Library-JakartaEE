package lab.library.push.context;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.push.Push;
import jakarta.faces.push.PushContext;
import jakarta.inject.Inject;
import lab.library.push.dto.Message;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

@ApplicationScoped
@Log
@NoArgsConstructor(force = true)
public class PushMessageContext {

    private PushContext broadcastChannel;

    private PushContext userChannel;

    @Inject
    public PushMessageContext(
            @Push(channel = "broadcastChannel") PushContext broadcastChannel,
            @Push(channel = "userChannel") PushContext userChannel
    ) {
        this.broadcastChannel = broadcastChannel;
        this.userChannel = userChannel;
    }

    public void notifyAll(Message message) {
        broadcastChannel.send(message);
    }

    public void notifyUser(Message message, String username) {
        userChannel.send(message, username);
    }
}
