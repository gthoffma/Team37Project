import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

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

    private XYChart.Series<Number, String> series1 = new XYChart.Series<>();

    @FXML
    public void initialize(){
        loadData();
    }

    private void loadData(){
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
}


