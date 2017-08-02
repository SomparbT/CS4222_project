import java.sql.ResultSet;
import java.sql.SQLException;

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
import javafx.scene.control.Label;
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
import model.Department;
import model.Professor;

public class ListDepartment2 extends Application {

	private TableView<Department> tableView = new TableView<Department>();

	private final ObservableList<Department> data = FXCollections
			.observableArrayList();

	private final VBox vbAddForm = new VBox();

	private final Text actionTarget = new Text();

	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("List Department");
		tableView.setEditable(false);

		TableColumn<Department, String> numberCol = new TableColumn<Department, String>(
				"Department Number");
		TableColumn<Department, String> nameCol = new TableColumn<Department, String>(
				"Department Name");
		TableColumn<Department, String> mainOfficeCol = new TableColumn<Department, String>(
				"Main Office");
		TableColumn<Department, String> chairmanNameCol = new TableColumn<Department, String>(
				"Chairman Name");

		numberCol.setCellValueFactory(
				new PropertyValueFactory<Department, String>(
						"departmentNumber"));
		nameCol.setCellValueFactory(
				new PropertyValueFactory<Department, String>("departmentName"));
		mainOfficeCol.setCellValueFactory(
				new PropertyValueFactory<Department, String>("mainOffice"));
		chairmanNameCol.setCellValueFactory(
				new PropertyValueFactory<Department, String>("chairmanName"));

		tableView.getColumns().add(numberCol);
		tableView.getColumns().add(nameCol);
		tableView.getColumns().add(mainOfficeCol);
		tableView.getColumns().add(chairmanNameCol);

		// Insert Button
		TableColumn col_action = new TableColumn<>("Action");
		tableView.getColumns().add(col_action);

