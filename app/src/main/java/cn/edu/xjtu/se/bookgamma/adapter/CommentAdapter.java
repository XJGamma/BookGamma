package cn.edu.xjtu.se.bookgamma.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import cn.edu.xjtu.se.bean.Comment;
import cn.edu.xjtu.se.bookgamma.R;

/**
 * Created by DUAN Yufei on 16-6-29.
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> implements View.OnClickListener{
    public List<Comment> comments = null;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;

    private OnRecyclerViewItemClickListener onItemClickListener = null;

    public CommentAdapter(List<Comment> comments, Context context) {
        this.comments = comments;
//        imageLoader = ImageLoader.getInstance();
//        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.loading)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_comment, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.commentTime.setText(comments.get(position).getCreated_time().toString());
        viewHolder.commentContent.setText(comments.get(position).getContent());
        viewHolder.itemView.setTag(comments.get(position).getId());
//        viewHolder.bookName.setText(comments.get(position).getName());
//        imageLoader.displayImage(comments.get(position).getImage(), viewHolder.bookImage, options);
//        viewHolder.itemView.setTag(comments.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            onItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView commentTime;
        public TextView commentContent;

        public ViewHolder(View view) {
            super(view);
            commentTime = (TextView) view.findViewById(R.id.it_comment_time);
            commentContent = (TextView) view.findViewById(R.id.it_comment_content);
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int tag);
    }
}
