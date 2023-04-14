package pt.ipvc.views;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import pt.ipvc.base.Scene;
import pt.ipvc.components.buttons.Button;
import pt.ipvc.components.buttons.ButtonSize;
import pt.ipvc.components.inputs.PasswordField;
import pt.ipvc.components.inputs.TextField;

public class LoginScene extends Scene {

    private final TextField usernameField;
    private final PasswordField passwordField;

    public LoginScene() {
        super();
        GridPane root = new GridPane();
        root.setHgap(32);
        root.setAlignment(Pos.CENTER);

        VBox container = new VBox(32);
        container.setPrefWidth(375);
        container.setAlignment(Pos.CENTER);

        Label header = new Label("Login");
        header.setFont(Font.font(null, FontWeight.BOLD,40));

        VBox form = new VBox(8);
        form.setPrefWidth(container.getPrefWidth());

        usernameField = new TextField();
        usernameField.setPromptText("Username");

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button loginButton = new Button("Login", ButtonSize.LARGE);
        loginButton.setOnAction(event -> onLogin());
        loginButton.setMaxWidth(form.getPrefWidth());


        // Add components to GUI
        form.getChildren().addAll(usernameField, passwordField, loginButton);
        container.getChildren().addAll(header, form);
        root.getChildren().addAll(container);
        setRoot(root);
    }

    private void onLogin() {
        System.out.println("Tried to login: " + usernameField.getText() + " " + passwordField.getText());
    }

}
