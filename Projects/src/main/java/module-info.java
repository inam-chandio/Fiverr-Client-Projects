module com.example.projects {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    // Export your package to allow JavaFX to access it
    exports com.project.projects to javafx.graphics;
}
