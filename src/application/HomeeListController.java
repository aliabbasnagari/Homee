package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

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
		DatabaseHandler db = DatabaseHandler.getInstance();
		homee = Homee.getInstance();
		if (homee != null) {
			ArrayList<Integer> homeeId = db.getHomeeID(homee.getCurrentUser().getId());
			System.out.println(homeeId.get(0));
			if (homeeId != null && homeeId.size() > 0) {
				ArrayList<Button> buttons = new ArrayList<Button>();
				for (int id : homeeId) {
					Button btn = new Button("Profile " + id);
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
		
	}
}
