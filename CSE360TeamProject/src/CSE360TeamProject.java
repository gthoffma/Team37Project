import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;


public class CSE360TeamProject extends Application{
	public static void main(String[] args) {
        launch(args);
    }
	public void start(Stage primaryStage) {
		Button OKButton = new Button("OK");
		Scene scene = new Scene(OKButton, 200, 300);
		primaryStage.setTitle("CSETeamProject360 Testing");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
