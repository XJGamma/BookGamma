package cn.edu.xjtu.se.bookgamma;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.File;
import java.util.List;

/**
 * Created by qh on 2016/6/21.
 */
public class BookAdapter extends ArrayAdapter<Book>{

    private  int resourceId;

    class ViewHolder {

        ImageView bookImage;
        TextView bookName;
        TextView bookPages;
    }
    public BookAdapter(Context context, int textViewResourceId, List<Book> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Book book = getItem(position);//获取当前Book实例

//        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.bookImage = (ImageView) view.findViewById(R.id.book_image);
            viewHolder.bookName = (TextView) view.findViewById(R.id.book_name);
            viewHolder.bookPages = (TextView) view.findViewById(R.id.book_page);
            view.setTag(viewHolder);

        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
//        viewHolder.bookImage.setImageResource(book.getImage());
//        viewHolder.bookImage.setImageURI(Uri.parse(book.getImage()));
        viewHolder.bookImage.setImageURI(Uri.parse(new File(book.getImage()).toString()));
//        viewHolder.bookImage.setImageResource(R.drawable.santi);
        viewHolder.bookName.setText(book.getName());
        viewHolder.bookPages.setText( Integer.toString(book.getPages()));
        return view;
    }


}
