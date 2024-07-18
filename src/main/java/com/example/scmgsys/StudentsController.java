package com.example.scmgsys;

import com.example.scmgsys.dbUtil.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class StudentsController implements Initializable {
    @FXML
    private TextField lnameField;
    @FXML
    private TextField groupField;
    @FXML
    private Label statusLabel;
    @FXML
    private Label date;

    @FXML
    private TableView<StudentsDataUser> studenttable;
    @FXML
    private TableColumn<StudentsDataUser, String> fnamecolumn;
    @FXML
    private TableColumn<StudentsDataUser, String> lnamecolumn;
    @FXML
    private TableColumn<StudentsDataUser, String> groupcolumn;
    @FXML
    private TableColumn<StudentsDataUser, Integer> monthhourscolumn;
    @FXML
    private TableColumn<StudentsDataUser, Integer> totalhourscolumn;

    @FXML
    private Tab tabGroup;
    @FXML
    private Rectangle settingsRectangle;

    Date currentDate = new Date();
    // Форматирование даты в строку с помощью SimpleDateFormat
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    private ObservableList<StudentsDataUser> std_data;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        date.setText(dateFormat.format(currentDate));
        refreshTable();


        tabGroup.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event t) {
                refreshTable();
            }
        });
    }
    private void refreshTable(){
        try{
            String sql = "SELECT fname, lname, section, monthhours, hours FROM students;";
            Connection conn = DbConnection.getConnection();
            this.std_data = FXCollections.observableArrayList();

            ResultSet rs = conn.createStatement().executeQuery(sql);

            while (rs.next()){
                this.std_data.add(new StudentsDataUser(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5)));
            }
            conn.close();
            statusLabel.setTextFill(Color.GREEN);
            statusLabel.setText("Data is loaded");
        }catch (SQLException exception){
            statusLabel.setTextFill(Color.RED);
            statusLabel.setText("Data is not loaded");
            System.err.println("!!!!!Error " + exception);
        }

        this.fnamecolumn.setCellValueFactory(new PropertyValueFactory<StudentsDataUser, String>("FIRSTNAME"));
        this.lnamecolumn.setCellValueFactory(new PropertyValueFactory<StudentsDataUser, String>("LASTNAME"));
        this.groupcolumn.setCellValueFactory(new PropertyValueFactory<StudentsDataUser, String>("SECTION"));
        this.monthhourscolumn.setCellValueFactory(new PropertyValueFactory<StudentsDataUser, Integer>("MONTHHOURS"));
        this.totalhourscolumn.setCellValueFactory(new PropertyValueFactory<StudentsDataUser, Integer>("HOURS"));

        this.studenttable.setItems(null);
        this.studenttable.setItems(this.std_data);
    }
    @FXML
    private void refreshTableVIew(ActionEvent event){
        refreshTable();
    }
    @FXML
    private void searchByLastName(ActionEvent event){
        try{
            String sql = "SELECT fname, lname, section, monthhours, hours FROM students WHERE lname = ?;";
            Connection conn = DbConnection.getConnection();
            this.std_data = FXCollections.observableArrayList();

            PreparedStatement stmt = conn.prepareStatement(sql);

            // Set values for the prepared statement
            stmt.setString(1, lnameField.getText());

            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                this.std_data.add(new StudentsDataUser(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5)));
            }
            conn.close();
            statusLabel.setTextFill(Color.GREEN);
            statusLabel.setText("Data is loaded");
        }catch (SQLException exception){
            statusLabel.setTextFill(Color.RED);
            statusLabel.setText("Data is not loaded");
            System.err.println("!!!!!Error " + exception);
        }

        this.fnamecolumn.setCellValueFactory(new PropertyValueFactory<StudentsDataUser, String>("FIRSTNAME"));
        this.lnamecolumn.setCellValueFactory(new PropertyValueFactory<StudentsDataUser, String>("LASTNAME"));
        this.groupcolumn.setCellValueFactory(new PropertyValueFactory<StudentsDataUser, String>("SECTION"));
        this.monthhourscolumn.setCellValueFactory(new PropertyValueFactory<StudentsDataUser, Integer>("MONTHHOURS"));
        this.totalhourscolumn.setCellValueFactory(new PropertyValueFactory<StudentsDataUser, Integer>("HOURS"));

        this.studenttable.setItems(null);
        this.studenttable.setItems(this.std_data);
    }
    @FXML
    private void searchByGroup(ActionEvent event){
        try{
            String sql = "SELECT fname, lname, section, monthhours, hours FROM students WHERE section = ?;";
            Connection conn = DbConnection.getConnection();
            this.std_data = FXCollections.observableArrayList();

            PreparedStatement stmt = conn.prepareStatement(sql);

            // Set values for the prepared statement
            stmt.setString(1, groupField.getText());

            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                this.std_data.add(new StudentsDataUser(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5)));
            }
            conn.close();
            statusLabel.setTextFill(Color.GREEN);
            statusLabel.setText("Data is loaded");
        }catch (SQLException exception){
            statusLabel.setTextFill(Color.RED);
            statusLabel.setText("Data is not loaded");
            System.err.println("!!!!!Error " + exception);
        }

        this.fnamecolumn.setCellValueFactory(new PropertyValueFactory<StudentsDataUser, String>("FIRSTNAME"));
        this.lnamecolumn.setCellValueFactory(new PropertyValueFactory<StudentsDataUser, String>("LASTNAME"));
        this.groupcolumn.setCellValueFactory(new PropertyValueFactory<StudentsDataUser, String>("SECTION"));
        this.monthhourscolumn.setCellValueFactory(new PropertyValueFactory<StudentsDataUser, Integer>("MONTHHOURS"));
        this.totalhourscolumn.setCellValueFactory(new PropertyValueFactory<StudentsDataUser, Integer>("HOURS"));

        this.studenttable.setItems(null);
        this.studenttable.setItems(this.std_data);
    }
    @FXML
    private void clearFields(ActionEvent event){
        this.lnameField.setText("");
        this.groupField.setText("");
    }
    @FXML
    private void logOut(ActionEvent event){
        try {
            Stage loginStage = new Stage();
            FXMLLoader loginLoader = new FXMLLoader();
            Pane root = (Pane) loginLoader.load(getClass().getResource("hello-view.fxml").openStream());
            Scene scene = new Scene(root);
            loginStage.setScene(scene);
            loginStage.setTitle("School Management System");
            loginStage.setResizable(false);
            loginStage.show();
            Stage thisStage = (Stage) this.settingsRectangle.getScene().getWindow();
            thisStage.close();
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }
    @FXML
    private void aboutbtn(ActionEvent event){
        try {
            Stage aboutStage = new Stage();
            FXMLLoader aboutLoader = new FXMLLoader();
            Pane root = (Pane) aboutLoader.load(getClass().getResource("about.fxml").openStream());

            AboutController aboutController = aboutLoader.getController();

            Scene scene = new Scene(root);
            aboutStage.setScene(scene);
            aboutStage.setTitle("Help / About");
            aboutStage.setResizable(false);
            aboutStage.show();


        }catch (IOException ex){
            ex.printStackTrace();
        }
    }
}
