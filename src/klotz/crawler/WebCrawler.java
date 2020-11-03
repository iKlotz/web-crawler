package klotz.crawler;

import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebCrawler extends JFrame {
    private JTextArea textArea;
    private JTextField textField;
    private JButton button;
    private JScrollPane scrollPane;
    private JLabel urlLabel;
    private JLabel titleLabel;
    private JLabel titleLabelText;


    public WebCrawler() {
        super("Web Crawler");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);

        initComponent();

        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);

        buttonsAction();
    }

    void initComponent() {
        textArea = new JTextArea("HTML code?");
        textArea.setName("HtmlTextArea");
        textArea.setBounds(5,60,275,180);
        textArea.setEnabled(false);
        add(textArea);

        scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(5, 80, 275, 210);
        add(scrollPane);

        urlLabel = new JLabel();
        urlLabel.setText("URL:");
        urlLabel.setBounds(5, 20, 50, 20);
        add(urlLabel);

        textField = new JTextField();
        textField.setName("UrlTextField");
        textField.setBounds(55,20,140,20);
        add(textField);

        button = new JButton("Get text!");
        button.setName("RunButton");
        button.setBounds(190,20,90,20);
        add(button);

        titleLabel = new JLabel();
        titleLabel.setText("Title:");
        titleLabel.setBounds(5, 40, 100, 20);
        add(titleLabel);

        titleLabelText = new JLabel();
        titleLabelText.setName("TitleLabel");
        titleLabelText.setBounds(55, 40, 100, 20);
        add(titleLabelText);
    }

    void buttonsAction() {
        button.addActionListener(e -> {
            final String url = textField.getText();
            try (InputStream inputStream = new BufferedInputStream(new URL(url).openStream())) {
                String siteText = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                textArea.setText(siteText);
                setTitleLabel(getTitle(siteText));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }

    private String getTitle(String text) {
        String title = null;
        String pattern = "(<title>)(\\w*\\s?\\w*)(</title>)";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(text);

        if(m.find()){
            title = m.group(2);
        }

        return title;
    }

    void setTitleLabel(String title){
        titleLabelText.setText(title);
    }
}