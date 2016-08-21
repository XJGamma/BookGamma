package cn.edu.xjtu.se.bookgamma;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.File;
import java.util.List;

/**
 * Created by qh on 2016/6/21.
 */
public class BookAdapter extends ArrayAdapter<Book>{

    private  int resourceId;
    private List<Book> listbooks;
    private cn.edu.xjtu.se.dao.DBHelper dbHelper;

    class ViewHolder {

        ImageView bookImage;
        TextView bookName;
        TextView bookPages;
        cn.edu.xjtu.se.bookgamma.CircleProgressView bookCurrentPage;
        //Button deleteBook;
    }
    public BookAdapter(Context context, int textViewResourceId, List<Book> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
        this.listbooks = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Book book = getItem(position);//获取当前Book实例
        final int selectID = position;

//        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.bookImage = (ImageView) view.findViewById(R.id.book_image);
            viewHolder.bookName = (TextView) view.findViewById(R.id.book_name);
            viewHolder.bookPages = (TextView) view.findViewById(R.id.book_page);
            viewHolder.bookCurrentPage = (cn.edu.xjtu.se.bookgamma.CircleProgressView) view.findViewById(R.id.circleProgressbar);
            //viewHolder.deleteBook = (Button) view.findViewById(R.id.delete_data);
            view.setTag(viewHolder);

        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }


        //viewHolder.bookImage.setImageURI(Uri.parse(new File(book.getImage()).toString()));
        Uri u = Uri.parse(book.getImage().toString());
        viewHolder.bookImage.setImageURI(u);

        viewHolder.bookName.setText(book.getName());
        viewHolder.bookPages.setText( Integer.toString(book.getCurrent_page()) + "/" + Integer.toString(book.getPages()));

        int p = (book.getCurrent_page() * 100/book.getPages())  ;
        viewHolder.bookCurrentPage.setProgress(p);

        return view;
    }


}
