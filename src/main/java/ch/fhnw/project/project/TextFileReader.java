package ch.fhnw.project.project.input;
import ch.fhnw.project.project.DataModel.DataContainer;
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
import java.io.FileNotFoundException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Component cfor reading different kind of text-files.
 */
public class TextFileReader {

    private static final Logger LOG = Logger.getLogger(TextFileReader.class.getName());
    private File file;
    private List<DataContainer> dataContainerList;
    private Map<String, DataContainer> dataContainerMap;
    private enum ValidDataType {txt, lin};


    public TextFileReader() {

    }



    /**
     * Creates a new Text File Reader.
     * @param file The file to read.
     * @return The Text file Reader if file exists and file datatype is supported. Otherwise null.
     */
    /*public void TextFileReader create(File file) {
        if (!file.exists())
        {
            return null;
        }
        for (ValidDataType dataType : ValidDataType.values()) {
            if (file.getName().endsWith(dataType.toString())) {
                return new TextFileReader(file);
            }
        }
        LOG.log(Level.WARNING, "Text File Reader could not be created because " +
                "of non-existing or not supported file.");
    }*/

    /**
     * Returns the data containers in a List.
     * If parsing has not been done yet, parsing gets done and the data container are then returned.
     * @return DataContainer in a list.
     */
    private List<DataContainer> getDataContainerList() {
        if (dataContainerList == null) {
            parse();
        }
        return this.dataContainerList;
    }

    /**
     * Returns the data containers in a Map. Key is the name of the variable.
     * If parsing has not been done yet, parsing gets done and the data containers are then returned.
     * @return The data containers in a map.
     */
    private Map<String, DataContainer> getDataContainerMap() {

        if (this.dataContainerMap == null) {
            if (this.dataContainerList == null) {
                parse();
            }

            this.dataContainerMap = new HashMap<>();
            for (DataContainer dataContainer : this.dataContainerList) {
                this.dataContainerMap.put(dataContainer.getVariableName(), dataContainer);
            }
        }
        return this.dataContainerMap;
    }

    /**
     * Parses the file.
     * @return The parsed Data. Null if file type is not supported.
     */
    private List<DataContainer> parse() {
        if (file.getName().endsWith(ValidDataType.txt.toString())) {
            return parseTabDelimitedTextFile();
        } else {
            return parseLineOrientedTextFile();
        }
    }

    private List<DataContainer> parseTabDelimitedTextFile() {
        createEmptyDataContainerForTabDelimitedTextFile();

        try (Scanner scanner = new Scanner(this.file)) {
            // skip column names
            if (!scanner.hasNextLine()) {
                LOG.log(Level.WARNING, "No column names have been set.");
                return null;
            }
            scanner.nextLine();

            int columnPos = 0;
            int amountOfColumns = this.dataContainerList.size();
            while(scanner.hasNextDouble() || scanner.hasNextInt()) {
                // do that for indexing to corresponding column
                if (columnPos == amountOfColumns) {
                    columnPos = columnPos - amountOfColumns;
                }

                if (scanner.hasNextDouble()) {
                    dataContainerList.get(columnPos).addValue(scanner.nextDouble());
                } else {
                    dataContainerList.get(columnPos).addValue((double) scanner.nextInt());
                }
                columnPos++;
            }
            return this.dataContainerList;

        } catch (FileNotFoundException e) {
            LOG.log(Level.WARNING, e.toString());
            return null;

        }
    }

