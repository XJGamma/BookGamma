package cn.edu.xjtu.se.booklistview;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

import cn.edu.xjtu.se.bean.Book;
import cn.edu.xjtu.se.bookgamma.R;
import cn.edu.xjtu.se.util.UtilAction;

/**
 * Created by qh on 2016/6/21.
 */
public class BookAdapter extends ArrayAdapter<Book> {

    private int resourceId;
    private ImageLoader imageLoader;
    class ViewHolder {

        ImageView bookImage;
        TextView bookName;
        TextView bookPages;
        CircleProgressView bookCurrentPage;
    }

    public BookAdapter(Context context, int textViewResourceId, List<Book> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Book book = getItem(position);//获取当前Book实例

        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.bookImage = (ImageView) view.findViewById(R.id.book_image);
            viewHolder.bookName = (TextView) view.findViewById(R.id.view_book_name);
            viewHolder.bookPages = (TextView) view.findViewById(R.id.view_book_page);
            viewHolder.bookCurrentPage = (CircleProgressView) view.findViewById(R.id.circleProgressbar);
            //viewHolder.deleteBook = (Button) view.findViewById(R.id.delete_data);
            view.setTag(viewHolder);

        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        imageLoader.displayImage(book.getImage(), viewHolder.bookImage, UtilAction.getDisplayImageOptions());

        viewHolder.bookName.setText(book.getName());
        viewHolder.bookPages.setText(Integer.toString(book.getCurrent_page()) + "/" + Integer.toString(book.getPages()));

        viewHolder.bookCurrentPage.setProgress(UtilAction.percent(book.getCurrent_page(), book.getPages()));

        return view;
    }


}
