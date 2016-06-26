package cn.edu.xjtu.se.bookgamma;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BookListActivity extends AppCompatActivity {

    private List<Book> bookList = new ArrayList<Book>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        initBook();//初始化书籍信息
        BookAdapter adapter = new BookAdapter(BookListActivity.this, R.layout.book_item, bookList);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book book = bookList.get(position);
                Toast.makeText(BookListActivity.this, book.getName(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initBook() {

//        Book santi = new Book("三体", R.drawable.santi, "100");
//        bookList.add(santi);
//
//        Book xianjian = new Book("仙剑奇侠传",R.drawable.xianjian, "191");
//        bookList.add(xianjian);
//
//        Book bingyuhuo = new Book("冰与火之歌",R.drawable.ice_and_fire, "192");
//        bookList.add(bingyuhuo);
//
//        Book douluo = new Book("斗罗大陆",R.drawable.douluo, "193");
//        bookList.add(douluo);
//
//        Book aQ = new Book("阿Q正传",R.drawable.aq, "194");
//        bookList.add(aQ);
    }

}
