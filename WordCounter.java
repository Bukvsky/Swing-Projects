package wordCounter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WordCounter extends JFrame {
    private JLabel wordCountLabel;
    private JTextArea textArea;

    public WordCounter() {
        setTitle("Word Counter");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Text area for user input
        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        // Button to calculate word count
        JButton countButton = new JButton("Count Words");
        countButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                countWords();
                clearBrackets();
            }
        });
        add(countButton, BorderLayout.SOUTH);

        // Label to display word count
        wordCountLabel = new JLabel("Word Count: 0");
        add(wordCountLabel, BorderLayout.NORTH);

        setVisible(true);
    }

    private void clearBrackets() {
       textArea.setText("");
    }

    private void countWords() {
        String text = textArea.getText();
        String[] words = text.split("\\s+"); // Split text by whitespace characters
        int wordCount = words.length;
        wordCountLabel.setText("Word Count: " + wordCount);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new WordCounter();
            }
        });
    }
}
