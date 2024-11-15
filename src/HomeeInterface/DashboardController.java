package HomeeInterface;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Homee.Dashboard;
import Homee.Homee;
import Homee.Room;
import HomeeUtils.DatabaseHandler;
import javafx.application.Platform;
import javafx.event.ActionEvent;

public class DashboardController implements Initializable {
	@FXML
	private Button btnRooms;

	@FXML
	private Label labelPowerMode;

	@FXML
	private Label lbUsgae;
	@FXML
	private Label lbSolarE;
	@FXML
	private Label lbGridE;
	@FXML
	private Label lbTemperature;
	@FXML
	private Label lbHumidity;

	private Dashboard dboard;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		dboard = Dashboard.getInstance();
		dboard.getFullStats().refresh(dboard.getRooms());
		if (dboard.getPowerMode().toLowerCase().equals("grid")) {
			labelPowerMode.setText("GRID");
			labelPowerMode.getStyleClass().setAll("modeGrid");
		} else if (dboard.getPowerMode().toLowerCase().equals("solar")) {
			labelPowerMode.setText("SOLAR");
			labelPowerMode.getStyleClass().setAll("modeSolar");
		} else {
			labelPowerMode.setText("HYBRID");
			labelPowerMode.getStyleClass().setAll("modeHybrid");
		}
		simulateStats();
	}

	private void simulateStats() {
		double gridUsage = (dboard.getPowerMode().equals("SOLAR")) ? 0 : (Math.random() * 25);
		double solarUsage = (dboard.getPowerMode().equals("GRID")) ? 0 : (Math.random() * 25);
		lbUsgae.setText(String.format("%.3f (kW)", dboard.getFullStats().getPowerUsage()));
		lbGridE.setText(String.format("+%.3f (kW)   -%.3f (kW)", dboard.getFullStats().getGridEnergy(), gridUsage));
		lbSolarE.setText(String.format("+%.3f (kW)   -%.3f (kW)", dboard.getFullStats().getSolarEnergy(), solarUsage));
		lbTemperature.setText(String.format("%.3f⁰C", dboard.getFullStats().getTemperature()));
		lbHumidity.setText(String.format("%.3f (RH)", dboard.getFullStats().getHumidity()));
	}

	// Event Listener on Button[#btnRooms].onAction
	@FXML
	public void btnShowRooms(ActionEvent event) {
		Homee homee = Homee.getInstance();
		Dashboard dboard = homee.getDashboard();
		ArrayList<Room> rooms = dboard.getRooms();
		if (rooms != null) {
			dboard.setRooms(rooms);
		}
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("res/RoomDeviceScene.fxml"));
			Parent root = loader.load();
			Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			currentStage.setScene(new Scene(root));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void actionActivateGrid(ActionEvent event) {
		labelPowerMode.setText("GRID");
		labelPowerMode.getStyleClass().setAll("modeGrid");
		DatabaseHandler.getInstance().updateDashboard(dboard.getId(), "GRID");
		dboard.setPowerMode("GRID");
		simulateStats();
	}

	@FXML
	private void actionActivateSolar(ActionEvent event) {
		labelPowerMode.setText("SOLAR");
		labelPowerMode.getStyleClass().setAll("modeSolar");
		DatabaseHandler.getInstance().updateDashboard(dboard.getId(), "SOLAR");
		dboard.setPowerMode("SOLAR");
		simulateStats();
	}

	@FXML
	private void actionActivateHybrid(ActionEvent event) {
		labelPowerMode.setText("HYBRID");
		labelPowerMode.getStyleClass().setAll("modeHybrid");
		DatabaseHandler.getInstance().updateDashboard(dboard.getId(), "HYBRID");
		dboard.setPowerMode("HYBRID");
		simulateStats();
	}

	@FXML
	private void actionGotoHome(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("res/HomeScene.fxml"));
			Parent root = loader.load();
			Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			currentStage.setScene(new Scene(root));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void actionExitApp(ActionEvent event) {
		Platform.exit();
	}
}
