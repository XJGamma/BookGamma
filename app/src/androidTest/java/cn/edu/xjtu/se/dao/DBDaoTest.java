package cn.edu.xjtu.se.dao;

import android.test.AndroidTestCase;

import java.util.Calendar;

/**
 * Created by DUAN Yufei on 16-6-24.
 */
public class DBDaoTest extends AndroidTestCase {

    public void testAddBook() throws Exception {
        String bookName = "book_name_test";
        int pages = 560;
        Calendar cal = Calendar.getInstance();
        String isbn = "";
        String image = "file:///storage/0/20156353.jpg";
        long rowID = DBDao.addBook(bookName, pages, cal.getTime(), isbn, image);
        assertEquals(true, rowID > 0);
    }

}
