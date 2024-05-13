package com.example.civil_engineer_m_system;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressBar;

import java.io.*;
import java.util.Properties;

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

    private Properties properties;
    private File file;

    @FXML
    public void initialize() {
        file = new File("todo.properties");
        properties = new Properties();
        loadState();

        // Add listener to checkboxes
        task1.setOnAction(event -> {
            updateState();
            updateProgress();
        });
        task2.setOnAction(event -> {
            updateState();
            updateProgress();
        });
        task3.setOnAction(event -> {
            updateState();
            updateProgress();
        });
        task4.setOnAction(event -> {
            updateState();
            updateProgress();
        });

        // Update progress bar initially
        updateProgress();
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

    private void loadState() {
        try (FileInputStream fis = new FileInputStream(file)) {
            properties.load(fis);
            task1.setSelected(Boolean.parseBoolean(properties.getProperty("task1", "false")));
            task2.setSelected(Boolean.parseBoolean(properties.getProperty("task2", "false")));
            task3.setSelected(Boolean.parseBoolean(properties.getProperty("task3", "false")));
            task4.setSelected(Boolean.parseBoolean(properties.getProperty("task4", "false")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateState() {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            properties.setProperty("task1", String.valueOf(task1.isSelected()));
            properties.setProperty("task2", String.valueOf(task2.isSelected()));
            properties.setProperty("task3", String.valueOf(task3.isSelected()));
            properties.setProperty("task4", String.valueOf(task4.isSelected()));
            properties.store(fos, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
