package cn.edu.xjtu.se.bookgamma;

/**
 * Created by qh on 2016/6/21.
 */
public class Book {
    private String name;
    private String image;
    private int pages;

    public Book(String name, String image, int pages) {
        this.name = name;
        this.image = image;
        this.pages = pages;
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
}
