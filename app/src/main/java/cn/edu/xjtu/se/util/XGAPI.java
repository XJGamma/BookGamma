package cn.edu.xjtu.se.util;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.HttpUrl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.edu.xjtu.se.bean.Book;
import cn.edu.xjtu.se.bean.Comment;
import cn.edu.xjtu.se.dao.DBDao;

/**
 * Created by Jingkai Tang on 8/29/16.
 */
public class XGAPI {
    public static final String TAG = XGAPI.class.getName();

    public static XGHttp xgHttp = XGHttp.getInstance();
    public static Gson gson = new Gson();

    public static String BASE_URL = "";

    public static class Result {
        private int code;
        private JsonElement ret;

        public Result(int code, JsonElement ret) {
            this.code = code;
            this.ret = ret;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public JsonElement getRet() {
            return ret;
        }

        public void setRet(JsonElement ret) {
            this.ret = ret;
        }
    }

    public static JsonElement processResult(Context context, String json) {
        Result result = gson.fromJson(json, Result.class);
        // TODO: a lot
        return result.getRet();
    }

    public static <T> T getReturn(Context context, String json, Class<T> classOfT) {
        JsonElement ret = processResult(context, json);
        return gson.fromJson(ret, classOfT);
    }

    public static String SIGNUP_URL = BASE_URL + "/accounts/signup";

    public static class SignupParameter {
        private String name;
        private String password;

