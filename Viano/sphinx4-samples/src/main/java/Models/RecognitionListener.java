package Models;

import java.util.List;

/**
 * Created by dimamj on 25.04.2015.
 */
public interface RecognitionListener {

    void wordRecognized(String word);

    void setImage(String key);

    String goModel(String nextModel);

    public void setSpeedCursor(int speed);

    public String getText(String view);

    public void setText(String view,List<String> list);

    public void setProgressVisible();

    public void disposeElements(String view);

    void disposeGui(String view);

    public void createSettingsGui();

    public Boolean getEdit(String view);

}
