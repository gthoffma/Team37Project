import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CSE360TeamProject extends Application{
	public static void main(String[] args) {
        launch(args);
    }
	public void start(Stage primaryStage) {
		// First Column
		Text text1 = new Text("File Path: ");
		Text filePath = new Text("/directory/file.csv");
		filePath.setId("filePath");
		Button button1 = new Button("Load File");
		Button button2 = new Button("Manual Entry");
		Button button3 = new Button("Append Data");
		Button button4 = new Button("Delete Data");

		GridPane.setHalignment(text1, HPos.LEFT);
		GridPane.setHalignment(filePath, HPos.LEFT);
		GridPane.setHalignment(button1, HPos.LEFT);
		GridPane.setHalignment(button2, HPos.LEFT);
		GridPane.setHalignment(button3, HPos.LEFT);
		GridPane.setHalignment(button4, HPos.LEFT);

		Label title = new Label("Grade Analytics");
		title.setId("title");
		GridPane.setHalignment(title, HPos.CENTER);

		TextArea display = new TextArea();
		display.setId("display");
		display.setText("Error: Input is outside current range: LOW: 0 HIGH: 100");
		GridPane.setValignment(display, VPos.BOTTOM);

		// num of entries, high, low, mean, median, mode
		// median of even numbered list = average of middle two.

		Label numEntriesLabel = new Label("Entries:");
		Label meanLabel = new Label("Mean:");
		Label medianLabel = new Label("Median:");
		Label modeLabel = new Label("Mode:");
		Label highLabel = new Label("High:");
		Label lowLabel = new Label("Low:");
		Text numEntries = new Text("500");
		Text mean = new Text("75.5");
		Text median = new Text("74");
		Text mode = new Text("75");
		Text high = new Text("100");
		Text low = new Text("0");
		numEntries.setId("numEntries");
		high.setId("high");
		low.setId("low");
		mean.setId("mean");
		median.setId("median");
		mode.setId("mode");
		GridPane.setValignment(low, VPos.TOP);

		//Creating a Grid Pane
		GridPane gridPane = new GridPane();
		gridPane.setMinSize(900, 700);
		gridPane.setPadding(new Insets(10, 10, 10, 10));

		//Setting the vertical and horizontal gaps between the columns
		gridPane.setVgap(8);
		gridPane.setHgap(10);

		//Setting the Grid alignment
		gridPane.setAlignment(Pos.CENTER);

		// Horizontal Bar Chart
		final NumberAxis xAxis = new NumberAxis();
		final CategoryAxis yAxis = new CategoryAxis();
		final BarChart<Number,String> bc = new BarChart<>(xAxis,yAxis);
		bc.setId("bar-chart");
		xAxis.setLabel("Frequency");
		xAxis.setTickLabelRotation(90);
		yAxis.setLabel("Grade Distribution");

		//Creating the Bar chart
		XYChart.Series<Number, String> series1 = new XYChart.Series<>();
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

		bc.getData().add(series1);
		bc.setLegendVisible(false);
		GridPane.setValignment(bc, VPos.TOP);


		//Left Sidebar
		gridPane.add(text1, 0, 4,2,1);
		gridPane.add(filePath, 0, 5,2,1);
		gridPane.add(button1, 0, 6,2,1);
		gridPane.add(button2, 0, 7,2,1);
		gridPane.add(button3, 0, 8,2,1);
		gridPane.add(button4, 0, 9,2,1);
		gridPane.add(numEntriesLabel, 0, 13,1,1);
		gridPane.add(numEntries, 1, 13,1,1);
		gridPane.add(highLabel, 0, 14,1,1);
		gridPane.add(high, 1, 14,1,1);
		gridPane.add(lowLabel, 0, 15,1,1);
		gridPane.add(low, 1, 15,1,1);
		gridPane.add(meanLabel, 0, 16,1,1);
		gridPane.add(mean, 1, 16,1,1);
		gridPane.add(medianLabel, 0, 17,1,1);
		gridPane.add(median, 1, 17,1,1);
		gridPane.add(modeLabel, 0, 18,1,1);
		gridPane.add(mode, 1, 18,1,1);

		gridPane.add(title, 3, 1, 1,1);
		gridPane.add(display, 3, 21, 1, 4);
		gridPane.add(bc, 3, 2,1,20);

		// Creating a scene object
		Scene scene = new Scene(gridPane);

		Stage stage = new Stage();

		// Setting title to the Stage
		stage.setTitle("Grade Analytics");

		// Adding scene to the stage
		stage.setScene(scene);

		//Displaying the contents of the stage
		stage.show();

		gridPane.setGridLinesVisible(false);

		scene.getStylesheets().add("./stylesheet.css");
	}

}
