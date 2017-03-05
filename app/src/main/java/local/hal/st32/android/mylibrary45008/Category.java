package local.hal.st32.android.mylibrary45008;

/**
 * Created by fei on 2016/07/10.
 */
public class Category
{
    private String categoryId = "";
    private String categoryName = "";
    private int flag = 0;
    private boolean _valid = false;

    public boolean isValid ()
    {
        return _valid;
    }

    public String getCategoryId()
    {
        return categoryId;
    }

    public void setCategoryId(String categoryId)
    {
        this.categoryId = categoryId;
    }

    public String getCategoryName()
    {
        return categoryName;
    }

    public void setCategoryName(String categoryName)
    {
        this.categoryName = categoryName;
    }

    public int getFlag()
    {
        return flag;
    }

    public void setFlag(int flag)
    {
        this.flag = flag;
    }
}
