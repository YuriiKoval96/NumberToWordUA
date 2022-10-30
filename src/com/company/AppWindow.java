package com.company;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;

public class AppWindow extends JFrame {

    static JFrame window = new JFrame("Перетворити число в суму прописом");
    JTextField sumTextField = createSumTextField();
    JTextField priceTextField = createPriceTextField();
    JTextField amountTextField = createAmountTextField();
    JTextArea resultTextArea = createResultTextArea();
    Sum sum = new Sum();

    public AppWindow(){
        initialize();
    }

    public void initialize(){
        window.setSize(400, 330);
        window.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setResizable(false);
        window.add(createInputPanel(), BorderLayout.CENTER);
        window.add(createResultPanel(), BorderLayout.SOUTH);
        window.setVisible(true);
    }

    private JPanel createInputPanel(){
        JPanel inputPanel = new JPanel(new MigLayout("fill", "30[][]0", "[][][][][]"));
        inputPanel.setSize(430, 25);
        inputPanel.add(new JLabel("Рахувати за: "));
        inputPanel.add(createCountModeBox(), "wrap");
        inputPanel.add(new JLabel("Введіть суму: "));
        inputPanel.add(sumTextField, "wrap");
        inputPanel.add(new JLabel("Введіть кількість: "));
        inputPanel.add(amountTextField, "wrap");
        inputPanel.add(new JLabel("Введіть ціну: "));
        inputPanel.add(priceTextField,"wrap");
        inputPanel.add(new JLabel("Виберіть розмір ПДВ: "));
        inputPanel.add(createTaxBox());
        return inputPanel;
    }

    private JPanel createResultPanel(){
        JPanel resultPanel = new JPanel(new MigLayout("", "[]"));
        resultPanel.setSize(400, 200);
        resultPanel.add(resultTextArea);
        resultPanel.add(createButtonPanel());
        return resultPanel;
    }

    private JPanel createButtonPanel(){
        JPanel buttonPanel = new JPanel(new MigLayout("", "0[]0", "5[]10[]5"));
        buttonPanel.setPreferredSize(new Dimension(100, 90));
        buttonPanel.add(createFirstConvertButton(), "wrap");
        buttonPanel.add(createCopyButton());
        return buttonPanel;
    }