    private List<DataContainer> parseLineOrientedTextFile() {
        try (Scanner scanner = new Scanner(this.file)) {

            this.dataContainerList = new ArrayList<>();

            // get number of variables
            if (!scanner.hasNextLine()) {
                LOG.log(Level.WARNING, "No number of variables have been set!");
                return null;
            }

            int numberOfVariables;
            String nextLine = scanner.nextLine().replace("\n", "");
            try {
                numberOfVariables = Integer.valueOf(nextLine);
            } catch (NumberFormatException e) {
                LOG.log(Level.WARNING, "'" + nextLine + "' is not a valid amount of variables!");
                return null;
            }

            // store variable names
            for (int i = 0; i < numberOfVariables; i++) {
                if (!scanner.hasNextLine()) {
                    LOG.log(Level.WARNING, "Number of variables WRONG!");
                    return null;
                }
                this.dataContainerList.add(i, new DataContainer(scanner.nextLine().replace("\n", "")));
            }

            // check for delimiter
            if (!scanner.hasNextLine()) {
                LOG.log(Level.WARNING, "No delimiter has been set!");
                return null;
            }
            // delimiter has to be single character
            char delimiter = scanner.nextLine().charAt(0);


            // get variable values
            for (int i = 0; i < numberOfVariables; i++) {

                if (!scanner.hasNextLine()) {
                    LOG.log(Level.WARNING, "Amount of estimated variables:'" + numberOfVariables + "' is wrong!");
                    return null;
                }

                nextLine = scanner.nextLine().replace("\n", "");
                char[] nextLineCharArray = nextLine.toCharArray();
                int startIndex = 0;
                for (int j = 0; j < nextLine.length(); j++) {
                    // check if char equals delimiter or if at last position of the line
                    if (nextLineCharArray[j] == delimiter || j == nextLine.length()-1) {
                        String substring = nextLine.substring(startIndex, j);
                        this.dataContainerList.get(i).addValue(Double.parseDouble(substring));
                        startIndex = j+1;
                    }
                }

            }

            return this.dataContainerList;

        } catch (FileNotFoundException e) {
            LOG.log(Level.WARNING, e.toString());
            return null;
        }
    }

    private void createEmptyDataContainerForTabDelimitedTextFile() {
        this.dataContainerList = new ArrayList<>();
        try (Scanner scanner = new Scanner(this.file)) {
            scanner.useDelimiter("\t");
            while(scanner.hasNext()) {
                String next = scanner.next();
                if (next.contains("\t")) {
                    dataContainerList.add(new DataContainer(next.substring(0, next.indexOf("\t"))));
                } else if (next.contains("\n")) {
                    dataContainerList.add(new DataContainer(next.substring(0, next.indexOf("\n"))));
                    break;
                } else {
                    dataContainerList.add(new DataContainer(next));
                }
            }
        } catch (FileNotFoundException e) {
            LOG.log(Level.WARNING, e.toString());
        }
    }



    public void readFile(Stage primaryStage) {
        primaryStage.setTitle("Extension Filter Example");
        final Label fileLabel = new Label();
        FileChooser fileChooser = new FileChooser();

        Button btn = new Button("Open FileChooser");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                // Show open file dialog
                file = fileChooser.showOpenDialog(primaryStage);
                if (file != null) {
                    fileLabel.setText(file.getPath());
                    chooseFile(fileChooser);
                }

            }
        });


        VBox vBox = new VBox(30);
        vBox.getChildren().addAll(fileLabel, btn);
        vBox.setAlignment(Pos.BASELINE_CENTER);

        StackPane root = new StackPane();
        root.getChildren().add(vBox);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();

    }


    private void chooseFile(FileChooser fileChooser){


            // Set extension filter
            FileChooser.ExtensionFilter extFilter =
                    new FileChooser.ExtensionFilter("TEXT files", "*.txt");

            FileChooser.ExtensionFilter extFilter1 =
                    new FileChooser.ExtensionFilter("LIN files", "*.lin");

            fileChooser.getExtensionFilters().add(extFilter);
            fileChooser.getExtensionFilters().add(extFilter1);


        this.dataContainerMap = getDataContainerMap();
    }

    public Map<String, DataContainer> getDataMap(){
        return dataContainerMap;
    }

    public void print(){

        Set<String> keys = dataContainerMap.keySet();
        System.out.println("X: " + keys);

    }
}

