package application;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import java.util.List;

public class Main extends Application {
	private int winWidth = 1000;
	private int winHeight = 700;
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("res/LoginScene.fxml"));
			Scene scene = new Scene(root, winWidth, winHeight);
			primaryStage.setScene(scene);
			
			List<Image> icons = new ArrayList<>();
	        icons.add(new Image(getClass().getResourceAsStream("res/img/app_icon.png")));
	        primaryStage.getIcons().addAll(icons);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
