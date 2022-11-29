package com.example.fecultypannel.models;

public class f_show_assignment_dataClass {

    String id;
    String pdfURL;
    String pdf_title;
    String s_div;
    String upload_date;
    String submission_date;
    String sub_name;
    String f_name;
    String f_email;

    public f_show_assignment_dataClass(String id, String pdfURL, String pdf_title, String s_div, String upload_date, String submission_date, String sub_name, String f_name, String f_email) {
        this.id = id;
        this.pdfURL = pdfURL;
        this.pdf_title = pdf_title;
        this.s_div = s_div;
        this.upload_date = upload_date;
        this.submission_date = submission_date;
        this.sub_name = sub_name;
        this.f_name = f_name;
        this.f_email = f_email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPdfURL() {
        return pdfURL;
    }

    public void setPdfURL(String pdfURL) {
        this.pdfURL = pdfURL;
    }

    public String getPdf_title() {
        return pdf_title;
    }

    public void setPdf_title(String pdf_title) {
        this.pdf_title = pdf_title;
    }

    public String getS_div() {
        return s_div;
    }

    public void setS_div(String s_div) {
        this.s_div = s_div;
    }

    public String getUpload_date() {
        return upload_date;
    }

    public void setUpload_date(String upload_date) {
        this.upload_date = upload_date;
    }

    public String getSubmission_date() {
        return submission_date;
    }

    public void setSubmission_date(String submission_date) {
        this.submission_date = submission_date;
    }

    public String getSub_name() {
        return sub_name;
    }

    public void setSub_name(String sub_name) {
        this.sub_name = sub_name;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getF_email() {
        return f_email;
    }

    public void setF_email(String f_email) {
        this.f_email = f_email;
    }
}
