
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.sun.prism.impl.Disposer.Record;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Professor;

public class ListProfessor extends Application {

	private final TableView<Professor> tableView = new TableView<>();

	private static final ObservableList<Professor> data = FXCollections
			.observableArrayList();

	private final VBox vbAddForm = new VBox();

	private final Text actionTarget = new Text();

	private final Map<String, Integer> departmentMap = new HashMap();

	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("List Professor");
		tableView.setEditable(false);

		TableColumn<Professor, String> ssnCol = new TableColumn<Professor, String>(
				"SSN");
		TableColumn<Professor, String> nameCol = new TableColumn<Professor, String>(
				"Name");
		TableColumn<Professor, String> ageCol = new TableColumn<Professor, String>(
				"Age");
		TableColumn<Professor, String> genderCol = new TableColumn<Professor, String>(
				"Gender");
		TableColumn<Professor, String> rankCol = new TableColumn<Professor, String>(
				"Rank");
		TableColumn<Professor, String> researchCol = new TableColumn<Professor, String>(
				"Research Specialty");
		TableColumn<Professor, String> deptNameCol = new TableColumn<Professor, String>(
				"Department Name");

		ssnCol.setCellValueFactory(
				new PropertyValueFactory<Professor, String>("ssn"));
		nameCol.setCellValueFactory(
				new PropertyValueFactory<Professor, String>("name"));
		ageCol.setCellValueFactory(
				new PropertyValueFactory<Professor, String>("age"));
		genderCol.setCellValueFactory(
				new PropertyValueFactory<Professor, String>("gender"));
		rankCol.setCellValueFactory(
				new PropertyValueFactory<Professor, String>("rank"));
		researchCol.setCellValueFactory(
				new PropertyValueFactory<Professor, String>(
						"researchSpecialty"));
		deptNameCol.setCellValueFactory(
				new PropertyValueFactory<Professor, String>("departmentName"));

		tableView.getColumns().add(ssnCol);
		tableView.getColumns().add(nameCol);
		tableView.getColumns().add(ageCol);
		tableView.getColumns().add(genderCol);
		tableView.getColumns().add(rankCol);
		tableView.getColumns().add(researchCol);
		tableView.getColumns().add(deptNameCol);

		// Insert Show Project Button
		TableColumn col_showProject = new TableColumn<>("Project");
		tableView.getColumns().add(col_showProject);

