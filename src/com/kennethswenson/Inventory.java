package com.kennethswenson;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private ObservableList<Product> products = FXCollections.observableArrayList();

    public void addProduct(Product product){
        if (!products.isEmpty()){
            if(products.contains(product)){
                return;
            }
        }
        products.add(product);
    }
    public boolean removeProduct(int productID){
        if (products.isEmpty()) {
            return false;
        } else {
            for (Product product: products) {
                if(product.getProductID() == productID){
                    products.remove(product);
                    return true;
                }
            }
            return false;
        }
    }
    public Product lookupProduct(int productID){
        if (products.isEmpty()) {
            return null;
        } else {
            for (Product product: products) {
                if(product.getProductID() == productID){
                    return product;
                }
            }
            return null;
        }
    }

    public void updateProduct(int productID, Product newProduct) {
        if (!products.isEmpty()) {
            for (Product product: products) {
                if(product.getProductID() == productID){
                    products.set(products.indexOf(product), newProduct);
                }
            }
        }
    }

    public int size(){
        return products.size();
    }

    public int nextProdId(){
        if (products.size() != 0){
            return products.get(size() - 1).getProductID();
        } else {
            return 0;
        }
    }

    public ObservableList<Product> getProducts() {
        return products;
    }
}
