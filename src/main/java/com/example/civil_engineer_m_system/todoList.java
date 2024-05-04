package com.example.civil_engineer_m_system;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressBar;

public class todoList {
    @FXML
    private CheckBox task1;

    @FXML
    private CheckBox task2;

    @FXML
    private CheckBox task3;

    @FXML
    private CheckBox task4;

    @FXML
    private ProgressBar progressBar;

    @FXML
    public void initialize() {
        // Add listener to checkboxes
        task1.setOnAction(event -> updateProgress());
        task2.setOnAction(event -> updateProgress());
        task3.setOnAction(event -> updateProgress());
        task4.setOnAction(event -> updateProgress());
    }

    private void updateProgress() {
        double completedTasks = 0;
        if (task1.isSelected()) completedTasks++;
        if (task2.isSelected()) completedTasks++;
        if (task3.isSelected()) completedTasks++;
        if (task4.isSelected()) completedTasks++;

        double progress = completedTasks / 4.0;
        progressBar.setProgress(progress);
    }
}
