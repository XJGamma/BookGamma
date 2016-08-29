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
    private Date created_at;
    private Date updated_at;

    public Comment(int id, int book_id, String content, Date created_at, Date updated_at) {
        this.id = id;
        this.book_id = book_id;
        this.content = content;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Comment(int book_id, String content, Date created_at, Date updated_at) {
        this.book_id = book_id;
        this.content = content;
        this.created_at = created_at;
        this.updated_at = updated_at;
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

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}
