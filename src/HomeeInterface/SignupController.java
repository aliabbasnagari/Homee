package HomeeInterface;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import Homee.User;
import HomeeUtils.DatabaseHandler;
import HomeeUtils.Alerts.FailureAlert;
import HomeeUtils.Alerts.HomeeAlerts;
import HomeeUtils.Alerts.SuccessAlert;
import HomeeUtils.Alerts.WarningAlert;
import HomeeUtils.HomeeValidator.CnicValidator;
import HomeeUtils.HomeeValidator.LengthValidator;
import HomeeUtils.HomeeValidator.PasswordValidator;
import HomeeUtils.HomeeValidator.Validator;
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
		Validator validateName = new Validator(new LengthValidator(3, null));
		Validator validateCnic = new Validator(new CnicValidator());
		Validator validatePassword = new Validator(new PasswordValidator(5, null));
		if (!validateName.validate("First Name", tfFName.getText())
				|| !validateName.validate("Last Name", tfLName.getText()) || !validateCnic.validate(tfCnic.getText())
				|| !validatePassword.validate(pfPassword.getText(), pfCPassword.getText())) {
			return;
		}

		if (dpBirthDate.getValue() == null) {
			new HomeeAlerts(new WarningAlert(null, "Attention", "Date of Birth is required!"));
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
			new HomeeAlerts(new SuccessAlert(null, "Success", "Account created successfully!"));
		} else {
			new HomeeAlerts(new FailureAlert(null, "Failed", "Account creation failed!"));
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
}
