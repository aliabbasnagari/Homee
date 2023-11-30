package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class RoomDeviceController implements Initializable {
	@FXML
	private ScrollPane scrollPane;

	@FXML
	private ListView<Button> listRooms;

	@FXML
	private Label labelSeletedRoom;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Homee homee = Homee.getInstance();
		ArrayList<Room> rooms = homee.getDashboard().getRooms();
		if (rooms != null) {
			ArrayList<Button> roomButtons = new ArrayList<>();
			for (Room room : rooms) {
				Button nbtn = new Button(room.getTitle());
				nbtn.setId(String.valueOf(room.getId()));
				nbtn.setUserData(room.getTitle());
				nbtn.setOnAction(this::btnPress);
				nbtn.setMaxWidth(Double.MAX_VALUE);
				nbtn.setPadding(new Insets(10));
				roomButtons.add(nbtn);
			}
			labelSeletedRoom.setText(rooms.get(0).getTitle());
			ObservableList<Button> observableList = FXCollections.observableArrayList(roomButtons);
			listRooms.setItems(observableList);
		}
	}

	private void btnPress(ActionEvent e) {
		Button clickedButton = (Button) e.getSource();
		System.out.println(clickedButton.getId());
		System.out.println(clickedButton.getUserData());
		labelSeletedRoom.setText(clickedButton.getUserData().toString());

	}

	@FXML
	private void actionGotoAddRoom(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("res/AddRoomScene.fxml"));
			Parent root = loader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void actionGotoDashboard(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("res/DashboardScene.fxml"));
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
