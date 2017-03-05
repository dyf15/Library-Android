package local.hal.st32.android.mylibrary45008;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by fei on 2016/07/09.
 */
public class LibraryAddCategory extends AppCompatActivity
{

    /**
     * ListView
     */
    private ListView list;

    /**
     * 編集の時本のID
     */
    private String bookId;

    /**
     * カテゴリー情報格納配列
     */
    List<String> _categoryList;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_add_category);

        //ActionBarに表示するタイトル
        getSupportActionBar().setTitle("カテゴリ");

        Intent intent = getIntent();
        /**
         * 本の情報を更新するモード時
         */

        //本のIDを取得

        int _bookId = intent.getIntExtra("idNo", 0);
        bookId = String.valueOf(_bookId);
        System.out.println("book_ud" + bookId);


        list = (ListView) this.findViewById(R.id.category_list);

        refreshList();


    }

    @Override
    public void onResume()
    {
        super.onResume();

        refreshList();

    }


    public void refreshList()
    {

        Cursor cursor = DataAccess.findAllCategory(LibraryAddCategory.this);
        String[] from = {"category_name", "_id"};
        int[] to = {R.id.categoryName, R.id.checkState};


        SimpleCursorAdapter adapter = new SimpleCursorAdapter(LibraryAddCategory.this, R.layout.category_customize, cursor, from, to, 0);

        adapter.setViewBinder(new CustomViewBinder());
        //list.setAdapter(adapter);
        System.out.println(adapter.getCount());

        System.out.println(adapter);

        list.setAdapter(adapter);

    }

    /**
     * リストをクリックする処理
     */
//    public class ListItemClickListener implements AdapterView.OnItemClickListener
//    {
//
//        @Override
//        public void onItemClick (AdapterView<?> parent , View view, int position, long id)
//        {
//            //ListView list = (ListView) this.findViewById(R.id.android_list);
//
//            //super.onListItemClick(listView, view, position, id);
//            //ListView list = (ListView) findViewById(R.id.android_list);
//            list = (ListView) findViewById(R.id.category_list);
//
//            Cursor item = (Cursor) list.getItemAtPosition(position);
//            //Cursor test = (Cursor) list.getAdapter();
//
//            int idxId = item.getColumnIndex("_id");
//            int idNo = item.getInt(idxId);
//
////            Intent intent = new Intent(LibraryAddCategory.this, LibraryEditActivity.class);
////            intent.putExtra("idNo", idNo);
////            intent.putExtra("idNo", (int) id);
////            startActivity(intent);
//
//        }
//    }
    public void onCategoryAddClick(View view)
    {
        EditText category = (EditText) findViewById(R.id.categoryAdd);
        String categoryName = category.getText().toString();
        System.out.println("categoryNAme" + categoryName);
        DataAccess.insertCategory(LibraryAddCategory.this, categoryName);

        onResume();
    }


    private class CustomViewBinder implements SimpleCursorAdapter.ViewBinder
    {
        @Override
        public boolean setViewValue(View view, Cursor cursor, int columnIndex)
        {


            int viewId = view.getId();

            int idIdx = cursor.getColumnIndex("_id");
            long id = cursor.getLong(idIdx);

            System.out.println("setView" + id);

            //id = (int)id;

            switch (viewId) {

                default:
                    System.out.println("通ってる" + view.getClass());
                    break;

                case R.id.checkState:
                    CheckBox checkState = (CheckBox) view;

                    //0711追加
                    int checkIdx = cursor.getColumnIndex("flag");
                    boolean checked = false;
                    if (checkIdx == 1)
                    {
                        checked = true;
                    }
                    checkState.setTag(id);
                    System.out.println("check" + id);
//                  checkState.setOnClickListener(new OnCheckBoxClickListener());

                    return true;

            }
            return false;

        }

    }

//    //チェックボックスクリックされた処理
//    private class OnCheckBoxClickListener implements View.OnClickListener
//    {
//        @Override
//        public void onClick(View view)
//        {
//            CheckBox checkState = (CheckBox) view;
//            boolean isChecked = checkState.isChecked();
//            long id = (long) checkState.getTag();
//            String idStr = String.valueOf(id);
//
//            ArrayList<String> arrayList = new ArrayList<>();
//            if (isChecked)
//            {
//                arrayList.add(idStr);
//
//            }
//            System.out.println("array"+arrayList);
//        }
//    }


    public void onAddClick(View view)
    {
        for (int i = 0; i < list.getCount(); i++) {
            _categoryList = new ArrayList<String>();
            View row = list.getChildAt(i);
            if (row != null) {

                TextView category = (TextView) row.findViewById(R.id.categoryName);

                CheckBox check = (CheckBox) row.findViewById(R.id.checkState);

                if (check.isChecked()) {

                    System.out.println("check.book" + bookId);
                    System.out.println("check.get" + check.getTag().toString());
                    //DataAccess.insertBelong(LibraryAddCategory.this,bookId,check.getTag().toString());

                    _categoryList.add(bookId);
                    _categoryList.add(check.getTag().toString());

//
//                    Intent intent = new Intent(LibraryAddCategory.this, LibraryEditActivity.class);
//                    intent.putCharSequenceArrayListExtra("_categoryList",_categoryList);
//                    startActivity(intent);

                    //finish();
                }

                System.out.println("ID : " + check.getTag() + " /" + "CategoryName[" + i + "] : " + category.getText().toString() + " / Checked : " + (check.isChecked() ? "YES" : "NO"));
            } else {
                System.out.println("++++++++++ ROW = NULL!");
            }
        }
    }

}
