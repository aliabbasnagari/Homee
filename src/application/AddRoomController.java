package application;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import javafx.event.ActionEvent;

public class AddRoomController {
	@FXML
	private TextField tfRoomTitle;
	@FXML
	private Button btnAddRoom;

	private RoomDeviceController roomCont;

	// Event Listener on Button[#btnAddRoom].onAction
	@FXML
	public void actionAddRoom(ActionEvent event) {
		if (tfRoomTitle.getText().isBlank()) {
			new HomeeAlerts(new FailureAlert(null, "Failed!", "Failed to add the room!"));
		}
		DatabaseHandler db = DatabaseHandler.getInstance();
		if (db.createNewRoom(Dashboard.getInstance().getId(), tfRoomTitle.getText())) {
			new HomeeAlerts(new SuccessAlert(null, "Success!", "Successfully added the room!"));
			roomCont.loadRooms();
		}
	}

	public RoomDeviceController getRoomController() {
		return roomCont;
	}

	public void setRoomController(RoomDeviceController roomCont) {
		this.roomCont = roomCont;
	}

}
