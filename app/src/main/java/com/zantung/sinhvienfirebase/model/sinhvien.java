package com.zantung.sinhvienfirebase.model;

import java.util.HashMap;
import java.util.Map;

public class sinhvien {
    private int id;
    private String ten;

    public sinhvien(int id, String ten) {
        this.id = id;
        this.ten = ten;
    }

    public sinhvien() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("ten", ten);
        return result;
    }
}
