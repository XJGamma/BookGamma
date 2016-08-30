package cn.edu.xjtu.se.bookgamma.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import cn.edu.xjtu.se.bean.Comment;
import cn.edu.xjtu.se.bookgamma.R;

/**
 * Created by DUAN Yufei on 16-6-29.
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {
    public List<Comment> comments = null;
    private LayoutInflater mInflater;

    private static SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public interface onItemClickListener {
        void onItemClick(View view, int id);

        void onItemLongClick(View view, int pos, int id);
    }

    private onItemClickListener onItemClickListener;

    public void setOnItemClickListener(onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public CommentAdapter(Context context, List<Comment> datas) {
        mInflater = LayoutInflater.from(context);
        comments = datas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(mInflater.inflate(
                R.layout.item_comment, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.commentTime.setText(dt.format(comments.get(position).getCreated_at()));
        holder.commentContent.setText(comments.get(position).getContent());
        holder.itemView.setTag(comments.get(position).getId());

        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(holder.itemView, (int)v.getTag());

                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemClickListener.onItemLongClick(holder.itemView, holder.getLayoutPosition(), (int)v.getTag());
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public void removeItem(int pos){
        comments.remove(pos);
    }

    public void setData(List<Comment> comments){
        this.comments = comments;
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView commentTime;
        public TextView commentContent;

        public MyViewHolder(View view) {
            super(view);
            commentTime = (TextView) view.findViewById(R.id.it_comment_time);
            commentContent = (TextView) view.findViewById(R.id.it_comment_content);
        }
    }
}
