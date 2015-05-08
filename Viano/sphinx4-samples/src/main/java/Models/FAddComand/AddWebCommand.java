package Models.FAddComand;

/**
 * Created by dimamj on 07.05.2015.
 */
public class AddWebCommand extends AbstractAddCommand {

    private static final AddWebCommand instance = new AddWebCommand();

    private AddWebCommand(){
    }
    protected static AddWebCommand getInstance(){
        return instance;
    }

    @Override
    public void start(String[] array,String lang) {

    }
}
