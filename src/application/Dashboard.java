package application;

import java.util.ArrayList;

public class Dashboard {
	private ArrayList<Room> rooms;
	private String powerMode;
	private CollectiveStatistics fullStats;

	public Dashboard() {
		rooms = new ArrayList<Room>();
		powerMode = "GRID";
		fullStats = new CollectiveStatistics();
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
