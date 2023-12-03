package HomeeUtils.Alerts;

import javafx.scene.control.Alert;

public class SuccessAlert extends Alert {

	public SuccessAlert(String header, String title, String message) {
		super(AlertType.CONFIRMATION);
		setHeaderText(header);
		setTitle(title);
		setContentText(message);
	}
}