        public SignupParameter(String name, String password) {
            this.password = password;
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public static class SignupReturn {
        private int code;
        private String msg;
        private String token;

        public SignupReturn(int code, String msg, String token) {
            this.code = code;
            this.msg = msg;
            this.token = token;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

    public static String LOGIN_URL = BASE_URL + "/accounts/login";

    public static class LoginParameter {
        private String name;
        private String password;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public LoginParameter(String name, String password) {
            this.name = name;
            this.password = password;
        }
    }

    public static class LoginReturn {
        private int code;
        private String msg;
        private String token;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public LoginReturn(int code, String msg, String token) {
            this.code = code;
            this.msg = msg;
            this.token = token;
        }
    }

    public static String LOGOUT_URL = BASE_URL + "/accounts/logout";

    public static class LogoutParameter {
        private String name;
        private String token;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public LogoutParameter(String name, String token) {
            this.name = name;
            this.token = token;
        }
    }

    public static class LogoutReturn {
        private int code;
        private String msg;

        public LogoutReturn(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

    public static String PASSWORD_CHANGE_URL = BASE_URL + "/accounts/password/change";

    public static class PasswordChangeParameter {
        private String name;
        private String token;
        @SerializedName("old_password")
        private String oldPassword;
        @SerializedName("new_password")
        private String newPassword;

        public PasswordChangeParameter(String name, String token, String oldPassword, String newPassword) {
            this.name = name;
            this.token = token;
            this.oldPassword = oldPassword;
            this.newPassword = newPassword;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getOldPassword() {
            return oldPassword;
        }

        public void setOldPassword(String oldPassword) {
            this.oldPassword = oldPassword;
        }

        public String getNewPassword() {
            return newPassword;
        }

        public void setNewPassword(String newPassword) {
            this.newPassword = newPassword;
        }
    }

    public static class PasswordChangeReturn {
        private int code;
        private String msg;

        public PasswordChangeReturn(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

    // TODO: 改密码
    private void passwordChange(String oldPassword, String newPassword) {
        String name = XGUserInfo.getName();
        String token = XGUserInfo.getToken();
        PasswordChangeParameter pcp = new PasswordChangeParameter(name, token, oldPassword, newPassword);
        String json = gson.toJson(pcp);
        xgHttp.post(PASSWORD_CHANGE_URL, json, new XGHttp.MOkCallBack() {
            @Override
            public void onSuccess(String str) {
                // PasswordChangeReturn pcr = getReturn(null, str, PasswordChangeReturn.class);
            }

            @Override
            public void onError() {

            }
        });
    }

    public static String AVATAR_CHANGE_URL = BASE_URL + "/accounts/avatar/change";

    public static class AvatarChangeParameter {
        private String name;
        private String token;
        private String avatar;

        public AvatarChangeParameter(String name, String token, String avatar) {
            this.name = name;
            this.token = token;
            this.avatar = avatar;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }

    public static class AvatarChangeReturn {
        private int code;
        private String msg;

        public AvatarChangeReturn(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

    // TODO: avatar change
    public static void avatarChange(String avatar) {
        String json = null;
        xgHttp.post(AVATAR_CHANGE_URL, json, new XGHttp.MOkCallBack() {
            @Override
            public void onSuccess(String str) {

            }

            @Override
            public void onError() {

            }
        });
    }

    public static String AVATAR_GET_URL = BASE_URL + "/accounts/avatar";
    public static void avatarGet() {
        if (!XGUserInfo.getStatus()) {
            Log.e(TAG, "未登录");
            return;
        }

        String name = XGUserInfo.getName();
        HttpUrl url = HttpUrl
                .parse(AVATAR_GET_URL)
                .newBuilder()
                .addQueryParameter("name", name)
                .build();
        xgHttp.get(url.toString(), new XGHttp.MOkCallBack() {
            @Override
            public void onSuccess(String str) {
                XGUserInfo.setAvatar(str);
            }

            @Override
            public void onError() {

            }
        });
    }

    public static class CheckCell {
        @SerializedName("created_at")
        private Date createdAt;
        @SerializedName("updated_at")
        private Date updatedAt;

        public CheckCell(Date createdAt, Date updatedAt) {
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
        }

        public Date getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(Date createdAt) {
            this.createdAt = createdAt;
        }

        public Date getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(Date updatedAt) {
            this.updatedAt = updatedAt;
        }
    }

    public static String DATA_CHECK_URL = BASE_URL + "/data/check";

    public static class DataCheckParameter {
        private String name;
        private String token;
        @SerializedName("check_list")
        private List<CheckCell> checkList;

        public DataCheckParameter(String name, String token, List<CheckCell> checkList) {
            this.name = name;
            this.token = token;
            this.checkList = checkList;
        }

        public static DataCheckParameter getDataCheckParameter() {
            String name = XGUserInfo.getName();
            String token = XGUserInfo.getToken();
            List<CheckCell> checkList = new ArrayList<>();
            List<Book> books = DBDao.findBooksAll();
            List<Comment> comments = DBDao.findCommentsAll();
            for (Book book: books) {
                checkList.add(new CheckCell(book.getCreated_at(), book.getUpdated_at()));
            }
            for (Comment comment: comments) {
                checkList.add(new CheckCell(comment.getCreated_at(), comment.getUpdated_at()));
            }
            return new DataCheckParameter(name, token, checkList);
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public List<CheckCell> getCheckList() {
            return checkList;
        }

        public void setCheckList(List<CheckCell> checkList) {
            this.checkList = checkList;
        }
    }

    public static class DataCheckReturn {
        @SerializedName("pull_list")
        private List<Date> pullList;
        @SerializedName("push_list")
        private List<Date> pushList;

        public DataCheckReturn(List<Date> pullList, List<Date> pushList) {
            this.pullList = pullList;
            this.pushList = pushList;
        }

        public List<Date> getPullList() {
            return pullList;
        }

        public void setPullList(List<Date> pullList) {
            this.pullList = pullList;
        }

        public List<Date> getPushList() {
            return pushList;
        }

        public void setPushList(List<Date> pushList) {
            this.pushList = pushList;
        }
    }

    public static void dataCheck(final Context context) {
        DataCheckParameter dcp = DataCheckParameter.getDataCheckParameter();
        String json = gson.toJson(dcp);
        xgHttp.post(DATA_CHECK_URL, json, new XGHttp.MOkCallBack() {
            @Override
            public void onSuccess(String str) {
                DataCheckReturn dcr = getReturn(context, str, DataCheckReturn.class);
                dataPush(context, dcr.getPushList());
                dataPull(context, dcr.getPullList());
            }

            @Override
            public void onError() {

            }
        });
    }

    public static class DataCell {
        @SerializedName("created_at")
        private Date createdAt;
        @SerializedName("updated_at")
        private Date updatedAt;
        private String content;

        public DataCell(Date createdAt, Date updatedAt, String content) {
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
            this.content = content;
        }

        public Date getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(Date createdAt) {
            this.createdAt = createdAt;
        }

        public Date getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(Date updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public static String DATA_PUSH_URL = BASE_URL + "/data/push";

    public static class DataPushParameter {
        private String name;
        private String token;
        private List<DataCell> list;

        public DataPushParameter(String name, String token, List<DataCell> list) {
            this.name = name;
            this.token = token;
            this.list = list;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public List<DataCell> getList() {
            return list;
        }

        public void setList(List<DataCell> list) {
            this.list = list;
        }
    }

    public static class DataPushReturn {
        private int count;

        public DataPushReturn(int count) {
            this.count = count;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }

    public static class DataContent {
        private String data;
        private List<String> attach;

        public DataContent(String data, List<String> attach) {
            this.data = data;
            this.attach = attach;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public List<String> getAttach() {
            return attach;
        }

        public void setAttach(List<String> attach) {
            this.attach = attach;
        }
    }

    public static String toDataContent(Book book) {
        String data = gson.toJson(book);
        String cover = ""; // book.image

        List<String> attach = new ArrayList<>();
        attach.add(cover);

        return gson.toJson(new DataContent(data, attach));
    }

    public static String toDataContent(Comment comment) {
        String data = gson.toJson(comment);
        return gson.toJson(new DataContent(data, new ArrayList<String>()));
    }

    public static void dataPush(Context context, List<Date> pushList) {
        String name = XGUserInfo.getName();
        String token = XGUserInfo.getToken();
        List<DataCell> list = new ArrayList<>();
        List<Book> books = DBDao.findBooksAll();
        List<Comment> comments = DBDao.findCommentsAll();
        for (Date date: pushList) {
            for (Book book: books) {
                if (date.compareTo(book.getCreated_at()) == 0) {
                    list.add(new DataCell(book.getCreated_at(), book.getUpdated_at(), toDataContent(book)));
                }
            }
            for (Comment comment: comments) {
                if (date.compareTo(comment.getCreated_at()) == 0) {
                    list.add(new DataCell(comment.getCreated_at(), comment.getUpdated_at(), toDataContent(comment)));
                }
            }
        }
        DataPushParameter dpp = new DataPushParameter(name, token, list);
        String json = gson.toJson(dpp);
        xgHttp.post(DATA_PUSH_URL, json, new XGHttp.MOkCallBack() {
            @Override
            public void onSuccess(String str) {

            }

            @Override
            public void onError() {

            }
        });
    }

    public static String DATA_PULL_URL = BASE_URL + "/data/pull";

    public static class DataPullParameter {
        private String name;
        private String token;
        private List<Date> list;

        public DataPullParameter(String name, String token, List<Date> list) {
            this.name = name;
            this.token = token;
            this.list = list;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public List<Date> getList() {
            return list;
        }

        public void setList(List<Date> list) {
            this.list = list;
        }
    }

    public static class DataPullReturn {
        private List<DataCell> list;

        public DataPullReturn(List<DataCell> list) {
            this.list = list;
        }

        public List<DataCell> getList() {
            return list;
        }

        public void setList(List<DataCell> list) {
            this.list = list;
        }
    }

    public static void dataPull(final Context context, List<Date> pullList) {
        String name = XGUserInfo.getName();
        String token = XGUserInfo.getToken();
        DataPullParameter dpp = new DataPullParameter(name, token, pullList);
        String json = gson.toJson(dpp);
        xgHttp.post(DATA_PULL_URL, json, new XGHttp.MOkCallBack() {
            @Override
            public void onSuccess(String str) {
                DataPullReturn dpr = getReturn(context, str, DataPullReturn.class);

            }

            @Override
            public void onError() {

            }
        });
    }

    public static String DATA_SYNC_URL = BASE_URL + "/data/sync";

    public static class DataSyncParameter {
        private String name;
        private String token;
        @SerializedName("check_list")
        private List<CheckCell> checkList;

        public DataSyncParameter(String name, String token, List<CheckCell> checkList) {
            this.name = name;
            this.token = token;
            this.checkList = checkList;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public List<CheckCell> getCheckList() {
            return checkList;
        }

        public void setCheckList(List<CheckCell> checkList) {
            this.checkList = checkList;
        }
    }

    public static class DataSyncReturn {
        @SerializedName("pull_list")
        private List<DataCell> pullList;
        @SerializedName("push_list")
        private List<Date> pushList;

        public DataSyncReturn(List<DataCell> pullList, List<Date> pushList) {
            this.pullList = pullList;
            this.pushList = pushList;
        }

        public List<DataCell> getPullList() {
            return pullList;
        }

        public void setPullList(List<DataCell> pullList) {
            this.pullList = pullList;
        }

        public List<Date> getPushList() {
            return pushList;
        }

        public void setPushList(List<Date> pushList) {
            this.pushList = pushList;
        }
    }

    public static void dataSync() {
        xgHttp.post(DATA_SYNC_URL, new XGHttp.MOkCallBack() {
            @Override
            public void onSuccess(String str) {

            }

            @Override
            public void onError() {

            }
        });
    }
}
