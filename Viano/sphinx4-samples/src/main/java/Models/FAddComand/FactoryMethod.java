package Models.FAddComand;

/**
 * Created by dimamj on 07.05.2015.
 */
public class FactoryMethod {

    public AbstractAddCommand getInstance(Boolean flag)
    {
        return flag ? AddWebCommand.getInstance():
                AddAppCommand.getInstance();
    }

}
