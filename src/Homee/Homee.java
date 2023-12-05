package Homee;

import java.util.ArrayList;

public class Homee {
	private static Homee _homee;
	private int id;
	private User currentUser;
	private String title;
	private ArrayList<User> users;
	private ArrayList<Network> networks;
	private Dashboard dashboard;
	private Payment payment;

	private Homee() {
		currentUser = null;
		users = new ArrayList<User>();
		networks = new ArrayList<Network>();
		dashboard = Dashboard.getInstance();
		payment = new Payment();
	}

	public ArrayList<Network> getNetworks() {
		return networks;
	}

	public void setNetworks(ArrayList<Network> networks) {
		this.networks = networks;
	}

	public static Homee getInstance() {
		if (_homee == null) {
			_homee = new Homee();
		}
		return _homee;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
