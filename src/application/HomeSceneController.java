package application;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.application.Platform;
import javafx.event.ActionEvent;

public class HomeSceneController {
	@FXML
	private Button btnExit;

	// Event Listener on Button[#btnExit].onAction
	@FXML
	public void actionExitApp(ActionEvent event) {
		Platform.exit();
	}
}