		col_action.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Record, Boolean>, ObservableValue<Boolean>>() {

					@Override
					public ObservableValue<Boolean> call(
							TableColumn.CellDataFeatures<Record, Boolean> p) {
						return new SimpleBooleanProperty(p.getValue() != null);
					}
				});

		// Adding the Button to the cell
		col_action.setCellFactory(
				new Callback<TableColumn<Record, Boolean>, TableCell<Record, Boolean>>() {

					@Override
					public TableCell<Record, Boolean> call(
							TableColumn<Record, Boolean> p) {
						return new ButtonCell();
					}

				});

		getDepartments();

		tableView.setItems(data);

		//////////////////////////////////////////////////////////////////
		// Add Department Form

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(5, 5, 5, 5));

		Text scenetitle = new Text("Add New Department");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 2, 1);

		Text required = new Text("* Required");
		required.setFont(Font.font("Tahoma", FontWeight.NORMAL, 10));
		required.setFill(Color.RED);
		grid.add(required, 0, 1);

		Label departmentNumber = new Label("*Department Number:");
		departmentNumber.setMinWidth(Region.USE_PREF_SIZE);
		grid.add(departmentNumber, 0, 2);

		TextField departmentNumberTextField = new TextField();
		grid.add(departmentNumberTextField, 1, 2);

		Label departmentName = new Label("*Department Name:");
		departmentName.setMinWidth(Region.USE_PREF_SIZE);
		grid.add(departmentName, 0, 3);

		TextField departmentNameTextField = new TextField();
		grid.add(departmentNameTextField, 1, 3);

		Label mainOffice = new Label("Main Office:");
		mainOffice.setMinWidth(Region.USE_PREF_SIZE);
		grid.add(mainOffice, 0, 4);

		TextArea mainOfficeTextArea = new TextArea();
		grid.add(mainOfficeTextArea, 1, 4, 1, 2);

		Label chairmanSSN = new Label("*Chairman SSN:");
		chairmanSSN.setMinWidth(Region.USE_PREF_SIZE);
		grid.add(chairmanSSN, 0, 6);

		TextField chairmanSSNTextField = new TextField();
		grid.add(chairmanSSNTextField, 1, 6);

		Label chairmanName = new Label("*Chairman Name:");
		chairmanName.setMinWidth(Region.USE_PREF_SIZE);
		grid.add(chairmanName, 0, 7);

		TextField chairmanNameTextField = new TextField();
		grid.add(chairmanNameTextField, 1, 7);

		Button addBtn = new Button("Add");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(addBtn);
		grid.add(hbBtn, 1, 8);

		grid.add(actionTarget, 1, 9);

		// disable add button until all necessary fields are filled
		addBtn.disableProperty()
				.bind(Bindings
						.createBooleanBinding(
								() -> departmentNumberTextField.getText().trim()
										.isEmpty(),
								departmentNumberTextField.textProperty())
						.or(Bindings.createBooleanBinding(
								() -> departmentNameTextField.getText().trim()
										.isEmpty(),
								departmentNameTextField.textProperty()))
						.or(Bindings.createBooleanBinding(
								() -> chairmanSSNTextField.getText()
										.trim().isEmpty(),
								chairmanSSNTextField.textProperty()))
						.or(Bindings.createBooleanBinding(
								() -> chairmanNameTextField.getText().trim()
										.isEmpty(),
								chairmanNameTextField.textProperty()))

		);

		addBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {

				Department newDepartment = new Department(
						Integer.parseInt(departmentNumberTextField.getText()),
						departmentNameTextField.getText(),
						mainOfficeTextArea.getText(),
						chairmanNameTextField.getText());
				Professor chairman = new Professor(
						chairmanSSNTextField.getText(),
						chairmanNameTextField.getText(), 0, null, null, null,
						departmentNameTextField.getText());
				if (addDepartment(newDepartment, chairman)) {
					departmentNumberTextField.clear();
					departmentNameTextField.clear();
					mainOfficeTextArea.clear();
					chairmanSSNTextField.clear();
					chairmanNameTextField.clear();
					data.add(newDepartment);
					actionTarget.setText("Add department successful");

				} else {
					actionTarget.setFill(Color.FIREBRICK);
					actionTarget.setText("Add department failed");
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
		primaryStage.setScene(new Scene(vBox, 600, 600));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	// Define the button cell
	private class ButtonCell extends TableCell<Record, Boolean> {
		final Button cellButton = new Button("Delete");

		ButtonCell() {

			// Action when the button is pressed
			cellButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent t) {
					// get Selected Item
					Department currentDepartment = (Department) ButtonCell.this
							.getTableView().getItems()
							.get(ButtonCell.this.getIndex());

					if (removeDepartment(
							currentDepartment.getDepartmentNumber())) {
						// remove selected item from the table list if delete
						// from database is successful
						data.remove(currentDepartment);
						actionTarget.setText("Remove department "
								+ currentDepartment.getDepartmentName()
								+ " successful");
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

	private void getDepartments() {

		ResultSet rs = util.ConnectionManager.getResultSet(
				"SELECT * FROM department LEFT JOIN professor ON department.ssn_chairman = professor.ssn;");
		try {
			while (rs.next()) {
				int number = rs.getInt("department_number");
				String name = rs.getString("department_name");
				String mainOffice = rs.getString("main_office");
				String chairmanName = rs.getString("name");
				System.out.println("DEPARTMENT NUMBER = " + number);
				System.out.println("DEPARTMENT NAME = " + name);
				System.out.println("MAIN OFFICE = " + mainOffice);
				System.out.println("CHAIRMAN NAME = " + chairmanName);
				System.out.println();
				data.add(
						new Department(number, name, mainOffice, chairmanName));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			util.ConnectionManager.closeConnection();
		}
	}

	private boolean removeDepartment(int departmentNumber) {
		String query = "DELETE FROM department WHERE department_number = "
				+ departmentNumber;
		System.out.println(query);
		int deleted = util.ConnectionManager.runQuery(query);
		if (deleted == 0)
			System.out.println("delete operation failed");
		return deleted != 0;
	}

	private boolean addDepartment(Department department, Professor chairman) {
		String queryAddDepartment = "INSERT INTO department (department_number, department_name, main_office, ssn_chairman) VALUES("
				+ department.getDepartmentNumber() + ", '"
				+ department.getDepartmentName() + "', '"
				+ department.getMainOffice() + "', '" + chairman.getSsn()
				+ "');";
		String queryAddProfessor = "INSERT INTO professor (ssn, name, department_number) VALUES('"
				+ chairman.getSsn() + "', '" + chairman.getName() + "', "
				+ department.getDepartmentNumber() + ");";

		String query = queryAddDepartment + queryAddProfessor;
		int added = util.ConnectionManager.runQuery(query);
		if (added == 0)
			System.out.println("insert operation failed");
		return added != 0;
	}

}