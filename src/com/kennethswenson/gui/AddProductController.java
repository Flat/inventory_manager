package com.kennethswenson.gui;


import com.kennethswenson.Part;
import com.kennethswenson.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AddProductController {
    @FXML
    private TextField tbId;
    @FXML
    private TextField tbName;
    @FXML
    private TextField tbInv;
    @FXML
    private TextField tbPrice;
    @FXML
    private TextField tbMin;
    @FXML
    private TextField tbMax;
    @FXML
    private TextField tbSearch;
    @FXML
    private TableView<Part> tvUnaddedParts;
    @FXML
    private TableView<Part> tvAddedParts;
    @FXML
    private TableColumn<Part, String> colPartIdUnadded;
    @FXML
    private TableColumn<Part, String> colPartNameUnadded;
    @FXML
    private TableColumn<Part, String> colPartInvUnadded;
    @FXML
    private TableColumn<Part, String> colPartPriceUnadded;
    @FXML
    private TableColumn<Part, String> colPartIdAdded;
    @FXML
    private TableColumn<Part, String> colPartNameAdded;
    @FXML
    private TableColumn<Part, String> colPartInvAdded;
    @FXML
    private TableColumn<Part, String> colPartPriceAdded;


    private static Product product = null;
    private static int autoNum = 0;
    private static ObservableList<Part> parts = FXCollections.observableArrayList();
    private ObservableList<Part> addedParts = FXCollections.observableArrayList();


    public void btnSearch(ActionEvent actionEvent) {
        FilteredList<Part> filteredParts = new FilteredList<Part>(parts, p -> true);
        filteredParts.setPredicate(part -> tbSearch.getText().isEmpty() || part.getName().toUpperCase().contains(tbSearch.getText().toUpperCase()));
        SortedList<Part> sortedParts = new SortedList<Part>(filteredParts);
        sortedParts.comparatorProperty().bind(tvUnaddedParts.comparatorProperty());
        tvUnaddedParts.setItems(sortedParts);
    }

    public void btnAddPart(ActionEvent actionEvent) {
        Part toAdd = tvUnaddedParts.getSelectionModel().getSelectedItem();
        if(toAdd != null){
            addedParts.add(toAdd);
            parts.remove(toAdd);
        }
    }

    public void btnDelPart(ActionEvent actionEvent) {
        Part toDel = tvAddedParts.getSelectionModel().getSelectedItem();
        if(toDel != null){
            addedParts.remove(toDel);
            parts.add(toDel);
        }
    }

    public void btnCancel(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel? ");
        alert.showAndWait().filter(response -> response == ButtonType.OK).ifPresent(response ->{
            Stage stage = (Stage)tbId.getScene().getWindow();
            product = null;
            stage.close();});
    }

    public void btnSave(ActionEvent actionEvent) {
        if(!validate()){
            return;
        } else {
            product = new Product(Integer.valueOf(tbId.getText()), tbName.getText(), Double.valueOf(tbPrice.getText()), Integer.valueOf(tbInv.getText()), Integer.valueOf(tbMin.getText()), Integer.valueOf(tbMax.getText()));
        }
        Stage stage = (Stage)tbId.getScene().getWindow();
        stage.close();
    }

    public Product display(int maxProductId, ObservableList<Part> part){
        autoNum = maxProductId + 1;
        parts = part;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("add_product.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Add Product");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to open new Add window.");
            e.printStackTrace();
            alert.showAndWait();
        }
        return product;
    }

    @FXML
    private void initialize(){
        tbId.setText(String.valueOf(autoNum));
        tvUnaddedParts.setItems(parts);
        colPartIdUnadded.setCellValueFactory(new PropertyValueFactory<>("partID"));
        colPartNameUnadded.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPartInvUnadded.setCellValueFactory(new PropertyValueFactory<>("instock"));
        colPartPriceUnadded.setCellValueFactory(new PropertyValueFactory<>("price"));
        tvAddedParts.setItems(addedParts);
        colPartIdAdded.setCellValueFactory(new PropertyValueFactory<>("partID"));
        colPartNameAdded.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPartInvAdded.setCellValueFactory(new PropertyValueFactory<>("instock"));
        colPartPriceAdded.setCellValueFactory(new PropertyValueFactory<>("price"));

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
            tbInv.setText("0");
            valid = true;
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
        if(addedParts.isEmpty()){
            valid = false;
            sb.append("\nProduct must have at least one part");
        }
        if(!addedParts.isEmpty()){
            double totalPartsCost = 0;
            for (Part part: addedParts) {
                totalPartsCost += part.getPrice();
            }
            try{
                if(Integer.valueOf(tbPrice.getText()) < totalPartsCost){
                    valid = false;
                    sb.append("\nProduct cost must be more than the cost of the parts associated");
                    tbPrice.pseudoClassStateChanged(invalid, true);
                } else {
                    tbPrice.pseudoClassStateChanged(invalid, false);
                }
            } catch (Exception ignored){}
        }
        if (!valid) {
            Alert alert = new Alert(Alert.AlertType.ERROR, sb.toString());
            alert.getDialogPane().setPrefSize(320, 250);
            alert.showAndWait();
        }
        return valid;
    }
}
