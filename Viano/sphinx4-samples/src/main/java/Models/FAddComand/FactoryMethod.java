package Models.FAddComand;

/**
 * Created by dimamj on 07.05.2015.
 */
public class FactoryMethod {

    /**
     * Метод возвращает ссылку на обьект,
     * для добавления команды
     * @param flag истина если добавление ссылки
     *             ложь, если добавление приложения
     * @return ссылку на обьект
     */
    public AbstractAddCommand getInstance(Boolean flag)
    {
        return flag ? AddWebCommand.getInstance():
                AddAppCommand.getInstance();
    }

}
