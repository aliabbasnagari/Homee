package application;

import java.util.ArrayList;

public class Homee {
	private static Homee _homee;
	private User currentUser;
	private ArrayList<User> users;
	private Dashboard dashboard;
	private Payment payment;

	private Homee() {
		currentUser = null;
		users = new ArrayList<User>();
		dashboard = new Dashboard();
		payment = new Payment();
	}

	public static Homee getInstance() {
		if (_homee == null) {
			_homee = new Homee();
		}
		return _homee;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public ArrayList<User> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}

	public Dashboard getDashboard() {
		return dashboard;
	}

	public void setDashboard(Dashboard dashboard) {
		this.dashboard = dashboard;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

}
