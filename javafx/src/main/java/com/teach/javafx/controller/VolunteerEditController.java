package com.teach.javafx.controller;
import com.teach.javafx.request.OptionItem;
import com.teach.javafx.util.CommonMethod;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class VolunteerEditController {
    @FXML
    private ComboBox<OptionItem> studentComboBox;
    private List<OptionItem> studentList;

    @FXML
    private TextField nameField;
    @FXML
    private DatePicker datePick;
    @FXML
    private TextField authorityField;
    @FXML
    private TextField timeField;

    private VolunteerTableController volunteerTableController= null;
    private Integer volunteerId= null;
    @FXML
    public void initialize() {
    }

    @FXML
    public void okButtonClick(){
        Map<String,Object> data = new HashMap<>();
        OptionItem op;
        op = studentComboBox.getSelectionModel().getSelectedItem();
        if(op != null) {
            data.put("personId",Integer.parseInt(op.getValue()));
        }
        data.put("volunteerId",volunteerId);
        data.put("name",nameField.getText());
        data.put("date",datePick.getEditor().getText());
        data.put("authority",authorityField.getText());
        data.put("time",timeField.getText());
        volunteerTableController.doClose("ok",data);
    }
    @FXML
    public void cancelButtonClick(){
        volunteerTableController.doClose("cancel",null);
    }

    public void setVolunteerTableController(VolunteerTableController volunteerTableController) {
        this.volunteerTableController = volunteerTableController;
    }
    public void init(){
        studentList =volunteerTableController.getStudentList();
        studentComboBox.getItems().addAll(studentList );
    }
    public void showDialog(Map data){
        if(data == null) {
            volunteerId = null;
            studentComboBox.getSelectionModel().select(-1);
            studentComboBox.setDisable(false);
            nameField.setText("");
            datePick.getEditor().setText("");
            authorityField.setText("");
            timeField.setText("");
        }else {
            volunteerId = CommonMethod.getInteger(data,"volunteerId");
            studentComboBox.getSelectionModel().select(CommonMethod.getOptionItemIndexByValue(studentList, CommonMethod.getString(data, "personId")));
            studentComboBox.setDisable(true);
            nameField.setText(CommonMethod.getString(data, "name"));
            datePick.getEditor().setText(CommonMethod.getString(data, "date"));
            authorityField.setText(CommonMethod.getString(data, "authority"));
            timeField.setText(CommonMethod.getString(data, "time"));
        }
    }
}

