package local.hal.st32.android.mylibrary45008;

import android.os.Parcel;
import android.os.Parcelable;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by fei on 2016/07/06.
 */
public class Book implements Parcelable
{
    //本の情報
    private String isbn = "";
    private String title = "";
    private String author = "";
    private String publisher = "";
    private String date = "";

    //本の情報が有効かどうか
    private boolean _valid = false;




    Book(String isbn)
    {
        this.isbn = isbn;
    }

    private Book(Parcel value)
    {

        isbn = value.readString();
        title = value.readString();
        author = value.readString();
        publisher = value.readString();
    }


    public boolean isValid ()
    {
        return _valid;
    }

    public void sync()
    {
        try
        {
            // 書籍情報を取得する(XML)
            URL url = new URL("http://iss.ndl.go.jp/api/opensearch?isbn=" + isbn);
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            http.setRequestMethod("GET");
            http.connect();

            // XMLをパースし、書籍項目を取り出す
            parseXml(new BufferedInputStream(http.getInputStream()));

            //本の情報を有効する
            _valid = true;
        }
        catch (Exception e)
        {

        }
    }

    /**
     * xmlパースして、各プロパティに値をsetしている
     * @param stream
     * @throws XmlPullParserException
     * @throws IOException
     */
    private void parseXml(BufferedInputStream stream) throws XmlPullParserException,IOException
    {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();
        xpp.setInput(new InputStreamReader(stream));



        String lastElement = "";
        int eventType = xpp.getEventType();

        while (eventType != XmlPullParser.END_DOCUMENT)
        {
            if (eventType == XmlPullParser.START_TAG)
            {
                lastElement = xpp.getName();
            }
            else if (eventType == XmlPullParser.TEXT)
            {
                switch (lastElement) {
                    case "title":
                        this.title = xpp.getText();
                        break;
                    case "creator":
                        this.author = xpp.getText();
                        break;
                    case "publisher":
                        this.publisher = xpp.getText();
                        break;
                    case "pubDate":
                        this.date = xpp.getText();
                        break;
                }

                lastElement = "";
            }
            eventType = xpp.next();
        }

    }

    /**
     * Parcelableのインタフェースを実装
     */
    @Override
    public void writeToParcel(Parcel out, int flags)
    {
        out.writeString(isbn);
        out.writeString(title);
        out.writeString(author);
        out.writeString(publisher);

    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    public static final Parcelable.Creator CREATOR
            = new Parcelable.Creator() {
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    //デバッグ用
    public String toString()
    {
        return title + "," + author + "," + publisher + "," + date;
    }


    //値をget、setする
    public String getIsbn()
    {
        return isbn;
    }

    public void setIsbn(String isbn)
    {
        this.isbn = isbn;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
