package View;

import Models.FAddComand.AddAppCommand;
import Presenter.RecognitionListener;

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
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private Boolean flag = false;
    String file ;
    String commandName;
    String accent ;
    String  flags ;
    String errorMessage = "";
    RecognitionListener listener;
    @Override
    public void dispose() {
        flag=true;
        super.dispose();
    }

    public AddCommand( RecognitionListener listener) {
        AddCommand.super.setTitle("Viano Add Command");
        Image icon = new ImageIcon(getClass().getResource("/images/trey.png")).getImage();
        AddCommand.super.setIconImage(icon);
        setContentPane(panel);
        this.listener = listener;
        AddCommand.super.setSize(800, 500);
        AddCommand.super.setLocationRelativeTo(null);
        AddCommand.super.setResizable(false);
        AddCommand.super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        init();
        setVisible(true);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    int acc =  Integer.parseInt(accentTextField.getText());
                    if(!textField1.getText().isEmpty()&&!textField2.getText().isEmpty()
                            &&!accentTextField.getText().isEmpty()&&acc>=0){
                        file = textField1.getText();
                        commandName = textField2.getText().toLowerCase();
                        accent = accentTextField.getText();
                        flags = String.valueOf(checkBox1.isSelected());
                        flag=true;

                    } else {
                        error();
                    }
                }catch (Exception er){
                    error();
                }

            }
        });

    }

    @Override
    public void init() {
        Properties prop = listener.getProperties();
        checkBox1.setText("URL");
        checkBox1.setToolTipText(prop.getProperty("checkBox"));
        addButton.setText(prop.getProperty("addButton"));
        textField1.setToolTipText(prop.getProperty("textField1"));
        textField2.setToolTipText(prop.getProperty("textField2"));
        accentTextField.setToolTipText(prop.getProperty("accentTextField"));
        label1.setText(prop.getProperty("label1"));
        label2.setText(prop.getProperty("label2"));
        label3.setText(prop.getProperty("label3"));
        errorMessage = prop.getProperty("error");
    }

    private void error(){
            listener.errorMessage(errorMessage);
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
