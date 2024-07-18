package com.example.scmgsys;

import com.example.scmgsys.dbUtil.DbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StudentCardController implements Initializable {

    private StudentsData rowData;
    private DbConnection dc;

    @FXML
    private Label cardID;
    @FXML
    private Label cardFirstName;
    @FXML
    private Label cardLastName;
    @FXML
    private Label cardSection;
    @FXML
    private Label cardDOB;
    @FXML
    private Label cardEmail;
    @FXML
    private Label cardMonthHours;
    @FXML
    private Label cardTotalHours;
    @FXML
    private ImageView cardImg;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.dc = new DbConnection();
    }

    public void setRowData(StudentsData rowData) {
        this.rowData = rowData;

        // Here rowData is using to fill in the corresponding user interface elements on the student page

        InputStream stream = null;
        try {
            if (rowData.getIMAGE()==null){
                stream = new FileInputStream("src\\main\\resources\\com\\example\\scmgsys\\student_default.png");
            }else {
                stream = new FileInputStream(rowData.getIMAGE());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Image image = new Image(stream);
        cardImg.setImage(image);


        cardID.setText(rowData.getID());
        cardFirstName.setText(rowData.getFIRSTNAME());
        cardLastName.setText(rowData.getLASTNAME());
        cardEmail.setText(rowData.getEMAIL());
        cardDOB.setText(rowData.getDOB());
        cardSection.setText(rowData.getSECTION());
        cardMonthHours.setText(String.valueOf(rowData.getMONTHHOURS()));
        cardTotalHours.setText(String.valueOf(rowData.getHOURS()));

    }

    public void openEditPage(ActionEvent event) {
        try {
            Stage studentEditStage = new Stage();
            FXMLLoader studentEditLoader = new FXMLLoader();
            Pane root = (Pane) studentEditLoader.load(getClass().getResource("studentEdit.fxml").openStream());

            StudentEditController studentEditController = studentEditLoader.getController();
            studentEditController.setStudentData(rowData);

            Scene scene = new Scene(root);
            studentEditStage.setScene(scene);
            studentEditStage.setTitle("Student editor");
            studentEditStage.setResizable(false);
            studentEditStage.show();

            Stage stage = (Stage) this.cardFirstName.getScene().getWindow();
            stage.close();

        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public void deleteStudent(ActionEvent event) {
        try {
            // Connect to the database
            Connection conn = DbConnection.getConnection();
            String sqlInsert = "DELETE FROM students WHERE id = ?;";
            PreparedStatement stmt = conn.prepareStatement(sqlInsert);

            // Set values for the prepared statement
            stmt.setString(1, cardID.getText());

            // Execute the query and close the connection
            stmt.execute();

            Stage stage = (Stage) this.cardTotalHours.getScene().getWindow();
            stage.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}