package src.sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import static src.sample.Controller.currentLocale;

public class FNotepad extends BaseView {

    public static void main(String[] args) {
        new FNotepad();
    }

    public static FXMLLoader loader;
    static Stage window;
    static Scene scene1, scene2;
    private static Locale locale = new Locale("en");


    public FNotepad() {
        Application app = new Application() {
            @Override
            public void start(Stage stage) throws Exception {
                window = stage;


                Label label1 = new Label("Welcome to FNotepad!");
                ChoiceBox<String> language = new ChoiceBox<>(FXCollections.observableArrayList(
                        "English", "Deutsch", "Italiano", "Francais")
                );
                ChoiceBox<String> fullscreen = new ChoiceBox<>(FXCollections.observableArrayList(
                        "Fullscreen", "Windowed")
                );
                fullscreen.setValue("Fullscreen");
                language.setValue("English");
                language.setOnAction(e -> {
                    if (language.getValue().equals("English")) {
                        window.setTitle("Language - FNotepad");
                        locale = new Locale("en");
                        fullscreen.setItems(FXCollections.observableArrayList(
                                "Fullscreen", "Windowed"));
                        fullscreen.setValue("Fullscreen");
                        label1.setText("Welcome to FNotepad!");
                    }
                    if (language.getValue().equals("Deutsch")) {
                        window.setTitle("Sprache - FNotepad");
                        locale = new Locale("de");
                        fullscreen.setItems(FXCollections.observableArrayList(
                                "Vollbild", "Fenster Modus"));
                        fullscreen.setValue("Vollbild");
                        label1.setText("Willkommen im FNotepad!");
                    }
                    if (language.getValue().equals("Italiano")) {
                        window.setTitle("Lingua - FNotepad");
                        locale = new Locale("it");
                        fullscreen.setItems(FXCollections.observableArrayList(
                                "a schermo interio", "finestrato"));
                        fullscreen.setValue("a schermo interio");
                        label1.setText("Benvenuto in FNotepad!");
                    }
                    if (language.getValue().equals("Francais")) {
                        window.setTitle("Langue - FNotepad");
                        locale = new Locale("fr");
                        fullscreen.setItems(FXCollections.observableArrayList(
                                "Plain écran", "Mode fenêtre"));
                        fullscreen.setValue("Plain écran");
                        label1.setText("Benvenuto in FNotepad!");
                    }
                });
                language.setTooltip(new Tooltip("Select the Language"));
                Button button1 = new Button("Choose Language");
                button1.setOnAction(e -> {
                    try {
                        scene2 = FNotepad(locale);
                        window.setScene(scene2);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                });

                Button close = new Button("Cancel");
                close.setOnAction(e -> close());


                VBox layout1 = new VBox(20);
                layout1.getChildren().addAll(label1, language, fullscreen, button1, close);
                layout1.setAlignment(Pos.CENTER);
                scene1 = new Scene(layout1);
                window.setScene(scene1);
                window.setTitle("Language - FNotepad");
                window.setMaximized(true);
                window.show();
                window.setOnCloseRequest(e -> {
                    e.consume();
                    close();
                });

            }

            private Scene FNotepad(Locale locale) throws IOException {
                loader = new FXMLLoader(getClass().getResource("FNotepad.fxml"), ResourceBundle.getBundle("sample.Main", locale));
                currentLocale = locale;
                return new Scene(loader.load());
            }

            public  void close() {
                if (ConfirmBox.display("Close?", "Are you sure, you want to close this application??")) window.close();
            }
        };
        ;
    }
}






