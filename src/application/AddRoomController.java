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

	private RoomDeviceController roomDeviceController;

	// Event Listener on Button[#btnAddRoom].onAction
	@FXML
	public void actionAddRoom(ActionEvent event) {
		if (tfRoomTitle.getText().isBlank()) {
			new HomeeAlerts(new FailureAlert(null, "Failed!", "Failed to add the room!"));
		}
		DatabaseHandler db = DatabaseHandler.getInstance();
		if (db.createNewRoom(Dashboard.getInstance().getId(), tfRoomTitle.getText())) {
			new HomeeAlerts(new SuccessAlert(null, "Success!", "Successfully added the room!"));
			roomDeviceController.loadRooms();
		}
	}

	public RoomDeviceController getRoomController() {
		return roomDeviceController;
	}

	public void setRoomController(RoomDeviceController roomCont) {
		this.roomDeviceController = roomCont;
	}

}
