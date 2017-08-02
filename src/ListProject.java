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
import model.Project;

public class ListProject extends Application {

	private final TableView<Project> tableView = new TableView<>();

	private static final ObservableList<Project> data = FXCollections
			.observableArrayList();

	private final VBox vbAddForm = new VBox();

	private final Text actionTarget = new Text();

	private final Map<String, Integer> departmentMap = new HashMap();

	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("List Project");
		tableView.setEditable(false);

		TableColumn<Project, String> ssnCol = new TableColumn<Project, String>(
				"ProjectNumber");
		TableColumn<Project, String> nameCol = new TableColumn<Project, String>(
				"ProjectNumber");
		TableColumn<Project, String> ageCol = new TableColumn<Project, String>(
				"StartingDate");
		TableColumn<Project, String> genderCol = new TableColumn<Project, String>(
				"EndingDate");
		TableColumn<Project, String> rankCol = new TableColumn<Project, String>(
				"Budget");
		TableColumn<Project, String> researchCol = new TableColumn<Project, String>(
				"PIName");

		ssnCol.setCellValueFactory(
				new PropertyValueFactory<Project, String>("projectNumber"));
		nameCol.setCellValueFactory(
				new PropertyValueFactory<Project, String>("projectName"));
		ageCol.setCellValueFactory(
				new PropertyValueFactory<Project, String>("StartingDate"));
		genderCol.setCellValueFactory(
				new PropertyValueFactory<Project, String>("EndingDate"));
		rankCol.setCellValueFactory(
				new PropertyValueFactory<Project, String>("Budget"));
		researchCol.setCellValueFactory(
				new PropertyValueFactory<Project, String>(
						"PIName"));
		

		tableView.getColumns().add(ssnCol);
		tableView.getColumns().add(nameCol);
		tableView.getColumns().add(ageCol);
		tableView.getColumns().add(genderCol);
		tableView.getColumns().add(rankCol);
		tableView.getColumns().add(researchCol);
		

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

		getProjects();

		tableView.setItems(data);

		//////////////////////////////////////////////////////////////////
		// Add Department Form

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(5, 5, 5, 5));

		Text scenetitle = new Text("Add New Project");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 2, 1);

		Text required = new Text("* Required");
		required.setFont(Font.font("Tahoma", FontWeight.NORMAL, 10));
		required.setFill(Color.RED);
		grid.add(required, 0, 1);

		Label ssn = new Label("*ProjectNumber");
		ssn.setMinWidth(Region.USE_PREF_SIZE);
		grid.add(ssn, 0, 2);

		TextField ssnTextField = new TextField();
		grid.add(ssnTextField, 1, 2);

		Label name = new Label("*ProjectName:");
		name.setMinWidth(Region.USE_PREF_SIZE);
		grid.add(name, 0, 3);

		TextField nameTextField = new TextField();
		grid.add(nameTextField, 1, 3);

		Label age = new Label("StartingDate");
		age.setMinWidth(Region.USE_PREF_SIZE);
		grid.add(age, 0, 4);

		TextField ageTextField = new TextField();
		grid.add(ageTextField, 1, 4);

		Label gender = new Label("EndingDate");
		gender.setMinWidth(Region.USE_PREF_SIZE);
		grid.add(gender, 0, 5);

		TextField genderTextField = new TextField();
		grid.add(genderTextField, 1, 5);

		Label rank = new Label("Budget");
		rank.setMinWidth(Region.USE_PREF_SIZE);
		grid.add(rank, 0, 6);

		TextField rankTextField = new TextField();
		grid.add(rankTextField, 1, 6);

		Label research = new Label("PIName");
		research.setMinWidth(Region.USE_PREF_SIZE);
		grid.add(research, 0, 7);

		TextField pinameTextField = new TextField();
		grid.add(pinameTextField, 1, 7);


		Label departmentName = new Label("*Department:");
		departmentName.setMinWidth(Region.USE_PREF_SIZE);
		grid.add(departmentName, 0, 9);

//		ComboBox departmentNameComboBox = new ComboBox();
		getDepartments();
