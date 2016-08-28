package cn.edu.xjtu.se.util;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.edu.xjtu.se.bean.Book;
import cn.edu.xjtu.se.bookgamma.R;
import cn.edu.xjtu.se.dao.DBDao;

/**
 * Created by Jingkai Tang on 8/28/16.
 */
public class UtilAction {
    public static class bookComment {
        public static void delete(final Context context, final int commentId, final DoActionListener dal) {
            new AlertDialog.Builder(context)
                    .setTitle(R.string.txt_delete)
                    .setMessage(R.string.book_comment_del_confirm)
                    .setPositiveButton(R.string.button_ok, new AlertDialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int ret = DBDao.delComment(commentId);
                            if (ret > 0) {
                                toast.s(context, R.string.tip_del_comment_succeed);
                            } else {
                                toast.s(context, R.string.tip_del_comment_fail);
                            }
                            dal.doAction(context);
                        }
                    })
                    .setNegativeButton(R.string.cancel, new AlertDialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .show();
        }
    }

    public static class book {
        public static void delete(final Context context, int bookId, final DoActionListener dal) {
            final Book book = DBDao.getBook(bookId);
            new AlertDialog.Builder(context)
                    .setTitle(R.string.txt_delete)
                    .setMessage("您确定要删除《" + book.getName() + "》吗?")
                    .setPositiveButton(R.string.button_ok, new AlertDialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int ret = DBDao.delBook(book.getId());
                            if (ret > 0) {
                                toast.s(context, R.string.tip_del_book_succeed);
                            } else {
                                toast.s(context, R.string.tip_del_book_failed);
                            }

                            // TODO: 此处应当移到DBDao中
                            int ret2 = DBDao.delReadingRemindByBook(book.getId());
                            if (ret2 > 0) {
                                toast.s(context, R.string.tip_del_remind_succeed);
                            } else {
                                toast.s(context, R.string.tip_del_remind_failed);
                            }

                            // TODO: 删除读书笔记

                            dal.doAction(context);
                        }
                    })
                    .setNegativeButton(R.string.cancel, new AlertDialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .show();
        }
    }

    public static class fmt {
        private static SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        public static String date(Date date) {
            return fmt.format(date);
        }
    }

    public static List<Integer> range(int min, int max) {
        List<Integer> list = new ArrayList<>();
        for (int i = min; i <= max; i++) {
            list.add(i);
        }
        return list;
    }

    public static List<String> srange(int min, int max) {
        List<String> list = new ArrayList<>();
        for (int i = min; i <= max; i++) {
            list.add(String.valueOf(i));
        }
        return list;
    }

    public static class toast {
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

    public static int percent(int part, int total) {
        if (part <= 0) {
            return 0;
        }

        if (part > total || total == 0) {
            return 100;
        }

        return part * 100 / total;
    }
}
