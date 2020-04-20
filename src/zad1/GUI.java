package zad1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class GUI extends JPanel implements ActionListener {
    static Map<String, Integer> mapServer = Run.getMapLanguageServer();
    static Toolkit kit = Toolkit.getDefaultToolkit();
   static Dimension screenSize = kit.getScreenSize();
   static JButton jButton = new JButton("Ok");
   static JButton jButtonReturn = new JButton("Return");
    JFrame mainFrame = new JFrame();
    JFrame resultFrame = new JFrame();
    JTextField slowo = new JTextField();
    JComboBox comboBox = new JComboBox();


    public void inputDateFrame() {

        mainFrame.setSize(screenSize.width / 6, screenSize.height / 6);
        mainFrame.setLocation(screenSize.width / 4, screenSize.height / 4);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel jPanel = new JPanel(new GridLayout (5,1));
        jButton.addActionListener(this::actionPerformed);
       System.out.println(mapServer.get("RU"));
        for (String arg :
                mapServer.keySet()) {
            comboBox.addItem(arg);
        }
        jPanel.add(new JLabel("Fraza"));
        jPanel.add(slowo);
        jPanel.add(new JLabel("Jezyk"));
        jPanel.add(comboBox);
        jPanel.add(jButton);
        mainFrame.add(jPanel);
        mainFrame.setVisible(true);

    }

    public String returnText() {
        return slowo.getText() + "/" + comboBox.getSelectedItem();
    }

    public  void resultFrame(String result) {

        resultFrame.setSize(screenSize.width / 6, screenSize.height / 6);
        resultFrame.setLocation(screenSize.width / 4, screenSize.height / 4);
        resultFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jButtonReturn.addActionListener(this::actionPerformed);

        JPanel jPanel = new JPanel(new GridLayout (2,1));

        jPanel.add(new JLabel("Tłumaczenie: " + result));
        jPanel.add(jButtonReturn);
        resultFrame.add(jPanel);
        resultFrame.setVisible(true);
    }



    public  void actionPerformed(ActionEvent akcja) {

        if (akcja.getSource() == jButton) {
            try {
              Client.setValueFromGui(returnText());
              Client.flagaTrue();
              mainFrame.setVisible(false);
            } catch (Exception e){

            }
        } else if (akcja.getSource() == jButtonReturn){
           mainFrame.setVisible(true);
            slowo.setText("");
        }
    }

}
