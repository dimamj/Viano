package Presenter;

import java.util.List;

/**
 * Created by dimamj on 25.04.2015.
 */
public interface RecognitionListener {

    void wordRecognized(String word);

    void setImage(String key);

    String goModel(String nextModel);

    public void setSpeedCursor(int speed,Boolean flag);

    public String[] getText(String view);

    public void setText(String view,List<String> list);

    public void setProgressVisible(String view);

    public void disposeElements(String view);

    void disposeGui(String view);

    public Boolean getEdit(String view);

    public void setLabel(String text,String view);

    public void addCommand(String[] array);

    public void createGui(String view,String lang);

    public void errorMessage(String message);

}
