package com.example.a1213;

public class SingleItem {
    String title;
    String writer;
    int resld;

    public SingleItem(String title, String writer, int resld) {
        this.title = title;
        this.writer = writer;
        this.resld = resld;
    }

    @Override
    public String toString() {
        return "SingleItem{" +
                "title='" + title + '\'' +
                ", writer='" + writer + '\'' +
                ", resld=" + resld +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public int getResld() {
        return resld;
    }

}

