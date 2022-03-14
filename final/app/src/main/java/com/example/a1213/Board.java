package com.example.a1213;

public class Board {
    private int no;
    private int bno;
    private String title;
    private String content;
    private String category;

    public void setNo(int no) {
        this.no = no;
    }

    public void setBno(int bno) {
        this.bno = bno;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public int getNo() {
        return no;
    }

    public int getBno() {
        return bno;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getCategory() {
        return category;
    }

}
