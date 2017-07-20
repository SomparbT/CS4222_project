package reference;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Win10
 */
public class ProjectFlower extends Application {

	private static final double WindowWidth = 600;
	private static final double WindowHeight = 400;
	Button B1;
	private Stage primaryStage;

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		primaryStage.setTitle("Flowerstyle Net");

		GridPane GP = new GridPane();
		GP.setAlignment(Pos.CENTER);
		GP.setHgap(10);
		GP.setVgap(10);
		GP.setPadding(new Insets(10, 10, 10, 10));
		GP.setPrefSize(WindowWidth, WindowHeight);
		GP.setMinSize(WindowWidth, WindowHeight);

		ColumnConstraints left = new ColumnConstraints();
		left.setHalignment(HPos.RIGHT);
		left.setHgrow(Priority.NEVER);

		ColumnConstraints right = new ColumnConstraints();
		right.setHgrow(Priority.ALWAYS);

		GP.getColumnConstraints().addAll(left, right);

		Text text = new Text("\t\t\t\t WELCOME TO FLOWERSTYLE NET"
				+ "\n\n\t\t\t\t\t " + "ENJOY YOUR SHOPPING!! :)");
		text.setFont(Font.font("SHOWCARD GOTHIC", FontWeight.NORMAL, 20));
		GP.add(text, 0, 0, 2, 1);

		GridPane.setHalignment(text, HPos.LEFT);

		Label L1 = new Label("User Name");
		GP.add(L1, 0, 1);

		Label L2 = new Label("Password");
		GP.add(L2, 0, 2);

		TextField TF1 = new TextField();
		GP.add(TF1, 1, 1);

		TextField TF2 = new TextField();
		GP.add(TF2, 1, 2);

		Button B1 = new Button("Sign in");
		GP.add(B1, 1, 3);
		GridPane.setHalignment(B1, HPos.CENTER);

		B1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// change scenes here
				changeScenes();

			}

		});

		GP.setStyle("-fx-background-color : grey");

		Scene scene = new Scene(GP, WindowWidth, WindowHeight);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	private void changeScenes() {
		CusInfo ci = new CusInfo();
		Scene scene = ci.getScene();

		// set new title
		primaryStage.setTitle("Customer Information");

		// change the scene on the stage
		primaryStage.setScene(scene);
	}

}