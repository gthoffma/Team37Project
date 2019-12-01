import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
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
    public Text text1;
    public TextField filePath;
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

    private XYChart.Series<Number, String> series1 = new XYChart.Series<>();

    @FXML
    public void initialize() {
        loadData();
    }

    private void loadData() {
        series1.getData().add(new XYChart.Data<>(20.0f, "0-10"));
        series1.getData().add(new XYChart.Data<>(30.0f, "11-20"));
        series1.getData().add(new XYChart.Data<>(40.0f, "21-30"));
        series1.getData().add(new XYChart.Data<>(50.0f, "31-40"));
        series1.getData().add(new XYChart.Data<>(60.0f, "41-50"));
        series1.getData().add(new XYChart.Data<>(70.0f, "51-60"));
        series1.getData().add(new XYChart.Data<>(80.0f, "61-70"));
        series1.getData().add(new XYChart.Data<>(70.0f, "71-80"));
        series1.getData().add(new XYChart.Data<>(60.0f, "81-90"));
        series1.getData().add(new XYChart.Data<>(40.0f, "91-100"));
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
                    display.setText("file extension: " + extension + "not recognized");
                    display.setStyle("-fx-text-fill: red ;");
                }

            }

        }
    }

    private void readCSVFile(File file) {
        //System.out.println(file);
        String line;
        String cvsSplitBy = ",";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            while ((line = br.readLine()) != null) {

                // remove zero-width space
                line = line.replace("\uFEFF", "");
                System.out.println(line + "\n");
                String[] gradeLineStringArray = line.split(cvsSplitBy);
                for (String s : gradeLineStringArray) {
                    Float gradeFloat = Float.parseFloat(s);
                    grades.add(gradeFloat);
                }

                printGrades(grades);


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

    private void printGrades(ArrayList<Float> grades) {
        for (Float g : grades) {
            System.out.println(g + " ");
        }
    }
}



