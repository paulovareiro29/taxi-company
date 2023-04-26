package pt.ipvc.base.table;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.Objects;

public class ButtonIconTableCell<S> extends TableCell<S, String> {

    private final Button button;

    public ButtonIconTableCell(String icon, EventHandler<MouseEvent> onClick) {
        ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pt/ipvc/assets/icons/" + icon))));
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(20);

        button = new Button();
        button.setGraphic(imageView);
        button.setOnMouseClicked(onClick);

        setGraphic(button);
        setStyle("-fx-alignment: CENTER");
    }

    public ButtonIconTableCell(String icon) {
        this(icon, e -> {});
    }

    public void setOnClick(EventHandler<MouseEvent> onClick) {
        button.setOnMouseClicked(onClick);
    }
}

