import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class Controller {
    public Button button1;
    public Button button2;
    public Button button3;
    public Button button4;
    public Button button5;
    public Button button6;
    public Label boundaryLow;
    public Label boundaryHigh;
    public TextField inputBoundLow;
    public TextField inputBoundHigh;
    public TextField manualInput;
    public Label title;
    public TextArea display;
    public Label manualInputLabel;
    public Label numEntriesLabel;
    public Label meanLabel;
    public Label medianLabel;
    public Label modeLabel;
    public Label highLabel;
    public Label lowLabel;
    public Text numEntries;
    public Text mean;
    public Text median;
    public Text mode;
    public Text high;
    public Text low;
    public BarChart<Number, String> barChart;
    public javafx.scene.layout.GridPane GridPane;
    public ArrayList<Float> grades = new ArrayList<>();
    public float highBound = 100.0f;
    public float lowBound = 0.0f;
    public int numberOfEntries = 0;
    public float meanValue;
    public float medianValue;
    public float modeValue;
    public float highValue;
    public float lowValue;
    public XYChart.Series<Number, String> series1 = new XYChart.Series<>();
    public String reportFile = "report.txt"; // TODO: if this is just a file name, it should be a constant -JC
    public String logString;

    @FXML
    public void initialize() {
        updateNumberOfEntries(grades);
        display.setText("Welcome to Grade Analytics!\nLoad a file or enter grades manually to begin. ");
    }

    /* TODO the report can be written to a file for each method called
     *  for instance, when the clear data method is called, we can write to the
     *  file named report.txt
     * I think a better way is to append to a String instance variable and to write the contents of it to the file only when the
     * Create Report button is clicked. - JC
     */

    //TODO the grades may not be from 0 to 100,
    // we should divide the graph into equal parts from the high value
    // to the low value
    // i.e., if the user set the boundaries from -10 to 120, we could have 10 divisions of 
    // size 13
    // I put together a fix for this, but it needs testing. -JC

    /**
     * Populates the grade histogram.
     *
     * @param grades    - The arrayList of grades
     * @param highBound - The current highBound
     * @param lowBound  - The current lowBound
     */
    private void populateHistogram(ArrayList<Float> grades, float highBound, float lowBound) {
        CategoryAxis yAxis = (CategoryAxis) barChart.getYAxis();
        NumberAxis xAxis = (NumberAxis) barChart.getXAxis();
        xAxis.setLabel("Frequency");
        yAxis.setLabel("Grade Distribution");
        float divisionSize = (highBound - lowBound) / 10.0f;

        int firstDivision = 0;
        int secondDivision = 0;
        int thirdDivision = 0;
        int fourthDivision = 0;
        int fifthDivision = 0;
        int sixthDivision = 0;
        int seventhDivision = 0;
        int eighthDivision = 0;
        int ninthDivision = 0;
        int tenthDivision = 0;
        for (Float g : grades) {
            if (g >= lowBound && g < lowBound + (1 * divisionSize)) {
                firstDivision++;
            } else if (g >= lowBound + (1 * divisionSize) && g < lowBound + (2 * divisionSize)) {
                secondDivision++;
            } else if (g >= lowBound + (2 * divisionSize) && g < lowBound + (3 * divisionSize)) {
                thirdDivision++;
            } else if (g >= lowBound + (3 * divisionSize) && g < lowBound + (4 * divisionSize)) {
                fourthDivision++;
            } else if (g >= lowBound + (4 * divisionSize) && g < lowBound + (5 * divisionSize)) {
                fifthDivision++;
            } else if (g >= lowBound + (5 * divisionSize) && g < lowBound + (6 * divisionSize)) {
                sixthDivision++;
            } else if (g >= lowBound + (6 * divisionSize) && g < lowBound + (7 * divisionSize)) {
                seventhDivision++;
            } else if (g >= lowBound + (7 * divisionSize) && g < lowBound + (8 * divisionSize)) {
                eighthDivision++;
            } else if (g >= lowBound + (8 * divisionSize) && g < lowBound + (9 * divisionSize)) {
                ninthDivision++;
            } else if (g >= lowBound + (9 * divisionSize) && g <= highBound) {
                tenthDivision++;
            }

        }
        series1.getData().add(new XYChart.Data<>(firstDivision, (int) lowBound + "-" + (int) (lowBound + (1 * divisionSize))));
        series1.getData().add(new XYChart.Data<>(secondDivision, (int) ((lowBound + (1 * divisionSize)) + 1) + "-" + (int) (lowBound + (2 * divisionSize))));
        series1.getData().add(new XYChart.Data<>(thirdDivision, (int) ((lowBound + (2 * divisionSize)) + 1) + "-" + (int) (lowBound + (3 * divisionSize))));
        series1.getData().add(new XYChart.Data<>(fourthDivision, (int) ((lowBound + (3 * divisionSize)) + 1) + "-" + (int) (lowBound + (4 * divisionSize))));
        series1.getData().add(new XYChart.Data<>(fifthDivision, (int) ((lowBound + (4 * divisionSize)) + 1) + "-" + (int) (lowBound + (5 * divisionSize))));
        series1.getData().add(new XYChart.Data<>(sixthDivision, (int) ((lowBound + (5 * divisionSize)) + 1) + "-" + (int) (lowBound + (6 * divisionSize))));
        series1.getData().add(new XYChart.Data<>(seventhDivision, (int) ((lowBound + (6 * divisionSize)) + 1) + "-" + (int) (lowBound + (7 * divisionSize))));
        series1.getData().add(new XYChart.Data<>(eighthDivision, (int) ((lowBound + (7 * divisionSize)) + 1) + "-" + (int) (lowBound + (8 * divisionSize))));
        series1.getData().add(new XYChart.Data<>(ninthDivision, (int) ((lowBound + (8 * divisionSize)) + 1) + "-" + (int) (lowBound + (9 * divisionSize))));
        series1.getData().add(new XYChart.Data<>(tenthDivision, (int) ((lowBound + (9 * divisionSize)) + 1) + "-" + (int) (highBound)));
        // yAxis Labels must be set manually because of a bug in JavaFx
        yAxis.setCategories(FXCollections.observableArrayList(
                (int) lowBound + "-" + (int) (lowBound + (1 * divisionSize)),
                (int) ((lowBound + (1 * divisionSize)) + 1) + "-" + (int) (lowBound + (2 * divisionSize)),
                (int) ((lowBound + (2 * divisionSize)) + 1) + "-" + (int) (lowBound + (3 * divisionSize)),
                (int) ((lowBound + (3 * divisionSize)) + 1) + "-" + (int) (lowBound + (4 * divisionSize)),
                (int) ((lowBound + (4 * divisionSize)) + 1) + "-" + (int) (lowBound + (5 * divisionSize)),
                (int) ((lowBound + (5 * divisionSize)) + 1) + "-" + (int) (lowBound + (6 * divisionSize)),
                (int) ((lowBound + (6 * divisionSize)) + 1) + "-" + (int) (lowBound + (7 * divisionSize)),
                (int) ((lowBound + (7 * divisionSize)) + 1) + "-" + (int) (lowBound + (8 * divisionSize)),
                (int) ((lowBound + (8 * divisionSize)) + 1) + "-" + (int) (lowBound + (9 * divisionSize)),
                (int) ((lowBound + (9 * divisionSize)) + 1) + "-" + (int) (highBound)));
        barChart.getData().add(series1);


    }

    //TODO error handling for file not found, etc.
    // FYI - There won't be a file not found error with the file chooser implementation -JC
    public void onLoadDataClicked() {

        FileChooser fileChooser = new FileChooser();
        Stage stage = (Stage) GridPane.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            String extension;
            String fileName = file.getName();
            int i = fileName.lastIndexOf('.');
            if (i > 0) {
                extension = fileName.substring(i + 1);
                if (extension.equals("csv")) {
                    grades.clear();
                    if (readCSVFile(file)) {
                        display.setText("Data Was Loaded Successfully");
                        display.setStyle("-fx-text-fill: green ;");
                        logString += "Grade Data Loaded From: " + file + "\n";
                    }
                } else if (extension.equals("txt")) {
                    grades.clear();
                    if (readTXTFile(file)) {
                        display.setText("Data Was Loaded Successfully");
                        display.setStyle("-fx-text-fill: green ;");
                        logString += "Grade Data Loaded From: " + file + "\n";
                    }
                } else {
                    display.setText("File extension: " + extension + " not recognized");
                    display.setStyle("-fx-text-fill: red ;");
                }
            }
        }
    }

    /**
     * Reads the grade data from the .csv file.
     *
     * @param file - The .csv file to read.
     * @return - true if the file was read successfully.
     */
    private boolean readCSVFile(File file) {
        boolean returnValue = true;
        String line;
        String cvsSplitBy = ",";
        int numberOfLines = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            while ((line = br.readLine()) != null) {
                numberOfLines++;
                // remove zero-width space
                line = line.replace("\uFEFF", "");
                String[] gradeLineStringArray = line.split(cvsSplitBy);
                if (checkLineCSV(gradeLineStringArray, numberOfLines)) {
                    for (String s : gradeLineStringArray) {
                        //TODO parseFloat error handling
                        Float gradeFloat = Float.parseFloat(s);
                        grades.add(gradeFloat);
                    }
                    updateNumberOfEntries(grades);
                    populateHistogram(grades, highBound, lowBound);
                } else {
                    returnValue = false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            returnValue = false;
        }
        return returnValue;
    }

    /**
     * Reads the grade data from the .txt file.
     *
     * @param file - The .txt file to read
     * @return - true if the file was read successfully.
     */
    private boolean readTXTFile(File file) {
        boolean returnValue = true;
        String line;
        int numberOfLines = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            while ((line = br.readLine()) != null) {
                numberOfLines++;
                if (checkLineTXT(line, numberOfLines)) {
                    Float gradeFloat = Float.parseFloat(line.trim());
                    grades.add(gradeFloat);
                } else {
                    returnValue = false;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            returnValue = false;
        }
        if (returnValue) {
            updateNumberOfEntries(grades);
            populateHistogram(grades, highBound, lowBound);
        }
        return returnValue;
    }

    // TODO Implement checkLine to ensure values in each line of the file are valid
    // TODO ie that each entry in the array can be parsed to a float - if not, we have the line number to output the required message.
    private boolean checkLineCSV(String[] line, int numberOfLines) {
        return true;
    }

    // TODO Implement checkLine to ensure values in each line of the file are valid
    // TODO ie that there is 1 entry per line and it can be parsed to a float - if not, we have the line number to output the required message.
    private boolean checkLineTXT(String line, int numberOfLines) {
        return true;
    }

    /**
     * Updates the number of grade entries in the grades ArrayList.
     *
     * @param grades - The ArrayList storing the grades.
     */
    public void updateNumberOfEntries(ArrayList<Float> grades) {
        numberOfEntries = grades.size();
        numEntries.setText(String.valueOf(numberOfEntries));
    }

    /**
     * Sets the upper and lower bounds when the user clicks the "Set Bounds" button.
     */
    public void onSetBoundsClicked() {
        String highValueText = inputBoundHigh.getText();
        String lowValueText = inputBoundLow.getText();
        if (setUpperBound(highValueText) && setLowerBound(lowValueText)) {
            display.setText("Lower Bound Set To: " + lowValueText + "\n" + "Upper Bound Set to: " + highValueText);
            display.setStyle("-fx-text-fill: green;");
            logString += "Lower Bound Set To: " + lowValueText + "\n";
            logString += "Upper Bound Set To: " + highValueText + "\n";
        }
    }

    /**
     * Tests the upper bound value entered to ensure it can be parsed to a float.
     *
     * @param highValueText - The value entered into the inputBoundHigh TextField.
     * @return - true if the value entered can be converted into a valid float.
     */
    private boolean setUpperBound(String highValueText) {
        boolean returnValue = true;
        float highValueFloat;
        try {
            highValueFloat = Float.parseFloat(highValueText);
            highBound = highValueFloat;
        } catch (NumberFormatException e) {
            display.setText(e.getMessage());
            display.setStyle("-fx-text-fill: red;");
            returnValue = false;
        }
        return returnValue;
    }

    /**
     * Tests the lower bound value entered to ensure it can be parsed to a float.
     *
     * @param lowValueText - The value entered into the inputBoundLow TextField.
     * @return - true if the value entered can be converted into a valid float.
     */
    private boolean setLowerBound(String lowValueText) {
        boolean returnValue = true;
        float lowValueFloat;
        try {
            lowValueFloat = Float.parseFloat(lowValueText);
            lowBound = lowValueFloat;
        } catch (NumberFormatException e) {
            display.setText(e.getMessage());
            display.setStyle("-fx-text-fill: red ;");
            returnValue = false;
        }
        return returnValue;

    }

    // TODO Implement Manual Entry Clicked
    public void onManualEntryClicked() {

    }

    // TODO Implement onAppendDataClicked
    public void onAppendDataClicked() {

    }

    // TODO Implement onCreateReportClicked
    public void onCreateReportClicked(ArrayList<Float> grades) {

    }

    //TODO Implement calculate mean
    public void calculateMean(ArrayList<Float> grades) {

    }

    //TODO Implement calculate median
    public void calculateMedian(ArrayList<Float> grades) {

    }

    // TODO Implement calculate Mode
    public void calculateMode(ArrayList<Float> grades) {

    }

    // TODO Implement calculate high value
    public void calculateHighValue(ArrayList<Float> grades) {

    }

    // TODO Implement calculate low value
    public void calculateLowValue(ArrayList<Float> grades) {

    }

    /**
     * Prompts the user to confirm deletion of all grade data.
     */
    public void onDeleteDataClicked() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Delete All Data");
        alert.setContentText("Are you sure you want to delete all grade data?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == ButtonType.OK) {
                // ... user chose OK
                grades.clear();
                populateHistogram(grades, highBound, lowBound);
                updateNumberOfEntries(grades);
                display.setText("Grade Data Deleted");
                display.setStyle("-fx-text-fill: black");
            }
        }

    }

    // Helper method to print the grade array to the console - can be deleted before final submission.
    private void printGrades(ArrayList<Float> grades) {
        for (Float g : grades) {
            System.out.println(g + " ");
        }
    }
}



