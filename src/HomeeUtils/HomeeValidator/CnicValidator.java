package HomeeUtils.HomeeValidator;

import HomeeUtils.Alerts.HomeeAlerts;
import HomeeUtils.Alerts.WarningAlert;

public class CnicValidator implements Validate {

	@Override
	public boolean validate(String sequence) {
		if (sequence.isBlank()) {
			new HomeeAlerts(new WarningAlert(null, "Attention", "Cnic is required!"));
			return false;
		}
		if (!sequence.matches("\\d{5}-\\d{7}-\\d")) {
			new HomeeAlerts(new WarningAlert("Invalid Cnic!", "Attention", "Correct format is xxxxx-xxxxxxx-x"));
			return false;
		}
		return true;
	}

	@Override
	public boolean validate(String field, String sequence) {
		if (sequence.isBlank()) {
			new HomeeAlerts(new WarningAlert(null, "Attention", field + " is required!"));
			return false;
		}
		if (!sequence.matches("\\d{5}-\\d{7}-\\d")) {
			new HomeeAlerts(new WarningAlert("Invalid " + field + "!", "Attention", "Correct format is xxxxx-xxxxxxx-x (only numbers)"));
			return false;
		}
		return true;
	}
}
