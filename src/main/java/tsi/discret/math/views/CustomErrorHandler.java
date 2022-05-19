package tsi.discret.math.views;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.ErrorEvent;
import com.vaadin.flow.server.ErrorHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomErrorHandler implements ErrorHandler {

    public void error(ErrorEvent errorEvent) {
        log.error("Error handled:", errorEvent.getThrowable());
        if (UI.getCurrent() != null) {
            UI.getCurrent().access(() -> {
                new NotificationError(errorEvent.getThrowable());
            });
        }
    }
}