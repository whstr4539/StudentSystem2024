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
public class SocialEditController {
    @FXML
    private ComboBox<OptionItem> studentComboBox;
    private List<OptionItem> studentList;

    @FXML
    private TextField nameField;
    @FXML
    private DatePicker datePick;
    @FXML
    private TextField authorityField;
    private SocialTableController socialTableController= null;
    private Integer socialId= null;
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
        data.put("socialId",socialId);
        data.put("name",nameField.getText());
        data.put("date",datePick.getEditor().getText());
        data.put("authority",authorityField.getText());
        socialTableController.doClose("ok",data);
    }
    @FXML
    public void cancelButtonClick(){
        socialTableController.doClose("cancel",null);
    }

    public void setSocialTableController(SocialTableController socialTableController) {
        this.socialTableController = socialTableController;
    }
    public void init(){
        studentList =socialTableController.getStudentList();
        studentComboBox.getItems().addAll(studentList );
    }
    public void showDialog(Map data){
        if(data == null) {
            socialId = null;
            studentComboBox.getSelectionModel().select(-1);
            studentComboBox.setDisable(false);
            nameField.setText("");
            datePick.getEditor().setText("");
            authorityField.setText("");
        }else {
            socialId = CommonMethod.getInteger(data,"socialId");
            studentComboBox.getSelectionModel().select(CommonMethod.getOptionItemIndexByValue(studentList, CommonMethod.getString(data, "personId")));
            studentComboBox.setDisable(true);
            nameField.setText(CommonMethod.getString(data, "name"));
            datePick.getEditor().setText(CommonMethod.getString(data, "date"));
            authorityField.setText(CommonMethod.getString(data, "authority"));
        }
    }
}
