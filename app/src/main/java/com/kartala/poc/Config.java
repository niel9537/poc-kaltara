package com.kartala.poc;

public class Config {

    public static final String BASE_URL = "http://192.168.1.9:8080/po_api/api/";
    public static final int SCAN = 111;
    public static final int PERMISSION_REQUEST_CODE = 200;
    public static final int CAMERA_REQUEST = 1888;
    public static final int MY_CAMERA_PERMISSION_CODE = 100;
    public static String setStatus(String status){
        if(status.equals("1")){
            return "Pesanan telah terkirim";
        }else if(status.equals("2")){
            return "Pesanan berhasil di terima oleh customer";
        }else if(status.equals("3")){
            return "Pesanan sedang di antar oleh driver ke alamat customer";
        }else if(status.equals("4")){
            return "Pesanan telah sampai di gudang terdekat";
        }else if(status.equals("5")){
            return "Pesanan sedang di antar oleh driver ke gudang terdekat";
        }else if(status.equals("6")){
            return "Pesanan sedang di proses oleh vendor";
        }else if(status.equals("7")){
            return "Pesanan sedang di proses oleh driver ke alamat vendor";
        }else if(status.equals("8")){
            return "Pesanan menunggu di proses";
        }else{
            return "Pesanan Sukses";
        }
    }
}
