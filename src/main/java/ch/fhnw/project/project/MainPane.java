package ch.fhnw.project.project;

import ch.fhnw.project.project.DataModel.DataContainer;
import javafx.geometry.Insets;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Stack;

/**
 * Created by TheGod on 23.05.16.
 */
public class MainPane {

    public static Pane createMainPain(List<DataContainer> dataContainerList){

        VBox mainBox = new VBox();
        mainBox.getChildren().addAll(ScatterPlot.creatterScatterPane(dataContainerList),Histogramm.createHistogram(dataContainerList));
        mainBox.setPadding(new Insets(5,5,5,5));
        mainBox.setSpacing(10);

        StackPane mainPain = new StackPane();
        mainPain.getChildren().addAll(mainBox);

        return mainPain;
    }

}
