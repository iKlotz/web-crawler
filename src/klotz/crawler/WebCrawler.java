package klotz.crawler;

import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class WebCrawler extends JFrame {
    private JTextArea textArea;
    private JTextField textField;
    private JButton button;
    private JScrollPane scrollPane;


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
        scrollPane.setBounds(5, 50, 275, 210);
        add(scrollPane);

        textField = new JTextField();
        textField.setName("UrlTextField");
        textField.setBounds(5,20,180,20);
        add(textField);

        button = new JButton("Get text!");
        button.setName("RunButton");
        button.setBounds(190,20,90,20);
        add(button);
    }

    void buttonsAction() {
        button.addActionListener(e -> {
            final String url = textField.getText();
            try (InputStream inputStream = new BufferedInputStream(new URL(url).openStream())) {
                String siteText = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                textArea.setText(siteText);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }
}