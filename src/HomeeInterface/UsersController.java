package HomeeInterface;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import Homee.Homee;
import Homee.User;
import HomeeUtils.DatabaseHandler;
import HomeeUtils.Alerts.FailureAlert;
import HomeeUtils.Alerts.HomeeAlerts;
import HomeeUtils.Alerts.WarningAlert;
import HomeeUtils.HomeeValidator.CnicValidator;
import HomeeUtils.HomeeValidator.Validator;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.ListView;

import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

public class UsersController implements Initializable {
	@FXML
	private TextField tfFirstName;
	@FXML
	private TextField tfLastName;
	@FXML
	private TextField tfCnic;
	@FXML
	private TextField tfPassword;
	@FXML
	private DatePicker dpDOB;
	@FXML
	private TextField tfNewUserCnic;
	@FXML
	private Button actionGotoHome;
	@FXML
	private Button actionExit;
	@FXML
	private ListView<GridPane> lvUsers;

	private Homee homee;
	private ArrayList<User> users;
	private User currUser;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		homee = Homee.getInstance();
		users = homee.getUsers();
		currUser = homee.getCurrentUser();
		if (users != null) {
			ArrayList<GridPane> labels = new ArrayList<GridPane>();
			for (User user : users) {
				Label labelName = new Label(user.getFirstName() + " " + user.getLastName());
				Label labelCnic = new Label(user.getCnic());
				labelName.setFont(new Font(20));
				labelCnic.setFont(new Font(20));
				GridPane gridPane = new GridPane();
				gridPane.add(labelName, 0, 1);
				gridPane.add(labelCnic, 1, 1);
				ColumnConstraints widthSet = new ColumnConstraints();
				widthSet.setPercentWidth(50);
				gridPane.getColumnConstraints().add(widthSet);
				labels.add(gridPane);
			}
			ObservableList<GridPane> listGrids = FXCollections.observableArrayList(labels);
			lvUsers.setItems(listGrids);
		}
		if (currUser != null) {
			tfFirstName.setText(currUser.getFirstName());
			tfLastName.setText(currUser.getLastName());
			dpDOB.setValue(LocalDate.parse(currUser.getBirthDate()));
			tfCnic.setText(currUser.getCnic());
			tfPassword.setText(currUser.getPassword());
		}
	}

	// Event Listener on Button.onAction
	@FXML
	public void actionEditUpdateUser(ActionEvent event) {
		Button editBtn = (Button) event.getSource();
		if (editBtn.getText().equals("Edit")) {
			editBtn.setText("Update");
			tfFirstName.setDisable(false);
			tfLastName.setDisable(false);
			dpDOB.setDisable(false);
			tfPassword.setDisable(false);
			tfFirstName.requestFocus();
		} else {
			editBtn.setText("Edit");
			currUser.setFirstName(tfFirstName.getText());
			currUser.setLastName(tfLastName.getText());
			currUser.setBirthDate(dpDOB.getValue().toString());
			currUser.setPassword(tfPassword.getText());
			tfFirstName.setDisable(true);
			tfLastName.setDisable(true);
			dpDOB.setDisable(true);
			tfPassword.setDisable(true);
			if (DatabaseHandler.getInstance().updateUser(currUser)) {
				for (User usr : users) {
					if (usr.getId() == currUser.getId()) {
						usr.setFirstName(currUser.getFirstName());
						usr.setLastName(currUser.getLastName());
						usr.setBirthDate(currUser.getBirthDate());
						usr.setPassword(currUser.getPassword());
						break;
					}
				}
				initialize(null, null);
			}
		}
	}

	// Event Listener on Button.onAction
	@FXML
	public void actionAddUser(ActionEvent event) {
		String cnic = tfNewUserCnic.getText();
		Validator cnicValidator = new Validator(new CnicValidator());
		if (!cnicValidator.validate(cnic)) {
			return;
		}
		User user = DatabaseHandler.getInstance().getUser(cnic);
		for (User usr : users) {
			if (usr.getCnic().equals(user.getCnic())) {
				new HomeeAlerts(new FailureAlert(null, "Attention", "User already found in the list!"));
				return;
			}
		}
		if (user != null && DatabaseHandler.getInstance().addUserToHomee(user.getId(), homee.getId())) {
			users.add(user);
			initialize(null, null);
		} else {
			new HomeeAlerts(new FailureAlert(null, "Attention", "User does not exist!"));
			return;
		}
	}

	@FXML
	private void actionGotoHome(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("res/HomeScene.fxml"));
			Parent root = loader.load();
			Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			currentStage.setScene(new Scene(root));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void actionExitApp(ActionEvent event) {
		Platform.exit();
	}
}
