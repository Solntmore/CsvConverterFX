package com.example.csvconverterfx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class FileChooser_2 extends Application {

    private final Converter converter = new Converter();

    public static void main(String args[]) {
        launch(args);
    }


    public void start(Stage stage) {

        try {


            stage.setTitle("1,КИЗ,");

            FileChooser fil_chooser = new FileChooser();
            DirectoryChooser dir_chooser = new DirectoryChooser();


            Label label = new Label("Сначала выберите папку, куда вы хотите сохранять файл, " +
                    "а затем выбирайте нужный файл.");
            label.setFont(new Font("Arial", 15));


            Button chooseButton = new Button("Выберите папку, куда вы хотите сохранять полученный файл.");


            EventHandler<ActionEvent> chooseEvent =
                    new EventHandler<ActionEvent>() {

                        public void handle(ActionEvent e) {

                            // get the file selected
                            File file = dir_chooser.showDialog(stage);

                            if (file != null) {
                                label.setTextFill(Color.web("#000000"));
                                label.setText("Файлы будут сохраняться в папку по адресу " + file.getAbsolutePath()
                                );
                                converter.setOutDirectory(file.getAbsolutePath());
                            }
                        }
                    };

            Button convertButton = new Button("Выберите файл, который хотите конвертировать.");


            EventHandler<ActionEvent> convertEvent = new EventHandler<ActionEvent>() {

                public void handle(ActionEvent e) {
                    if (converter.getOutDirectory() == null) {
                        label.setTextFill(Color.web("#FF0000"));
                        label.setText("Сначала выберите папку сохранения, только потом можно выбрать файл, который вы " +
                                "хотите конвертировать!");
                    } else {
                        File file = fil_chooser.showOpenDialog(stage);

                        if (file != null) {
                            label.setTextFill(Color.web("#008000"));
                            label.setText("Кажется получилось, проверьте результат в папке сохранения, которую вы выбрали " +
                                    "ранее");
                            converter.setInDirectory(file.getAbsolutePath());
                            converter.convertXlsToCsv();
                        }
                    }
                }
            };

            Button openButton = new Button("Открыть получившийся файл.");


            EventHandler<ActionEvent> openEvent =
                    new EventHandler<ActionEvent>() {

                        public void handle(ActionEvent e) {
                            if (converter.getOutDirectory() == null) {
                                label.setTextFill(Color.web("#FF0000"));
                                label.setText("Сначала выберите папку сохранения, только потом можно выбрать файл, который вы " +
                                        "хотите конвертировать!");
                            } else {
                                String path = converter.getOutDirectory() + "\\output.txt";
                                try {
                                    File file = new File(path);
                                    Desktop desktop = Desktop.getDesktop();
                                    desktop.open(file);
                                } catch (IOException ex) {
                                    label.setTextFill(Color.web("#FF0000"));
                                    label.setText("Сначала выберите папку сохранения, только потом можно выбрать файл, который вы " +
                                            "хотите конвертировать!");

                                    throw new RuntimeException(ex);
                                }
                            }
                        }
                    };

            convertButton.setOnAction(convertEvent);
            chooseButton.setOnAction(chooseEvent);
            openButton.setOnAction(openEvent);

            VBox vbox = new VBox(30, label, chooseButton, convertButton, openButton);

            vbox.setAlignment(Pos.CENTER);

            Scene scene = new Scene(vbox, 800, 500);

            stage.setScene(scene);

            stage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}