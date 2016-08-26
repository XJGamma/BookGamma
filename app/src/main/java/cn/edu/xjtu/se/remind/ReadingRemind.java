package cn.edu.xjtu.se.remind;

/**
 * Created by asus on 2016/8/23.
 */
public class ReadingRemind {
    private int id;
    private int bookId;
    private String bookName;
    private String image;
    private String remindTime;
    private int status;
    private boolean checked;

    public ReadingRemind(int id, int bookId, String bookName, String image, String remindTime, int status) {
        this.id = id;
        this.bookId = bookId;
        this.bookName = bookName;
        this.image = image;
        this.remindTime = remindTime;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public int getBookId() {
        return bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public String getImage() {
        return image;
    }

    public String getRemindTime(){
        return remindTime;
    }

    public int getStatus(){
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
        if (status == 1 )
            checked = true;
        else
            checked = false;
    }

    public boolean getchecked(){
        return  checked;
    }
}
