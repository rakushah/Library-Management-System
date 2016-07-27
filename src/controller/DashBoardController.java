package controller;

import java.awt.print.Book;
import java.io.IOException;

import application.BookList;
import application.FrmCheckOutRecordListClass;
import application.FrmDashboard;
import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import members.FrmMemberList;

public class DashBoardController {
	public FrmDashboard getObjFrmDashboard() {
		return objFrmDashboard;
	}

	public void setObjFrmDashboard(FrmDashboard objFrmDashboard) {
		this.objFrmDashboard = objFrmDashboard;
	}

	FrmDashboard objFrmDashboard;
	public Stage loginForm;
	@FXML AnchorPane contentPane;
	Stage memberList;
	Stage bookList;
	Stage checkoutList;
	@FXML
	public void memberList() {
	/*	memberList=FrmMemberList.getNewMemberListInstance();
		memberList.show();*/
		//memberList=FrmMemberList.getNEwCheckOutInstance();
		//memberList.show();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/members/fxmlMemberList.fxml"));
		try {
			Pane root = (Pane) loader.load();
			//checkoutList=FrmCheckOutRecordListClass.getNEwCheckOutInstance();
			//Pane contentPane1=(Pane) objFrmDashboard.getScene().getRoot().getParent();
			contentPane.getChildren().clear();
			contentPane.getChildren().add(root);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//checkoutList.show();
	}

	@FXML
	public void bookList() {
		//bookList=BookList.getNEwCheckOutInstance();
		//bookList.show();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/FrmBooklist.fxml"));
		try {
			Pane root = (Pane) loader.load();
			//checkoutList=FrmCheckOutRecordListClass.getNEwCheckOutInstance();
			//Pane contentPane1=(Pane) objFrmDashboard.getScene().getRoot().getParent();
			contentPane.getChildren().clear();
			contentPane.getChildren().add(root);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void checkOutRecordList() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/FrmCheckOUtRecordList.fxml"));
		try {
			Pane root = (Pane) loader.load();
			//checkoutList=FrmCheckOutRecordListClass.getNEwCheckOutInstance();
			//Pane contentPane1=(Pane) objFrmDashboard.getScene().getRoot().getParent();
			contentPane.getChildren().clear();
			contentPane.getChildren().add(root);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//checkoutList.show();
	}

	@FXML
	void logOut(){
		try {
			objFrmDashboard.close();
			Main obj = new Main();
			obj.start(loginForm);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
