package HomeeUtils.HomeeValidator;

import HomeeUtils.Alerts.HomeeAlerts;
import HomeeUtils.Alerts.WarningAlert;

public class LengthValidator implements Validate {
	private int length;
	private String message;

	public LengthValidator(int length, String error_message) {
		this.length = length;
		this.message = error_message;
	}

	@Override
	public boolean validate(String sequence) {
		if (sequence.length() < length) {
			if (message == null) {
				message = "Should be atleast " + length + " characters long!";
				if (sequence.isBlank()) {
					message = "Cannot be blank!";
				}
			}
			new HomeeAlerts(new WarningAlert(null, "Attention", message));
			return false;
		}
		return true;
	}

	@Override
	public boolean validate(String field, String sequence) {
		if (sequence.length() < length) {
			if (message == null) {
				message = field + " should be atleast " + length + " characters long!";
				if (sequence.isBlank()) {
					message = field + " is required!";
				}
			}
			new HomeeAlerts(new WarningAlert(null, "Attention", message));
			return false;
		}
		return true;
	}

}
