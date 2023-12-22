module com.example.calculatorsimplejava {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.example.calculatorsimplejava to javafx.fxml;
    exports com.example.calculatorsimplejava;
}