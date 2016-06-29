package cn.edu.xjtu.se.bookgamma.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

import cn.edu.xjtu.se.bean.Book;
import cn.edu.xjtu.se.bookgamma.R;

/**
 * Created by DUAN Yufei on 16-6-28.
 */
public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    public List<Book> books = null;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;

    public BookAdapter(List<Book> books,Context context) {
        this.books = books;
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.loading)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_book, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.bookName.setText(books.get(position).getName());
        imageLoader.displayImage(books.get(position).getImage(), viewHolder.bookImage, options);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView bookName;
        public ImageView bookImage;

        public ViewHolder(View view) {
            super(view);
            bookName = (TextView) view.findViewById(R.id.it_book_name);
            bookImage = (ImageView) view.findViewById(R.id.iv_book_image);
        }
    }
}
