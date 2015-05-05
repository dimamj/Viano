package View;

import Presenter.VPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


/**
 * Created by dimamj on 16.01.2015.
 */
public class CtrlGui extends JFrame implements ViewInterface {
    private JPanel panel;
    private JTextArea TextArea;
    private JTextField textField1;
    private static String word = "";
    private JTextArea подсказкаДляУлучшенияРаспознаванияTextArea;
    private JLabel comp;
    private JLabel mouse;
    private JLabel keyboard;
    private JLabel internet;
    private JLabel games;
    private JLabel master;

    private Icon iconcompActive;
    private Icon iconcomp;
    private Icon mouseIcon;
    private Icon mouseIconActive;
    private Icon keyboardIcon;
    private Icon keyboardIconActive;
    private Icon internetIcon;
    private Icon internetIconActive;
    private Icon gamesIcon;
    private Icon gamesIconActive;
    private Icon masterIcon;
    private Icon masterIconActive;

    private static TrayIcon trayIcon;
    String s = "";
    Settings settings;

    public CtrlGui(Boolean flag)
    {   CtrlGui.super.setTitle("Viano");
        Image icon = new ImageIcon(getClass().getResource("/images/trey.png")).getImage();
        CtrlGui.super.setIconImage(icon);
        Dimension sSize = Toolkit.getDefaultToolkit ().getScreenSize ();
        int vert = sSize.height;
        int hor  = sSize.width;
        setContentPane(panel);
        CtrlGui.super.setSize(800, 500);
        CtrlGui.super.setLocationRelativeTo(null);
        CtrlGui.super.setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        TextArea.setLineWrap(true);
        TextArea.setWrapStyleWord(true);
        setTrayIcon();

        iconcomp = new ImageIcon(getClass().getResource("/images/Computer.png"));
        iconcompActive = new ImageIcon(getClass().getResource("/images/ComputerActive.png"));

        internetIcon = new ImageIcon(getClass().getResource("/images/Internet-Explorer.png"));
        internetIconActive = new ImageIcon(getClass().getResource("/images/Internet-Explorer-Active.png"));

        gamesIcon = new ImageIcon(getClass().getResource("/images/games.png"));
        gamesIconActive = new ImageIcon(getClass().getResource("/images/gamesActive.png"));

        mouseIcon = new ImageIcon(getClass().getResource("/images/mouse-icon.png"));
        mouseIconActive = new ImageIcon(getClass().getResource("/images/mouse-icon-active.png"));

        keyboardIcon =  new ImageIcon(getClass().getResource("/images/keyboard-icon.png"));
        keyboardIconActive =  new ImageIcon(getClass().getResource("/images/keyboard-icon-active.png"));

        masterIcon =  new ImageIcon(getClass().getResource("/images/master-icon.png"));
        masterIconActive =  new ImageIcon(getClass().getResource("/images/master-icon-active.png"));

        setDefaultTextArea();
        if(flag) {
            setVisible(true);
        }
    }


    @Override
    public void setLabel(String text) {

    }

    public  void setWords(String words)
    {
        if(!words.equals("<unk>"))
        {
            setTreyMessage(words);
        }

        this.textField1.setText("");
        word = words;
        this.textField1.setText(word);
    }

    public void setImage(String key)
    {

        if (key=="computer active")
        {

            TextArea.setText(textEdit(VPresenter.getConfig().Computer_Words));
            comp.setIcon(iconcompActive);
        }
        else if(key=="computer")
        {
            comp.setIcon(iconcomp);
        }
        else if(key=="mouse active")
        {
            TextArea.setText(textEdit(VPresenter.getConfig().Mouse_Words));
            mouse.setIcon(mouseIconActive);
        }
        else if(key=="mouse")
        {
            mouse.setIcon(mouseIcon);
        }
        else if(key=="keyboard active")
        {
            TextArea.setText(textEdit(VPresenter.getConfig().KeyBoard_Words));
            keyboard.setIcon(keyboardIconActive);
        }
        else if(key=="keyboard")
        {
            keyboard.setIcon(keyboardIcon);
        }
        else if(key=="internet active")
        {
            TextArea.setText(textEdit(VPresenter.getConfig().Internet_Words));
            internet.setIcon(internetIconActive);
        }
        else if(key=="internet")
        {
            internet.setIcon(internetIcon);
        }
        else if(key=="games active")
        {
            TextArea.setText(textEdit(VPresenter.getConfig().Applications_Words));
            games.setIcon(gamesIconActive);
        }
        else if(key=="games")
        {
            games.setIcon(gamesIcon);
        }
        else if (key.equals("paint"))
        {
            TextArea.setText(textEdit(VPresenter.getConfig().Paint_Words));
        }
        else if (key.equals("racing"))
        {
            TextArea.setText(textEdit(VPresenter.getConfig().Racing_Words));
        }
        else if (key.equals("masterActive"))
        {
            TextArea.setText(textEdit(VPresenter.getConfig().Master_Words));
            master.setIcon(masterIconActive);
        }
        else if (key.equals("master"))
        {
            master.setIcon(masterIcon);
        }
        else if (key.equals("сменить язык"))
        {
            TextArea.setText("Назад\n"
                    +"Английский\n"
                    +"Русский\n");
        }
        else if(key.equals("change language"))
        {
            TextArea.setText("Back\n"
                    +"English\n"
                    +"Russian\n");
        }
    }


    private  void setTrayIcon() {
        if(! SystemTray.isSupported() ) {
            return;
        }

        PopupMenu trayMenu = new PopupMenu();
        MenuItem item = new MenuItem("Exit");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        trayMenu.add(item);

        Image icon = new ImageIcon(getClass().getResource("/images/trey.png")).getImage();
        trayIcon = new TrayIcon(icon, "Viano", trayMenu);
        trayIcon.setImageAutoSize(true);

        SystemTray tray = SystemTray.getSystemTray();
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
        }

        trayIcon.displayMessage("Viano", "Application started!",
                TrayIcon.MessageType.INFO);
    }

    public  void setTreyMessage(String s)
    {
        trayIcon.displayMessage("Viano", s,
                TrayIcon.MessageType.INFO);

    }

    @Override
    public  void setErrorTreyMessage(String s)
    {
        trayIcon.displayMessage("Viano", s,
                TrayIcon.MessageType.ERROR);

    }

    public static void setErrorTM(String s)
    {
        trayIcon.displayMessage("Viano", s,
                TrayIcon.MessageType.ERROR);

    }
    private void setDefaultTextArea()
    {
       TextArea.setText(textEdit(VPresenter.getConfig().Master_Words));
    }




    private String textEdit(List<String> list)
    {
        String result="";
        for(String str : list)
        {
            result += str + "\n";
        }
        return result;
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    @Override
    public void setText(List<String> list) {

    }

    @Override
    public Boolean getEdit() {
        return null;
    }

    @Override
    public void setProgressVisible() {

    }

    @Override
    public void disposeElements() {

    }

    @Override
    public String getText() {
        return null;
    }
}
