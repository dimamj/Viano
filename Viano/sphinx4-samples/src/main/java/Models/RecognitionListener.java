package Models;

/**
 * Created by dimamj on 25.04.2015.
 */
public interface RecognitionListener {

    void wordRecognized(String word);

    void setImage(String key);

    String goModel(String nextModel);

}
