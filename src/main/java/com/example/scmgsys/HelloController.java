package com.example.scmgsys;

import com.example.scmgsys.login.LoginModel;
import com.example.scmgsys.login.Option;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    LoginModel loginModel = new LoginModel();
    @FXML
    private Label dbStatus;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private ComboBox<Option> combobox;
    @FXML
    private Button login_btn;
    @FXML
    private Label loginStatus;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (this.loginModel.isDatabaseConnected()){
            this.dbStatus.setText("Connected");
            this.dbStatus.setTextFill(Color.GREEN);
        }else {
            this.dbStatus.setText("Not connected");
            this.dbStatus.setTextFill(Color.RED);
        }
        this.combobox.setItems(FXCollections.observableArrayList(Option.values()));
    }
    @FXML
    public void login(ActionEvent event){
        try {
            if (this.loginModel.isLogin(this.username.getText(),
            this.password.getText(), ((Option)
            this.combobox.getValue()).toString().toLowerCase())){
                Stage stage = (Stage) this.login_btn.getScene().getWindow();
                stage.close();
                switch (((Option) this.combobox.getValue()).toString()) {
                    case "Admin" -> adminLogin();
                    case "Student" -> studentLogin();
                }
            }else {
                this.loginStatus.setText("Wrong creditials");
                this.loginStatus.setTextFill(Color.RED);
            }
        }catch (Exception localExeption){
            localExeption.printStackTrace();
        }
    }
    public void studentLogin(){
        try {
            Stage userStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane) loader.load(getClass().getResource("studentFXML.fxml").openStream());

            StudentsController studentsController = (StudentsController) loader.getController();

            Scene scene = new Scene(root);
            userStage.setScene(scene);
            userStage.setTitle("Students dashboard");
            userStage.setResizable(false);
            userStage.show();

        }catch (IOException ex){
            ex.printStackTrace();
        }
    }
    public void adminLogin() {
        try {
            Stage adminStage = new Stage();
            FXMLLoader adminLoader = new FXMLLoader();
            Pane root = (Pane) adminLoader.load(getClass().getResource("admin.fxml").openStream());

            AdminsController adminsController = (AdminsController) adminLoader.getController();

            Scene scene = new Scene(root);
            adminStage.setScene(scene);
            adminStage.setTitle("Admin dashboard");
            adminStage.setResizable(false);
            adminStage.show();

        }catch (IOException ex){
            ex.printStackTrace();
        }
    }
}