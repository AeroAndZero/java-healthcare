module com.project {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires javafx.base;

    opens com.project to javafx.fxml;
    exports com.project;
    exports com.project.classes;
}
