package cn.edu.xjtu.se.bookgamma;

/**
 * Created by qh on 2016/6/21.
 */
public class Book {

    private String name;
    private String image;
    private int pages;
    private int current_page;

    public Book(String name, String image, int pages, int current_page) {
        this.name = name;
        this.image = image;
        this.pages = pages;
        this.current_page = current_page;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public int getPages(){
        return pages;
    }

    public int getCurrent_page(){
        return current_page;
    }
}
