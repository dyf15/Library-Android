package local.hal.st32.android.mylibrary45008;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by fei on 2016/07/06.
 */
public class MyAsyncTask extends AsyncTask<String, Void, Book> {

    private CallBackTask callbacktask;


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected Book doInBackground(String... params) {

        String isbnCode = params[0];

        Book result = new Book(isbnCode);
        result.sync();

        return result;
    }


    @Override
    protected void onPostExecute(Book result) {
        super.onPostExecute(result);
        callbacktask.CallBack(result);
    }


    public void setOnCallBack(CallBackTask _cbj) {
        callbacktask = _cbj;
    }


    /**
     * コールバック用のstaticなclass
     */
    public static class CallBackTask {
        public void CallBack(Book result) {
        }
    }

}
