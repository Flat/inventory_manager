package com.kennethswenson;

import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AddPartController {
    @FXML
    private TextField tbId;
    @FXML
    private TextField tbName;
    @FXML
    private TextField tbInv;
    @FXML
    private TextField tbPrice;
    @FXML
    private TextField tbMax;
    @FXML
    private TextField tbMin;
    @FXML
    private TextField tbManufac;
    @FXML
    private RadioButton radioInHouse;
    @FXML
    private RadioButton radioOutsourced;
    @FXML
    private Label lblManufacLoc;

    private static Part part = null;
    private static int autoNum = 1;

    @FXML
    private void initialize(){
        tbId.setText(String.valueOf(autoNum));
    }

    @FXML
    public void handleInHouse(ActionEvent actionEvent) {
        if (radioInHouse.isSelected()){
            lblManufacLoc.setText("Machine ID");
        } else {
            lblManufacLoc.setText("Company Name");
        }
    }

    @FXML
    public void handleOutsourced(ActionEvent actionEvent) {
        if (radioOutsourced.isSelected()){
            lblManufacLoc.setText("Company Name");
        } else {
            lblManufacLoc.setText("Machine ID");
        }
    }

    public void btnCancel(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel? ");
        alert.showAndWait().filter(response -> response == ButtonType.OK).ifPresent(response ->{
            Stage stage = (Stage)tbId.getScene().getWindow();
            part = null;
            stage.close();});
    }

    public Part display(int maxPartId){
        autoNum = maxPartId + 1;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("gui/add_part.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Add Part");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to open new Add window.");
            alert.showAndWait();
        }
        return part;
    }

    public void btnAdd(ActionEvent actionEvent){
        if(!validate()){
            return;
        }
        if (radioInHouse.isSelected()) {
            part = new Inhouse(tbName.getText(), Integer.valueOf(tbId.getText()), Double.valueOf(tbPrice.getText()), Integer.valueOf(tbInv.getText()),Integer.valueOf(tbMin.getText()), Integer.valueOf(tbMax.getText()), Integer.valueOf(tbManufac.getText()));
        } else {
            part = new Outsourced(tbName.getText(), Integer.valueOf(tbId.getText()), Double.valueOf(tbPrice.getText()), Integer.valueOf(tbInv.getText()),Integer.valueOf(tbMin.getText()), Integer.valueOf(tbMax.getText()), tbManufac.getText());
        }
        Stage stage = (Stage)tbId.getScene().getWindow();
        stage.close();
    }

    private boolean validate(){
        boolean valid = true;
        StringBuilder sb = new StringBuilder();
        sb.append("The following fields are invalid: ");
        PseudoClass invalid = PseudoClass.getPseudoClass("invalid");
        if(tbName.getText().isEmpty()){
            tbName.pseudoClassStateChanged(invalid, true);
            sb.append("Name, ");
            valid = false;
        } else {
            tbName.pseudoClassStateChanged(invalid, false);
        }
        if(tbInv.getText().isEmpty()){
            tbInv.pseudoClassStateChanged(invalid, true);
            sb.append("Inv, ");
            valid = false;
        } else {
            try{
                Integer.valueOf(tbInv.getText());
                tbInv.pseudoClassStateChanged(invalid, false);
            } catch (NumberFormatException e) {
                tbInv.pseudoClassStateChanged(invalid, true);
                sb.append("Inv, ");
                valid = false;
            }
        }
        if(tbPrice.getText().isEmpty()){
            tbPrice.pseudoClassStateChanged(invalid, true);
            valid = false;
            sb.append("Price, ");
        } else {
            try{
                Double.valueOf(tbPrice.getText());
                tbPrice.pseudoClassStateChanged(invalid, false);
            } catch (NumberFormatException e) {
                tbPrice.pseudoClassStateChanged(invalid, true);
                valid = false;
                sb.append("Price, ");
            }
        }
        if(tbMax.getText().isEmpty()){
            tbMax.pseudoClassStateChanged(invalid, true);
            valid = false;
            sb.append("Max, ");
        } else {
            try{
                Integer.valueOf(tbMax.getText());
                tbMax.pseudoClassStateChanged(invalid, false);
            } catch (NumberFormatException e) {
                tbMax.pseudoClassStateChanged(invalid, true);
                valid = false;
                sb.append("Max, ");
            }
        }
        if(tbMin.getText().isEmpty()){
            tbMin.pseudoClassStateChanged(invalid, true);
            valid = false;
            sb.append("Min, ");
        } else {
            try{
                Integer.valueOf(tbMin.getText());
                tbMin.pseudoClassStateChanged(invalid, false);
            } catch (NumberFormatException e) {
                tbMin.pseudoClassStateChanged(invalid, true);
                sb.append("Min, ");
                valid = false;
            }
        }
        if(radioInHouse.isSelected()) {
            if(tbManufac.getText().isEmpty()){
                tbManufac.pseudoClassStateChanged(invalid, true);
                valid = false;
                sb.append("Machine ID, ");
            } else {
                try{
                    Integer.valueOf(tbManufac.getText());
                    tbManufac.pseudoClassStateChanged(invalid, false);
                } catch (NumberFormatException e) {
                    tbManufac.pseudoClassStateChanged(invalid, true);
                    valid = false;
                    sb.append("Machine ID, ");
                }
            }
        } else {
            if(tbManufac.getText().isEmpty()){
                tbManufac.pseudoClassStateChanged(invalid, true);
                valid = false;
                sb.append("Company Name, ");
            } else {
                tbManufac.pseudoClassStateChanged(invalid, false);
            }
        }
        if(sb.toString().contains(", ")) {
            sb.replace(sb.lastIndexOf(","), sb.lastIndexOf(",") + 1, ".");
        }
        if(sb.toString().contains(", ")){
            sb.replace(sb.lastIndexOf(", "), sb.lastIndexOf(", ") + 1, " and");
        }
        try{
            if(Integer.valueOf(tbMin.getText()) > Integer.valueOf(tbMax.getText())) {
                sb.append("\nMax must be greater than Min.");
                tbMin.pseudoClassStateChanged(invalid, true);
                tbMax.pseudoClassStateChanged(invalid, true);
                valid = false;
            }
            if((Integer.valueOf(tbMin.getText()) > Integer.valueOf(tbInv.getText())) || (Integer.valueOf(tbInv.getText()) > Integer.valueOf(tbMax.getText()))){
                sb.append("\nInv must be between Max and Min");
                tbInv.pseudoClassStateChanged(invalid, true);
                valid = false;
            }
        } catch (Exception ignored){}
        if (!valid) {
            Alert alert = new Alert(Alert.AlertType.ERROR, sb.toString());
            alert.getDialogPane().setPrefSize(480, 320);
            alert.showAndWait();
        }
        return valid;
    }
}
