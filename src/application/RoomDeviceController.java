package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;

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
}
