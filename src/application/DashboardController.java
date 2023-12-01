package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.event.ActionEvent;

public class DashboardController {
	@FXML
	private Button btnRooms;

	// Event Listener on Button[#btnRooms].onAction
	@FXML
	public void btnShowRooms(ActionEvent event) {
		Homee homee = Homee.getInstance();
		Dashboard dboard = homee.getDashboard();
		ArrayList<Room> rooms = dboard.getRooms();
		if (rooms != null) {
			dboard.setRooms(rooms);
		}
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("res/RoomDeviceScene.fxml"));
			Parent root = loader.load();
			Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			currentStage.setScene(new Scene(root));
		} catch (IOException e) {
			e.printStackTrace();
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
