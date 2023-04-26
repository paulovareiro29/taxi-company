package pt.ipvc.layout.popup;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import pt.ipvc.base.EventListener;
import pt.ipvc.base.Popup;
import pt.ipvc.components.buttons.Button;
import pt.ipvc.components.buttons.ButtonAppearance;

public class DangerConfirmationPopup extends Popup {
    public DangerConfirmationPopup(EventListener listener) {
        super("Are you sure?", listener);


        Button cancelButton = new Button("Cancel", ButtonAppearance.outlined_primary);
        Button confirmButton = new Button("Confirm", ButtonAppearance.danger);
        cancelButton.setOnAction(e -> {
            listener.onCancel();
            hide();
        });
        confirmButton.setOnAction(e -> {
            listener.onSuccess();
            hide();
        });

        HBox options = new HBox(8);

        HBox.setHgrow(cancelButton, Priority.ALWAYS);
        HBox.setHgrow(confirmButton, Priority.ALWAYS);
        cancelButton.setMaxWidth(Double.MAX_VALUE);
        confirmButton.setMaxWidth(Double.MAX_VALUE);

        options.getChildren().addAll(cancelButton, confirmButton);
        addChildren(options);

        confirmButton.requestFocus();
    }

    @Override
    public void update() {

    }
}