    private JButton createFirstConvertButton(){
        JButton firstConvertButton = new JButton("Перетворити");
        ImageIcon convertIcon = new ImageIcon("C:\\Users\\Юрій\\IdeaProjects\\NumberToWordUA\\src\\convert.png");
        firstConvertButton.setIcon(convertIcon);
        firstConvertButton.setPreferredSize(new Dimension(100, 40));
        firstConvertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String result = "";
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                if (!amountTextField.getText().isEmpty() && !priceTextField.getText().isEmpty()){
                    try {
                        sum.countSum();
                    } catch (ValueIsTooBigException exception){
                        resultTextArea.setText("Сума не може бути більшою ніж 999999999,99");
                    } catch (Exception exception){

                    }
                }
                try {
                    result = sum.toText();
                } catch (NullPointerException exception){
                    resultTextArea.setText("Введено недостатньо даних");
                }
                StringSelection stringSelection = new StringSelection(result);
                clipboard.setContents(stringSelection, null);
                resultTextArea.setText(result);
            }
        });
        return firstConvertButton;
    }

    private JButton createCopyButton(){
        JButton copyButton = new JButton("   Копіювати  ");
        ImageIcon copyIcon = new ImageIcon("C:\\Users\\Юрій\\IdeaProjects\\NumberToWordUA\\src\\copy15.png");
        copyButton.setIcon(copyIcon);
        copyButton.setPreferredSize(new Dimension(100, 40));
        copyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                StringSelection stringSelection = new StringSelection(resultTextArea.getText());
                clipboard.setContents(stringSelection, null);
            }
        });
        return copyButton;
    }

    private JTextField createSumTextField(){
        JTextField sumTextField = new JTextField();
        sumTextField.setPreferredSize(new Dimension(160, 25));
        sumTextField.setToolTipText("Введіть суму");
        sumTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    sum.setSum(sumTextField.getText().replace(',', '.')
                            .replace(" ", ""));
                    resultTextArea.setText("");
                } catch (NegativeValueException exception){
                    if (sumTextField.getText().isEmpty()){} else {
                        resultTextArea.setText("Сума не може бути відємною ");
                    }
                    resultTextArea.setText("Сума не може бути відємною");
                } catch (NumberFormatException exception){
                    resultTextArea.setText("Некоректно введена сума");
                } catch (ValueIsTooBigException exception) {
                    resultTextArea.setText("Сума повинна бути менщою ніж 999999999,99 ");
                }
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                try {
                    sum.setSum(sumTextField.getText().replace(',', '.')
                            .replace(" ", ""));
                    resultTextArea.setText("");
                } catch (NegativeValueException exception){
                    resultTextArea.setText("Сума не може бути відємною");
                } catch (NumberFormatException exception){
                    resultTextArea.setText("Некоректно введена сума");
                } catch (ValueIsTooBigException exception) {
                    resultTextArea.setText("Сума повинна бути менщою ніж 999999999,99 ");
                }
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                try {
                    sum.setSum(sumTextField.getText().replace(',', '.')
                            .replace(" ", ""));
                    resultTextArea.setText("");
                } catch (NegativeValueException exception){
                    resultTextArea.setText("Сума не може бути відємною");
                } catch (NumberFormatException exception){
                    resultTextArea.setText("Некоректно введена сума");
                } catch (ValueIsTooBigException exception) {
                    resultTextArea.setText("Сума повинна бути менщою ніж 999999999,99 ");
                }
            }
        });
        return sumTextField;
    }

    private JTextField createAmountTextField(){
        JTextField amountTextField = new JTextField();
        amountTextField.setPreferredSize(new Dimension(160, 25));
        amountTextField.setLocation(50,100);
        amountTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    sum.setAmount(Double.parseDouble(amountTextField.getText().replace(',', '.')
                            .replace(" ", "")));
                    resultTextArea.setText("");
                } catch (NegativeValueException exception){
                    resultTextArea.setText("Кількість не може бути відємною");
                } catch (NumberFormatException exception){
                    resultTextArea.setText("Некоректно введена кількість");
                }catch (ValueIsTooBigException exception){
                    resultTextArea.setText("При ціні в " + sum.getPrice() + " кількість не можу бути більшою ніж "
                            + String.format("%.2f", 999999999.99 / sum.getPrice()) );
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                try {
                    sum.setAmount(Double.parseDouble(amountTextField.getText().replace(',', '.')
                            .replace(" ", "")));
                    resultTextArea.setText("");
                } catch (NegativeValueException exception){
                    resultTextArea.setText("Кількість не може бути відємною");
                } catch (NumberFormatException exception){
                    resultTextArea.setText("Некоректно введена кількість");
                } catch (ValueIsTooBigException exception){
                    resultTextArea.setText("При ціні в " + sum.getPrice() + " кількість не можу бути більшою ніж "
                            + String.format("%.2f", 999999999.99 / sum.getPrice()) );
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                try {
                    sum.setAmount(Double.parseDouble(amountTextField.getText().replace(',', '.')
                            .replace(" ", "")));
                    resultTextArea.setText("");
                } catch (NegativeValueException exception){
                    resultTextArea.setText("Кількість не може бути відємною");
                } catch (NumberFormatException exception){
                    resultTextArea.setText("Некоректно введена кількість");
                } catch (ValueIsTooBigException exception){
                    resultTextArea.setText("При ціні в " + sum.getPrice() + " кількість не можу бути більшою ніж "
                            + String.format("%.2f", 999999999.99 / sum.getPrice()) );
                }
            }
        });

        return amountTextField;
    }

    private JTextField createPriceTextField(){
        JTextField priceTextField = new JTextField();
        priceTextField.setPreferredSize(new Dimension(160, 25));
        priceTextField.setLocation(50,100);
            priceTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    sum.setPrice(Double.parseDouble(priceTextField.getText().replace(',', '.')
                            .replace(" ", "")));
                    resultTextArea.setText("");
                } catch (NegativeValueException exception){
                    resultTextArea.setText("Ціна не може бути відємною");
                } catch (NumberFormatException exception){
                    resultTextArea.setText("Некоректно введена ціна");
                } catch (ValueIsTooBigException exception){
                    resultTextArea.setText("При кількості в " + sum.getAmount() + " ціна не можу бути більшою ніж"
                            + String.format("%.2f", 999999999.99 / sum.getAmount()));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                try {
                    sum.setPrice(Double.parseDouble(priceTextField.getText().replace(',', '.')
                            .replace(" ", "")));
                    resultTextArea.setText("");
                } catch (NegativeValueException exception){
                    resultTextArea.setText("Ціна не може бути відємною");
                } catch (NumberFormatException exception){
                    resultTextArea.setText("Некоректно введена ціна");
                } catch (ValueIsTooBigException exception){
                    resultTextArea.setText("При кількості в " + sum.getAmount() + " ціна не можу бути більшою ніж"
                            + String.format("%.2f", 999999999.99 / sum.getAmount()));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                try {
                    sum.setPrice(Double.parseDouble(priceTextField.getText().replace(',', '.')
                            .replace(" ", "")));
                    resultTextArea.setText("");
                } catch (NegativeValueException exception){
                    resultTextArea.setText("Ціна не може бути відємною");
                } catch (NumberFormatException exception){
                    resultTextArea.setText("Некоректно введена ціна");
                } catch (ValueIsTooBigException exception){
                    resultTextArea.setText("При кількості в " + sum.getAmount() + " ціна не можу бути більшою ніж"
                            + String.format("%.2f", 999999999.99 / sum.getAmount()));
                }
            }
            });

        return priceTextField;
    }

    private JTextArea createResultTextArea(){
        JTextArea resultTextArea = new JTextArea();
        resultTextArea.setPreferredSize(new Dimension(275, 100));
        resultTextArea.setLineWrap(true);
        resultTextArea.setWrapStyleWord(true);
        Border border = BorderFactory.createLineBorder(Color.GRAY);
        resultTextArea.setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(1, 1, 1, 1)));
        resultTextArea.setEditable(false);
        return resultTextArea;
    }

    private JComboBox createTaxBox(){
        sum.setTax(0.2);
        sum.setTaxName("20%");
        JComboBox taxBox = new JComboBox(new String[] {"ПДВ 20%", "ПДВ 7%", "ПДВ 0%"});
        taxBox.setPreferredSize(new Dimension(160, 25));
        taxBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                switch (taxBox.getSelectedIndex()){
                    case (0):
                        sum.setTax(0.2);
                        sum.setTaxName("20%");
                        break;
                    case (1):
                        sum.setTax(0.07);
                        sum.setTaxName("7%");
                        break;
                    case (2):
                        sum.setTax(0);
                        sum.setTaxName("0%");
                        break;
                }
            }
        });
        return taxBox;
    }

    private JComboBox createCountModeBox(){
        sumTextField.enable();
        sumTextField.setBackground(Color.WHITE);
        amountTextField.disable();
        amountTextField.setBackground(Color.LIGHT_GRAY);
        priceTextField.disable();
        priceTextField.setBackground(Color.LIGHT_GRAY);
        JComboBox countModeBox = new JComboBox(new String[] {"Cумою",
                "Кількістю та ціною"});
        countModeBox.setPreferredSize(new Dimension(160, 25));
        countModeBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                switch (countModeBox.getSelectedIndex()){

                    case (0):
                        sumTextField.enable();
                        sumTextField.setBackground(Color.WHITE);
                        amountTextField.disable();
                        amountTextField.setBackground(Color.LIGHT_GRAY);
                        amountTextField.setText("");
                        priceTextField.disable();
                        priceTextField.setBackground(Color.LIGHT_GRAY);
                        priceTextField.setText("");
                        break;
                    case (1):
                        sumTextField.disable();
                        sumTextField.setText("");
                        sumTextField.setBackground(Color.LIGHT_GRAY);
                        amountTextField.enable();
                        amountTextField.setBackground(Color.WHITE);
                        priceTextField.enable();
                        priceTextField.setBackground(Color.WHITE);
                        break;
                }
            }
        });
        return countModeBox;
    }

}
