package com.kartala.poc;

public class Config {

    //public static final String BASE_URL = "http://192.168.1.6:8080/po_api/api/";
    public static final String BASE_URL = "https://pockaltara.000webhostapp.com/po_api/api/";
    public static final int SCAN = 111;
    public static final int PERMISSION_REQUEST_CODE = 200;
    public static final int CAMERA_REQUEST = 1888;
    public static final int MY_CAMERA_PERMISSION_CODE = 100;
    public static final int KEEP_LOGIN = 0;
    public static String setStatus(String status){
        if(status.equals("1")){
            return "Pesanan sedang di antar ke alamat pelanggan";
        }else if(status.equals("2")){
            return "Pesanan di gudang transit";
        }else if(status.equals("3")){
            return "Pesanan dalam perjalanan ke gudang transit";
        }else if(status.equals("4")){
            return "Menunggu driver pick up pesanan";
        }else if(status.equals("5")){
            return "Pesanan sedang di proses";
        }else{
            return "Barang selesai diantar";
        }
    }

    public static String setStatusItem(String status){
        if(status.equals("1")){
            return "Pengiriman";
        }else if(status.equals("2")){
            return "Transit";
        }else if(status.equals("3")){
            return "Perjalanan";
        }else if(status.equals("4")){
            return "Pick up";
        }else if(status.equals("5")){
            return "Konfirmasi";
        }else{
            return "Selesai";
        }
    }
}
