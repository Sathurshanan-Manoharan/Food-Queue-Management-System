module com.example.newguiapp {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.example.New to javafx.fxml;
    exports com.example.New;
}