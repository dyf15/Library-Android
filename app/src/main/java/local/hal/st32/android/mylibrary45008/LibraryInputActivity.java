package local.hal.st32.android.mylibrary45008;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

/**
 * Created by fei on 2016/07/07.
 */
public class LibraryInputActivity extends AppCompatActivity
{
    private String barCode = "";

    private int _mode = LibraryListActivity.MODE_INSERT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_input);

        //戻る
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }


    /**
     * 戻る
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int itemId = item.getItemId();

        switch (itemId)
        {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * バーコードスキャン検索を選択された時
     * @param view
     */
    public void scanBarcode(View view) {
        new IntentIntegrator(this).initiateScan();
    }

    /**
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Log.d("MainActivity", "Cancelled scan");
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Log.d("MainActivity", "Scanned");
                System.out.println("バーコード" + result.getContents());
                barCode = result.getContents();
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();

                /**
                 * ISBNコードから本の情報取得
                 */

                MyAsyncTask atClass = new MyAsyncTask();
                atClass.setOnCallBack(new MyAsyncTask.CallBackTask()
                {

                    @Override
                    public void CallBack(Book result) {
                        super.CallBack(result);
                        // ※１
                        // resultにはdoInBackgroundの返り値が入ります。
                        // ここからAsyncTask処理後の処理を記述します。

                        Log.i("AsyncTaskCallback", "非同期処理が終了しました。");
                        System.out.println(result.toString());

                        //本情報を取得できた時
                        if (result.isValid())
                        {
                            System.out.println("++++++++++++++++");
                            //本の情報取得後画面遷移
                            Intent intent = new Intent(LibraryInputActivity.this, LibraryEditActivity.class);
                            intent.putExtra("book",result);
                            startActivity(intent);
                        }

                    }

                });

                // AsyncTaskの実行
                atClass.execute(barCode);



            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }

    }



}
