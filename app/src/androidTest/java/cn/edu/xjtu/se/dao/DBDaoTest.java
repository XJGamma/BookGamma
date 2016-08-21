package cn.edu.xjtu.se.dao;

import android.test.AndroidTestCase;

import java.util.Calendar;
import java.util.List;

import cn.edu.xjtu.se.bean.Book;
import cn.edu.xjtu.se.bean.Comment;

/**
 * Created by DUAN Yufei on 16-6-24.
 */
public class DBDaoTest extends AndroidTestCase {

    private long rowID;

    public void testAddBook() throws Exception {
        String bookName = "book_name_test";
        int pages = 560;
        Calendar cal = Calendar.getInstance();
        String isbn = "";
        String image = "file:///storage/0/20156353.jpg";
        rowID = DBDao.addBook(bookName, pages, cal.getTime(), isbn, image);
        assertEquals(true, rowID > 0);
    }

    public void testAddComment() throws Exception {
        int book_id = (int) rowID;
        String content = "test content";
        long commentID = DBDao.addComment(book_id, content);
        assertEquals(true, commentID > 0);
    }

    public void testFindBooksAll() throws Exception {
        List<Book> list = DBDao.findBooksAll();
        assertEquals(false, list.isEmpty());
    }

    public void testGetCommentByBook() throws Exception {
        List<Comment> list = DBDao.getCommentByBook((int) rowID);
        assertEquals(false, list.isEmpty());
    }

}
