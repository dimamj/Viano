package View;

import java.util.List;

/**
 * Created by dimamj on 26.04.2015.
 */
public interface ViewInterface {

    public void setText(List<String> list);
    public Boolean getEdit();
    public void setProgressVisible();
    public void disposeElements();
    public String[] getText();
    public  void setWords(String words);
    public void setImage(String key);
    void dispose();
    void setErrorTreyMessage(String message);
    void setLabel(String text);
    boolean isDisplayable();


}
