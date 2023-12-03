package HomeeUtils.HomeeValidator;

import HomeeUtils.Alerts.HomeeAlerts;
import HomeeUtils.Alerts.WarningAlert;

public class PasswordValidator implements Validate {

	private int minLen;
	private String regex;

	public PasswordValidator(int minLen, String regex) {
		this.minLen = minLen;
		this.regex = regex;

	}

	@Override
	public boolean validate(String sequence) {
		if (sequence.length() < minLen) {
			new HomeeAlerts(
					new WarningAlert(null, "Attention", "Password should be atleast " + minLen + " characters long."));
			return false;
		}
		if (regex != null) {
			if (!sequence.matches(regex)) {
				new HomeeAlerts(new WarningAlert(null, "Attention", "Password should include the given symbols!"));
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean validate(String sequence1, String sequence2) {
		if (this.validate(sequence1)) {
			if (sequence1.equals(sequence2)) {
				return true;
			} else {
				new HomeeAlerts(new WarningAlert(null, "Attention", "Passwords are not matching!"));
			}
		}
		return false;
	}

}
