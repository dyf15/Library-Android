package local.hal.st32.android.mylibrary45008;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by fei on 2016/07/07.
 */
public class DatabaseHelper extends SQLiteOpenHelper
{
    /**
     * データベースファイル名の定数フィールド
     */
    private static final String DATABASE_NAME = "library.db";

    /**
     * バージョン情報の定数フィールド
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * コンストラクタ
     *
     * @param context コンテキスト
     */
    public DatabaseHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        StringBuffer sb = new StringBuffer();

        sb.append("CREATE TABLE book (");
        sb.append("_id INTEGER PRIMARY KEY AUTOINCREMENT,");
        sb.append("isbn text,");
        sb.append("title text,");
        sb.append("author text,");
        sb.append("publisher text, ");
        sb.append("input_date text,");
        sb.append("memo text,");
        sb.append("impression text,");

        //RatingBarの評価情報
        sb.append("num_stars INTEGER,");
        //所持フラグ
        sb.append("have_flag INTEGER DEFAULT 0,");

        //本を削除されたかどうかを確認するため
        sb.append("flag INTEGER DEFAULT 0");
        sb.append(");");


        StringBuffer ce = new StringBuffer();

        ce.append("CREATE TABLE category (");
        ce.append("_id INTEGER PRIMARY KEY AUTOINCREMENT,");
        ce.append("category_name text,");
        ce.append("flag INTEGER DEFAULT 0");
        ce.append(");");


        StringBuffer be = new StringBuffer();

        be.append("CREATE TABLE belong(");
        be.append("_id INTEGER PRIMARY KEY AUTOINCREMENT,");
        be.append("book_id INTEGER NOT NULL,");
        be.append("category_id INTEGER NOT NULL,");
        be.append("flag INTEGER DEFAULT 0,");
        be.append("FOREIGN KEY (book_id)");
        be.append("REFERENCES book (_id),");
        be.append("FOREIGN KEY (category_id)");
        be.append("REFERENCES category (_id),");
        be.append("UNIQUE(book_id,category_id)");
        be.append(");");
        String sql = sb.toString();
        String ceSql = ce.toString();
        String beSql = be.toString();

        db.execSQL(ceSql);
        db.execSQL(beSql);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }

}
