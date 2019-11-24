import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class CSE360TeamProject extends Application{
	public static void main(String[] args) {
        launch(args);
    }
	public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

		// Creating a scene object
		Scene scene = new Scene(root);

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
