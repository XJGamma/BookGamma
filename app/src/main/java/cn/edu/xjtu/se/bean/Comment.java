package cn.edu.xjtu.se.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by DUAN Yufei on 16-6-29.
 */
public class Comment implements Serializable {

    private int id;
    private int book_id;
    private String content;
    private Date created_time;

    public Comment(int id, int book_id, Date created_time, String content) {
        this.book_id = book_id;
        this.id = id;
        this.created_time = created_time;
        this.content = content;
    }

    public Comment(int book_id, String content, Date created_time) {
        this.book_id = book_id;
        this.content = content;
        this.created_time = created_time;
    }

    public int getId() {
        return id;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreated_time() {
        return created_time;
    }

    public void setCreated_time(Date created_time) {
        this.created_time = created_time;
    }
}
