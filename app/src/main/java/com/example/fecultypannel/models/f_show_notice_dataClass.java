package com.example.fecultypannel.models;

public class f_show_notice_dataClass {

    String id;
    String notice_title;
    String notice_pdf_url;
    String date;

    public f_show_notice_dataClass(String id, String notice_title, String notice_pdf_url, String date) {
        this.id = id;
        this.notice_title = notice_title;
        this.notice_pdf_url = notice_pdf_url;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNotice_title() {
        return notice_title;
    }

    public void setNotice_title(String notice_title) {
        this.notice_title = notice_title;
    }

    public String getNotice_pdf_url() {
        return notice_pdf_url;
    }

    public void setNotice_pdf_url(String notice_pdf_url) {
        this.notice_pdf_url = notice_pdf_url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
