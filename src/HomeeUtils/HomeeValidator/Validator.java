package HomeeUtils.HomeeValidator;

public class Validator {
	private Validate valid_checker;

	public Validator(Validate validate) {
		this.valid_checker = validate;
	}

	public boolean validate(String sequence) {
		return valid_checker.validate(sequence);
	}

	public boolean validate(String field, String sequence) {
		return valid_checker.validate(field, sequence);
	}
}
