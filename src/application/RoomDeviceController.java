package application;

import java.io.IOException;
import java.io.InputStream;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class RoomDeviceController implements Initializable {
	@FXML
	private ScrollPane scrollPane;

	@FXML
	private ListView<Button> listRooms;

	@FXML
	private TextField tfRoomTitle;

	@FXML
	private GridPane gridDevices;

	@FXML
	private Label labelD0, labelD1, labelD2, labelD3, labelD4, labelD5, labelD6, labelD7, labelD8;
	private Label[] dLabels;

	@FXML
	private TextField tfDN0, tfDN1, tfDN2, tfDN3, tfDN4, tfDN5, tfDN6, tfDN7, tfDN8;
	private TextField[] tfDNames;

	@FXML
	private Button btnN0, btnN1, btnN2, btnN3, btnN4, btnN5, btnN6, btnN7, btnN8;
	private Button[] notBtns;

	@FXML
	private Button btnD0, btnD1, btnD2, btnD3, btnD4, btnD5, btnD6, btnD7, btnD8;
	private Button[] dltBtns;

	@FXML
	private Button btnP0, btnP1, btnP2, btnP3, btnP4, btnP5, btnP6, btnP7, btnP8;
	private Button[] powBtns;

	public Room selectedRoom;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		dLabels = new Label[] { labelD0, labelD1, labelD2, labelD3, labelD4, labelD5, labelD6, labelD7, labelD8 };
		tfDNames = new TextField[] { tfDN0, tfDN1, tfDN2, tfDN3, tfDN4, tfDN5, tfDN6, tfDN7, tfDN8 };
		notBtns = new Button[] { btnN0, btnN1, btnN2, btnN3, btnN4, btnN5, btnN6, btnN7, btnN8 };
		dltBtns = new Button[] { btnD0, btnD1, btnD2, btnD3, btnD4, btnD5, btnD6, btnD7, btnD8 };
		powBtns = new Button[] { btnP0, btnP1, btnP2, btnP3, btnP4, btnP5, btnP6, btnP7, btnP8 };

		loadRooms();
	}

	public void loadRooms() {
		ArrayList<Room> rooms = Dashboard.getInstance().getRooms();
		if (rooms != null) {
			ArrayList<Button> roomButtons = new ArrayList<>();
			for (Room room : rooms) {
				Button nbtn = new Button(room.getTitle());
				nbtn.setId(String.valueOf(room.getId()));
				nbtn.setUserData(room);
				nbtn.setOnAction(this::btnPress);
				nbtn.setMaxWidth(Double.MAX_VALUE);
				nbtn.setPadding(new Insets(10));
				roomButtons.add(nbtn);
			}
			if (rooms.size() > 0) {
				selectedRoom = rooms.get(0);
				tfRoomTitle.setText(rooms.get(0).getTitle());
				gridDevices.setDisable(false);
				loadDevices();
			} else {
				selectedRoom = null;
				tfRoomTitle.setText("");
				gridDevices.setDisable(true);
			}
			ObservableList<Button> observableList = FXCollections.observableArrayList(roomButtons);
			listRooms.setItems(observableList);
		}
	}

	private void loadDevices() {
		Device[] devices = selectedRoom.getDevices();
		for (int i = 0; i < devices.length; i++) {
			dLabels[i].setText(devices[i].toString());
			if (devices[i].getId() > -1) {
				devices[i].simulate();
				powBtns[i].setText((devices[i].getPower()) ? "ON" : "OFF");
				notBtns[i].setAccessibleText((devices[i].getNotification()) ? "ON" : "OFF");
				updateImage(notBtns[i]);
			}
			notBtns[i].setDisable(false);
			dltBtns[i].setDisable(false);
			powBtns[i].setDisable(false);
			if (devices[i].getId() == -1) {
				notBtns[i].setDisable(true);
				dltBtns[i].setDisable(true);
				powBtns[i].setDisable(true);
			}
		}
	}

	private void btnPress(ActionEvent e) {
		Button clickedButton = (Button) e.getSource();
		selectedRoom = (Room) clickedButton.getUserData();
		System.out.println(clickedButton.getId() + " <btn> " + selectedRoom.getTitle());
		tfRoomTitle.setText(selectedRoom.getTitle());
		loadDevices();
	}

	@FXML
	private void actionGotoAddRoom(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("res/AddRoomScene.fxml"));
			Parent root = loader.load();
			((AddRoomController) loader.getController()).setRoomController(this);
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void actionEditRoom(ActionEvent event) {
		Button clicked = (Button) event.getSource();
		if (clicked.getText().equals("Edit")) {
			tfRoomTitle.setEditable(true);
			tfRoomTitle.requestFocus();
			clicked.setText("Update");
		} else {
			tfRoomTitle.setEditable(false);
			clicked.setText("Edit");
			String oldTitle = selectedRoom.getTitle();
			selectedRoom.setTitle(tfRoomTitle.getText());
			if (DatabaseHandler.getInstance().updateRoom(selectedRoom)) {
				new HomeeAlerts(new SuccessAlert(null, "Success!", "Room Updated!"));
				loadRooms();
			} else {
				selectedRoom.setTitle(oldTitle);
			}
		}
	}

	@FXML
	private void actionDeleteRoom(ActionEvent event) {
		int rid = Integer.valueOf(selectedRoom.getId());
		if (DatabaseHandler.getInstance().deleteRoom(rid)) {
			Dashboard.getInstance().getRooms().removeIf(room -> room.getId() == rid);
			new HomeeAlerts(new SuccessAlert(null, "Success!", "Room Deleted!"));
			loadRooms();
		}
	}

	@FXML
	private void actionModifyDevice(ActionEvent event) {
		Button modifyBtn = (Button) event.getSource();
		int btnID = Integer.valueOf(modifyBtn.getId().substring(4, 5));
		Label currL = dLabels[btnID];
		TextField currTF = tfDNames[btnID];
		Button currP = powBtns[btnID];
		Button currN = notBtns[btnID];
		if (modifyBtn.getText().equals("modify")) {
			currTF.setVisible(true);
			currL.setVisible(false);
			modifyBtn.setText("update");
		} else {
			if (!currTF.getText().isBlank()) {
				Device[] devices = selectedRoom.getDevices();
				devices[btnID].setName(currTF.getText());
				devices[btnID].setPower(currP.getText().equals("ON"));
				devices[btnID].setNotification(currN.getAccessibleText().equals("ON"));
				if (devices[btnID].getId() < 0) {
					devices[btnID].setId(btnID);
					if (DatabaseHandler.getInstance().createNewDevice(selectedRoom.getId(), devices[btnID])) {
						loadDevices();
					}
				} else {
					if (DatabaseHandler.getInstance().updateDevice(devices[btnID])) {
						loadDevices();
					}
				}
			} else {
				new HomeeAlerts(new FailureAlert(null, "Failed!", "Device Name is required!"));
			}
			currTF.setText("");
			currTF.setVisible(false);
			currL.setVisible(true);
			modifyBtn.setText("modify");
		}
	}

	@FXML
	private void actionDeviceNotification(ActionEvent event) {
		Button notifyBtn = (Button) event.getSource();
		int btnID = Integer.valueOf(notifyBtn.getId().substring(4, 5));
		if (notifyBtn.getAccessibleText().equals("ON")) {
			notifyBtn.setAccessibleText("OFF");
			selectedRoom.getDevices()[btnID].setNotification(false);
		} else {
			notifyBtn.setAccessibleText("ON");
			selectedRoom.getDevices()[btnID].setNotification(true);
		}
		DatabaseHandler.getInstance().updateDevice(selectedRoom.getDevices()[btnID]);
		updateImage(notifyBtn);
	}

	private void updateImage(Button notifyBtn) {
		if (notifyBtn.getAccessibleText().equals("ON")) {
			((ImageView) notifyBtn.getGraphic())
					.setImage(new Image(getClass().getResourceAsStream("res/img/notification_on.png")));
		} else {
			((ImageView) notifyBtn.getGraphic())
					.setImage(new Image(getClass().getResourceAsStream("res/img/notification_off.png")));
		}
	}

	@FXML
	private void actionDeleteDevice(ActionEvent event) {
		Button modifyBtn = (Button) event.getSource();
		int btnID = Integer.valueOf(modifyBtn.getId().substring(4, 5));
		if (DatabaseHandler.getInstance().deleteDevice(selectedRoom.getDevices()[btnID].getId())) {
			selectedRoom.getDevices()[btnID].setId(-1);
			selectedRoom.getDevices()[btnID].setName("Empty Slot");
			loadDevices();
		}
	}

	@FXML
	private void actionPowerDevice(ActionEvent event) {
		Button powerBtn = (Button) event.getSource();
		int btnID = Integer.valueOf(powerBtn.getId().substring(4, 5));
		if (powerBtn.getText().equals("ON")) {
			powerBtn.setText("OFF");
			selectedRoom.getDevices()[btnID].setPower(false);
		} else {
			powerBtn.setText("ON");
			selectedRoom.getDevices()[btnID].setPower(true);
		}
		DatabaseHandler.getInstance().updateDevice(selectedRoom.getDevices()[btnID]);
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
