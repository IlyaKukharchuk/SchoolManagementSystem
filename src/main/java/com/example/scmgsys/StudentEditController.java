package com.example.scmgsys;

import com.example.scmgsys.dbUtil.DbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static com.example.scmgsys.AdminsController.isValidEmail;

public class StudentEditController implements Initializable {

    @FXML
    private Label idLabel;
    @FXML
    private TextField fnameField;
    @FXML
    private TextField lnameField;
    @FXML
    private TextField sectionField;
    @FXML
    private DatePicker dobField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField monthField;
    @FXML
    private TextField totalField;
    @FXML
    private ImageView cardImg;
    @FXML
    private Label statusLabel;


    private StudentsData studentData;
    private DbConnection dc;
    private String fileUrl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.dc = new DbConnection();
    }

    public void setStudentData(StudentsData studentData) {
        this.studentData = studentData;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate dateOfBirth = LocalDate.parse(studentData.getDOB(), formatter);

        idLabel.setText(studentData.getID());
        fnameField.setText(studentData.getFIRSTNAME());
        lnameField.setText(studentData.getLASTNAME());
        sectionField.setText(studentData.getSECTION());
        dobField.setValue(dateOfBirth);
        emailField.setText(studentData.getEMAIL());
        monthField.setText(String.valueOf(studentData.getMONTHHOURS()));
        totalField.setText(String.valueOf(studentData.getHOURS()));

        InputStream stream = null;
        try {
            if (studentData.getIMAGE()==null){
                stream = new FileInputStream("src\\main\\resources\\com\\example\\scmgsys\\student_default.png");
            }else {
                stream = new FileInputStream(studentData.getIMAGE());
                this.fileUrl = studentData.getIMAGE();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Image image = new Image(stream);
        cardImg.setImage(image);
    }
    public void choosingPhoto(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите фото");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Изображения", "*.jpg", "*.png", "*.jpeg")
        );

        // Открытие диалогового окна выбора файла
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                // Получение абсолютного пути к корневой папке проекта
                String projectPath = System.getProperty("user.dir");

                // Получение относительного пути файла относительно корневой папки проекта
                String selectedFilePath = selectedFile.getAbsolutePath();
                String relativePath = selectedFilePath.replace(projectPath, "");

                // Желаемый путь к файлу внутри корневой папки проекта
                String desiredPath = projectPath + "src\\main\\resources\\com\\example\\scmgsys\\student_default.png";

                // Загрузка выбранного файла в ImageView
                Image image = new Image(selectedFile.toURI().toString());
                cardImg.setImage(image);

                relativePath = relativePath.substring(1); // Удаление первого символа - слэша
                // Сохранение пути выбранного файла в переменную
                this.fileUrl = relativePath;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void submitChanges(ActionEvent event) {
        try {
            // Connect to the database
            Connection conn = DbConnection.getConnection();
            String sqlInsert = "UPDATE students SET fname = ?, lname = ?, section = ?, DOB = ?, email = ?, monthhours = ?, hours = ?, img = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sqlInsert);


            // Set values for the prepared statement
            stmt.setString(1, fnameField.getText());
            stmt.setString(2, lnameField.getText());
            stmt.setString(3, sectionField.getText());
            stmt.setString(4, dobField.getEditor().getText());
            stmt.setString(5, emailField.getText());
            stmt.setString(6, monthField.getText());
            stmt.setString(7, totalField.getText());
            stmt.setString(8, fileUrl);
            stmt.setString(9, idLabel.getText());


            // Check if email is valid and fields are not null
            if (!isValidEmail(emailField.getText()) || fnameField.getText() == null || lnameField.getText() == null || emailField.getText() == null || dobField.getValue() == null || sectionField.getText() == null) {
                statusLabel.setTextFill(Color.RED);
                statusLabel.setText("Fields must not be null and email must be valid");
            } else {
                // Execute the query and close the connection
                stmt.execute();
                statusLabel.setTextFill(Color.GREEN);
                statusLabel.setText("Data successfully replaced");

                Stage stage = (Stage) this.idLabel.getScene().getWindow();
                stage.close();
                conn.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
