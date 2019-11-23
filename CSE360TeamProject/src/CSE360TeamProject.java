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

		Label highLabel = new Label("High:");
		Label lowLabel = new Label("Low:");
		Text high = new Text("100");
		Text low = new Text("0");
		high.setId("high");
		low.setId("low");
		GridPane.setValignment(low, VPos.TOP);

		//Creating a Grid Pane
		GridPane gridPane = new GridPane();

		//Setting size for the pane
		gridPane.setMinSize(900, 700);

		//Setting the padding
		gridPane.setPadding(new Insets(10, 10, 10, 10));

		gridPane.setGridLinesVisible(false);

		//Setting the vertical and horizontal gaps between the columns
		gridPane.setVgap(10);
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
		gridPane.add(text1, 0, 4);
		gridPane.add(filePath, 0, 5);
		gridPane.add(button1, 0, 6);
		gridPane.add(button2, 0, 7);
		gridPane.add(button3, 0, 8);
		gridPane.add(button4, 0, 9);
		gridPane.add(highLabel, 0, 10);
		gridPane.add(high, 0, 11);
		gridPane.add(lowLabel, 0, 12);
		gridPane.add(low, 0, 13);

		gridPane.add(title, 1, 1, 1,1);
		gridPane.add(display, 1, 21, 1, 1);
		gridPane.add(bc, 1, 2,1,20);

		// Creating a scene object
		Scene scene = new Scene(gridPane);

		Stage stage = new Stage();

		// Setting title to the Stage
		stage.setTitle("Grade Analytics");

		// Adding scene to the stage
		stage.setScene(scene);

		//Displaying the contents of the stage
		stage.show();

		scene.getStylesheets().add("./stylesheet.css");
	}

}
