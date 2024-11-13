package edu.gmu.cs321;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ImmigrationSponsorshipLogin extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Immigration Sponsorship");

        // Title
        Text title = new Text("Immigration Sponsorship");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        // Sponsor Login Section
        GridPane sponsorLoginPane = createLoginSection("Sponsor Login");

        // Employer Login Section
        GridPane employerLoginPane = createLoginSection("Employer Login");

        // Layout the main screen with the title and both login sections
        VBox mainLayout = new VBox(20, title, sponsorLoginPane, employerLoginPane);
        mainLayout.setPadding(new Insets(20, 20, 20, 20));
        mainLayout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(mainLayout, 400, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private GridPane createLoginSection(String loginTitle) {
        // Create the layout for each login section
        GridPane loginPane = new GridPane();
        loginPane.setAlignment(Pos.CENTER);
        loginPane.setHgap(10);
        loginPane.setVgap(10);
        loginPane.setPadding(new Insets(20, 20, 20, 20));

        // Title for each section
        Text loginLabel = new Text(loginTitle);
        loginLabel.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 18));
        loginPane.add(loginLabel, 0, 0, 2, 1);

        // Username Label and Field
        Label userLabel = new Label("Username:");
        TextField userTextField = new TextField();
        loginPane.add(userLabel, 0, 1);
        loginPane.add(userTextField, 1, 1);

        // Password Label and Field
        Label pwLabel = new Label("Password:");
        PasswordField pwBox = new PasswordField();
        loginPane.add(pwLabel, 0, 2);
        loginPane.add(pwBox, 1, 2);

        // Login Button
        Button loginButton = new Button("Login");
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        buttonBox.getChildren().add(loginButton);
        loginPane.add(buttonBox, 1, 3);

        // You can add functionality to the login button here if needed
        loginButton.setOnAction(e -> {
            String username = userTextField.getText();
            String password = pwBox.getText();
            System.out.println(loginTitle + " - Username: " + username + ", Password: " + password);
            // Add validation logic here
        });

        return loginPane;
    }
}