package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.control.ListView;

public class HomeeListController implements Initializable {
	@FXML
	private Label labelWelcome;
	@FXML
	private ListView<Button> lvHomeeList;

	private Homee homee;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loadData();
	}

	private void loadData() {
		DatabaseHandler db = DatabaseHandler.getInstance();
		homee = Homee.getInstance();
		if (homee != null) {
			ArrayList<Integer> homeeId = db.getHomeeID(homee.getCurrentUser().getId());
			if (homeeId != null && homeeId.size() > 0) {
				ArrayList<Button> buttons = new ArrayList<Button>();
				for (int id : homeeId) {
					Button btn = new Button("Profile " + id);
					btn.setMaxWidth(Double.MAX_VALUE);
					btn.setPadding(new Insets(10));
					buttons.add(btn);
				}
				ObservableList<Button> observableList = FXCollections.observableArrayList(buttons);
				lvHomeeList.setItems(observableList);
			}
		}
	}

	// Event Listener on Button.onAction
	@FXML
	public void actionCreateNewProfile(ActionEvent event) {
		DatabaseHandler db = DatabaseHandler.getInstance();
		if (db.createNewHomee(homee.getCurrentUser().getId())) {
			loadData();
		}
	}
}
