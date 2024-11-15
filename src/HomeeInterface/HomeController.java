package HomeeInterface;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;

public class HomeController {
	@FXML
	private Button btnGotoDashboard;
	@FXML
	private Button btnGotoUsers;
	@FXML
	private Button btnGotoNetworks;
	@FXML
	private Button btnGotoPayment;
	@FXML
	private Button btnGotoPaymentHistory;
	@FXML
	private Button btnGetCustomerSupport;
	@FXML
	private Button btnExit;

	// Event Listener on Button[#btnGotoDashboard].onAction
	@FXML
	public void actionGotoDashboard(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("res/DashboardScene.fxml"));
			Parent root = loader.load();
			Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			currentStage.setScene(new Scene(root));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Event Listener on Button[#btnGotoUsers].onAction
	@FXML
	public void actionGotoUsers(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("res/UsersScene.fxml"));
			Parent root = loader.load();
			Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			currentStage.setScene(new Scene(root));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Event Listener on Button[#btnGotoNetworks].onAction
	@FXML
	public void actionGotoNetworks(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("res/NetworksScene.fxml"));
			Parent root = loader.load();
			Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			currentStage.setScene(new Scene(root));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Event Listener on Button[#btnGotoPayment].onAction
	@FXML
	public void actionGotoPayment(ActionEvent event) {
		// TODO Autogenerated
	}

	// Event Listener on Button[#btnGotoPaymentHistory].onAction
	@FXML
	public void actionGotoPaymentHistory(ActionEvent event) {
		// TODO Autogenerated
	}

	// Event Listener on Button[#btnGetCustomerSupport].onAction
	@FXML
	public void actionGetCustomerSupport(ActionEvent event) {
		// TODO Autogenerated
	}

	// Event Listener on Button[#btnExit].onAction
	@FXML
	public void actionExitApp(ActionEvent event) {
		// Platform.exit();
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
