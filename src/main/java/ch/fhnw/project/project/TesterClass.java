package ch.fhnw.project.project;
import ch.fhnw.project.project.DataModel.DataContainer;
import ch.fhnw.project.project.input.TextFileReader;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class TesterClass extends Application {
    public static File file;

    /*public static File FileChooser() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Chose a File");
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("TEXT files", "*.txt");

        FileChooser.ExtensionFilter extFilter1 =
                new FileChooser.ExtensionFilter("LIN files", "*.lin");

        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.getExtensionFilters().add(extFilter1);

        File file = fileChooser.showOpenDialog(null);
        if (file!= null) {
            return file;
        } else {
            FileChooser();
        }

        return file;
    }*/


    @Override
    public void start(final Stage primaryStage) {

       TextFileReader textFileReader = new TextFileReader();
        textFileReader.readFile(primaryStage);
        textFileReader.print();

        //List<DataContainer>dataContainerList = textFileReader.getDataContainerList();





        //StackPane pane = new StackPane(MainPane.createMainPain(dataContainerList));
        //Scene scene = new Scene(pane);
        //primaryStage.setScene(scene);
        //primaryStage.show();




    }


    public static void main(String[] args) {
        launch(args);
    }


}


