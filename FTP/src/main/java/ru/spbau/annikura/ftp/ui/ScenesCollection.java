package ru.spbau.annikura.ftp.ui;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import ru.spbau.annikura.service.ServerFile;

import javax.xml.ws.Action;
import javax.xml.ws.Holder;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Optional;

/**
 * Scenes creator class
 */
public class ScenesCollection {
    private final static String MAIN_CSS = "-fx-background-color: #0048ad";
    private final static int BUTTONS_WIDTH = 150;
    private final static int BUTTONS_HEIGHT = 25;

    public static Scene newLogInScene(final double width, final double height, @NotNull final Stage stage) {
        VBox body = new VBox();

        VBox vBox = new VBox(10);
        final TextField portField = new TextField();
        portField.setPromptText("Port number");
        final TextField serverField = new TextField();
        serverField.setPromptText("Server url");
        Button okButton = new Button("Next");

        vBox.getChildren().addAll(portField, serverField, okButton);
        vBox.setAlignment(Pos.CENTER);
        vBox.setMaxWidth(300);

        body.setAlignment(Pos.CENTER);
        body.getChildren().addAll(vBox);

        okButton.setOnAction(event -> {
            String port = portField.getCharacters().toString();
            String url = serverField.getCharacters().toString();

            boolean portIsValid = port.length() <= 5 && port.matches("\\d+");
            if (!portIsValid) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Invalid port");
                alert.setHeaderText("Port is invalid. Please, try again");
                alert.setContentText("Port should be an integer from 0 to 65535.");
                alert.showAndWait();
                return;
            }

            FileDataController controller;
            try {
                controller = new FileDataController(Integer.valueOf(port), url);
            } catch (UnknownHostException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("IP address of the host could not be determined");
                alert.showAndWait();
                return;
            } catch (IOException e) {
                // Connection failure
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Connection Failure");
                alert.setHeaderText(e.getMessage());
                alert.setContentText("Try again?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get().equals(ButtonType.OK)) {
                    okButton.getOnAction().handle(event);
                }
                return;
            }
            stage.setScene(ScenesCollection.newClientScene(width, height, controller, stage));
        });
        return new Scene(body, width, height);
    }

    public static Scene newClientScene(double width, double height,
                                       @NotNull FileDataController controller,
                                       @NotNull Stage stage) {
        ListView<Text> listView = new ListView<>(FXCollections.observableArrayList());
        Holder<ServerFile[]> filesHolder = new Holder<>(new ServerFile[0]);

        Text currentDirectory = new Text("Current directory:");
        TextField directoryField = new TextField();
        directoryField.setMinWidth(500);
        directoryField.setPromptText("Enter directory path");
        Button goToDirectoryPathButton = new Button("Go");
        goToDirectoryPathButton.setOnAction(event -> {
            try {
                controller.moveOnAbsolutePath(directoryField.getCharacters().toString());
                filesHolder.value = controller.getFiles();
            } catch (IOException e) {
                newIOError(e.getMessage()).showAndWait();
                return;
            }
            setFileList(listView, filesHolder.value);
        });

        listView.setOnMouseClicked(event -> {
            int index = listView.getFocusModel().getFocusedIndex();
            if (index < 0) {
                return;
            }
            if (index > 0 && !filesHolder.value[index - 1].isDirectory()) {
                FileChooser fileChooser = new FileChooser();
                File file = fileChooser.showSaveDialog(stage);
                if (file == null) {
                    return;
                }
                try {
                    controller.downloadTo(filesHolder.value[index].getPath(), file);
                } catch (IOException e) {
                    newIOError(e.getMessage()).showAndWait();
                }
                return;
            }
            if (index == 0 || filesHolder.value[index - 1].isDirectory()) {
                controller.moveOnRelativePath(listView.getItems().get(index).getText());
                try {
                    filesHolder.value = controller.getFiles();
                } catch (IOException e) {
                    newIOError(e.getMessage()).showAndWait();
                    return;
                }
                setFileList(listView, filesHolder.value);
                directoryField.setText(controller.getPath());
                return;
            }
        });

        HBox headerBox = new HBox(10);
        headerBox.setPadding(new Insets(10, 0, 0, 10));
        headerBox.getChildren().addAll(currentDirectory, directoryField, goToDirectoryPathButton);

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(event -> stage.close());
        Button leaveServerButton = new Button("Leave server");
        leaveServerButton.setOnAction(event -> {
            try {
                controller.closeConnection();
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("An error occurred while closing server connection.");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
            stage.setScene(newLogInScene(width, height, stage));
        });
        HBox buttonsBox = new HBox(10);
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.getChildren().addAll(exitButton, leaveServerButton);

        VBox mainBox = new VBox(10);
        mainBox.getChildren().addAll(headerBox, listView, buttonsBox);

        return new Scene(mainBox, width, height);
    }

    private static void setFileList(ListView listView, ServerFile[] files) {
        Text[] fields = new Text[files.length + 1];
        Color directoryColor = Color.BLUEVIOLET;
        Color fileColor = Color.BLACK;
        fields[0] = new Text("..");
        fields[0].setFill(directoryColor);

        for (int i = 0; i < files.length; i++) {
            ServerFile file = files[i];
            fields[i + 1] = new Text(file.getPath());
            Text field = fields[i + 1];
            field.setText(file.getPath());
            if (file.isDirectory()) {
                field.setFill(directoryColor);
                field.setFont(Font.font("Veranda", FontWeight.BOLD, 14));
            } else {
                field.setFill(fileColor);
            }
        }
        listView.setItems(FXCollections.observableArrayList(fields));
    }

    public static Alert newIOError(@NotNull String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("IO Error");
        alert.setHeaderText("Could not get data");
        alert.setContentText(content);
        return alert;
    }
}
