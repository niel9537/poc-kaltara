package com.kartala.poc.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListTransaction {
    @SerializedName("id_transaction")
    @Expose
    private String idTransaction;
    @SerializedName("id_customer")
    @Expose
    private String idCustomer;
    @SerializedName("customer_name")
    @Expose
    private String customerName;
    @SerializedName("id_driver")
    @Expose
    private String idDriver;
    @SerializedName("driver_name")
    @Expose
    private String driverName;
    @SerializedName("id_admin")
    @Expose
    private String idAdmin;
    @SerializedName("admin_name")
    @Expose
    private Object adminName;
    @SerializedName("id_product")
    @Expose
    private String idProduct;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("product_price")
    @Expose
    private String productPrice;
    @SerializedName("product_image")
    @Expose
    private String productImage;
    @SerializedName("vendor")
    @Expose
    private String vendor;
    @SerializedName("vendor_name")
    @Expose
    private String vendorName;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("vendor_phone")
    @Expose
    private String vendorPhone;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("update_at")
    @Expose
    private String updateAt;
    @SerializedName("status")
    @Expose
    private String status;

    public ListTransaction(String idTransaction, String idCustomer, String customerName, String idDriver, String driverName, String idAdmin, Object adminName, String idProduct, String productName, String productPrice, String productImage, String vendor, String vendorName, String address, String vendorPhone, String phone, String createdAt, String updateAt, String status) {
        this.idTransaction = idTransaction;
        this.idCustomer = idCustomer;
        this.customerName = customerName;
        this.idDriver = idDriver;
        this.driverName = driverName;
        this.idAdmin = idAdmin;
        this.adminName = adminName;
        this.idProduct = idProduct;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productImage = productImage;
        this.vendor = vendor;
        this.vendorName = vendorName;
        this.address = address;
        this.vendorPhone = vendorPhone;
        this.phone = phone;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
        this.status = status;
    }

    public String getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(String idTransaction) {
        this.idTransaction = idTransaction;
    }

    public String getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(String idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getIdDriver() {
        return idDriver;
    }

    public void setIdDriver(String idDriver) {
        this.idDriver = idDriver;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(String idAdmin) {
        this.idAdmin = idAdmin;
    }

    public Object getAdminName() {
        return adminName;
    }

    public void setAdminName(Object adminName) {
        this.adminName = adminName;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVendorPhone() {
        return vendorPhone;
    }

    public void setVendorPhone(String vendorPhone) {
        this.vendorPhone = vendorPhone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
