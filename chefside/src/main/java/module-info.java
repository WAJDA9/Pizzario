module wajda9ni.dev.chefside {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens wajda9ni.dev.chefside to javafx.fxml;
    exports wajda9ni.dev.chefside;
}