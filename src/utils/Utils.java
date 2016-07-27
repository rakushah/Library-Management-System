package utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Utils {
	public static void dialogShow(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Library Management System");
		alert.setHeaderText("Information Alert");
		alert.setContentText(message);
		alert.show();

	}

}
