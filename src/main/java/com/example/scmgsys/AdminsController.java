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
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AdminsController implements Initializable {
    // Variables
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    // Form fields
    @FXML
    private TextField firstname;
    @FXML
    private TextField lastname;
    @FXML
    private TextField email;
    @FXML
    private DatePicker dob;
    @FXML
    private TextField section;
    @FXML
    private Label statusLabel;
    @FXML
    private TextField lnameSearchField;

    // DB table
    @FXML
    private TableView<StudentsData> studenttable;
    @FXML
    private TableColumn<StudentsData, String> idcolumn;
    @FXML
    private TableColumn<StudentsData, String> firstnamecolumn;
    @FXML
    private TableColumn<StudentsData, String> lastnamecolumn;
    @FXML
    private TableColumn<StudentsData, String> emailcolumn;
    @FXML
    private TableColumn<StudentsData, String> dobcolumn;
    @FXML
    private TableColumn<StudentsData, String> sectioncolumn;
    @FXML
    private TableColumn<StudentsData, Integer> hourscolumn;
    @FXML
    private TableColumn<StudentsData, Integer> monthhourscolumn;
    @FXML
    private TableColumn<StudentsData, String> imgcolumn;

    // group
    @FXML
    private Label date2;
    @FXML
    private Label statusLabel2;
    @FXML
    private TextField sectionField2;

    @FXML
    private Tab tabGroup;
    @FXML
    private TableView<StudentsDataGroup> studenttable2;
    @FXML
    private TableColumn<StudentsDataGroup, String> idcolumn2;
    @FXML
    private TableColumn<StudentsDataGroup, String> firstnamecolumn2;
    @FXML
    private TableColumn<StudentsDataGroup, String> lastnamecolumn2;
    @FXML
    private TableColumn<StudentsDataGroup, String> sectioncolumn2;
    @FXML
    private TableColumn<StudentsDataGroup, Integer> hourscolumn2;
    @FXML
    private TableColumn<StudentsDataGroup, Integer> monthhourscolumn2;

    @FXML
    private Rectangle settingsRectangle;
    // date
    @FXML
    private Label date;

    private ObservableList<StudentsData> std_data;
    private ObservableList<StudentsDataGroup> std_group_data;
    ObservableList<TableColumn<StudentsDataGroup, ?>> columns;
    Date currentDate = new Date();
    // Форматирование даты в строку с помощью SimpleDateFormat
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    private Map<String, Integer> spinnerValuesMap = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        date.setText(dateFormat.format(currentDate));
        refreshTable();


        // double-click on the row of table -> open student page
        studenttable.setRowFactory( tv -> {
            TableRow<StudentsData> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    StudentsData rowData = row.getItem();

                    studentCardPage(rowData);
                }
            });
            return row;
        });

        tabGroup.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event t) {
                openGroupTab();
            }
        });
    }

    @FXML
    private void loadStudentData(ActionEvent event){
        refreshTable();
    }

    private void refreshTable(){
        try{
            String sql = "SELECT * FROM students;";
            Connection conn = DbConnection.getConnection();
            this.std_data = FXCollections.observableArrayList();

            ResultSet rs = conn.createStatement().executeQuery(sql);

            while (rs.next()){
                this.std_data.add(new StudentsData( rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getInt(8), rs.getString(9)));
            }
            conn.close();
            statusLabel.setTextFill(Color.GREEN);
            statusLabel.setText("Data is loaded");
        }catch (SQLException exception){
            statusLabel.setTextFill(Color.RED);
            statusLabel.setText("Data is not loaded");
            System.err.println("!!!!!Error " + exception);
        }

        this.idcolumn.setCellValueFactory(new PropertyValueFactory<StudentsData, String>("ID"));
        this.firstnamecolumn.setCellValueFactory(new PropertyValueFactory<StudentsData, String>("FIRSTNAME"));
        this.lastnamecolumn.setCellValueFactory(new PropertyValueFactory<StudentsData, String>("LASTNAME"));
        this.emailcolumn.setCellValueFactory(new PropertyValueFactory<StudentsData, String>("EMAIL"));
        this.dobcolumn.setCellValueFactory(new PropertyValueFactory<StudentsData, String>("DOB"));
        this.sectioncolumn.setCellValueFactory(new PropertyValueFactory<StudentsData, String>("SECTION"));
        this.hourscolumn.setCellValueFactory(new PropertyValueFactory<StudentsData, Integer>("HOURS"));
        this.monthhourscolumn.setCellValueFactory(new PropertyValueFactory<StudentsData, Integer>("MONTHHOURS"));

        this.imgcolumn.setCellValueFactory(new PropertyValueFactory<StudentsData, String>("IMAGE"));

        this.studenttable.setItems(null);
        this.studenttable.setItems(this.std_data);
    }
    @FXML
    private void searchByLastName(ActionEvent event){
        try{
            String sql = "SELECT * FROM students WHERE lname = ?;";
            Connection conn = DbConnection.getConnection();
            this.std_data = FXCollections.observableArrayList();

            PreparedStatement stmt = conn.prepareStatement(sql);

            // Set values for the prepared statement
            stmt.setString(1, lnameSearchField.getText());

            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                this.std_data.add(new StudentsData( rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getInt(8), rs.getString(9)));
            }
            conn.close();
            statusLabel.setTextFill(Color.GREEN);
            statusLabel.setText("Data is loaded");
        }catch (SQLException exception){
            statusLabel.setTextFill(Color.RED);
            statusLabel.setText("Data is not loaded");
            System.err.println("!!!!!Error " + exception);
        }

        this.idcolumn.setCellValueFactory(new PropertyValueFactory<StudentsData, String>("ID"));
        this.firstnamecolumn.setCellValueFactory(new PropertyValueFactory<StudentsData, String>("FIRSTNAME"));
        this.lastnamecolumn.setCellValueFactory(new PropertyValueFactory<StudentsData, String>("LASTNAME"));
        this.emailcolumn.setCellValueFactory(new PropertyValueFactory<StudentsData, String>("EMAIL"));
        this.dobcolumn.setCellValueFactory(new PropertyValueFactory<StudentsData, String>("DOB"));
        this.sectioncolumn.setCellValueFactory(new PropertyValueFactory<StudentsData, String>("SECTION"));
        this.hourscolumn.setCellValueFactory(new PropertyValueFactory<StudentsData, Integer>("HOURS"));
        this.monthhourscolumn.setCellValueFactory(new PropertyValueFactory<StudentsData, Integer>("MONTHHOURS"));

        this.imgcolumn.setCellValueFactory(new PropertyValueFactory<StudentsData, String>("IMAGE"));

        this.studenttable.setItems(null);
        this.studenttable.setItems(this.std_data);
    }



    @FXML
    private void addStudent(ActionEvent event){
        String sqlInsert = "INSERT INTO students(id, fname, lname, email, DOB, section) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            // connect to DB
            Connection conn = DbConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sqlInsert);

            // generating 8-digit id
            UUID uniqueId = UUID.randomUUID();
            String uniqueNumber = uniqueId.toString().substring(0, 8);

            // creating SQL querry from our data
            stmt.setString(1,uniqueNumber);
            stmt.setString(2,this.firstname.getText());
            stmt.setString(3,this.lastname.getText());
            stmt.setString(4,this.email.getText());
            stmt.setString(5,this.dob.getEditor().getText());
            stmt.setString(6,this.section.getText());

            // check if email is valid and fields is not null
            if(!isValidEmail(this.email.getText()) || this.firstname.getText() == null || this.lastname.getText() == null || this.email.getText() == null || this.dob.getValue() == null || this.section.getText() == null){
                statusLabel.setTextFill(Color.RED);
                statusLabel.setText("Fields must be not null and email must be valid");
            }else{
                // execute querry and close connection
                stmt.execute();
                statusLabel.setTextFill(Color.GREEN);
                statusLabel.setText("Student is added");

                // refreshing table
                refreshTable();

                conn.close();
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    @FXML
    private void clearField(ActionEvent event){
        this.firstname.setText("");
        this.lastname.setText("");
        this.email.setText("");
        this.dob.setValue(null);
        this.section.setText("");
    }
    // student page
    public void studentCardPage(StudentsData rowData) {
        try {
            Stage studentCardStage = new Stage();
            FXMLLoader studentCardLoader = new FXMLLoader();
            Pane root = (Pane) studentCardLoader.load(getClass().getResource("studentCard.fxml").openStream());

            StudentCardController studentCardController = studentCardLoader.getController();
            studentCardController.setRowData(rowData);

            Scene scene = new Scene(root);
            studentCardStage.setScene(scene);
            studentCardStage.setTitle("Student card");
            studentCardStage.setResizable(false);
            studentCardStage.show();

        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    // GROUP TAB
    @FXML
    private void openGroupTab(){
        date2.setText(dateFormat.format(currentDate));

        try{
            String sql = "SELECT id, fname, lname, section, monthhours, hours FROM students;";
            Connection conn = DbConnection.getConnection();
            this.std_group_data = FXCollections.observableArrayList();

            ResultSet rs = conn.createStatement().executeQuery(sql);

            while (rs.next()){
                this.std_group_data.add(new StudentsDataGroup( rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(6) ));
            }
            conn.close();
            statusLabel2.setTextFill(Color.GREEN);
            statusLabel2.setText("Data is loaded");
        }catch (SQLException exception){
            statusLabel2.setTextFill(Color.RED);
            statusLabel2.setText("Data is not loaded");
            System.err.println("!!!!!Error " + exception);
        }

        this.idcolumn2.setCellValueFactory(new PropertyValueFactory<StudentsDataGroup, String>("ID"));
        this.firstnamecolumn2.setCellValueFactory(new PropertyValueFactory<StudentsDataGroup, String>("FIRSTNAME"));
        this.lastnamecolumn2.setCellValueFactory(new PropertyValueFactory<StudentsDataGroup, String>("LASTNAME"));
        this.sectioncolumn2.setCellValueFactory(new PropertyValueFactory<StudentsDataGroup, String>("SECTION"));
        this.monthhourscolumn2.setCellValueFactory(new PropertyValueFactory<StudentsDataGroup, Integer>("MONTHHOURS"));
        this.hourscolumn2.setCellValueFactory(new PropertyValueFactory<StudentsDataGroup, Integer>("HOURS"));

        this.studenttable2.setItems(null);
        this.studenttable2.setItems(this.std_group_data);
        addSpinnerToTable();
    }
    private void refreshGroupTableView(){
        try{
            String sql = "SELECT id, fname, lname, section, monthhours, hours FROM students;";
            Connection conn = DbConnection.getConnection();
            this.std_group_data = FXCollections.observableArrayList();

            ResultSet rs = conn.createStatement().executeQuery(sql);

            while (rs.next()){
                this.std_group_data.add(new StudentsDataGroup( rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(6) ));
            }
            conn.close();
            statusLabel2.setTextFill(Color.GREEN);
            statusLabel2.setText("Data is loaded");
        }catch (SQLException exception){
            statusLabel2.setTextFill(Color.RED);
            statusLabel2.setText("Data is not loaded");
            System.err.println("!!!!!Error " + exception);
        }

        this.idcolumn2.setCellValueFactory(new PropertyValueFactory<StudentsDataGroup, String>("ID"));
        this.firstnamecolumn2.setCellValueFactory(new PropertyValueFactory<StudentsDataGroup, String>("FIRSTNAME"));
        this.lastnamecolumn2.setCellValueFactory(new PropertyValueFactory<StudentsDataGroup, String>("LASTNAME"));
        this.sectioncolumn2.setCellValueFactory(new PropertyValueFactory<StudentsDataGroup, String>("SECTION"));
        this.monthhourscolumn2.setCellValueFactory(new PropertyValueFactory<StudentsDataGroup, Integer>("MONTHHOURS"));
        this.hourscolumn2.setCellValueFactory(new PropertyValueFactory<StudentsDataGroup, Integer>("HOURS"));

        this.studenttable2.setItems(null);
        this.studenttable2.setItems(this.std_group_data);

        // cleaning of spinners
        columns = studenttable2.getColumns();
        if (!columns.isEmpty()) {
            int lastIndex = columns.size() - 1;
            TableColumn<StudentsDataGroup, ?> lastColumn = columns.get(lastIndex);
            columns.remove(lastIndex);
        }
        addSpinnerToTable();
    }
    @FXML
    private void seacrhGroup(ActionEvent event){
        try{
            String sql = "SELECT id, fname, lname, section, monthhours, hours FROM students WHERE section = ?;";
            Connection conn = DbConnection.getConnection();
            this.std_group_data = FXCollections.observableArrayList();

            PreparedStatement stmt = conn.prepareStatement(sql);

            // Set values for the prepared statement
            stmt.setString(1, sectionField2.getText());

            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                this.std_group_data.add(new StudentsDataGroup( rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(6) ));
            }
            conn.close();
            statusLabel2.setTextFill(Color.GREEN);
            statusLabel2.setText("Data is loaded");
        }catch (SQLException exception){
            statusLabel2.setTextFill(Color.RED);
            statusLabel2.setText("Data is not loaded");
            System.err.println("!!!!!Error " + exception);
        }

        this.idcolumn2.setCellValueFactory(new PropertyValueFactory<StudentsDataGroup, String>("ID"));
        this.firstnamecolumn2.setCellValueFactory(new PropertyValueFactory<StudentsDataGroup, String>("FIRSTNAME"));
        this.lastnamecolumn2.setCellValueFactory(new PropertyValueFactory<StudentsDataGroup, String>("LASTNAME"));
        this.sectioncolumn2.setCellValueFactory(new PropertyValueFactory<StudentsDataGroup, String>("SECTION"));
        this.monthhourscolumn2.setCellValueFactory(new PropertyValueFactory<StudentsDataGroup, Integer>("MONTHHOURS"));
        this.hourscolumn2.setCellValueFactory(new PropertyValueFactory<StudentsDataGroup, Integer>("HOURS"));

        this.studenttable2.setItems(null);
        this.studenttable2.setItems(this.std_group_data);
    }
    @FXML
    private void showAllStudents(ActionEvent event){
        refreshGroupTableView();
    }
    @FXML
    public void submitHours(ActionEvent event) {
        // Обход всех строк в таблице
        for (StudentsDataGroup dataGroup : studenttable2.getItems()) {
            String studentId = dataGroup.getID();
            int monthHours = dataGroup.getMONTHHOURS();
            int hours = dataGroup.getHOURS();

            // Получение значения из addSpinnerToTable
            int spinnerValue = processSpinnerValues(studentId);

            // Обновление записи в базе данных
            updateStudentData(studentId, monthHours, hours, spinnerValue);
        }
        refreshGroupTableView();
        statusLabel2.setTextFill(Color.GREEN);
        statusLabel2.setText("Changes is submited");
    }
    public int processSpinnerValues(String specificKey) {
        // Retrieve a value from the map using a specific key (ID)
        Integer value = spinnerValuesMap.get(specificKey);

        if (value != null) {
            // The value exists in the map for the given key
            System.out.println("Value for key " + specificKey + ": " + value);
            return value;
        } else {
            // The value does not exist in the map for the given key
            System.out.println("No value found for key " + specificKey);
            // Handle the case when the key does not exist in the map
            return 0;
        }
    }

    public void updateStudentData(String studentId, int monthHours, int hours, int spinnerValue) {
        try {
            // Connect to the database
            Connection conn = DbConnection.getConnection();
            String sqlInsert = "UPDATE students SET monthhours = ?, hours = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sqlInsert);

            // Обновление значения monthHours
            monthHours += spinnerValue;
            hours += spinnerValue;

            // Set values for the prepared statement
            stmt.setString(1, String.valueOf(monthHours));
            stmt.setString(2, String.valueOf(hours));
            stmt.setString(3, studentId);

            // Execute the query and close the connection
            stmt.execute();
            statusLabel.setTextFill(Color.GREEN);
            statusLabel.setText("Data successfully updated");

            conn.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }





    private void addSpinnerToTable() {
        TableColumn<StudentsDataGroup, Void> addhoursColumn2 = new TableColumn<>("Add hours");
        addhoursColumn2.setPrefWidth(256);

        Callback<TableColumn<StudentsDataGroup, Void>, TableCell<StudentsDataGroup, Void>> cellFactory = param -> {
            final TableCell<StudentsDataGroup, Void> cell = new TableCell<StudentsDataGroup, Void>() {
                private final Spinner<Integer> spinner = new Spinner<>();
                private final SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory;

                {
                    valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(
                            Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 2);
                    spinner.setValueFactory(valueFactory);
                    setAlignment(Pos.CENTER);

                    spinner.valueProperty().addListener((obs, oldValue, newValue) -> {
                        StudentsDataGroup rowData = getTableRow().getItem();
                        String idRow = rowData.getID();

                        // Update the map with Spinner value
                        spinnerValuesMap.put(idRow, newValue);

                        System.out.println("New value: " + newValue);
                        System.out.println("id row: " + idRow);
                        // Perform actions specific to the row
                    });
                }

                @Override
                public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(spinner);
                    }
                }
            };
            return cell;
        };

        addhoursColumn2.setCellFactory(cellFactory);
        studenttable2.getColumns().add(addhoursColumn2);

        studenttable2.setRowFactory(tv -> {
            TableRow<StudentsDataGroup> row = new TableRow<>();
            return row;
        });
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
