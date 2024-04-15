module wajda9.dev.pizzario {
    requires javafx.controls;
    requires javafx.fxml;
    requires pdfbox;


    opens wajda9.dev.pizzario to javafx.fxml;
    exports wajda9.dev.pizzario;
}