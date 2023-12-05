package HomeeInterface;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Homee.Homee;
import Homee.Network;
import Homee.User;
import HomeeUtils.DatabaseHandler;
import HomeeUtils.Alerts.HomeeAlerts;
import HomeeUtils.Alerts.SuccessAlert;
import HomeeUtils.Alerts.WarningAlert;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.ListView;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class NetworksController implements Initializable {
	@FXML
	private TextField tfNetTitle;
	@FXML
	private TextField tfIP;
	@FXML
	private Button btnStatus;
	@FXML
	private Button btnAccess;
	@FXML
	private ListView<GridPane> lvNetwork;

	private Homee homee;
	private ArrayList<Network> nets;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		homee = Homee.getInstance();
		nets = Homee.getInstance().getNetworks();
		if (nets != null) {
			ArrayList<GridPane> labels = new ArrayList<GridPane>();
			for (Network net : nets) {
				Label label1 = new Label(net.getTitle() + "  " + net.getAccess());
				Label label2 = new Label(net.getIp());
				label1.setFont(new Font(20));
				label2.setFont(new Font(20));
				GridPane gridPane = new GridPane();
				gridPane.add(label1, 0, 1);
				gridPane.add(label2, 1, 1);
				ColumnConstraints widthSet = new ColumnConstraints();
				widthSet.setPercentWidth(50);
				gridPane.getColumnConstraints().add(widthSet);
				labels.add(gridPane);
			}
			ObservableList<GridPane> listGrids = FXCollections.observableArrayList(labels);
			lvNetwork.setItems(listGrids);
		}
	}

	// Event Listener on Button.onAction
	@FXML
	public void actionAddNetwork(ActionEvent event) {
		if (tfNetTitle.getText().isBlank()) {
			new HomeeAlerts(new WarningAlert(null, "Attention", "Network Title Required!"));
			return;
		}
		if (tfIP.getText().isBlank()) {
			new HomeeAlerts(new WarningAlert(null, "Attention", "IP Address Required!"));
			return;
		}
		if (!tfIP.getText().matches("\\d{3}.\\d{3}.\\d{3}")) {
			new HomeeAlerts(new WarningAlert(null, "Attention", "IP Address is invalid!"));
			return;
		}
		Network net = new Network();
		net.setIp(tfIP.getText());
		net.setTitle(tfNetTitle.getText());
		net.setAccess(btnAccess.getText());
		net.setLive(btnStatus.getText().equals("LIVE"));
		if (DatabaseHandler.getInstance().addNetwork(Homee.getInstance().getId(), net)) {
			new HomeeAlerts(new SuccessAlert(null, "Success", "Network Added!"));
			homee.setNetworks(DatabaseHandler.getInstance().getNetwork(homee.getId()));
			initialize(null, null);
		}
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

	@FXML
	public void actionSetAccess(ActionEvent event) {
		if (btnAccess.getText().equals("PRIVATE")) {
			btnAccess.setText("PUBLIC");
		} else {
			btnAccess.setText("PRIVATE");
		}
	}

	@FXML
	public void actionSetStatus(ActionEvent event) {
		if (btnStatus.getText().equals("LIVE")) {
			btnStatus.setText("DOWN");
		} else {
			btnStatus.setText("LIVE");
		}
	}
}
