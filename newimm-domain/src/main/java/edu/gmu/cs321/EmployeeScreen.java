package edu.gmu.cs321;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class EmployeeScreen extends Application {

    private Stage window;
    private String selectedApplicationId = null;

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        showEmployeeDashboard();
    }

    private void showEmployeeDashboard() {
        window.setTitle("Employee Dashboard");

        Label titleLabel = new Label("Employee Dashboard");
        titleLabel.setFont(Font.font(null, FontWeight.BOLD, 24));

        Button reviewFormButton = new Button("Review Sponsor Form");
        Button sendUpdatesButton = new Button("Send Application Updates");
        Button workflowTableButton = new Button("Workflow Table");
        
        Button logoutButton = new Button("Logout");
        logoutButton.setFont(Font.font(null, FontWeight.BOLD, 16));

        logoutButton.setOnAction(e -> showLoginScreen());

        reviewFormButton.setFont(Font.font(null, FontWeight.BOLD, 16));
        sendUpdatesButton.setFont(Font.font(null, FontWeight.BOLD, 16));
        workflowTableButton.setFont(Font.font(null, FontWeight.BOLD, 16));

        reviewFormButton.setOnAction(e -> showApplicationSearchScreen());
        sendUpdatesButton.setOnAction(e -> showSendUpdateScreen());
        workflowTableButton.setOnAction(e -> workflowTableScreen());

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(30));
        layout.getChildren().addAll(titleLabel, reviewFormButton, sendUpdatesButton, workflowTableButton, logoutButton);

        Scene scene = new Scene(layout, 500, 400);
        window.setScene(scene);
        window.show();
    }

    private void showLoginScreen() {
        ImmigrationSponsorshipLogin loginScreen = new ImmigrationSponsorshipLogin();
        loginScreen.start(window);
    }

    private void workflowTableScreen() {
        window.setTitle("Workflow Table");

        Label workflowLabel = new Label("Workflow Table");
        workflowLabel.setFont(Font.font(null, FontWeight.BOLD, 20));

        CheckBox reviewedFilter = new CheckBox("Reviewed");
        CheckBox inProgressFilter = new CheckBox("In Progress");
        CheckBox pendingFilter = new CheckBox("Pending");

        TextField searchField = new TextField();
        searchField.setPromptText("Search by application ID or name");

        HBox filterBox = new HBox(15);
        filterBox.setPadding(new Insets(10));
        filterBox.getChildren().addAll(reviewedFilter, inProgressFilter, pendingFilter, searchField);

        TestDatabase db = new TestDatabase();
        List<String> applications = new ArrayList<>();

        // Fetch applications from database
        boolean dataFetched = db.fetchApplicationsForEmployee(1, applications);

        // Log if data fetching fails
        if (!dataFetched) {
            System.out.println("No applications found or database connection failed.");
        }

        VBox applicationList = new VBox(15);
        applicationList.setPadding(new Insets(10));

        if (applications.isEmpty()) {
            applicationList.getChildren().add(new Label("No applications found."));
        } else {
            for (String appId : applications) {
                HBox applicationRow = new HBox(15);
                Label applicationLabel = new Label("Application ID: " + appId);
                Button viewButton = new Button("View");

                viewButton.setOnAction(e -> showReviewPage(appId));

                applicationRow.getChildren().addAll(applicationLabel, viewButton);
                applicationList.getChildren().add(applicationRow);
            }
        }

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(applicationList);
        scrollPane.setFitToWidth(true);

        Button goBackButton = new Button("Go Back <--");
        goBackButton.setFont(Font.font(null, FontWeight.BOLD, 14));
        goBackButton.setOnAction(e -> showEmployeeDashboard());

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(30));
        layout.getChildren().addAll(workflowLabel, filterBox, scrollPane, goBackButton);

        Scene workflowScene = new Scene(layout, 600, 500);
        window.setScene(workflowScene);
    }

    private void showReviewPage(String applicationId) {
        this.selectedApplicationId = applicationId;
        TestDatabase db = new TestDatabase();
        StringBuilder details = new StringBuilder();
        db.fetchApplicationDetails(applicationId, details);

        Label formDetails = new Label(details.toString());

        window.setTitle("Review Form - " + applicationId);

        Label reviewLabel = new Label("Review Form for " + applicationId);
        reviewLabel.setFont(Font.font(null, FontWeight.BOLD, 20));

        Button goBackButton = new Button("Go Back to Workflow Table");
        goBackButton.setFont(Font.font(null, FontWeight.BOLD, 14));
        goBackButton.setOnAction(e -> workflowTableScreen());

        Button sendUpdatesButton = new Button("Send Updates");
        sendUpdatesButton.setFont(Font.font(null, FontWeight.BOLD, 14));
        sendUpdatesButton.setOnAction(e -> showSendUpdateScreen());

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(30));
        layout.getChildren().addAll(reviewLabel, formDetails, sendUpdatesButton, goBackButton);

        Scene reviewScene = new Scene(layout, 500, 400);
        window.setScene(reviewScene);
    }

    private void showSendUpdateScreen() {
        window.setTitle("Send Application Update");

        Label updateLabel = new Label("Send Application Update");
        updateLabel.setFont(Font.font(null, FontWeight.BOLD, 20));

        TextArea updateTextArea = new TextArea();
        updateTextArea.setPromptText("Enter update message here...");
        updateTextArea.setPrefHeight(100);

        TextField updateEmailField = new TextField();
        updateEmailField.setPromptText("Enter Applicant's Email here");

        Label appIdLabel = new Label("Application ID: " + selectedApplicationId);
        appIdLabel.setFont(Font.font(null, FontWeight.BOLD, 14));

        Button goBackButton = new Button("Go Back <--");
        goBackButton.setFont(Font.font(null, FontWeight.BOLD, 14));
        goBackButton.setOnAction(e -> showReviewPage(selectedApplicationId));

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            TestDatabase db = new TestDatabase();
            db.updateApplicationStatus(selectedApplicationId, "Updated");
            showSuccessScreen();
        });

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(30));
        layout.getChildren().addAll(updateLabel, appIdLabel, updateTextArea, updateEmailField, submitButton, goBackButton);

        Scene updateScene = new Scene(layout, 500, 400);
        window.setScene(updateScene);
    }

    private void showSuccessScreen() {
        window.setTitle("Success");

        Label successLabel = new Label("Update Submitted Successfully!");
        successLabel.setFont(Font.font(null, FontWeight.BOLD, 18));

        Button goBackButton = new Button("Go Back to Workflow Table");
        goBackButton.setFont(Font.font(null, FontWeight.BOLD, 14));
        goBackButton.setOnAction(e -> workflowTableScreen());

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(30));
        layout.getChildren().addAll(successLabel, goBackButton);

        Scene successScene = new Scene(layout, 500, 300);
        window.setScene(successScene);
    }

    private void showApplicationSearchScreen() {
        window.setTitle("Application Search");

        Label searchLabel = new Label("Application Search");
        searchLabel.setFont(Font.font(null, FontWeight.BOLD, 20));

        TextField searchField = new TextField();
        searchField.setPromptText("Enter Application ID or Name");

        Button goBackButton = new Button("Go Back <--");
        goBackButton.setFont(Font.font(null, FontWeight.BOLD, 14));
        goBackButton.setOnAction(e -> showEmployeeDashboard());

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(30));
        layout.getChildren().addAll(searchLabel, searchField, goBackButton);

        Scene searchScene = new Scene(layout, 500, 300);
        window.setScene(searchScene);
    }
}
