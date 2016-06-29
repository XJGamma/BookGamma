package cn.edu.xjtu.se.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by DUAN Yufei on 16-6-29.
 */
public class Book implements Serializable {

    private int id;
    private String name;
    private String image;
    private String isbn;
    private int pages;
    private int current_page;
    private Date finish_time;
    private int total_reading_time;

    public Book(int current_page, Date finish_time, int id, String image, String isbn, String name, int pages, int total_reading_time) {
        this.current_page = current_page;
        this.finish_time = finish_time;
        this.id = id;
        this.image = image;
        this.isbn = isbn;
        this.name = name;
        this.pages = pages;
        this.total_reading_time = total_reading_time;
    }

    public Book(Date finish_time, String image, String isbn, String name, int pages) {
        this.finish_time = finish_time;
        this.image = image;
        this.isbn = isbn;
        this.name = name;
        this.pages = pages;
        this.current_page = 0;
        this.total_reading_time = 0;
    }

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public Date getFinish_time() {
        return finish_time;
    }

    public void setFinish_time(Date finish_time) {
        this.finish_time = finish_time;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getTotal_reading_time() {
        return total_reading_time;
    }

    public void setTotal_reading_time(int total_reading_time) {
        this.total_reading_time = total_reading_time;
    }
}
