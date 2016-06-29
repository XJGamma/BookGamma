package cn.edu.xjtu.se.bookgamma.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.edu.xjtu.se.bookgamma.R;

/**
 * Created by DUAN Yufei on 16-6-28.
 */
public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    public String[] datas = null;

    public BookAdapter(String[] datas) {
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_book, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.mTextView.setText(datas[position]);
    }

    @Override
    public int getItemCount() {
        return datas.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public ViewHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.it_book_name);
        }
    }
}
