package edu.gmu.cs321;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EmployeeScreen extends Application {

    private Stage window;

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        showEmployeeDashboard();
    }

    private void showEmployeeDashboard() {
        window.setTitle("Employee Dashboard");

        Label titleLabel = new Label("Employee Dashboard");

        Button reviewFormButton = new Button("Review Sponsor Form");
        Button sendUpdatesButton = new Button("Send Application Updates");
        Button workflowTableButton = new Button("Workflow Table");

        reviewFormButton.setOnAction(e -> showApplicationSearchScreen());
        sendUpdatesButton.setOnAction(e -> showSendUpdateScreen());
        workflowTableButton.setOnAction(e -> workflowTableScreen());

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(titleLabel, reviewFormButton, sendUpdatesButton, workflowTableButton);

        Scene scene = new Scene(layout, 300, 200);
        window.setScene(scene);
        window.show();
    }

    private void showApplicationSearchScreen() {
        window.setTitle("Application Search");

        Label searchLabel = new Label("Application Search");
        TextField searchField = new TextField();
        searchField.setPromptText("Enter Application ID or Name");

        Button goBackButton = new Button("Go Back <--");
        goBackButton.setOnAction(e -> showEmployeeDashboard());

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(searchLabel, searchField, goBackButton);

        Scene searchScene = new Scene(layout, 300, 200);
        window.setScene(searchScene);
    }

    private void showSendUpdateScreen() {
        window.setTitle("Send Application Update");

        Label updateLabel = new Label("Send Application Update");

        TextArea updateTextArea = new TextArea();
        updateTextArea.setPromptText("Enter update message here...");

        TextArea updateEmailTextArea = new TextArea();
        updateEmailTextArea.setPromptText("Enter Applicant's Email here");

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> showSuccessScreen());

        Button goBackButton = new Button("Go Back <--");
        goBackButton.setOnAction(e -> showEmployeeDashboard());

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(updateLabel, updateTextArea, submitButton, goBackButton);

        Scene updateScene = new Scene(layout, 300, 250);
        window.setScene(updateScene);
    }

    private void showSuccessScreen() {
        window.setTitle("Success");

        Label successLabel = new Label("Update Submitted Successfully!");

        Button goBackButton = new Button("Go Back to Employee Dashboard");
        goBackButton.setOnAction(e -> showEmployeeDashboard());

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(successLabel, goBackButton);

        Scene successScene = new Scene(layout, 300, 200);
        window.setScene(successScene);
    }

    private void workflowTableScreen() {
        window.setTitle("Workflow Table");

        Label workflowLabel = new Label("Workflow Table");

        CheckBox reviewedFilter = new CheckBox("Reviewed");
        CheckBox inProgressFilter = new CheckBox("In Progress");
        CheckBox pendingFilter = new CheckBox("Pending");

        TextField searchField = new TextField();
        searchField.setPromptText("Search by application ID or name");

        HBox filterBox = new HBox(10);
        filterBox.getChildren().addAll(reviewedFilter, inProgressFilter, pendingFilter, searchField);

        VBox applicationList = new VBox(10);
        int i = 1;
        while (i <= 10) {
            Label applicationLabel = new Label("Application " + i);
            applicationList.getChildren().add(applicationLabel);
            i++;
        }

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(applicationList);
        scrollPane.setFitToWidth(true);

        Button goBackButton = new Button("Go Back <--");
        goBackButton.setOnAction(e -> showEmployeeDashboard());

        VBox layout = new VBox(15);
        layout.getChildren().addAll(workflowLabel, filterBox, scrollPane, goBackButton);

        Scene workflowScene = new Scene(layout, 400, 300);
        window.setScene(workflowScene);
    }
}