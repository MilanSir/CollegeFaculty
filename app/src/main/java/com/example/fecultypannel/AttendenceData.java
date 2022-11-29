package com.example.fecultypannel;

public class AttendenceData {
    String id;
    String rollno;
    String name;
    String attendence_date;
    String attendence_result;
    String sub;
    String feculty_name;
    String s_div;
    String s_enrlno;

    public AttendenceData(String id, String rollno, String name, String attendence_date, String attendence_result, String sub, String feculty_name, String s_div, String s_enrlno) {
        this.id = id;
        this.rollno = rollno;
        this.name = name;
        this.attendence_date = attendence_date;
        this.attendence_result = attendence_result;
        this.sub = sub;
        this.feculty_name = feculty_name;
        this.s_div = s_div;
        this.s_enrlno = s_enrlno;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRollno() {
        return rollno;
    }

    public void setRollno(String rollno) {
        this.rollno = rollno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttendence_date() {
        return attendence_date;
    }

    public void setAttendence_date(String attendence_date) {
        this.attendence_date = attendence_date;
    }

    public String getAttendence_result() {
        return attendence_result;
    }

    public void setAttendence_result(String attendence_result) {
        this.attendence_result = attendence_result;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getFeculty_name() {
        return feculty_name;
    }

    public void setFeculty_name(String feculty_name) {
        this.feculty_name = feculty_name;
    }

    public String getS_div() {
        return s_div;
    }

    public void setS_div(String s_div) {
        this.s_div = s_div;
    }

    public String getS_enrlno() {
        return s_enrlno;
    }

    public void setS_enrlno(String s_enrlno) {
        this.s_enrlno = s_enrlno;
    }
}
