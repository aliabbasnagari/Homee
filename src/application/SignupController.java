package application;

import javafx.fxml.FXML;

import javafx.scene.control.TextField;

import javafx.event.ActionEvent;

import javafx.scene.control.PasswordField;
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

	private void showAlert(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
