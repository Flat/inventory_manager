package com.kennethswenson;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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


    private static Product product = null;
    private static int autoNum = 0;


    public void btnSearch(ActionEvent actionEvent) {
    }

    public void btnAddPart(ActionEvent actionEvent) {
    }

    public void btnDelPart(ActionEvent actionEvent) {
    }

    public void btnCancel(ActionEvent actionEvent) {
    }

    public void btnSave(ActionEvent actionEvent) {
    }

    public Product display(int maxProductId){
        autoNum = maxProductId + 1;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("gui/add_product.fxml"));
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
}
