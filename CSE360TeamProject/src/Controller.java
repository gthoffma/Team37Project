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

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
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
    public boolean modeValid = false;
    public float modeValue;
    public float highValue;
    public float lowValue;
    public XYChart.Series<Number, String> series1 = new XYChart.Series<>();
    public final String reportFile = "report.txt";
    public String logString = "ACTIVITY:\n\n";
    public boolean boundsSet = false;
    public String errorLogString = "ERRORS:\n\n";
    public Button DeleteSingleButton;
    public TextField inputSingleValueTextField;
    public final String welcomeText = "Welcome to Grade Analytics!\nPlease set the grade bounds by clicking the " +
            "Set Bounds button." + "\nThen, click: Load File";
    @FXML
    ToggleGroup graphToggleGroup;

    /**
     * initialize the graph with an empty graph and welcome user
     */
    @FXML
    public void initialize() {
        updateNumberOfEntries(grades);
        display.setText(welcomeText);
        graphToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (getSelectedRadioButton()) {
                populateDistributionHistogram(grades, highBound, lowBound);
            } else {
                populateAverageHistogram(grades, highBound, lowBound);
            }
        });
    }


    /**
     * returns true if the user selects graph by distribution and false
     * if the user selects graph by average
     */
    private boolean getSelectedRadioButton() {
        boolean returnValue = true;
        RadioButton selectedRadioButton = (RadioButton) graphToggleGroup.getSelectedToggle();
        if (selectedRadioButton.idProperty().getValue().equals("radio2")) {
            returnValue = false;
        }
        return returnValue;
    }

    /**
     * Populates the histogram of average grade per grade division.
     *
     * @param grades    - The grades ArrayList.
     * @param highBound - The highBound.
     * @param lowBound  - The lowBound.
     */
    private void populateAverageHistogram(ArrayList<Float> grades, float highBound, float lowBound) {
        //remove previous data
        barChart.getData().clear();
        CategoryAxis yAxis = (CategoryAxis) barChart.getYAxis();
        NumberAxis xAxis = (NumberAxis) barChart.getXAxis();
        xAxis.setLabel("Average Score");
        yAxis.setLabel("Grade Distribution");

        // needed to add this - yAxis Labels weren't showing.
        series1 = new XYChart.Series<>();
        float range = (highBound - lowBound);

        //nthDivision is the number of entries in the data set that are between
        // (n-1)*10% to (n)*10%. I.e., the first division holds the number of entries
        // that are between 0% and 10% of the maximum possible grade
        //nthDivisionSum is the sum of all the percentages in the division
        float firstDivision = 0.0f;
        float firstDivisionSum = 0.0f;
        float secondDivision = 0.0f;
        float secondDivisionSum = 0.0f;
        float thirdDivision = 0.0f;
        float thirdDivisionSum = 0.0f;
        float fourthDivision = 0.0f;
        float fourthDivisionSum = 0.0f;
        float fifthDivision = 0.0f;
        float fifthDivisionSum = 0.0f;
        float sixthDivision = 0.0f;
        float sixthDivisionSum = 0.0f;
        float seventhDivision = 0.0f;
        float seventhDivisionSum = 0.0f;
        float eighthDivision = 0.0f;
        float eighthDivisionSum = 0.0f;
        float ninthDivision = 0.0f;
        float ninthDivisionSum = 0.0f;
        float tenthDivision = 0.0f;
        float tenthDivisionSum = 0.0f;
        //loop through all the entries and input the entry into the 
        // appropriate division
        for (Float g : grades) {
            float calculatedGrade;
            //if the lower and upper bounds are the same, every valid grade is 100%
            if (range == 0) {
                calculatedGrade = 1;
            } else {
                calculatedGrade = (g - lowBound) / range;
            }
            //grades are rounded up if the user has a score ending in .5%
            if (calculatedGrade >= 0 && calculatedGrade < 0.095) {
                firstDivision++;
                firstDivisionSum += (calculatedGrade * 100);
            } else if (calculatedGrade >= 0.095 && calculatedGrade < 0.195) {
                secondDivision++;
                secondDivisionSum += (calculatedGrade * 100);
            } else if (calculatedGrade >= 0.195 && calculatedGrade < 0.295) {
                thirdDivision++;
                thirdDivisionSum += (calculatedGrade * 100);
            } else if (calculatedGrade >= 0.295 && calculatedGrade < 0.395) {
                fourthDivision++;
                fourthDivisionSum += (calculatedGrade * 100);
            } else if (calculatedGrade >= 0.395 && calculatedGrade < 0.495) {
                fifthDivision++;
                fifthDivisionSum += (calculatedGrade * 100);
            } else if (calculatedGrade >= 0.495 && calculatedGrade < 0.595) {
                sixthDivision++;
                sixthDivisionSum += (calculatedGrade * 100);
            } else if (calculatedGrade >= 0.595 && calculatedGrade < 0.695) {
                seventhDivision++;
                seventhDivisionSum += (calculatedGrade * 100);
            } else if (calculatedGrade >= 0.695 && calculatedGrade < 0.795) {
                eighthDivision++;
                eighthDivisionSum += (calculatedGrade * 100);
            } else if (calculatedGrade >= 0.795 && calculatedGrade < 0.895) {
                ninthDivision++;
                ninthDivisionSum += (calculatedGrade * 100);
            } else if (calculatedGrade >= 0.895 && calculatedGrade <= 1) {
                tenthDivision++;
                tenthDivisionSum += (calculatedGrade * 100);
            }
        }
        //nthDivisionAverage holds the average grade% in the 10% intervals
        Number firstDivisionAverage = 0.0;
        if (firstDivision != 0) {
            firstDivisionAverage = (firstDivisionSum / firstDivision);
        }
        Number secondDivisionAverage = 0.0;
        if (secondDivision != 0) {
            secondDivisionAverage = (secondDivisionSum / secondDivision);
        }
        Number thirdDivisionAverage = 0.0;
        if (thirdDivision != 0) {
            thirdDivisionAverage = (thirdDivisionSum / thirdDivision);
        }
        Number fourthDivisionAverage = 0.0;
        if (fourthDivision != 0) {
            fourthDivisionAverage = (fourthDivisionSum / fourthDivision);
        }
        Number fifthDivisionAverage = 0.0;
        if (fifthDivision != 0) {
            fifthDivisionAverage = (fifthDivisionSum / fifthDivision);
        }
        Number sixthDivisionAverage = 0.0;
        if (sixthDivision != 0) {
            sixthDivisionAverage = (sixthDivisionSum / sixthDivision);
        }
        Number seventhDivisionAverage = 0.0;
        if (seventhDivision != 0) {
            seventhDivisionAverage = (seventhDivisionSum / seventhDivision);
        }
        Number eighthDivisionAverage = 0.0;
        if (eighthDivision != 0) {
            eighthDivisionAverage = (eighthDivisionSum / eighthDivision);
        }
        Number ninthDivisionAverage = 0.0;
        if (ninthDivision != 0) {
            ninthDivisionAverage = (ninthDivisionSum / ninthDivision);
        }
        Number tenthDivisionAverage = 0.0;
        if (tenthDivision != 0) {
            tenthDivisionAverage = (tenthDivisionSum / tenthDivision);
        }
        //update the graph with the calculated variables
        series1.getData().add(new XYChart.Data<>(firstDivisionAverage, "0-9%"));
        series1.getData().add(new XYChart.Data<>(secondDivisionAverage, "10-19%"));
        series1.getData().add(new XYChart.Data<>(thirdDivisionAverage, "20-29%"));
        series1.getData().add(new XYChart.Data<>(fourthDivisionAverage, "30-39%"));
        series1.getData().add(new XYChart.Data<>(fifthDivisionAverage, "40-49%"));
        series1.getData().add(new XYChart.Data<>(sixthDivisionAverage, "50-59%"));
        series1.getData().add(new XYChart.Data<>(seventhDivisionAverage, "60-69%"));
        series1.getData().add(new XYChart.Data<>(eighthDivisionAverage, "70-79%"));
        series1.getData().add(new XYChart.Data<>(ninthDivisionAverage, "80-89%"));
        series1.getData().add(new XYChart.Data<>(tenthDivisionAverage, "90-100%"));
        // yAxis Labels must be set manually because of a bug in JavaFx
        yAxis.setCategories(FXCollections.observableArrayList("0-9%", "10-19%", "20-29%", "30-39%", "40-49%", "50-59%", "60-69%", "70-79%", "80-89%", "90-100%"));
        barChart.getData().add(series1);
    }

    /**
     * Populates the  grade distribution histogram.
     *
     * @param grades    - The arrayList of grades
     * @param highBound - The current highBound
     * @param lowBound  - The current lowBound
     */
    private void populateDistributionHistogram(ArrayList<Float> grades, float highBound, float lowBound) {
        CategoryAxis yAxis = (CategoryAxis) barChart.getYAxis();
        NumberAxis xAxis = (NumberAxis) barChart.getXAxis();
        xAxis.setLabel("Frequency");
        yAxis.setLabel("Grade Distribution");
        //remove previous data
        barChart.getData().clear();
        // needed to add this - yAxis Labels weren't showing.
        series1 = new XYChart.Series<>();
        float range = (highBound - lowBound);

        //nthDivision holds the number of entries in the (n-1)*10% to (n)*10% division
        // i.e., the first division holds the number of entries with a grade percent between
        // 0% to 10%
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
            //if the lower and upper bounds are the same, every valid grade is 100%
            float calculatedGrade;
            if (range == 0) {
                calculatedGrade = 1;
            } else {
                calculatedGrade = (g - lowBound) / range;
            }
            //grades are rounded up if the user has a score ending in .5%
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
        //populate the graph with the calculated results
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
        StringBuilder sb = new StringBuilder();
        //first check if the user has set bounds before loading a file
        if (!boundsSet) {
            display.setText("ERROR: Please set lower and upper bounds for values before loading a file \n (click the Set Bounds button)");
            display.setStyle("-fx-text-fill: red ;");
            sb.append("ERROR: Please set lower and upper bounds for values before loading a file \n (click the Set Bounds button)\n\n");
            errorLogString += sb.toString();
            return;
        }
        //prompt the user to select a file
        FileChooser fileChooser = new FileChooser();
        Stage stage = (Stage) GridPane.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            //check the fileName extension and load the entries appropriately
            String extension;
            String fileName = file.getName();
            int i = fileName.lastIndexOf('.');
            if (i > 0) {
                extension = fileName.substring(i + 1);
                if (extension.equals("csv")) {
                    if (readCSVFile(file, false)) {
                        errorLogString = "ERRORS:\n";
                        display.setText("Data Was Loaded Successfully");
                        display.setStyle("-fx-text-fill: green ;");
                        logString += "Grade Data Loaded From: " + file + "\n\n";
                    }
                } else if (extension.equals("txt")) {
                    if (readTXTFile(file, false)) {
                        errorLogString = "ERRORS:\n";
                        display.setText("Data Was Loaded Successfully");
                        display.setStyle("-fx-text-fill: green ;");
                        logString += "Grade Data Loaded From: " + file + "\n\n";
                    }
                } else {
                    display.setText("ERROR: File extension: " + extension + " not recognized");
                    display.setStyle("-fx-text-fill: red ;");
                    errorLogString += "ERROR: File extension: " + extension + " not recognized\n\n";
                }
            }
            //if i is less than 0, the file does not have an extension, give user an error message
            else {
                display.setText("ERROR: Please load a .csv or .txt file");
                display.setStyle("-fx-text-fill: red ;");
                errorLogString += "ERROR: Please load a .csv or .txt file\n\n";
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
        StringBuilder sb = new StringBuilder();
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
                //for every entry of grades in the line, parse the values and update the tempGradeArray
                for (String s : gradeLineStringArray) {
                    try {
                        float gradeFloat = Float.parseFloat(s);
                        if (gradeFloat < lowBound || gradeFloat > highBound) {
                            //if it is the first time getting an out of bounds value,
                            // clear the display
                            if (returnValue) {
                                display.setText("");
                            }
                            //if data is out of bounds, don't add it to the temp array
                            display.setText(display.getText() + "\tERROR: row: " + row + " column: " +
                                    column + " is " + s + ", which is out of bounds.\n");
                            display.setStyle("-fx-text-fill: red;");
                            returnValue = false;
                            sb.append("ERROR: row: ").append(row).append(" column: ").append(column).append(" is ").append(s).append(", which is out of bounds\n");
                        } else {
                            //if the data is in the bounds, add it to the temp array
                            tempGrades.add(gradeFloat);
                        }
                    } catch (NumberFormatException e) {
                        //if the value could not be read as a float, do not add values from the file into the
                        // grade array, tell the user of the errors, then set the return value to false
                        if (returnValue) {
                            display.setText("The following error(s) have occurred when reading input:");
                        }
                        display.setText(display.getText() + "\n\tERROR: row: " + row + " column: " +
                                column + " is " + s + ", which is not a float or int");
                        display.setStyle("-fx-text-fill: red;");
                        sb.append("ERROR: row: ").append(row).append(" column: ").append(column).append(" is ").append(s).append(", which is not a float or int\n");
                        returnValue = false;
                    }
                    column++;
                }
            }
        } catch (IOException e) {
            //if the file could not be read, tell the user
            display.setText("ERROR: Unable to read the file provided. " + e.getMessage());
            display.setStyle("-fx-text-fill: red;");
            sb.append("ERROR: Unable to read the file provided. ").append(e.getMessage()).append("\n");
            returnValue = false;
        }
        errorLogString += sb.toString() + "\n";
        if (!append) {
            //if the user has not selected to append the data, clear the previous data
            grades.clear();
        }
        //if all entries were invalid, do not add or calculate anything
        if (tempGrades.size() == 0) {
            return returnValue;
        }
        //add the temp grades array data to the grades array, update calculations and the graphs
        grades.addAll(tempGrades);
        updateNumberOfEntries(grades);
        calculateMean(grades);
        calculateMedian(grades);
        calculateMode(grades);
        calculateHighValue(grades);
        calculateLowValue(grades);
        if (getSelectedRadioButton()) {
            populateDistributionHistogram(grades, highBound, lowBound);
        } else {
            populateAverageHistogram(grades, highBound, lowBound);
        }
        return returnValue;
    }

    /**
     * Reads the grade data from the .txt file.
     *
     * @param file - The .txt file to read
     * @return - true if the file was read successfully.
     */
    private boolean readTXTFile(File file, boolean append) {
        StringBuilder sb = new StringBuilder();
        ArrayList<Float> tempGrades = new ArrayList<>();
        boolean returnValue = true;
        String line;
        int row = 0;
        //if a txt file is being read, each value is assumed to be on
        // its own line
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((line = br.readLine()) != null) {
                row++;
                try {
                    float gradeFloat = Float.parseFloat(line.trim());
                    if (gradeFloat < lowBound || gradeFloat > highBound) {
                        //if it is the first time getting an out of bounds value,
                        // clear the display
                        if (returnValue) {
                            display.setText("");
                        }
                        //if data is out of bounds, don't add it to the temp array
                        display.setText(display.getText() + "\tERROR: row: " + row + " is " + line.trim() +
                                ", which is out of bounds.\n");
                        display.setStyle("-fx-text-fill: red;");
                        returnValue = false;
                        sb.append("ERROR: row: ").append(row).append(" is ").append(line.trim()).append(", which is out of bounds\n");
                    } else {
                        //if the data is in the bounds, add it to the temp array
                        tempGrades.add(gradeFloat);
                    }
                } catch (NumberFormatException e) {
                    if (returnValue) {
                        display.setText("The following error(s) have occurred when reading input:");
                    }
                    display.setText(display.getText() + "\n\tERROR: row: " + row + " is " + line.trim() + ", which is not a float or int");
                    display.setStyle("-fx-text-fill: red;");
                    sb.append("ERROR: row: ").append(row).append(" is ").append(line.trim()).append(", which is not a float or int\n");
                    returnValue = false;
                }
            }
        } catch (IOException e) {
            //if the file could not be read, tell the user
            display.setText("ERROR: Unable to read the file provided. " + e.getMessage());
            display.setStyle("-fx-text-fill: red;");
            sb.append("ERROR: Unable to read the file provided. ").append(e.getMessage()).append("\n");
            returnValue = false;
        }
        errorLogString += sb.toString() + "\n";
        if (!append) {
            //if the user has not selected to append the data, clear the previous data
            grades.clear();
        }
        //if all entries were invalid, do not add or calculate anything
        if (tempGrades.size() == 0) {
            return returnValue;
        }
        //add the temp grades array data to the grades array, update calculations and the graphs
        grades.addAll(tempGrades);
        updateNumberOfEntries(grades);
        calculateMean(grades);
        calculateMedian(grades);
        calculateMode(grades);
        calculateHighValue(grades);
        calculateLowValue(grades);
        if (getSelectedRadioButton()) {
            populateDistributionHistogram(grades, highBound, lowBound);
        } else {
            populateAverageHistogram(grades, highBound, lowBound);
        }

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
        display.clear();
        String highValueText = inputBoundHigh.getText();
        String lowValueText = inputBoundLow.getText();
        //attempt to set the lower and upper bounds
        if (setUpperBound(highValueText) && setLowerBound(lowValueText)) {
            //if the lower bound is greater than the upperbound, tell the user
            if (lowBound > highBound) {
                display.setText("ERROR: Upper bound value cannot be less than lower bound value\n");
                display.setStyle("-fx-text-fill: red;");
                boundsSet = false;
            }
            if (display.getText().equals(welcomeText))
                display.clear();
            display.setText(display.getText() + "Lower Bound Set To: " + lowValueText + "\n" + "Upper Bound Set to: " + highValueText);
            display.setStyle("-fx-text-fill: green;");
            logString += "Lower Bound Set To: " + lowValueText + "\n\n";
            logString += "Upper Bound Set To: " + highValueText + "\n\n";
            if (getSelectedRadioButton()) {
                populateDistributionHistogram(grades, highBound, lowBound);
            } else {
                populateAverageHistogram(grades, highBound, lowBound);
            }
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
            testUpperBoundOnExistingValues(highValueFloat);
        } catch (NumberFormatException e) {
            display.setText("ERROR: " + highValueText + " is not a valid number (int or float).");
            display.setStyle("-fx-text-fill: red;");
            errorLogString += "ERROR: " + highValueText + " is not a valid number (int or float).\n\n";
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
            testLowerBoundOnExistingValues(lowValueFloat);
        } catch (NumberFormatException e) {
            display.setText("ERROR: " + lowValueText + " is not a valid number (int or float).");
            display.setStyle("-fx-text-fill: red ;");
            errorLogString += "ERROR: " + lowValueText + " is not a valid number (int or float).\n\n";
            returnValue = false;
        }
        return returnValue;
    }

    /**
     * Tests existing grades ArrayList for values greater than the newly-entered highBound. Removes them and logs error message.
     *
     * @param highValueFloat - The value of the newly entered highBound.
     */
    private void testUpperBoundOnExistingValues(float highValueFloat) {
        boolean removedValue = false;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < grades.size(); i++) {
            float g = grades.get(i);
            if (grades.get(i) > highValueFloat) {
                removedValue = true;
                grades.remove(g);
                display.setText(display.getText() + "Grade: " + g + " was removed. It is greater than upper bound: " + highValueFloat + "\n\n");
                display.setStyle("-fx-text-fill: red;");
                sb.append("Grade: ").append(g).append(" was removed. It is greater than upper bound: ").append(highValueFloat).append("\n");
                i--;
            }
        }
        if (removedValue) {
            updateNumberOfEntries(grades);
            calculateMean(grades);
            calculateMedian(grades);
            calculateMode(grades);
            calculateHighValue(grades);
            calculateLowValue(grades);

        }
        errorLogString += sb.toString() + "\n";
    }

    /**
     * Tests existing grades ArrayList for values less than the newly-entered lowBound. Removes them and logs error message.
     *
     * @param lowValueFloat - The value of the newly entered lowBound.
     */
    private void testLowerBoundOnExistingValues(float lowValueFloat) {
        boolean removedValue = false;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < grades.size(); i++) {
            float g = grades.get(i);
            if (grades.get(i) < lowValueFloat) {
                removedValue = true;
                grades.remove(g);
                display.setText(display.getText() + "Grade: " + g + " was removed. It is less than lower bound: " + lowValueFloat + "\n\n");
                display.setStyle("-fx-text-fill: red;");
                sb.append("Grade: ").append(g).append(" was removed. It is less than lower bound: ").append(lowValueFloat).append("\n");
                i--;
            }
        }
        if (removedValue) {
            //update the graph
            updateNumberOfEntries(grades);
            calculateMean(grades);
            calculateMedian(grades);
            calculateMode(grades);
            calculateHighValue(grades);
            calculateLowValue(grades);
        }
        errorLogString += sb.toString() + "\n";
    }

    /**
     * Called when user clicks the Add Single Value button.
     */
    public void onManualEntryClicked() {
        //don't let the user add values if the bounds have not been set
        if (!boundsSet) {
            display.setText("ERROR: Please set lower and upper bounds for values before entering a value \n (click the Set Bounds button)");
            display.setStyle("-fx-text-fill: red ;");
            errorLogString += "ERROR: Please set lower and upper bounds for values before entering a value (click the Set Bounds button)\n\n";
            return;
        }
        float tempGrade;
        //try to add the user input to the grades array list
        try {
            tempGrade = Float.parseFloat(inputSingleValueTextField.getText());
            //if the input is out of bounds, tell the user
            if (tempGrade < lowBound || tempGrade > highBound) {
                display.setText("ERROR: " + tempGrade + " is not within set bounds");
                display.setStyle("-fx-text-fill: red ;");
                errorLogString += "ERROR: " + tempGrade + " is not within set bounds\n\n";
            } else {
                //if the input is in bounds, tell the user and update the calculations
                display.setText("Single Value: " + tempGrade + " was successfully added to the grades list.");
                display.setStyle("-fx-text-fill: green ;");
                grades.add(tempGrade);
                updateNumberOfEntries(grades);
                calculateMean(grades);
                calculateMedian(grades);
                calculateMode(grades);
                calculateHighValue(grades);
                calculateLowValue(grades);
                if (getSelectedRadioButton()) {
                    populateDistributionHistogram(grades, highBound, lowBound);
                } else {
                    populateAverageHistogram(grades, highBound, lowBound);
                }
                logString += "Single Value: " + tempGrade + " successfully added to the grades list.\n\n";
            }
        } catch (NumberFormatException e) {
            //if the input could not be recognized as a float, tell the user
            display.setText("ERROR: " + inputSingleValueTextField.getText() + " is not recognized as a valid number (float or int)");
            display.setStyle("-fx-text-fill: red ;");
            errorLogString += "ERROR: " + inputSingleValueTextField.getText() + " is not recognized as a valid number (float or int)\n\n";
        }
        //clear the manual input field after the input has been registered
        inputSingleValueTextField.clear();
    }

    /**
     * Prompts the user to enter a .csv or .txt file of grades. Appending data does not clear the existing data set.
     */
    public void onAppendDataClicked() {
        //first check if the user has set bounds before loading a file
        if (!boundsSet) {
            display.setText("ERROR: Please set lower and upper bounds for values before loading a file \n (click the Set Bounds button)");
            display.setStyle("-fx-text-fill: red ;");
            errorLogString += "ERROR: Please set lower and upper bounds for values before loading a file \n (click the Set Bounds button)\n\n";
            return;
        }
        FileChooser fileChooser = new FileChooser();
        Stage stage = (Stage) GridPane.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            //get the extension of the file and call the appropriate method
            String extension;
            String fileName = file.getName();
            int i = fileName.lastIndexOf('.');
            if (i > 0) {
                // if the data was loaded successfully, tell the user.
                // Since the user has selected to append the data,
                // the read files methods are passed in false to not delete the data
                // before adding data from the file
                extension = fileName.substring(i + 1);
                if (extension.equals("csv")) {
                    if (readCSVFile(file, true)) {
                        display.setText("Data Was Appended Successfully");
                        display.setStyle("-fx-text-fill: green ;");
                        logString += "Grade Data Appended with data from: " + file + "\n\n";
                    }
                } else if (extension.equals("txt")) {
                    if (readTXTFile(file, true)) {
                        display.setText("Data Was Appended Successfully");
                        display.setStyle("-fx-text-fill: green ;");
                        logString += "Grade Data Appended with data from: " + file + "\n\n";
                    }
                } else {
                    display.setText("ERROR: File extension: " + extension + " not recognized");
                    display.setStyle("-fx-text-fill: red ;");
                    errorLogString += "ERROR: File extension: " + extension + " not recognized\n";
                }
            }
            //if i is less than 0, the file does not have an extension, give user an error message
            else {
                display.setText("ERROR: File type not recognized. Please load a .csv or .txt file");
                display.setStyle("-fx-text-fill: red ;");
                errorLogString += "ERROR: File type not recognized. Please load a .csv or .txt file\n";
            }
        }
    }

    /**
     * Creates the 3 Reports Needed:
     * 1. Display Data in the GUI in 4 columns in descending order
     * 2. Error Log
     * 3. External report.txt file
     */
    public void onCreateReportsClicked() {
        ArrayList<String> choices = new ArrayList<>();
        String errorLog = "Error Log";
        String dataReport = "Data Report";
        String activityReport = "Activity Report (file creation)";

        choices.add(errorLog);
        choices.add(dataReport);
        choices.add(activityReport);

        // Present the user the option of either making a GUI of the data in 4 columns,
        // an error log, or an external report.txt file.
        ChoiceDialog<String> dialog = new ChoiceDialog<>(errorLog, choices);

        dialog.setTitle("Report Dialog");
        dialog.setHeaderText("What Type Of Report Would You Like To Create?");
        dialog.setContentText("Choose Your Report:");

        //wait until the user enters a choice and call the appropriate function
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(s -> {
            switch (s) {
                case "Data Report":
                    // Create Data Report
                    createDataReport(grades);
                    break;
                case "Error Log":
                    // Create Error Log
                    display.clear();
                    display.setText(errorLogString);
                    display.setStyle("-fx-text-fill: red;");
                    break;
                case "Activity Report (file creation)":
                    // Create Activity Report
                    createActivityReport();
                    break;
            }
        });
    }

    /**
     * Creates an activity report of the actions performed while the program was running
     * and writes to a report.txt file
     */
    public void createActivityReport() {
        try {
            // Create file
            FileWriter fstream = new FileWriter(reportFile);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(logString);
            //Close the output stream
            out.close();
            display.setText("Activity Report: " + reportFile + " Successfully Created.");
            display.setStyle("-fx-text-fill: green;");
            logString += "Activity Report: " + reportFile + " Successfully Created.\n\n";
        } catch (Exception e) {
            //if the file could not be written to or the bufferedWriter could not start,
            // tell the user the error message
            display.setText("ERROR: There was an error creating the Activity Report: " + e.getMessage());
            display.setStyle("-fx-text-fill: red;");
            errorLogString += "ERROR: There was an error creating the Activity Report: " + e.getMessage() + "\n\n";
        }
    }

    /**
     * Creates the data report - displays grade data in descending order in four columns.
     *
     * @param grades - The grades ArrayList.
     */
    public void createDataReport(ArrayList<Float> grades) {
        display.clear();
        int rows = (int) Math.ceil(grades.size() / 4.0);
        int columns = 4;
        grades.sort(Collections.reverseOrder());

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (i + (rows * j) < grades.size()) {
                    display.setText(display.getText() + grades.get(i + (rows * j)) + "     ");
                }
            }
            display.setText(display.getText() + "\n");
        }
    }

    /**
     * Calculates mean value of grades ArrayList.
     *
     * @param grades - The grades ArrayList.
     */
    public void calculateMean(ArrayList<Float> grades) {
        //check to make sure there have been entries made
        if (grades.size() < 1) {
            meanValue = 0;
            mean.setText("");
            return;
        }
        float sum = 0.0f;
        float meanVal;

        for (Float g : grades) {
            sum += g;
        }
        meanVal = sum / grades.size();
        meanValue = meanVal;
        mean.setText(String.valueOf(meanValue));
    }

    /**
     * Calculates median value of grades ArrayList.
     *
     * @param grades - The grades ArrayList.
     */
    public void calculateMedian(ArrayList<Float> grades) {
        //make sure the array is bigger than size 0
        if (grades.size() < 1) {
            medianValue = 0;
            median.setText("");
            return;
        }
        float medianVal;
        Collections.sort(grades);
        int middle = grades.size() / 2;
        if (grades.size() % 2 == 1) {
            medianVal = grades.get(middle);
        } else {
            medianVal = ((grades.get(middle - 1) + grades.get(middle)) / 2.0f);
        }
        medianValue = medianVal;
        median.setText(String.valueOf(medianValue));

    }

    /**
     * Calculates mode value of grades ArrayList.
     * If every entry is input at most once,
     * there is no mode
     *
     * @param grades - The grades ArrayList.
     */
    public void calculateMode(ArrayList<Float> grades) {
        //check to make sure there are entries made
        if (grades.size() < 1) {
            modeValue = 0;
            mode.setText("");
            return;
        }
        float modeVal = 0;
        int maxCount = 0;
        for (int i = 0; i < grades.size(); i++) {
            int count = 0;
            for (Float grade : grades) {
                if (grade.equals(grades.get(i))) {
                    count++;
                }
            }
            if (count > maxCount) {
                maxCount = count;
                modeVal = grades.get(i);

            }
        }
        if (maxCount > 1) {
            modeValid = true;
            modeValue = modeVal;
            this.mode.setText(String.valueOf(modeValue));
        } else {
            this.mode.setText("No Mode");
        }
    }

    /**
     * Calculates high value of grades ArrayList.
     *
     * @param grades - The grades ArrayList.
     */
    public void calculateHighValue(ArrayList<Float> grades) {
        //check to make sure there are entries made
        if (grades.size() < 1) {
            highValue = 0;
            high.setText("");
            return;
        }
        float highVal = grades.get(0);
        for (Float g : grades) {
            if (g > highVal) {
                highVal = g;
            }
        }
        highValue = highVal;
        high.setText(String.valueOf(highValue));
    }

    /**
     * Calculates low value of grades ArrayList.
     *
     * @param grades - The grades ArrayList.
     */
    public void calculateLowValue(ArrayList<Float> grades) {
        //make sure the array is bigger than size 0
        if (grades.size() < 1) {
            lowValue = 0;
            low.setText("");
            return;
        }
        float lowVal = grades.get(0);
        for (Float g : grades) {
            if (g < lowVal) {
                lowVal = g;
            }
        }
        lowValue = lowVal;
        low.setText(String.valueOf(lowValue));
    }

    /**
     * Prompts the user to confirm deletion of all grade data
     * and deletes the data and calculations if the user selects
     * "OK"
     */
    public void onDeleteDataClicked() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Delete All Data");
        alert.setContentText("Are you sure you want to delete all grade data?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == ButtonType.OK) {
                // ... user chose OK, delete data and reset the calculated values
                grades.clear();
                if (getSelectedRadioButton()) {
                    populateDistributionHistogram(grades, highBound, lowBound);
                } else {
                    populateAverageHistogram(grades, highBound, lowBound);
                }
                updateNumberOfEntries(grades);
                meanValue = 0;
                mean.setText("");
                medianValue = 0;
                median.setText("");
                modeValue = 0;
                mode.setText("");
                highValue = 0;
                high.setText("");
                lowValue = 0;
                low.setText("");
                display.setText("Grade Data Deleted");
                display.setStyle("-fx-text-fill: green");
                logString += "Grade Data Deleted\n\n";
            }
        }
    }

    /**
     * Called when user clicks the: Delete Single Value button.
     */
    public void onDeleteSingleClicked() {
        float removeFloat;
        //try to convert the user input into a float
        try {
            removeFloat = Float.parseFloat(inputSingleValueTextField.getText());
            //attempt to remove the float from the array
            removeValueFromArray(removeFloat);
        } catch (NumberFormatException e) {
            //if the user did not input a float, tell the user the error
            display.setText(inputSingleValueTextField.getText() + " is not a valid number (float or int)");
            display.setStyle("-fx-text-fill: red ;");
            errorLogString += inputSingleValueTextField.getText() + " is not a valid number (float or int)\n";
        }
        //clear the text field after the input has been handled
        inputSingleValueTextField.clear();

    }

    /**
     * Removes the value from the grades ArrayList, logs an error if the value is not in the grades ArrayList.
     *
     * @param value - The value to remove.
     */
    public void removeValueFromArray(float value) {
        //attempt to remove the passed value from the grades list
        boolean inArray = grades.remove(value);
        if (inArray) {
            //if the value was removed, tell the user and update the calculated variables
            display.setText("Single Value: " + value + " was successfully removed from the grades list");
            logString += "Single Value: " + value + " was successfully removed from the grades list.\n\n";
            display.setStyle("-fx-text-fill: green ;");
            updateNumberOfEntries(grades);
            calculateMean(grades);
            calculateMedian(grades);
            calculateMode(grades);
            calculateHighValue(grades);
            calculateLowValue(grades);
            populateDistributionHistogram(grades, highBound, lowBound);
        } else {
            //if the value could not be removed, tell the user
            display.setText("The value " + value + " was not found in the grades list");
            display.setStyle("-fx-text-fill: red ;");
            errorLogString += "The value " + value + " was not found in the grades list\n\n";
        }
    }
}



