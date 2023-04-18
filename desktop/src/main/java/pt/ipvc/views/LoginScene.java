package pt.ipvc.views;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import pt.ipvc.base.Scene;
import pt.ipvc.bll.SessionBLL;
import pt.ipvc.components.buttons.Button;
import pt.ipvc.components.buttons.ButtonSize;
import pt.ipvc.components.inputs.PasswordField;
import pt.ipvc.components.inputs.TextField;
import pt.ipvc.handlers.SceneHandler;
import pt.ipvc.handlers.ScenesEnum;

import java.util.Objects;

public class LoginScene extends Scene {

    private final TextField emailField;
    private final PasswordField passwordField;

    public LoginScene() {
        super();
        GridPane root = new GridPane();
        root.setHgap(32);
        root.setAlignment(Pos.CENTER);

        VBox container = new VBox(32);
        container.setPrefWidth(375);
        container.setAlignment(Pos.CENTER);

        ImageView logo = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pt/ipvc/assets/logo.png"))));

        VBox form = new VBox(8);
        form.setPrefWidth(container.getPrefWidth());

        emailField = new TextField();
        emailField.setPromptText("Email");

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button loginButton = new Button("Login", ButtonSize.LARGE);
        loginButton.setOnAction(event -> onLogin());
        loginButton.setMaxWidth(form.getPrefWidth());

        // Add components to GUI
        form.getChildren().addAll(emailField, passwordField, loginButton);
        container.getChildren().addAll(logo, form);
        root.getChildren().addAll(container);
        setRoot(root);
    }

    private void onLogin() {
        if(SessionBLL.login(emailField.getText(), passwordField.getText())){
            SceneHandler.changeScene(ScenesEnum.DASHBOARD);
        }
    }

}
