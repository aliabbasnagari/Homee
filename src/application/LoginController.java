package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.event.ActionEvent;

import javafx.scene.control.PasswordField;

public class LoginController {
	@FXML
	private TextField tfCnic;
	@FXML
	private Button btnLogin;
	@FXML
	private PasswordField tfPassword;

	// Event Listener on Button[#btnLogin].onAction
	@FXML
	public void actionLogin(ActionEvent event) {
		String cnic = tfCnic.getText();
		String password = tfPassword.getText();
		if (!cnic.matches("\\d{5}-\\d{7}-\\d")) {
			showAlert("Failed", "Invalid Cnic!");
			return;
		}
		if (password.isBlank()) {
			showAlert("Failed", "Password is required!");
			return;
		}
		DatabaseHandler db = DatabaseHandler.getInstance();
		User currentUser = db.getUser(cnic, password);
		if (currentUser != null) {
			Homee homee = Homee.getInstance();
			homee.setCurrentUser(currentUser);
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("res/HomeeListScene.fxml"));
				Parent root = loader.load();
				Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				currentStage.setScene(new Scene(root));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			showAlert("Login Failed", "Invalid credentials!");
			return;
		}
	}

	// Event Listener on Button.onAction
	@FXML
	public void actionGotoSignup(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("res/SignupScene.fxml"));
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