//		for (String key : departmentMap.keySet()) {
//			departmentNameComboBox.getItems().add(key);
//			System.out.println(key);
//		}
//		grid.add(departmentNameComboBox, 1, 9);

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
						//.or(departmentNameComboBox.valueProperty().isNull())

		);

		addBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {

				Project newProject = new Project(Integer.parseInt(ssnTextField.getText()),
						nameTextField.getText(),
						ageTextField.getText(),
						genderTextField.getText(),
						Double.parseDouble(rankTextField.getText()),
						pinameTextField.getText());
						
//						genderComboBox.getSelectionModel().getSelectedItem()
//								.toString(),
//						rankTextField.getText(), researchTextArea.getText();
						//departmentNameComboBox.getSelectionModel()
								//.getSelectedItem().toString());

				if (addProject(newProject)) {
					ssnTextField.clear();
					nameTextField.clear();
					ageTextField.clear();
					rankTextField.clear();
					pinameTextField.clear();
					data.add(newProject);
					actionTarget.setText("Add project successful");

				} else {
					actionTarget.setFill(Color.FIREBRICK);
					actionTarget.setText("Add project failed");
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
		final Button cellButton = new Button("Show Students");

		ShowProjectButtonCell() {

			// Action when the button is pressed
			cellButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent t) {
					// get Selected Item
					Project currentProject = (Project) ShowProjectButtonCell.this
							.getTableView().getItems()
							.get(ShowProjectButtonCell.this.getIndex());

					Stage dialog = new Stage();
					dialog.setTitle("List of " + currentProject.getProjectName()
							+ "'s Students");
					GridPane pane = new GridPane();
					pane.setVgap(4);
					pane.setHgap(10);
					pane.setPadding(new Insets(5, 5, 5, 5));

					Text scenetitle = new Text("List of "
							+ currentProject.getProjectName() + "'s Students");
					scenetitle.setFont(
							Font.font("Tahoma", FontWeight.NORMAL, 20));
					pane.add(scenetitle, 0, 0, 2, 1);

					Label name = new Label("Project Name:");
					name.setMinWidth(Region.USE_PREF_SIZE);
					pane.add(name, 0, 1);

					ListView projectNameListView = new ListView();
					projectNameListView.setItems(		
					getProfessorProjects(currentProject.getProjectNumber()));
					pane.add(projectNameListView, 1, 1);

					VBox vb = new VBox(20);
					vb.getChildren().addAll(pane);
					Scene dialogScene = new Scene(vb, 300, 200);
					dialog.setScene(dialogScene);
					dialog.show();
				}

			});
		}
		private ObservableList<String> getProfessorProjects(int projectNumber) {
			ResultSet rs = util.ConnectionManager.getResultSet(
					"SELECT name FROM graduate_student WHERE project_number = '"
							+ projectNumber + "';");
			ObservableList<String> projectNames = FXCollections
					.observableArrayList();

			try {
				while (rs.next()) {
					//int number = rs.getInt("project_number");
					String name = rs.getString("name");
					System.out.println("Student name is = " + name);
					//System.out.println("PROJECT NAME = " + name);
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
					Project currentProject = (Project) DeleteButtonCell.this
							.getTableView().getItems()
							.get(DeleteButtonCell.this.getIndex());

					if (removeProject(currentProject.getProjectNumber())) {
						// remove selected item from the table list if delete
						// from database is successful
						data.remove(currentProject);
						actionTarget.setText("Remove project "
								+ currentProject.getProjectName() + " successful");
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

	private void getProjects() {
		ResultSet rs = util.ConnectionManager.getResultSet(
				"SELECT * FROM project;");

		try {
			while (rs.next()) {
				int ssn = rs.getInt("project_number");
				String name = rs.getString("project_name");
				String age = rs.getString("starting_date");
				String gender = rs.getString("ending_date");
				double rank = rs.getDouble("budget");
				String special = rs.getString("ssn_manage");
				//String depName = rs.getString("department_name");
				System.out.println("ProjectNumber = " + ssn);
				System.out.println("ProjectName = " + name);
				System.out.println("StartingDate = " + age);
				System.out.println("EndingDate = " + gender);
				System.out.println("Budget = " + rank);
				System.out.println("PIName = " + special);
				//System.out.println("DEPARTMENT NAME = " + depName);
				System.out.println();
				data.add(new Project(ssn, name, age, gender, rank, special));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			util.ConnectionManager.closeConnection();
		}

	}

	private boolean addProject(Project project) {
		String queryAddProject = "INSERT INTO project (project_number, project_name, starting_date, ending_date, budget, ssn_manage) VALUES ('"
				+ project.getProjectNumber() + "', '" + project.getProjectName() + "', '"
				+ project.getStartingDate() + "', '" + project.getEndingDate() + "', '"
				+ project.getBudget() + "', '"
				+ project.getPiName() + "') "
				;

		int added = util.ConnectionManager.runQuery(queryAddProject);
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

	private boolean removeProject(int projectNumber) {
		String query = "DELETE FROM project WHERE project_number = '" + projectNumber + "';";
		System.out.println(query);
		int deleted = util.ConnectionManager.runQuery(query);
		if (deleted == 0)
			System.out.println("delete operation failed");
		return deleted != 0;
	}

	private ObservableList<String> getProjectStudents(String projectNumber) {
		ResultSet rs = util.ConnectionManager.getResultSet(
				"SELECT * FROM student_works_on WHERE project_number = '"
						+ projectNumber + "';");
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