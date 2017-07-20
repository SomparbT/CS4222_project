package reference;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Test extends Application {

	private final TableView<Person> table = new TableView<>();
	private final ObservableList<Person> data = FXCollections
			.observableArrayList(
					new Person("Jacob", "Smith", "jacob.smith@example.com"),
					new Person("Isabella", "Johnson",
							"isabella.johnson@example.com"),
					new Person("Ethan", "Williams",
							"ethan.williams@example.com"),
					new Person("Emma", "Jones", "emma.jones@example.com"),
					new Person("Michael", "Brown",
							"michael.brown@example.com"));
	final HBox hb = new HBox();

	public static void main(String[] args) {
		launch(args);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage primaryStage) {
		try {
			Pane root = new Pane();
			Scene scene = new Scene(root, 400, 400);
			Button btn = new Button(); // create a btn
			root.getChildren().add(btn); // add this btn to the root

			btn.setOnAction(new EventHandler<ActionEvent>() // when click
			{
				@Override
				public void handle(ActionEvent e) {
					Stage dialog = new Stage(); // new stage
					dialog.initModality(Modality.APPLICATION_MODAL);
					// Defines a modal window that blocks events from being
					// delivered to any other application window.
					// dialog.initOwner(primaryStage);
					VBox vb = new VBox(20);
					Scene dialogScene = new Scene(vb, 300, 200);
					dialog.setScene(dialogScene);
					dialog.show();
				}
			});
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static class Person {

		private final SimpleStringProperty firstName;
		private final SimpleStringProperty lastName;
		private final SimpleStringProperty email;

		private Person(String fName, String lName, String email) {
			this.firstName = new SimpleStringProperty(fName);
			this.lastName = new SimpleStringProperty(lName);
			this.email = new SimpleStringProperty(email);
		}

		public String getFirstName() {
			return firstName.get();
		}

		public void setFirstName(String fName) {
			firstName.set(fName);
		}

		public String getLastName() {
			return lastName.get();
		}

		public void setLastName(String fName) {
			lastName.set(fName);
		}

		public String getEmail() {
			return email.get();
		}

		public void setEmail(String fName) {
			email.set(fName);
		}
	}
}