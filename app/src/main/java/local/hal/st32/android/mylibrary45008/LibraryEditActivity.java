package local.hal.st32.android.mylibrary45008;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;

/**
 * Created by fei on 2016/07/07.
 */
public class LibraryEditActivity extends AppCompatActivity
{

    /**
     * データベースの主キー
     */
    private int _idNo = 0;

    /**
     * 本を新規登録
     */
    private int _mode = LibraryListActivity.MODE_INSERT;

    /**
     * 本の情報を格納するクラス
     */
    Book book;

    RatingBar _ratingBar;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //Contentviewを設定
        setContentView(R.layout.activity_library_edit);

        //戻るAction bar
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //データ受ける
        Intent intent = getIntent();

        //getExtrasでbundleで取得
        Bundle bundle = intent.getExtras();

        //本の情報を格納するクラスに格納する
        book = bundle.getParcelable("book");

        //編集または新規登録状態取得
        _mode = intent.getIntExtra("mode", LibraryListActivity.MODE_INSERT);

        //新規登録の場合
        if (_mode == LibraryListActivity.MODE_INSERT)
        {

            /**
             * 本の基本情報を取得し、画面表示する
             */
            //本のISBNコード
            TextView tvIsbn = (TextView) findViewById(R.id.isbn);
            tvIsbn.setText(book.getIsbn());

            //本のタイトル
            TextView tvTitle = (TextView) findViewById(R.id.title);
            tvTitle.setText(book.getTitle());

            //本の著者
            TextView tvAuthor = (TextView) findViewById(R.id.author);
            tvAuthor.setText(book.getAuthor());

            //出版社
            TextView tvPublisher = (TextView) findViewById(R.id.publisher);
            tvPublisher.setText(book.getPublisher());

            //カテゴリー設定情報

            //DataAccess.insert(LibraryEditActivity.this, book.getIsbn(),book.getTitle(),book.getAuthor(),book.getPublisher());
        }
        //編集の場合
        else
        {

            //編集する本のIDを取得する
            _idNo = intent.getIntExtra("idNo", 0);

            //データベースから本の情報を取得クラスに格納
            Book bookDate = DataAccess.findByPK(LibraryEditActivity.this, _idNo);
            System.out.println("id" + _idNo);

            //データベースから取得した情報を画面に表示する
            TextView tvTitle = (TextView) findViewById(R.id.title);
            tvTitle.setText(bookDate.getTitle());

            //本の著者
            TextView tvAuthor = (TextView) findViewById(R.id.author);
            tvAuthor.setText(bookDate.getAuthor());

            //出版社
            TextView tvPublisher = (TextView) findViewById(R.id.publisher);
            tvPublisher.setText(bookDate.getPublisher());

            //本のISBNコード
            TextView tvIsbn = (TextView) findViewById(R.id.isbn);
            tvIsbn.setText(bookDate.getIsbn());

            //カテゴリー所属する情報

//        _ratingBar = (RatingBar) findViewById(R.id.ratingBar);
//
//        _ratingBar.setOnRatingBarChangeListener(
//                new RatingBar.OnRatingBarChangeListener() {
//                    public void onRatingChanged(
//                            RatingBar ratingBar,
//                            float rating,
//                            boolean fromUser) {
//                        // RatingBar のレイティング数が変わったときの動作
//                        float ratingvalue = ratingBar.getRating();
//                        //_textView.setText(Float.toString(ratingvalue));
//                    }
//                }
//        );


        }

//
//        _ratingBar = (RatingBar) findViewById(R.id.ratingBar);
//
//        _ratingBar.setOnRatingBarChangeListener(
//                new RatingBar.OnRatingBarChangeListener() {
//                    public void onRatingChanged(
//                            RatingBar ratingBar,
//                            float rating,
//                            boolean fromUser) {
//                        // RatingBar のレイティング数が変わったときの動作
//                        float ratingvalue = ratingBar.getRating();
//                        //_textView.setText(Float.toString(ratingvalue));
//                    }
//                }
//        );


        //

    }


    /**
     *  ActionBarのitem表示設定
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_edit_library, menu);

        //新規登録の場合は
        if (_mode == LibraryListActivity.MODE_INSERT)
        {
            //削除ボタンを表示させないようにfalse設定
            menu.getItem(0).setVisible(false);
            menu.getItem(1).setTitle("登録");
        }
        return true;
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

        //本のタイトル
        TextView tvTitle = (TextView) findViewById(R.id.title);
        String title = tvTitle.getText().toString();
        Intent intent = new Intent(LibraryEditActivity.this, LibraryAddCategory.class);
        switch (itemId) {

            //ActionBarの戻る矢印の設定
            case android.R.id.home:
                finish();
                return true;

            //ActionBarのsave item
            case R.id.btnUpdate:

                //新規登録する場合
                if (_mode == LibraryListActivity.MODE_INSERT)
                {
                    //データベースに登録
                    DataAccess.insert(LibraryEditActivity.this, book.getIsbn(), book.getTitle(), book.getAuthor(), book.getPublisher());
                    intent.putExtra("mode", _mode);
                }
                else
                {

                    /**
                     * 情報再び取得して本の情報を更新する
                     */

                    //本の著者
                    TextView tvAuthor = (TextView) findViewById(R.id.author);
                    String author = tvAuthor.getText().toString();

                    //出版社
                    TextView tvPublisher = (TextView) findViewById(R.id.publisher);
                    String publisher = tvPublisher.getText().toString();

                    //データベースの情報を更新
                    DataAccess.update(LibraryEditActivity.this, _idNo, title, author, publisher);

                }
                finish();

                break;

            //ActionBarの本を削除するitem
            case R.id.btnDelete:

                AlertDialog.Builder builder = new AlertDialog.Builder(LibraryEditActivity.this);
                builder.setTitle("本：" + title);
                builder.setMessage("削除してもよろしいですか？");
                builder.setPositiveButton("Cancel", new DialogButtonClickListener());
                builder.setNegativeButton("OK", new DialogButtonClickListener());
                AlertDialog dialog = builder.create();
                dialog.show();
                break;

            //ActionBarのカテゴリ追加するitem
            case R.id.btnAddCategory:

                intent.putExtra("idNo", _idNo);
                System.out.println("bookId" + _idNo);
                startActivity(intent);
        }


        return super.onOptionsItemSelected(item);
    }

    /**
     *
     */
    public class DialogButtonClickListener implements DialogInterface.OnClickListener
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    DataAccess.delete(LibraryEditActivity.this, _idNo);
                    finish();
                    break;
            }
        }
    }
}
