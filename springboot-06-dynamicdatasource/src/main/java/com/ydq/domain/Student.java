package com.ydq.domain;

public class Student {
    private String SNO;
    private String SNAME;
    private String SSEX;
    private String DATASOURCE;

    public String getSNO() {
        return SNO;
    }

    public void setSNO(String SNO) {
        this.SNO = SNO;
    }

    public String getSNAME() {
        return SNAME;
    }

    public void setSNAME(String SNAME) {
        this.SNAME = SNAME;
    }

    public String getSSEX() {
        return SSEX;
    }

    public void setSSEX(String SSEX) {
        this.SSEX = SSEX;
    }

    public String getDATASOURCE() {
        return DATASOURCE;
    }

    public void setDATASOURCE(String DATASOURCE) {
        this.DATASOURCE = DATASOURCE;
    }
}
