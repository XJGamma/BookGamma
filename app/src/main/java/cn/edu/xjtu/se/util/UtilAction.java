package cn.edu.xjtu.se.util;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import cn.edu.xjtu.se.bookgamma.R;
import cn.edu.xjtu.se.dao.DBDao;

/**
 * Created by Jingkai Tang on 8/28/16.
 */
public class UtilAction {
    public static void bookCommentDelete(final Context context, final int commentId) {
        new AlertDialog.Builder(context)
                .setTitle(R.string.txt_delete)
                .setMessage(R.string.book_comment_del_confirm)
                .setPositiveButton(R.string.button_ok, new AlertDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int ret = DBDao.delComment(commentId);
                        if (ret > 0) {
                            ToastMsg.s(context, R.string.tip_del_comment_succeed);
                        } else {
                            ToastMsg.s(context, R.string.tip_del_comment_fail);
                        }
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.cancel, new AlertDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    public static class ToastMsg {
        public static void s(Context context, int rid) {
            Toast.makeText(context, rid, Toast.LENGTH_SHORT).show();
        }

        public static void s(Context context, String msg) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }

        public static void l(Context context, int rid) {
            Toast.makeText(context, rid, Toast.LENGTH_LONG).show();
        }

        public static void l(Context context, String msg) {
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        }
    }
}
