package pt.ipvc.views;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
        emailField.setIcon("email--secondary.png");

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setIcon("lock--secondary.png");
        passwordField.setOnKeyPressed(event -> {
            if(event.getCode().equals(KeyCode.ENTER)){
                onLogin();
            }
        });

        Button loginButton = new Button("Login", ButtonSize.LARGE);
        loginButton.setOnAction(event -> onLogin());
        loginButton.setMaxWidth(form.getPrefWidth());

        // Add components to GUI
        form.getChildren().addAll(emailField, passwordField, loginButton);
        container.getChildren().addAll(logo, form);
        root.getChildren().addAll(container);
        setRoot(root);
    }

    @Override
    public void update() {}

    private void onLogin() {
        clearErrors();

        boolean hasError = false;

        if(emailField.getText().isBlank()) {
            emailField.setError("Email is required");
            hasError = true;
        }

        if(passwordField.getText().isBlank()) {
            passwordField.setError("Password is required");
            hasError = true;
        }

        if (hasError) return;

        if(SessionBLL.login(emailField.getText(), passwordField.getText())){
            SceneHandler.changeScene(ScenesEnum.DASHBOARD);
        }
    }

    private void clearErrors() {
        emailField.clearError();
        passwordField.clearError();
    }

}
