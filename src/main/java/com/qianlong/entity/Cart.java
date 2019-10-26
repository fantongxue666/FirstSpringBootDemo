package com.qianlong.entity;

public class Cart implements java.io.Serializable{
    private Shangpin shangpin;
    private Integer number;

    public Shangpin getShangpin() {
        return shangpin;
    }

    public void setShangpin(Shangpin shangpin) {
        this.shangpin = shangpin;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
