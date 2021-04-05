package src.sample;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AboutBox {
    public static void display(String title, String message1, String message2, String message3, String message4, String message5){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        TextFlow textFlow = new TextFlow();
        Text first = new Text(message1 + "\n");
        first.setFont(Font.font("Helvetica", FontWeight.BOLD, 40));
        Text second = new Text(message2+"\n"+message3+"\n"+message4+"\n"+message5);
        second.setFont(Font.font("Helvetica", FontWeight.NORMAL, 15));
        textFlow.getChildren().addAll(first, second);
        Button closeButton = new Button("Ok");
        closeButton.setOnAction(e -> window.close());
        VBox layout = new VBox(20);
        layout.getChildren().addAll(textFlow, closeButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.setResizable(false);
        window.showAndWait();
    }
}
