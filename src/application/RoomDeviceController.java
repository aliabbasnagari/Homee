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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RoomDeviceController implements Initializable {
	@FXML
	private ScrollPane scrollPane;

	@FXML
	private ListView<Button> listRooms;

	@FXML
	private TextField tfRoomTitle;

	private Room selectedRoom;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
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
			} else {
				selectedRoom = null;
				tfRoomTitle.setText("");
			}
			ObservableList<Button> observableList = FXCollections.observableArrayList(roomButtons);
			listRooms.setItems(observableList);
		}
	}

	private void btnPress(ActionEvent e) {
		Button clickedButton = (Button) e.getSource();
		selectedRoom = (Room) clickedButton.getUserData();
		System.out.println(clickedButton.getId() + " <btn> " + selectedRoom.getTitle());
		tfRoomTitle.setText(selectedRoom.getTitle());

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
