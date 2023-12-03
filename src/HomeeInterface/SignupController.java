package HomeeInterface;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import Homee.User;
import HomeeUtils.DatabaseHandler;
import javafx.event.ActionEvent;

import javafx.scene.control.PasswordField;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;

public class SignupController {
	@FXML
	private TextField tfFName;
	@FXML
	private TextField tfLName;
	@FXML
	private DatePicker dpBirthDate;
	@FXML
	private TextField tfCnic;
	@FXML
	private PasswordField pfPassword;
	@FXML
	private PasswordField pfCPassword;

	// Event Listener on Button.onAction
	@FXML
	public void actionSignUp(ActionEvent event) {
		if (tfFName.getText().isBlank() || tfFName.getText().isBlank()) {
			showAlert("Failed", "First Name and Last Name is required!");
			return;
		}
		if (dpBirthDate.getValue() == null) {
			showAlert("Failed", "Date of Birth is required!");
			return;
		}
		if (!tfCnic.getText().matches("\\d{5}-\\d{7}-\\d")) {
			showAlert("Failed", "Invalid Cnic!");
			return;
		}
		if (pfPassword.getText().isBlank() || pfPassword.getText().length() < 5) {
			showAlert("Failed", "Password should be atleast 5 characters long.");
			return;
		}
		if (!pfPassword.getText().equals(pfCPassword.getText())) {
			showAlert("Failed", "Password is not matching.");
			return;
		}
		DatabaseHandler db = DatabaseHandler.getInstance();
		User user = new User();
		user.setFirstName(tfFName.getText());
		user.setLastName(tfLName.getText());
		user.setBirthDate(dpBirthDate.getValue().toString());
		user.setCnic(tfCnic.getText());
		user.setPassword(pfCPassword.getText());
		if (db.addNewUser(user)) {
			showAlert("Success", "Account created successfully!");
		} else {
			showAlert("Failed", "Account creation failed!");
		}
	}
	
	@FXML
	private void actionGotoLogin(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("res/LoginScene.fxml"));
			Parent root = loader.load();
			Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			currentStage.setScene(new Scene(root));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void showAlert(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
