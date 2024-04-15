package wajda9.dev.pizzario;
import java.io.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Rectangle2D;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import javafx.stage.FileChooser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
public class RestaurantMenu extends Application {
    Double total = 0.0;
    String Fullorder="";
    @Override
    public void start(Stage stage) {

        Label titleLabel = new Label("Restaurant Menu");
        titleLabel.setFont(new Font("Arial", 20));
        titleLabel.setTextFill(Color.GREEN);

        Label foodTypeLabel = new Label("Food Type:");
        foodTypeLabel.setTextFill(Color.GREEN);

        ChoiceBox<String> foodTypeChoiceBox = new ChoiceBox<>();
        foodTypeChoiceBox.getItems().addAll("Pizza", "Calzoni", "Pasta", "Risotto", "Dolce", "Gelato", "Juice");
        foodTypeChoiceBox.setStyle("-fx-background-color: GREEN; -fx-text-fill: green;");

        Label foodItemLabel = new Label("Food Item:");
        foodItemLabel.setTextFill(Color.GREEN);

        ChoiceBox<String> foodItemChoiceBox = new ChoiceBox<>();
        foodItemChoiceBox.setStyle("-fx-background-color: GREEN; -fx-text-fill: green;");

        Label toppingsLabel = new Label("Toppings:");
        toppingsLabel.setTextFill(Color.GREEN);

        CheckBox pepperoniBox = new CheckBox("Pepperoni");
        pepperoniBox.setTextFill(Color.GREEN);

        CheckBox mushroomsBox = new CheckBox("Mushrooms");
        mushroomsBox.setTextFill(Color.GREEN);

        CheckBox olivesBox = new CheckBox("Olives");
        olivesBox.setTextFill(Color.GREEN);

        CheckBox onionsBox = new CheckBox("Onions");
        onionsBox.setTextFill(Color.GREEN);

        TextArea orderTextArea = new TextArea();
        orderTextArea.setEditable(false);
        orderTextArea.setStyle("-fx-control-inner-background: white;");

        Button orderButton = new Button("Order");
        orderButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        orderButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String foodType = foodTypeChoiceBox.getValue();
                String foodItem = foodItemChoiceBox.getValue();
                String toppings = "";
                if (foodType.equals("Pizza") || foodType.equals("Calzoni")) {
                    total += 12.0;
                    if (pepperoniBox.isSelected()) {
                        toppings += "Pepperoni, ";
                        total+=1.2;
                    }
                    if (mushroomsBox.isSelected()) {
                        toppings += "Mushrooms, ";
                        total+=1.2;
                    }
                    if (olivesBox.isSelected()) {
                        toppings += "Olives, ";total+=1.2;
                    }
                    if (onionsBox.isSelected()) {
                        toppings += "Onions, ";
                        total+=1.2;
                    }
                }

                String order = "You ordered a " + foodItem + " " + foodType;
                Fullorder+=order+" and ";
                orderTextArea.setText(order);
                String orderSocket = foodItem+"-"+foodType+"-"+ toppings+"-"+total;

                if (!toppings.equals("")) {
                    order += " with " + toppings;
                }
                orderTextArea.setText(order+" TOTAL:"+total);

                System.out.println("TRYING TO CONNECT ....");
                try {
                    int PORT = 9002 ;
                    String HOST = "192.168.5.223";
                    try (Socket s = new Socket(HOST, PORT)) {
                        System.out.println("[CONNECTED ON " + PORT + " TO " + HOST + "]");
                        OutputStream output = s.getOutputStream();
                        PrintWriter writer = new PrintWriter(output, true);

                        // Send data to server
                        writer.println(orderSocket);

                    }
                    // sending order
                    System.out.println(orderSocket);


                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });


        Button printbutton = new Button("Print Order");
        printbutton.setStyle("-fx-background-color: green; -fx-text-fill: white;");
        printbutton.setOnAction((ActionEvent event) -> {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage(new PDRectangle(1200,400));
            document.addPage(page);

            try {
                PDPageContentStream contentStream = new PDPageContentStream(document, page);
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.newLineAtOffset(200,30 );
                contentStream.showText(Fullorder+" the total is :"+total+"Dt");
                contentStream.endText();
                contentStream.close();

                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save Order PDF");
                fileChooser.setInitialFileName("order.pdf");
                File file = fileChooser.showSaveDialog(stage);

                if (file != null) {
                    document.save(file);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    document.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        foodTypeChoiceBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                String foodType = foodTypeChoiceBox.getValue();
                boolean showToppings = foodType.equals("Pizza") || foodType.equals("Calzoni");

                toppingsLabel.setVisible(showToppings);
                pepperoniBox.setVisible(showToppings);
                mushroomsBox.setVisible(showToppings);
                olivesBox.setVisible(showToppings);
                onionsBox.setVisible(showToppings);
                foodItemChoiceBox.getItems().clear();
                if (foodType.equals("Pizza")) {
                    foodItemChoiceBox.getItems().addAll("Margherita", "Pepperoni", "Hawaiian", "Meat Lovers", "Vegetarian");
                } else if (foodType.equals("Calzoni")) {
        total +=28.7;
                    foodItemChoiceBox.getItems().addAll("Meat", "Vegetarian");
                } else if (foodType.equals("Pasta")) {
        total +=28.7;
                    foodItemChoiceBox.getItems().addAll("Spaghetti Bolognese", "Carbonara", "Pesto", "Arrabbiata");
                } else if (foodType.equals("Risotto")) {
        total +=28.7;
                    foodItemChoiceBox.getItems().addAll("Mushroom", "Seafood", "Chicken", "Vegetable");
                } else if (foodType.equals("Dolce")) {
        total +=28.7;
                    foodItemChoiceBox.getItems().addAll("Tiramisu", "Panna Cotta", "Chocolate Cake", "Cheesecake");
                } else if (foodType.equals("Gelato")) {
        total +=28.7;
                    foodItemChoiceBox.getItems().addAll("Vanilla", "Chocolate", "Strawberry", "Mint");
                } else if (foodType.equals("Juice")) {
        total +=28.7;
                    foodItemChoiceBox.getItems().addAll("Orange", "Apple", "Pineapple", "Grape");
                }
                foodItemChoiceBox.getSelectionModel().selectFirst();
            }

        });


        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(20);
        gridPane.setVgap(10);
        gridPane.add(titleLabel, 0, 0, 2, 1);
        gridPane.add(foodTypeLabel, 0, 1);
        gridPane.add(foodTypeChoiceBox, 1, 1);
        gridPane.add(foodItemLabel, 0, 2);
        gridPane.add(foodItemChoiceBox, 1, 2);
        gridPane.add(toppingsLabel, 0, 3);
        gridPane.add(pepperoniBox, 0, 4);
        gridPane.add(mushroomsBox, 0, 5);
        gridPane.add(olivesBox, 1, 4);
        gridPane.add(onionsBox, 1, 5);
        gridPane.add(orderTextArea, 2, 0, 1, 6);
        gridPane.add(orderButton, 2, 6);
        gridPane.add(printbutton,2,7);

        StackPane stackPane = new StackPane();
        stackPane.setBackground(new Background(new BackgroundFill(new Color(0, 0, 0, 0.7), null, null)));

        stackPane.setBackground(new Background(new BackgroundImage(new Image("file:C:/Users/PCS/Desktop/23-24/TpJava/PizzaRio/src/main/java/wajda9/dev/pizzario/BGimg.jpeg", true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true))));

        stackPane.getChildren().addAll(gridPane);

        Scene scene = new Scene(stackPane, 400, 400);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
