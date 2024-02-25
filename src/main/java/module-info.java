module com.example.civil_engineer_m_system {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.civil_engineer_m_system to javafx.fxml;
    exports com.example.civil_engineer_m_system;
}