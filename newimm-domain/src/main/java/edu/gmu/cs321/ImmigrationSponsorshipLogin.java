package edu.gmu.cs321;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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

        Text title = new Text("Immigration Sponsorship");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        VBox sponsorLoginPane = createSponsorLoginSection(primaryStage);

        VBox employerLoginPane = createEmployerLoginSection(primaryStage);

        HBox loginSections = new HBox(20, sponsorLoginPane, employerLoginPane);
        loginSections.setAlignment(Pos.CENTER);

        VBox mainLayout = new VBox(20, title, loginSections);
        mainLayout.setPadding(new Insets(20, 20, 20, 20));
        mainLayout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(mainLayout, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createSponsorLoginSection(Stage primaryStage) {
        VBox sponsorPane = new VBox(10);
        sponsorPane.setPadding(new Insets(20, 20, 20, 20));
        sponsorPane.setAlignment(Pos.CENTER);

        Text loginLabel = new Text("Sponsor Login");
        loginLabel.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 18));

        Label userLabel = new Label("Unique ID:");
        TextField userTextField = new TextField();

        Button loginButton = new Button("Login");

        loginButton.setOnAction(e -> {
            String sponsorId = userTextField.getText();
            String status = TestDatabase.getSponsorStatus(sponsorId);

            if (status == null) {
                showAlert("Error", "Sponsor ID not found!");
            } else {
                showProgressScreen(primaryStage, status);
            }
        });

        Hyperlink createFormLink = new Hyperlink("Create a new form");
        createFormLink.setFont(Font.font("Arial", 14));
        createFormLink.setOnAction(e -> openSponsorForm(primaryStage));

        sponsorPane.getChildren().addAll(loginLabel, userLabel, userTextField, loginButton, createFormLink);
        return sponsorPane;
    }


    private VBox createEmployerLoginSection(Stage primaryStage) {
        VBox employerPane = new VBox(10);
        employerPane.setPadding(new Insets(20, 20, 20, 20));
        employerPane.setAlignment(Pos.CENTER);

        Text loginLabel = new Text("Employer Login");
        loginLabel.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 18));

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        Button loginButton = new Button("Login");

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (TestDatabase.validateEmployerCredentials(username, password)) {
                openEmployeeScreen(primaryStage); // Close the current window after opening EmployeeScreen
            } else {
                showAlert("Error", "Invalid username or password!");
            }
        });

        employerPane.getChildren().addAll(loginLabel, usernameLabel, usernameField, passwordLabel, passwordField, loginButton);
        return employerPane;
    }

    private void openSponsorForm(Stage primaryStage) {
        try {
            SponsorForm sponsorForm = new SponsorForm();
            Stage sponsorFormStage = new Stage();
            sponsorForm.start(sponsorFormStage);
            primaryStage.close();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Unable to open Sponsor Form");
        }
    }

    private void openEmployeeScreen(Stage primaryStage) {
        try {
            EmployeeScreen employeeScreen = new EmployeeScreen();
            Stage employeeScreenStage = new Stage();
            employeeScreen.start(employeeScreenStage);

            // Close the Immigration Sponsorship Login window
            primaryStage.close();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Unable to open Employee Screen");
        }
    }

    private void showProgressScreen(Stage primaryStage, String status) {
        Text progressTitle = new Text("Progress");
        progressTitle.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        Label statusLabel = new Label("Status: " + status);
        statusLabel.setFont(Font.font("Arial", 16));

        Button returnButton = new Button("Return to Main Menu");
        returnButton.setOnAction(e -> start(primaryStage));

        VBox progressLayout = new VBox(20, progressTitle, statusLabel, returnButton);
        progressLayout.setPadding(new Insets(20, 20, 20, 20));
        progressLayout.setAlignment(Pos.CENTER);

        Scene progressScene = new Scene(progressLayout, 400, 300);
        primaryStage.setScene(progressScene);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

