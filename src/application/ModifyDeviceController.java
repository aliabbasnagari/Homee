package application;

import javafx.fxml.FXML;

import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.event.ActionEvent;

public class ModifyDeviceController {
	@FXML
	private TextField tfDeviceTitle;

	private RoomDeviceController roomDeviceController;
	private int deviceID;
	private boolean power = false;
	private boolean notification = false;

	// Event Listener on ToggleButton.onAction
	@FXML
	public void actionPowerToggle(ActionEvent event) {
		ToggleButton btn = (ToggleButton) event.getSource();
		if (btn.getText().equals("ON")) {
			btn.setText("OFF");
			power = true;
		} else {
			btn.setText("ON");
			power = false;
		}
	}

	// Event Listener on ToggleButton.onAction
	@FXML
	public void actionNotificationToggle(ActionEvent event) {
		ToggleButton btn = (ToggleButton) event.getSource();
		if (btn.getText().equals("ON")) {
			btn.setText("OFF");
			notification = true;
		} else {
			btn.setText("ON");
			notification = false;
		}
	}

	// Event Listener on Button.onAction
	@FXML
	public void actionUpdateDevice(ActionEvent event) {
		Device[] devices = roomDeviceController.selectedRoom.getDevices();
		devices[deviceID].setName(tfDeviceTitle.getText());
		devices[deviceID].setPower(power);
		devices[deviceID].setNotification(notification);
		if (devices[deviceID].getId() < 0) {
			devices[deviceID].setId(deviceID);
			DatabaseHandler.getInstance().createNewDevice(roomDeviceController.selectedRoom.getId(), devices[deviceID]);
		} else {
			DatabaseHandler.getInstance().updateDevice(devices[deviceID]);
		}
		roomDeviceController.loadRooms();
	}

	public int getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(int deviceID) {
		this.deviceID = deviceID;
	}

	public RoomDeviceController getRoomDeviceController() {
		return roomDeviceController;
	}

	public void setRoomDeviceController(RoomDeviceController roomDeviceController) {
		this.roomDeviceController = roomDeviceController;
	}

}
