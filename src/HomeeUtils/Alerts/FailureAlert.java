package HomeeUtils.Alerts;

import javafx.scene.control.Alert;

public class FailureAlert extends Alert {
	public FailureAlert(String header, String title, String message) {
		super(AlertType.WARNING);
		setHeaderText(header);
		setTitle(title);
		setContentText(message);
	}
}
