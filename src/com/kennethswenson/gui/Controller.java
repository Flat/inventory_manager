package com.kennethswenson.gui;

import com.kennethswenson.Inventory;
import com.kennethswenson.Part;
import com.kennethswenson.Product;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


public class Controller {
    private Inventory inventory = new Inventory();
    private ObservableList<Part> parts = FXCollections.observableArrayList();
    @FXML
    private TableView<Part> partTable;
    @FXML
    private TableColumn<Part, String> colPartId;
    @FXML
    private TableColumn<Part, String> colPartName;
    @FXML
    private TableColumn<Part, String> colPartInventoryLevel;
    @FXML
    private TableColumn<Part, String> colPartPrice;
    @FXML
    private TextField tbSearchPart;
    @FXML
    private TextField tbSearchProduct;
    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Part, String> colProductId;
    @FXML
    private TableColumn<Part, String> colProductName;
    @FXML
    private TableColumn<Part, String> colProductInventoryLevel;
    @FXML
    private TableColumn<Part, String> colProductPrice;


    public void btnAddPart(ActionEvent actionEvent) {
        AddPartController apc = new AddPartController();
        int maxPartNum;
        if(parts.isEmpty()){
            maxPartNum = 0;
        } else {
            maxPartNum = parts.get(parts.size()-1).getPartID();
        }
        Part result = apc.display(maxPartNum);
        if (result != null){
            parts.add(result);
        }
    }

    public void btnExit(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit? ");
        alert.showAndWait().filter(response -> response == ButtonType.OK).ifPresent(response -> Platform.exit());
    }

    public void btnModifyPart(ActionEvent actionEvent) {
        ModifyPartController mpc = new ModifyPartController();
        Part toEdit = partTable.getSelectionModel().getSelectedItem();
        if(toEdit != null){
            Part edited = mpc.display(toEdit);
            if(edited != null){
                parts.add(parts.indexOf(toEdit), edited);
                parts.remove(toEdit);
            }
        }
    }

    public void btnDelPart(ActionEvent actionEvent) {
        Part toDel = partTable.getSelectionModel().getSelectedItem();
        if(toDel != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete part with ID: " + String.valueOf(toDel.getPartID()));
            alert.showAndWait().filter(response -> response == ButtonType.OK).ifPresent(response -> parts.remove(toDel));
        }
    }
    public void btnPartsSearch(ActionEvent actionEvent) {
        FilteredList<Part> filteredParts = new FilteredList<Part>(parts, p -> true);
        filteredParts.setPredicate(part -> tbSearchPart.getText().isEmpty() || part.getName().toUpperCase().contains(tbSearchPart.getText().toUpperCase()));
        SortedList<Part> sortedParts = new SortedList<Part>(filteredParts);
        sortedParts.comparatorProperty().bind(partTable.comparatorProperty());
        partTable.setItems(sortedParts);
    }

    public void btnAddProduct(ActionEvent actionEvent) {
        AddProductController apc = new AddProductController();
        int maxProductNum;
        if(inventory.getProducts().isEmpty()){
            maxProductNum = 0;
        } else {
            maxProductNum = inventory.nextProdId();
        }
        Product result = apc.display(maxProductNum, FXCollections.observableArrayList(parts));
        if (result != null){
            inventory.addProduct(result);
        }
    }

    public void btnModifyProduct(ActionEvent actionEvent) {
        ModifyProductController mpc = new ModifyProductController();
        Product selection = productTable.getSelectionModel().getSelectedItem();
        if(selection != null){
            Product toEdit = inventory.lookupProduct(selection.getProductID());
            if(toEdit != null){
                Product edited = mpc.display(toEdit, FXCollections.observableArrayList(parts));
                if(edited != null){
                    inventory.updateProduct(toEdit.getProductID(), edited);
                }
            }
        }
    }

    public void btnDelProduct(ActionEvent actionEvent) {
                Product toDel = productTable.getSelectionModel().getSelectedItem();
        if(toDel != null){
            StringBuilder sbConfirmationText = new StringBuilder();
            sbConfirmationText.append("Are you sure you want to delete Product with ID: ");
            sbConfirmationText.append(String.valueOf(toDel.getProductID()));
            if (toDel.hasParts()){
                sbConfirmationText.append("\nThis product still has parts assigned!");
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, sbConfirmationText.toString());
            alert.showAndWait().filter(response -> response == ButtonType.OK).ifPresent(response -> {
                inventory.removeProduct(toDel.getProductID());
                productTable.setItems(inventory.getProducts());
            });
        }
    }

    public void btnProductSearch(ActionEvent actionEvent) {
        FilteredList<Product> filteredProducts = new FilteredList<Product>(inventory.getProducts(), p -> true);
        filteredProducts.setPredicate(product -> tbSearchProduct.getText().isEmpty() || product.getName().toUpperCase().contains(tbSearchProduct.getText().toUpperCase()));
        SortedList<Product> sortedProducts = new SortedList<Product>(filteredProducts);
        sortedProducts.comparatorProperty().bind(productTable.comparatorProperty());
        productTable.setItems(sortedProducts);
    }

    @FXML
    public void initialize() {
        partTable.setItems(parts);
        colPartId.setCellValueFactory(new PropertyValueFactory<>("partID"));
        colPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPartInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("instock"));
        colPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        productTable.setItems(inventory.getProducts());
        colProductId.setCellValueFactory(new PropertyValueFactory<>("productID"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colProductInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("instock"));
        colProductPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

}
