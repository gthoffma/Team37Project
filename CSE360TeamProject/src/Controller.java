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
    public Float highBound = 100.0f;
    public Float lowBound = 0.0f;
    public int numberOfEntries = 0;
    public float meanValue;
    public float medianValue;
    public float modeValue;
    public float highValue;
    public float lowValue;
    public XYChart.Series<Number, String> series1 = new XYChart.Series<>();
    public String reportFile = "report.txt";

    @FXML
    public void initialize() {
        updateNumberOfEntries(grades);
        display.setText("Welcome to Grade Analytics!\nLoad a file or enter grades manually to begin. ");
    }
    
    /* TODO the report can be written to a file for each method called
     *  for instance, when the clear data method is called, we can write to the 
     *  file named report.txt
     */


    private void populateHistogram(ArrayList<Float> grades) {
    	//barChart.getData().removeAll();
        CategoryAxis yAxis = (CategoryAxis) barChart.getYAxis();
        NumberAxis xAxis = (NumberAxis) barChart.getXAxis();
        xAxis.setLabel("Frequency");
        yAxis.setLabel("Grade Distribution");
        
        //gradeDistribution holds an int array representing the 
        // number of inputs in that range
        int[] gradeDistribution = new int[10];

        //initialize the number of grades in each division to 0
        for(int i = 0; i < gradeDistribution.length; i++) {
        	gradeDistribution[i] = 0;
        }

        // string array for distribution labels
        String[] distLabelArr = new String[10];
        for (Float g : grades) {
        	//for each division of 10, if the element g in grades is between the 
        	// values in the division, increase the gradeDistribution at that index
        	// NOTE: integer division is done to prevent the graph from having long
        	// decimal places
            if (g >= lowBound && g < (highBound-lowBound)/10 + lowBound) {
                gradeDistribution[0]++;
            } else if (g >= (highBound-lowBound)/10 + lowBound &&  g < 2*(highBound-lowBound)/10 + lowBound) {
                gradeDistribution[1]++;
            } else if (g >= 2*(highBound-lowBound)/10 + lowBound &&  g < 3*(highBound-lowBound)/10 + lowBound) {
                gradeDistribution[2]++;
            } else if (g >= 3*(highBound-lowBound)/10 + lowBound &&  g < 4*(highBound-lowBound)/10 + lowBound) {
                gradeDistribution[3]++;
            } else if (g >= 4*(highBound-lowBound)/10 + lowBound &&  g < 5*(highBound-lowBound)/10 + lowBound) {
                gradeDistribution[4]++;;
            } else if (g >= 5*(highBound-lowBound)/10 + lowBound &&  g < 6*(highBound-lowBound)/10 + lowBound) {
                gradeDistribution[5]++;
            } else if (g >= 6*(highBound-lowBound)/10 + lowBound &&  g < 7*(highBound-lowBound)/10 + lowBound) {
                gradeDistribution[6]++;
            } else if (g >= 7*(highBound-lowBound)/10 + lowBound &&  g < 8*(highBound-lowBound)/10 + lowBound) {
                gradeDistribution[7]++;
            } else if (g >= 8*(highBound-lowBound)/10 + lowBound &&  g < 9*(highBound-lowBound)/10 + lowBound) {
                gradeDistribution[8]++;
            } else if (g >= 9*(highBound-lowBound)/10 + lowBound &&  g < highBound) {
                gradeDistribution[9]++;
            }

            //set the upper and lower distribution labels separately, then
            // iterate through the other labels
            distLabelArr[0] = lowBound.toString()+"-"+Float.toString((highBound-lowBound)/10 + lowBound);
            distLabelArr[9] = Float.toString(9*(highBound-lowBound)/10 + lowBound)+"-"+highBound.toString();
            for(int i = 1; i < 9; i++) {
            	distLabelArr[i] = Float.toString(i*(highBound-lowBound)/10 + lowBound)+"-"+Float.toString((i+1)*(highBound-lowBound)/10 + lowBound);
            }
        }
        //fill the graph with the labels and data
        for(int i = 0; i < 10; i++) {
        	series1.getData().add(new XYChart.Data<>(gradeDistribution[i], distLabelArr[i]));
        }
        
        yAxis.setCategories(FXCollections.observableArrayList(distLabelArr[0],distLabelArr[1],distLabelArr[2],distLabelArr[3],
        		distLabelArr[4],distLabelArr[5],distLabelArr[6],distLabelArr[7],distLabelArr[8],distLabelArr[9]));
        
        //old way of adding data
        /*
        series1.getData().add(new XYChart.Data<>(zeroToTen, "0-10"));
        series1.getData().add(new XYChart.Data<>(elevenToTwenty, "11-20"));
        series1.getData().add(new XYChart.Data<>(twentyOneToThirty, "21-30"));
        series1.getData().add(new XYChart.Data<>(thirtyOneToForty, "31-40"));
        series1.getData().add(new XYChart.Data<>(fortyOneToFifty, "41-50"));
        series1.getData().add(new XYChart.Data<>(fiftyOneToSixty, "51-60"));
        series1.getData().add(new XYChart.Data<>(sixtyOneToSeventy, "61-70"));
        series1.getData().add(new XYChart.Data<>(seventyOneToEighty, "71-80"));
        series1.getData().add(new XYChart.Data<>(eightyOneToNinety, "81-90"));
        series1.getData().add(new XYChart.Data<>(ninetyOneToOneHundred, "91-100"));
        yAxis.setCategories(FXCollections.observableArrayList("0-10", "11-20", "21-30", "31-40", "41-50", "51-60",
                "61-70", "71-80", "81-90", "91-100"));
                */
        barChart.getData().add(series1);


    }

    //TODO error handling for file not found, etc.
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
                    }
                } else if (extension.equals("txt")) {
                    grades.clear();
                    if (readTXTFile(file)) {
                        display.setText("Data Was Loaded Successfully");
                        display.setStyle("-fx-text-fill: green ;");
                    }
                } else {
                    display.setText("File extension: " + extension + " not recognized");
                    display.setStyle("-fx-text-fill: red ;");
                }
            }
        }
    }

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
                    populateHistogram(grades);
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

    private boolean readTXTFile(File file) {
        boolean returnValue = true;
        String line;
        int numberOfLines = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            while ((line = br.readLine()) != null) {
                numberOfLines++;
                if (checkLineTXT(line, numberOfLines)){
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
        if (returnValue){
            updateNumberOfEntries(grades);
            populateHistogram(grades);
        }
        return returnValue;
    }

    // TODO Implement checkLine to ensure values in each line of the file are valid
    private boolean checkLineCSV(String[] line, int numberOfLines) {
        return true;
    }

    // TODO Implement checkLine to ensure values in each line of the file are valid
    private boolean checkLineTXT(String line, int numberOfLines) {
        return true;
    }

    public void updateNumberOfEntries(ArrayList<Float> grades){
        numberOfEntries = grades.size();
        numEntries.setText(String.valueOf(numberOfEntries));
    }

    public void onSetBoundsClicked() {
        String highValueText = inputBoundHigh.getText();
        String lowValueText = inputBoundLow.getText();
        if (setUpperBound(highValueText) && setLowerBound(lowValueText)) {
            display.setText("Lower Bound Set To: " + lowValueText + "\n" + "Upper Bound Set to: " + highValueText);
            display.setStyle("-fx-text-fill: green;");
        }
    }

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

    public void onDeleteDataClicked() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Delete All Data");
        alert.setContentText("Are you sure you want to delete all grade data?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()){
            if (result.get() == ButtonType.OK){
                // ... user chose OK
                grades.clear();
                populateHistogram(grades);
                updateNumberOfEntries(grades);
                display.setText("Grade Data Deleted");
                display.setStyle("-fx-text-fill: black");
            }
        }

    }

    private void printGrades(ArrayList<Float> grades) {
        for (Float g : grades) {
            System.out.println(g + " ");
        }
    }
}



