package cn.edu.xjtu.se.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by DUAN Yufei on 16-6-29.
 */
public class Book implements Serializable {
    // TODO: 命名
    private int id;
    private String name;
    private String image;
    private String isbn;
    private int pages;
    private int current_page;
    private Date finish_time;
    private int total_reading_time;
    private Date created_at;
    private Date updated_at;

    public Book(int id, String name, String image, String isbn, int pages, int current_page, Date finish_time, int total_reading_time, Date created_at, Date updated_at) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.isbn = isbn;
        this.pages = pages;
        this.current_page = current_page;
        this.finish_time = finish_time;
        this.total_reading_time = total_reading_time;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Book(String name, String image, String isbn, int pages, int current_page, Date finish_time, int total_reading_time, Date created_at, Date updated_at) {
        this.name = name;
        this.image = image;
        this.isbn = isbn;
        this.pages = pages;
        this.current_page = current_page;
        this.finish_time = finish_time;
        this.total_reading_time = total_reading_time;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
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
