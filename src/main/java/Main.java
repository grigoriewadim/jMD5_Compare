import org.apache.commons.codec.digest.DigestUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    private static File filename1;
    private static File filename2;
    private static String md5text;

    protected static class myGui extends JFrame {  //делаем GUI наследуем от JFrame
        myGui() {
            this.setLayout(null);

            setTitle("MD5 Compare");
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            try {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            } catch (Exception e) {
                System.err.println("Failed to set LookAndFeel");
            }
            setSize(700, 500);
            setLocation(300, 300);

            final JButton button1 = new JButton("Open file 1"); // Button for choose first file
            button1.setSize(200, 50);
            button1.setLocation(50, 50);
            button1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JFileChooser choser1 = new JFileChooser();
                    int ret = choser1.showDialog(null, "Open");
                    if (ret == JFileChooser.APPROVE_OPTION) {
                        filename1 = choser1.getSelectedFile();
                        if (choser1.accept(filename1)) {
                            button1.setBackground(Color.green);
                        }
                    }
                }
            });
            add(button1);

            final JButton button2 = new JButton("Open file 2 "); //Button for choose second file
            button2.setSize(200, 50);
            button2.setLocation(50, 120);
            button2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JFileChooser choser2 = new JFileChooser();
                    int ret = choser2.showDialog(null, "Open");
                    if (ret == JFileChooser.APPROVE_OPTION) {
                        filename2 = choser2.getSelectedFile();
                        if (choser2.accept(filename1)) {
                            button2.setBackground(Color.green);
//
                        }
                    }
                }
            });
            add(button2);


            final JButton buttonCheck1 = new JButton("MD5"); //вычисляем md5 для первого файла
            buttonCheck1.setSize(60, 50);
            buttonCheck1.setLocation(270, 50);
            buttonCheck1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        md5text = md5Hash(readFile(filename1));
                        final JTextField textField1 = new JTextField(md5text);
                        textField1.setSize(250, 50);
                        textField1.setLocation(350, 50);
                        textField1.setEnabled(true);
                        textField1.setBorder(null);
                        textField1.setEditable(false);
                        add(textField1);
                        buttonCheck1.setBackground(Color.red);
                    } catch (NullPointerException eNull) {
                        JOptionPane.showMessageDialog(null, "Please choose the file!");
                    }
                }
            });
            add(buttonCheck1);

            final JButton buttonCheck2 = new JButton("MD5"); //вычисляем md5 для второго файла
            buttonCheck2.setSize(60, 50);
            buttonCheck2.setLocation(270, 120);
            buttonCheck2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        String md5text;
                        md5text = md5Hash(readFile(filename2));
                        final JTextField textField2 = new JTextField(md5text);
                        textField2.setSize(250, 50);
                        textField2.setLocation(350, 120);
                        textField2.setEnabled(true);
                        textField2.setBorder(null);
                        textField2.setEditable(false);
                        add(textField2);
                        buttonCheck2.setBackground(Color.red);
                    } catch (NullPointerException eNull) {
                        JOptionPane.showMessageDialog(null, "Please choose the file!");
                    }

                }
            });
            add(buttonCheck2);
        }

    }

    private static String md5Hash(String st) { //метод для вычисления хэш суммы
        return DigestUtils.md5Hex(st); //подключаем зависимость commons-codec org.apache.commons.codec.digest
    }

//    protected static String md5Compare(String md5first, String md5second) {
//
//    }

    private static String readFile(File fileName) {  //читаем файл построчно
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader(fileName.getAbsoluteFile()));
            try {
                String s;
                while ((s = in.readLine()) != null) {
                    stringBuilder.append(s);
                }
            } finally {
                in.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        myGui window = new myGui();
        window.setVisible(true);
    }
}