		col_showProject.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Record, Boolean>, ObservableValue<Boolean>>() {

					@Override
					public ObservableValue<Boolean> call(
							TableColumn.CellDataFeatures<Record, Boolean> p) {
						return new SimpleBooleanProperty(p.getValue() != null);
					}
				});

		// Adding the Show Project Button to the cell
		col_showProject.setCellFactory(
				new Callback<TableColumn<Record, Boolean>, TableCell<Record, Boolean>>() {

					@Override
					public TableCell<Record, Boolean> call(
							TableColumn<Record, Boolean> p) {
						return new ShowProjectButtonCell();
					}

				});

		// Insert Delete Button
		TableColumn col_delete = new TableColumn<>("Action");
		tableView.getColumns().add(col_delete);

		col_delete.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Record, Boolean>, ObservableValue<Boolean>>() {

					@Override
					public ObservableValue<Boolean> call(
							TableColumn.CellDataFeatures<Record, Boolean> p) {
						return new SimpleBooleanProperty(p.getValue() != null);
					}
				});

		// Adding the Delete Button to the cell
		col_delete.setCellFactory(
				new Callback<TableColumn<Record, Boolean>, TableCell<Record, Boolean>>() {

					@Override
					public TableCell<Record, Boolean> call(
							TableColumn<Record, Boolean> p) {
						return new DeleteButtonCell();
					}

				});

		getProfessors();

		tableView.setItems(data);

		//////////////////////////////////////////////////////////////////
		// Add Department Form

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(5, 5, 5, 5));

		Text scenetitle = new Text("Add New Professor");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 2, 1);

		Text required = new Text("* Required");
		required.setFont(Font.font("Tahoma", FontWeight.NORMAL, 10));
		required.setFill(Color.RED);
		grid.add(required, 0, 1);

		Label ssn = new Label("*SSN:");
		ssn.setMinWidth(Region.USE_PREF_SIZE);
		grid.add(ssn, 0, 2);

		TextField ssnTextField = new TextField();
		grid.add(ssnTextField, 1, 2);

		Label name = new Label("*Name:");
		name.setMinWidth(Region.USE_PREF_SIZE);
		grid.add(name, 0, 3);

		TextField nameTextField = new TextField();
		grid.add(nameTextField, 1, 3);

		Label age = new Label("Age:");
		age.setMinWidth(Region.USE_PREF_SIZE);
		grid.add(age, 0, 4);

		TextField ageTextField = new TextField();
		grid.add(ageTextField, 1, 4);

		Label gender = new Label("Gender:");
		gender.setMinWidth(Region.USE_PREF_SIZE);
		grid.add(gender, 0, 5);

		ComboBox genderComboBox = new ComboBox();
		genderComboBox.getItems().addAll("M", "F");
		genderComboBox.setValue("M");
		grid.add(genderComboBox, 1, 5);

		Label rank = new Label("Rank:");
		rank.setMinWidth(Region.USE_PREF_SIZE);
		grid.add(rank, 0, 6);

		TextField rankTextField = new TextField();
		grid.add(rankTextField, 1, 6);

		Label research = new Label("Research Specialty:");
		research.setMinWidth(Region.USE_PREF_SIZE);
		grid.add(research, 0, 7);

		TextArea researchTextArea = new TextArea();
		grid.add(researchTextArea, 1, 7, 1, 2);

		Label departmentName = new Label("*Department:");
		departmentName.setMinWidth(Region.USE_PREF_SIZE);
		grid.add(departmentName, 0, 9);

		ComboBox departmentNameComboBox = new ComboBox();
		getDepartments();
		for (String key : departmentMap.keySet()) {
			departmentNameComboBox.getItems().add(key);
			System.out.println(key);
		}
		grid.add(departmentNameComboBox, 1, 9);

		Button addBtn = new Button("Add");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(addBtn);
		grid.add(hbBtn, 1, 10);

		grid.add(actionTarget, 1, 11);

		// disable add button until all necessary fields are filled
		addBtn.disableProperty()
				.bind(Bindings
						.createBooleanBinding(
								() -> ssnTextField.getText().trim().isEmpty(),
								ssnTextField.textProperty())
						.or(Bindings.createBooleanBinding(
								() -> nameTextField.getText().trim().isEmpty(),
								nameTextField.textProperty()))
						.or(departmentNameComboBox.valueProperty().isNull())

		);

		addBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {

				Professor newProfessor = new Professor(ssnTextField.getText(),
						nameTextField.getText(),
						Integer.parseInt(ageTextField.getText()),
						genderComboBox.getSelectionModel().getSelectedItem()
								.toString(),
						rankTextField.getText(), researchTextArea.getText(),
						departmentNameComboBox.getSelectionModel()
								.getSelectedItem().toString());

				if (addProfessor(newProfessor)) {
					ssnTextField.clear();
					nameTextField.clear();
					ageTextField.clear();
					genderComboBox.setValue("M");
					rankTextField.clear();
					researchTextArea.clear();
					departmentNameComboBox.setValue("");
					data.add(newProfessor);
					actionTarget.setText("Add professor successful");

				} else {
					actionTarget.setFill(Color.FIREBRICK);
					actionTarget.setText("Add professor failed");
				}
			}
		});

		// for debugging
		grid.setGridLinesVisible(false);

		vbAddForm.setPadding(new Insets(10, 50, 10, 50));
		vbAddForm.getChildren().addAll(grid);
		vbAddForm.setSpacing(3);

		//////////////////////////////////////////////////////////////////

		VBox vBox = new VBox();
		vBox.getChildren().addAll(tableView, vbAddForm);
		primaryStage.setScene(new Scene(vBox, 750, 600));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	// Define the show project button cell
	private class ShowProjectButtonCell extends TableCell<Record, Boolean> {
		final Button cellButton = new Button("Show Project");

		ShowProjectButtonCell() {

			// Action when the button is pressed
			cellButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent t) {
					// get Selected Item
					Professor currentProfessor = (Professor) ShowProjectButtonCell.this
							.getTableView().getItems()
							.get(ShowProjectButtonCell.this.getIndex());

					Stage dialog = new Stage();
					dialog.setTitle("List of " + currentProfessor.getName()
							+ "'s projects");
					GridPane pane = new GridPane();
					pane.setVgap(4);
					pane.setHgap(10);
					pane.setPadding(new Insets(5, 5, 5, 5));

					Text scenetitle = new Text("List of "
							+ currentProfessor.getName() + "'s projects");
					scenetitle.setFont(
							Font.font("Tahoma", FontWeight.NORMAL, 20));
					pane.add(scenetitle, 0, 0, 2, 1);

					Label name = new Label("Project Name:");
					name.setMinWidth(Region.USE_PREF_SIZE);
					pane.add(name, 0, 1);

					ListView projectNameListView = new ListView();
					projectNameListView.setItems(
							getProfessorProjects(currentProfessor.getSsn()));
					pane.add(projectNameListView, 1, 1);

					VBox vb = new VBox(20);
					vb.getChildren().addAll(pane);
					Scene dialogScene = new Scene(vb, 300, 200);
					dialog.setScene(dialogScene);
					dialog.show();
				}

			});
		}

		// Display button if the row is not empty
		@Override
		protected void updateItem(Boolean t, boolean empty) {
			super.updateItem(t, empty);
			if (!empty) {
				setGraphic(cellButton);
			} else {
				setGraphic(null);
			}
		}

	}

	// Define the delete button cell
	private class DeleteButtonCell extends TableCell<Record, Boolean> {
		final Button cellButton = new Button("Delete");

		DeleteButtonCell() {

			// Action when the button is pressed
			cellButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent t) {
					// get Selected Item
					Professor currentProfessor = (Professor) DeleteButtonCell.this
							.getTableView().getItems()
							.get(DeleteButtonCell.this.getIndex());

					if (removeProfessor(currentProfessor.getSsn())) {
						// remove selected item from the table list if delete
						// from database is successful
						data.remove(currentProfessor);
						actionTarget.setText("Remove professor "
								+ currentProfessor.getName() + " successful");
					}
				}

			});
		}

		// Display button if the row is not empty
		@Override
		protected void updateItem(Boolean t, boolean empty) {
			super.updateItem(t, empty);
			if (!empty) {
				setGraphic(cellButton);
			} else {
				setGraphic(null);
			}
		}

	}

	private void getProfessors() {
		ResultSet rs = util.ConnectionManager.getResultSet(
				"SELECT * FROM professor NATURAL LEFT JOIN department;");

		try {
			while (rs.next()) {
				String ssn = rs.getString("ssn");
				String name = rs.getString("name");
				int age = rs.getInt("age");
				String gender = rs.getString("gender");
				String rank = rs.getString("rank");
				String special = rs.getString("research_specialty");
				String depName = rs.getString("department_name");
				System.out.println("SSN = " + ssn);
				System.out.println("NAME = " + name);
				System.out.println("AGE = " + age);
				System.out.println("GENDER = " + gender);
				System.out.println("RANK = " + rank);
				System.out.println("RESEARCH SPECIALTY = " + special);
				System.out.println("DEPARTMENT NAME = " + depName);
				System.out.println();
				data.add(new Professor(ssn, name, age, gender, rank, special,
						depName));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			util.ConnectionManager.closeConnection();
		}

	}

	private boolean addProfessor(Professor professor) {
		String queryAddProfessor = "INSERT INTO professor (ssn, name, age, gender, rank, research_specialty, department_number) VALUES ('"
				+ professor.getSsn() + "', '" + professor.getName() + "', '"
				+ professor.getAge() + "', '" + professor.getGender() + "', '"
				+ professor.getRank() + "', '"
				+ professor.getResearchSpecialty() + "', "
				+ departmentMap.get(professor.getDepartmentName()) + ");";

		int added = util.ConnectionManager.runQuery(queryAddProfessor);
		if (added == 0)
			System.out.println("insert operation failed");
		return added != 0;
	}

	private void getDepartments() {

		ResultSet rs = util.ConnectionManager
				.getResultSet("SELECT * FROM department;");
		try {
			while (rs.next()) {
				int number = rs.getInt("department_number");
				String name = rs.getString("department_name");
				System.out.println("DEPARTMENT NUMBER = " + number);
				System.out.println("DEPARTMENT NAME = " + name);
				System.out.println();
				departmentMap.put(name, number);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			util.ConnectionManager.closeConnection();
		}
	}

	private boolean removeProfessor(String ssn) {
		String query = "DELETE FROM professor WHERE ssn = '" + ssn + "';";
		System.out.println(query);
		int deleted = util.ConnectionManager.runQuery(query);
		if (deleted == 0)
			System.out.println("delete operation failed");
		return deleted != 0;
	}

	private ObservableList<String> getProfessorProjects(String ssn) {
		ResultSet rs = util.ConnectionManager.getResultSet(
				"SELECT * FROM professor_works_on NATURAL JOIN project WHERE ssn = '"
						+ ssn + "';");
		ObservableList<String> projectNames = FXCollections
				.observableArrayList();

		try {
			while (rs.next()) {
				int number = rs.getInt("project_number");
				String name = rs.getString("project_name");
				System.out.println("PROJECT NUMBER = " + number);
				System.out.println("PROJECT NAME = " + name);
				System.out.println();
				projectNames.add(name);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			util.ConnectionManager.closeConnection();
		}
		return projectNames;
	}

}