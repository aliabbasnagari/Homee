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

import javafx.event.ActionEvent;

public class DashboardController {
	@FXML
	private Button btnRooms;

	// Event Listener on Button[#btnRooms].onAction
	@FXML
	public void btnShowRooms(ActionEvent event) {
		DatabaseHandler handler = DatabaseHandler.getInstance();
		Homee homee = Homee.getInstance();
		Dashboard dboard = homee.getDashboard();
		ArrayList<Room> rooms = handler.getRooms(0);
		if (rooms != null) {
			for (int i = 0; i < rooms.size(); i++) {
				System.out.println(rooms.get(i).getTitle());
			}
		}
		dboard.setRooms(rooms);
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("res/RoomsScene.fxml"));
			Parent root = loader.load();
			//Stage stage = new Stage();
			Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			currentStage.setScene(new Scene(root));
			// Show the new stage (window)
			// stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
