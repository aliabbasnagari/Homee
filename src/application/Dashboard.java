package application;

import java.util.ArrayList;

public class Dashboard {
	private static Dashboard _dashboard;
	private int id;
	private ArrayList<Room> rooms;
	private String powerMode;
	private CollectiveStatistics fullStats;

	private Dashboard() {
		id = -1;
		rooms = new ArrayList<Room>();
		powerMode = "GRID";
		fullStats = CollectiveStatistics.getInstance();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public static Dashboard getInstance() {
		if (_dashboard == null) {
			_dashboard = new Dashboard();
		}
		return _dashboard;
	}

	public ArrayList<Room> getRooms() {
		return rooms;
	}

	public void setRooms(ArrayList<Room> rooms) {
		this.rooms = rooms;
	}

	public String getPowerMode() {
		return powerMode;
	}

	public void setPowerMode(String powerMode) {
		this.powerMode = powerMode;
	}

	public CollectiveStatistics getFullStats() {
		return fullStats;
	}

	public void setFullStats(CollectiveStatistics fullStats) {
		this.fullStats = fullStats;
	}

}
