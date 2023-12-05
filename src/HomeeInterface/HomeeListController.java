package HomeeInterface;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Homee.Homee;
import HomeeUtils.DatabaseHandler;
import HomeeUtils.Pair;
import HomeeUtils.Alerts.FailureAlert;
import HomeeUtils.Alerts.HomeeAlerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class HomeeListController implements Initializable {
	@FXML
	private Label labelWelcome;
	@FXML
	private ListView<Button> lvHomeeList;
	@FXML
	private TextField tfHomeeProfile;

	private Homee homee;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loadData();
		labelWelcome.setText("Welcome, " + homee.getCurrentUser().getFirstName());
	}

	private void loadData() {
		DatabaseHandler db = DatabaseHandler.getInstance();
		homee = Homee.getInstance();
		if (homee != null) {
			ArrayList<Pair<Integer, String>> homeeId = db.getUserHomees(homee.getCurrentUser().getId());
			if (homeeId != null && homeeId.size() > 0) {
				ArrayList<Button> buttons = new ArrayList<Button>();
				for (Pair<Integer, String> val : homeeId) {
					Button btn = new Button(val.getSecond());
					btn.setId(String.valueOf(val.getFirst()));
					btn.setUserData(val.getSecond());
					btn.setMaxWidth(Double.MAX_VALUE);
					btn.setPadding(new Insets(10));
					btn.setOnAction(this::btnHomeePress);
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
		if (tfHomeeProfile.getText().isBlank()) {
			new HomeeAlerts(new FailureAlert(null, "Failed!", "Profile Name is required."));
			return;
		}
		DatabaseHandler db = DatabaseHandler.getInstance();
		if (db.createNewHomee(homee.getCurrentUser().getId(), tfHomeeProfile.getText())) {
			loadData();
		}
	}

	private void btnHomeePress(ActionEvent e) {
		Button clickedButton = (Button) e.getSource();
		System.out.println(clickedButton.getId() + " <btn> " + clickedButton.getUserData());
		homee.setId(Integer.valueOf(clickedButton.getId()));
		try {
			DatabaseHandler db = DatabaseHandler.getInstance();
			if (db.populateDashboard(homee.getId())) {
				homee.setUsers(db.getHomeeUsers(homee.getId()));
				homee.setNetworks(db.getNetwork(homee.getId()));
				FXMLLoader loader = new FXMLLoader(getClass().getResource("res/HomeScene.fxml"));
				Parent root = loader.load();
				Stage currentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
				currentStage.setScene(new Scene(root));
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}
}
