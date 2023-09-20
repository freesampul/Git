import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class commit{
    // tree t = new
    public commit (String parentSha1, String author, String summary)
    {

    }
    public commit (String author, String summary)
    {

    }
    public String getDate()
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return(dateFormat.format(date));
    }
    public void writeOut ()
    {

    }
    public static void main(String[] args) {
        commit c = new commit("a","b");
        System.out.println(c.getDate());
    }
}