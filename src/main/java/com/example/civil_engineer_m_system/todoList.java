package com.example.civil_engineer_m_system;

import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.CheckBox;

public class todoList {

    @FXML
    private CheckBox tickingCheckBox;

    @FXML
    private PieChart pieChart;

    @FXML
    public void initialize() {
        // Initialize the pie chart with an empty data
        PieChart.Data slice = new PieChart.Data("Unchecked", 0);
        pieChart.getData().add(slice);

        // Listener for the tickingCheckBox to update the pie chart
        tickingCheckBox.setOnAction(event -> {
            if (tickingCheckBox.isSelected()) {
                // If checkbox is checked, fill the pie chart to 100%
                slice.setPieValue(100);
                slice.setName("Checked");
            } else {
                // If checkbox is unchecked, reset the pie chart
                slice.setPieValue(0);
                slice.setName("Unchecked");
            }
        });
    }
}
