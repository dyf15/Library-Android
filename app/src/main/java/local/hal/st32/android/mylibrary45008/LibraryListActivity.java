package local.hal.st32.android.mylibrary45008;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


public class LibraryListActivity extends AppCompatActivity
{


    /**
     * listView
     */
    private ListView list;

    /**
     * 新規登録モードを表す定数フィールド
     */
    static final int MODE_INSERT = 1;

    /**
     * 更新モードを表す呈す
     */
    static final int MODE_EDIT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_list);

        list = (ListView) this.findViewById(R.id.android_list);

        list.setOnItemClickListener(new ListItemClickListener());
    }


    @Override
    public void onResume()
    {
        super.onResume();

        Cursor cursor = DataAccess.findAll(LibraryListActivity.this);
        String[] from = {"_id", "title"};
        int[] to = {android.R.id.text1, android.R.id.text2};


        SimpleCursorAdapter adapter = new SimpleCursorAdapter(LibraryListActivity.this, android.R.layout.simple_list_item_2, cursor, from, to, 0);

        System.out.println(adapter.getCount());

        System.out.println(adapter);

        list.setAdapter(adapter);


    }

    public void onAddButtonClick(View view)
    {
        Intent intent = new Intent(LibraryListActivity.this, LibraryInputActivity.class);

        startActivity(intent);
    }


    /**
     * リストをクリックする処理
     */
    public class ListItemClickListener implements AdapterView.OnItemClickListener
    {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            list = (ListView) findViewById(R.id.android_list);
            Cursor item = (Cursor) list.getItemAtPosition(position);

            int idxId = item.getColumnIndex("_id");
            int idNo = item.getInt(idxId);

            Intent intent = new Intent(LibraryListActivity.this, LibraryEditActivity.class);
            intent.putExtra("mode", MODE_EDIT);
            intent.putExtra("idNo", idNo);
            intent.putExtra("idNo", (int) id);
            startActivity(intent);

        }
    }

}
