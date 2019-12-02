import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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

    @FXML
    public void initialize() {
        numEntries.setText(String.valueOf(numberOfEntries));
        display.setText("Welcome to Grade Analytics!\nLoad a file or enter grades manually to begin. ");
    }

    private void populateHistogram(ArrayList<Float> grades) {
        CategoryAxis yAxis = (CategoryAxis) barChart.getYAxis();
        NumberAxis xAxis = (NumberAxis) barChart.getXAxis();
        xAxis.setLabel("Frequency");
        yAxis.setLabel("Grade Distribution");

        int zeroToTen = 0;
        int elevenToTwenty = 0;
        int twentyOneToThirty = 0;
        int thirtyOneToForty = 0;
        int fortyOneToFifty = 0;
        int fiftyOneToSixty = 0;
        int sixtyOneToSeventy = 0;
        int seventyOneToEighty = 0;
        int eightyOneToNinety = 0;
        int ninetyOneToOneHundred = 0;
        for (Float g : grades){
            if ( g >= 0 && g < 10.5 ){
                zeroToTen++;
            } else if (g >= 10.5 && g < 20.5){
                elevenToTwenty++;
            } else if (g >= 20.5 && g < 30.5){
                twentyOneToThirty++;
            } else if (g >= 30.5 && g < 40.5){
                thirtyOneToForty++;
            } else if (g >= 40.5 && g < 50.5){
                fortyOneToFifty++;
            } else if (g >= 50.5 && g < 60.5){
                fiftyOneToSixty++;
            } else if (g >= 60.5 && g < 70.5){
                sixtyOneToSeventy++;
            } else if (g >= 70.5 && g < 80.5){
                seventyOneToEighty++;
            } else if (g >= 80.5 && g < 90.5){
                eightyOneToNinety++;
            } else if (g >= 90.5 && g <= 100){
                ninetyOneToOneHundred++;
            }

        }
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
        barChart.getData().add(series1);


    }

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
                    readCSVFile(file);
                } else if (extension.equals("txt")) {
                    readTXTFile(file);
                } else {
                    display.setText("File extension: " + extension + " not recognized");
                    display.setStyle("-fx-text-fill: red ;");
                }
            }
        }
    }

    private void readCSVFile(File file) {
        //System.out.println(file);
        String line;
        String cvsSplitBy = ",";
        int numberOfLines = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            while ((line = br.readLine()) != null) {
                numberOfLines++;
                // remove zero-width space
                line = line.replace("\uFEFF", "");
                String[] gradeLineStringArray = line.split(cvsSplitBy);
                if (checkLine(gradeLineStringArray, numberOfLines)) {
                    for (String s : gradeLineStringArray) {
                        Float gradeFloat = Float.parseFloat(s);
                        grades.add(gradeFloat);
                    }

                    populateHistogram(grades);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readTXTFile(File file) {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            while ((line = br.readLine()) != null) {
                Float gradeFloat = Float.parseFloat(line.trim());
                grades.add(gradeFloat);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        printGrades(grades);

    }
    // TODO Implement checkLine to ensure values in each line of the file are valid
    private boolean checkLine(String[] line, int numberOfLines) {
        return true;
    }

    public void onSetBoundsClicked() {
        String highValueText = inputBoundHigh.getText();
        String lowValueText = inputBoundLow.getText();
        if (setUpperBound(highValueText) && setLowerBound(lowValueText)){
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
    public void onManualEntryClicked(){

    }

    // TODO Implement onAppendDataClicked
    public void onAppendDataClicked(){

    }

    // TODO Implement onCreateReportClicked
    public void onCreateReportClicked(ArrayList<Float> grades){

    }

    //TODO Implement calculate mean
    public void calculateMean(ArrayList<Float> grades){

    }

    //TODO Implement calculate median
    public void calculateMedian(ArrayList<Float> grades){

    }

    // TODO Implement calculate Mode
    public void calculateMode(ArrayList<Float> grades){

    }

    // TODO Implement calculate high value
    public void calculateHighValue(ArrayList<Float> grades){

    }

    // TODO Implement calculate low value
    public void calculateLowValue(ArrayList<Float> grades){

    }

    public void onDeleteDataClicked(){
        grades.clear();
    }

    private void printGrades(ArrayList<Float> grades) {
        for (Float g : grades) {
            System.out.println(g + " ");
        }
    }
}



