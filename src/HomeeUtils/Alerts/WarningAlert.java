package HomeeUtils.Alerts;

import javafx.scene.control.Alert;

public class WarningAlert extends Alert {
	public WarningAlert(String header, String title, String message) {
		super(AlertType.WARNING);
		setHeaderText(header);
		setTitle(title);
		setContentText(message);
	}
}