package local.hal.st32.android.mylibrary45008;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

/**
 * Created by fei on 2016/07/07.
 */
public class DataAccess
{


    /**
     * 本のテーブルに関係する
     */
    /**
     * 本全データ検索メソッド
     * 削除された場合のWHERE文また
     * @param context
     * @return 検索結果のCursorオブジェクト
     */

    /**
     *
     * sql select * from book where flag = 0;
     */
    public static Cursor findAll(Context context)
    {
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "SELECT _id,isbn,title FROM book";
        //sql = "SELECT _id, name, deadline, note FROM tasks order by done asc ,_id desc";

        Cursor cursor = db.rawQuery(sql,null);
        return cursor;
    }

    /**
     * 本のテーブル主キーによる検索
     * @param context
     * @param id
     * @return
     * 主キーに対応するデータを格納したBookオブジェクト。対応するデータが存在しない場合はnull.
     */
    public static Book findByPK(Context context, int id)
    {
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = null;
        Book result = null;
        String sql = "SELECT * FROM book WHERE _id =" + id;
        try
        {
            cursor = db.rawQuery(sql, null);
            if (cursor != null && cursor.moveToFirst())
            {
                int idxTitle = cursor.getColumnIndex("title");
                int idxAuthor = cursor.getColumnIndex("author");
                int idxPublisher = cursor.getColumnIndex("publisher");
                int idxIsbn = cursor.getColumnIndex("isbn");

                String title = cursor.getString(idxTitle);
                String author = cursor.getString(idxAuthor);
                String publisher = cursor.getString(idxPublisher);
                String isbn = cursor.getString(idxIsbn);

                System.out.println("bookResult1"+ title);
                System.out.println("bookResult"+ author);
                System.out.println("bookResult1"+ isbn);

                result = new Book(isbn);
                result.setIsbn(isbn);
                result.setTitle(title);
                result.setAuthor(author);
                result.setPublisher(publisher);


            }

        }
        catch(Exception ex)
        {
            Log.e("ERROR", ex.toString());
        }
        finally
        {
            db.close();
        }
        return result;
    }

    /**
     * 本の情報を更新する場合
     * @param context
     * @param id
     * @param title
     * @param author
     * @param publisher
     */
    public static void update(Context context,int id,String title,String author,String publisher)
    {
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        String sql = "UPDATE book SET title = ?, author = ?, publisher = ? WHERE _id = ?";
        SQLiteStatement stmt = db.compileStatement(sql);
        stmt.bindString(1, title);
        stmt.bindString(2, author);
        stmt.bindString(3, publisher);
        stmt.bindLong(4, id);


        db.beginTransaction();
        try
        {

            stmt.executeInsert();
            db.setTransactionSuccessful();
        }
        catch(Exception ex)
        {
            Log.e("ERROR", ex.toString());
        }
        finally
        {
            db.endTransaction();
            db.close();
        }
    }

    /**
     * 本の情報を新規登録する
     * @param context
     * @param isbn
     * @param title
     * @param author
     * @param publisher
     */
    public static void insert(Context context,String isbn,String title,String author ,String publisher)
    {
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();



        String sql = "INSERT INTO book (isbn,title,author,publisher) VALUES (?,?,?,?)";
        SQLiteStatement stmt = db.compileStatement(sql);
        stmt.bindString(1, isbn);
        stmt.bindString(2, title);
        stmt.bindString(3, author);
        stmt.bindString(4, publisher);


        db.beginTransaction();
        try
        {
            stmt.executeInsert();
            db.setTransactionSuccessful();
        } catch (Exception ex)
        {
            Log.e("ERROR", ex.toString());
        }
        finally
        {
            db.endTransaction();
            db.close();
        }
    }

    /**
     *本情報を削除するメソッド
     *削除する場合フラグを1にする
     * @param context コンテキスト
     * @param id 主キー値
     */
    public static void delete(Context context, int id)
    {
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        String sql = "DELETE FROM book WHERE _id = ?";
        SQLiteStatement stmt = db.compileStatement(sql);
        stmt.bindLong(1, id);

        db.beginTransaction();
        try
        {
            stmt.executeInsert();
            db.setTransactionSuccessful();
        }
        catch (Exception ex)
        {
            Log.e("ERROR",ex.toString());
        }
        finally
        {
            db.endTransaction();
            db.close();
        }
    }




    //カテゴリー情報

    /**
     * カテゴリーの全データ
     * @param context
     * @return
     *
     */
    /**
     *select * from category where flag = 0;
     */
    public static Cursor findAllCategory(Context context)
    {
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "SELECT _id,category_name,flag FROM category";
        //sql = "SELECT _id, name, deadline, note FROM tasks order by done asc ,_id desc";

        Cursor cursor = db.rawQuery(sql,null);
        return cursor;
    }

    /**
     * カテゴリを新規登録
     * @param context
     * @param categoryName
     */
    public static void insertCategory(Context context,String categoryName)
    {
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        String sql = "INSERT INTO category (category_name) VALUES (?)";
        System.out.println("insert"+sql);
        SQLiteStatement stmt = db.compileStatement(sql);
        stmt.bindString(1, categoryName);

        db.beginTransaction();
        try
        {
            stmt.executeInsert();
            db.setTransactionSuccessful();
        } catch (Exception ex)
        {
            Log.e("ERROR", ex.toString());
        }
        finally
        {
            db.endTransaction();
            db.close();
        }
    }

    /**
     * カテゴリを消す場合、フラグを1に
     * update category set flag = 1 where _id = 1;
     */

    /**
     * カテゴリ所属するテーブル
     */
    /**
     *
     * @param context
     * @param book_id
     * @param category_id
     */
    public static void insertBelong(Context context,String book_id,String category_id)
    {
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();



        String sql = "INSERT INTO belong (book_id,category_id) VALUES (?,?)";
        SQLiteStatement stmt = db.compileStatement(sql);
        stmt.bindString(1, book_id);
        stmt.bindString(2, category_id);
        db.beginTransaction();
        try
        {
            stmt.executeInsert();
            db.setTransactionSuccessful();
        } catch (Exception ex)
        {
            Log.e("ERROR", ex.toString());
        }
        finally
        {
            db.endTransaction();
            db.close();
        }
    }

    /**
     * カテゴリ所属テーブルのフラグを変える前
     * すでに所属されているかどうか調べる
     *select * from belong
      where book_id = 1 and category_id = 1;
     *
     */


    /**
     * カテゴリ所属テーブルのフラグを変える
     * 所属テーブルを削除する場合
     *  update belong set flag = 1 where _id = 1;
     */

    /**
     * カテゴリが消され場合は、所属されでも表示しないように
     * select * from belong be
     inner join book b on be.book_id = b._id
     inner join category c on b.category_id = c._id
      where c.flag = 0;
     */

    /**
     * 編集画面のとき
     * 使えるカテゴリーを表示
     * されに所属したカテゴリの
     * select * from category where flag = 0;
     */


}
