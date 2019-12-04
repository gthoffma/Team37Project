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
    public final String reportFile = "report.txt";
    public String logString; //holds the log of all the user activity; write this string to the report
    public boolean boundsSet = false;
    public String errorLogString;
    public Button DeleteSingleButton;
    public TextField inputDeleteSingleTextField;
    public TextField inputSingleValueTextField;

    @FXML
    ToggleGroup graphToggleGroup;

    @FXML
    public void initialize() {
        RadioButton selectedRadioButton = (RadioButton) graphToggleGroup.getSelectedToggle();

        updateNumberOfEntries(grades);
        display.setText("Welcome to Grade Analytics!\nPlease set the grade bounds to begin.\nThen, click: Load File");
    }

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
        //remove previous data
        barChart.getData().clear();
        // needed to add this - yAxis Labels weren't showing.
        series1 = new XYChart.Series<>();
        float range = (highBound - lowBound);

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
            float calculatedGrade = g / range;
            if (calculatedGrade >= 0 && calculatedGrade < 0.095) {
                firstDivision++;
            } else if (calculatedGrade >= 0.095 && calculatedGrade < 0.195) {
                secondDivision++;
            } else if (calculatedGrade >= 0.195 && calculatedGrade < 0.295) {
                thirdDivision++;
            } else if (calculatedGrade >= 0.295 && calculatedGrade < 0.395) {
                fourthDivision++;
            } else if (calculatedGrade >= 0.395 && calculatedGrade < 0.495) {
                fifthDivision++;
            } else if (calculatedGrade >= 0.495 && calculatedGrade < 0.595) {
                sixthDivision++;
            } else if (calculatedGrade >= 0.595 && calculatedGrade < 0.695) {
                seventhDivision++;
            } else if (calculatedGrade >= 0.695 && calculatedGrade < 0.795) {
                eighthDivision++;
            } else if (calculatedGrade >= 0.795 && calculatedGrade < 0.895) {
                ninthDivision++;
            } else if (calculatedGrade >= 0.895 && calculatedGrade <= 1) {
                tenthDivision++;
            }

        }
        series1.getData().add(new XYChart.Data<>(firstDivision, "0-9%"));
        series1.getData().add(new XYChart.Data<>(secondDivision, "10-19%"));
        series1.getData().add(new XYChart.Data<>(thirdDivision, "20-29%"));
        series1.getData().add(new XYChart.Data<>(fourthDivision, "30-39%"));
        series1.getData().add(new XYChart.Data<>(fifthDivision, "40-49%"));
        series1.getData().add(new XYChart.Data<>(sixthDivision, "50-59%"));
        series1.getData().add(new XYChart.Data<>(seventhDivision, "60-69%"));
        series1.getData().add(new XYChart.Data<>(eighthDivision, "70-79%"));
        series1.getData().add(new XYChart.Data<>(ninthDivision, "80-89%"));
        series1.getData().add(new XYChart.Data<>(tenthDivision, "90-100%"));
        // yAxis Labels must be set manually because of a bug in JavaFx
        yAxis.setCategories(FXCollections.observableArrayList("0-9%", "10-19%", "20-29%", "30-39%", "40-49%", "50-59%", "60-69%", "70-79%", "80-89%", "90-100%"));
        barChart.getData().add(series1);

    }

    /**
     * Prompts the user to enter a .csv or .txt file of grades. Loading data clears the existing data set.
     */
    public void onLoadDataClicked() {
        //first check if the user has set bounds before loading a file
        if (!boundsSet) {
            display.setText("ERROR: Please set lower and upper bounds for values before loading a file");
            display.setStyle("-fx-text-fill: red ;");
            return;
        }
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
                    if (readCSVFile(file, false)) {
                        display.setText("Data Was Loaded Successfully");
                        display.setStyle("-fx-text-fill: green ;");
                        logString += "Grade Data Loaded From: " + file + "\n";
                    }
                } else if (extension.equals("txt")) {
                    if (readTXTFile(file, false)) {
                        display.setText("Data Was Loaded Successfully");
                        display.setStyle("-fx-text-fill: green ;");
                        logString += "Grade Data Loaded From: " + file + "\n";
                    }
                } else {
                    display.setText("File extension: " + extension + " not recognized");
                    display.setStyle("-fx-text-fill: red ;");
                }
            }
            //if i is less than 0, the file does not have an extension, give user an error message
            else {
                display.setText("ERROR: Please load a .csv or .txt file");
                display.setStyle("-fx-text-fill: red ;");
            }
        }
    }


    /**
     * Reads the grade data from the .csv file.
     *
     * @param file   - The .csv file to read.
     * @param append - whether to append to the data.
     * @return - true if read successfully.
     */
    private boolean readCSVFile(File file, boolean append) {
        ArrayList<Float> tempGrades = new ArrayList<>();
        boolean returnValue = true;
        String line;
        int row = 0;
        int column;
        String cvsSplitBy = ",";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((line = br.readLine()) != null) {
                row++;
                column = 0;
                // remove zero-width space
                line = line.replace("\uFEFF", "");
                String[] gradeLineStringArray = line.split(cvsSplitBy);
                for (String s : gradeLineStringArray) {
                    try {
                        float gradeFloat = Float.parseFloat(s);
                        if (gradeFloat < lowBound || gradeFloat > highBound) {
                            //if data is out of bounds, don't add it
                        } else {
                            tempGrades.add(gradeFloat);
                        }
                    } catch (NumberFormatException e) {
                        if (returnValue) {
                            display.setText("The following error(s) have occurred when reading input:");
                        }
                        display.setText(display.getText() + "\n\tERROR: row: " + row + " column: " +
                                column + " is " + s + ", which is not a float or int");
                        display.setStyle("-fx-text-fill: red;");
                        returnValue = false;
                    }
                    column++;
                }
            }
        } catch (IOException e) {
            display.setText(display.getText() + "\n" + e.getMessage());
            display.setStyle("-fx-text-fill: red;");
            returnValue = false;
        }


        if (!append) {
            grades.clear();
        }
        grades.addAll(tempGrades);
        updateNumberOfEntries(grades);
        populateHistogram(grades, highBound, lowBound);

        return returnValue;
    }

    /**
     * Reads the grade data from the .txt file.
     *
     * @param file - The .txt file to read
     * @return - true if the file was read successfully.
     */
    private boolean readTXTFile(File file, boolean append) {
        ArrayList<Float> tempGrades = new ArrayList<>();
        boolean returnValue = true;
        String line;
        int row = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((line = br.readLine()) != null) {
                row++;
                try {
                    Float gradeFloat = Float.parseFloat(line.trim());
                    tempGrades.add(gradeFloat);
                } catch (NumberFormatException e) {
                    display.setText(display.getText() + "\n\tERROR: row: " + row + " is " + line.trim() + ", which is not a float or int");
                    display.setStyle("-fx-text-fill: red;");
                    returnValue = false;
                }
            }

        } catch (IOException e) {
            display.setText(display.getText() + "\n" + e.getMessage());
            display.setStyle("-fx-text-fill: red;");
            returnValue = false;
        }
        if (!append) {
            grades.clear();
        }
        grades.addAll(tempGrades);
        updateNumberOfEntries(grades);
        populateHistogram(grades, highBound, lowBound);

        return returnValue;
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
        boundsSet = true;
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
        if (!boundsSet) {
            display.setText("ERROR: Please set lower and upper bounds for values before entering a value");
            display.setStyle("-fx-text-fill: red ;");
            return;
        }
        float tempGrade;
        try {
            tempGrade = Float.parseFloat(inputSingleValueTextField.getText());
            if (tempGrade < lowBound || tempGrade > highBound) {
                display.setText("ERROR: " + tempGrade + " is not within set bounds");
                display.setStyle("-fx-text-fill: red ;");
            } else {
                display.setText("Grade: " + tempGrade + " successfully added.");
                grades.add(tempGrade);
                updateNumberOfEntries(grades);
                populateHistogram(grades, highBound, lowBound);
            }
        } catch (NumberFormatException e) {
            display.setText("ERROR: " + inputSingleValueTextField.getText() + " is not recognized as a valid number (float or int)");
            display.setStyle("-fx-text-fill: red ;");
        }
        inputSingleValueTextField.clear();
    }

    /**
     * Prompts the user to enter a .csv or .txt file of grades. Appending data does not clear the existing data set.
     */
    public void onAppendDataClicked() {
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
                    if (readCSVFile(file, true)) {
                        display.setText("Data Was Appended Successfully");
                        display.setStyle("-fx-text-fill: green ;");
                        logString += "Grade Data Loaded From: " + file + "\n";
                    }
                } else if (extension.equals("txt")) {
                    if (readTXTFile(file, true)) {
                        display.setText("Data Was Appended Successfully");
                        display.setStyle("-fx-text-fill: green ;");
                        logString += "Grade Data Loaded From: " + file + "\n";
                    }
                } else {
                    display.setText("File extension: " + extension + " not recognized");
                    display.setStyle("-fx-text-fill: red ;");
                }
            }
            //if i is less than 0, the file does not have an extension, give user an error message
            else {
                display.setText("ERROR: Please load a .csv or .txt file");
                display.setStyle("-fx-text-fill: red ;");
            }
        }
    }

    // TODO Implement onCreateReportsClicked
    // 3 Reports Needed:
    // 1. Display Data in the GUI in 4 columns in descending order
    // 2. Error Log
    // 3. External report.txt file
    public void onCreateReportsClicked() {
        ArrayList<String> choices = new ArrayList<>();
        String errorLog = "Error Log";
        String dataReport = "DataReport";
        String activityReport = "Activity Report (file creation)";

        choices.add(errorLog);
        choices.add(dataReport);
        choices.add(activityReport);

        ChoiceDialog<String> dialog = new ChoiceDialog<>("Data Report", choices);

        dialog.setTitle("Report Dialog");
        dialog.setHeaderText("What Type Of Report Would You Like To Create?");
        dialog.setContentText("Choose Your Report:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(s -> {
            switch (s) {
                case "Data Report":
                    // Create Data Report
                    System.out.println(s);
                    break;
                case "Error Log":
                    // Create Error Log
                    System.out.println(s);
                    break;
                case "Activity Report (file creation)":
                    // Create Activity Report
                    System.out.println(s);
                    break;
            }
        });


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

    public void onDeleteSingleClicked() {
        float removeFloat;
        try {
            removeFloat = Float.parseFloat(inputSingleValueTextField.getText());
            removeValueFromArray(removeFloat);
        } catch (NumberFormatException e) {
            display.setText(inputDeleteSingleTextField.getText() + " is not a valid number");
            display.setStyle("-fx-text-fill: red ;");
        }
        inputDeleteSingleTextField.clear();
    }

    public void removeValueFromArray(float value) {
        boolean inArray = grades.remove(value);
        if (inArray) {
            display.setText("The value " + value + " was successfully removed from the grades list");
            display.setStyle("-fx-text-fill: green ;");
            updateNumberOfEntries(grades);
            populateHistogram(grades, highBound, lowBound);
        } else {
            display.setText("The value " + value + " was not found in the grades list");
            display.setStyle("-fx-text-fill: red ;");
        }
    }

    // Helper method to print the grade array to the console - can be deleted before final submission.
    private void printGrades(ArrayList<Float> grades) {
        for (Float g : grades) {
            System.out.println(g + " ");
        }
    }
}



