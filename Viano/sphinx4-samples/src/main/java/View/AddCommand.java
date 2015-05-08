package View;

import Models.FAddComand.AddAppCommand;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * Created by dimamj on 08.05.2015.
 */
public class AddCommand extends JFrame implements ViewInterface {
    private JPanel panel;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField accentTextField;
    private JButton addButton;
    private JCheckBox checkBox1;
    private JLabel img;
    private Boolean flag = false;
    private String language;
    String file ;
    String commandName;
    String accent ;
    String  flags ;

    @Override
    public void dispose() {
        flag=true;
        super.dispose();
    }

    public AddCommand(final String lang) {
        AddCommand.super.setTitle("Viano Add Command");
        Image icon = new ImageIcon(getClass().getResource("/images/trey.png")).getImage();
        AddCommand.super.setIconImage(icon);
        setContentPane(panel);
        AddCommand.super.setSize(800, 500);
        AddCommand.super.setLocationRelativeTo(null);
        AddCommand.super.setResizable(false);
        AddCommand.super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initText(lang);
        language = lang;
        setVisible(true);
        System.out.println(isDisplayable());
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                   Integer.parseInt(accentTextField.getText());
                    if(!textField1.getText().isEmpty()&&!textField2.getText().isEmpty()
                            &&!accentTextField.getText().isEmpty()){
                        file = textField1.getText();
                        commandName = textField2.getText();
                        accent = accentTextField.getText();
                        flags = String.valueOf(checkBox1.isSelected());
                        flag=true;
                        dispose();

                    } else {
                        error();
                    }
                }catch (Exception er){
                    error();
                }

            }
        });

    }

    private void error(){
        if(language.equals("russian")){
            CtrlGui.setErrorTM("Введите корректные данные!");
        } else if(language.equals("english")){
            CtrlGui.setErrorTM("Enter the correct data!");
        }
    }

    private void initText(String lang)
    {
        if(lang.equals("russian")){
            checkBox1.setText("URL");
            checkBox1.setToolTipText("Нажмите, если хотите добавить команду на открытие веб=ссылки.");
            addButton.setText("Добавить");
            textField1.setToolTipText("Введите название ссылки или приложения");
            textField2.setToolTipText("Введите название команды");
            accentTextField.setToolTipText("Введите номер буквы на которую падает ударение");
        }else if(lang.equals("english")){
            checkBox1.setText("URL");
            checkBox1.setToolTipText("Click if you want to add web link");
            addButton.setText("Add");
            textField1.setToolTipText("Enter apps or web link name");
            textField2.setToolTipText("Enter command name");
            accentTextField.setToolTipText("Enter the number of letter on  which the falls accent ");

        }

    }
    @Override
    public void setText(List<String> list) {

    }

    @Override
    public Boolean getEdit() {
        return flag;
    }

    @Override
    public void setProgressVisible() {

    }

    @Override
    public void disposeElements() {

    }

    @Override
    public String[] getText() {

        return new String[]{file,commandName,accent,flags};
    }

    @Override
    public void setWords(String words) {

    }

    @Override
    public void setImage(String key) {

    }

    @Override
    public void setErrorTreyMessage(String message) {

    }

    @Override
    public void setLabel(String text) {

    }
}
