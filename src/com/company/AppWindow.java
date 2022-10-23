package com.company;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;

public class AppWindow extends JFrame {

    static JFrame window = new JFrame("Перетворити число в суму прописом");
    JTextArea resultTextArea = createResultTextArea();
    Sum sum = new Sum();

    public AppWindow(){
        initialize();
    }

    public void initialize(){

        window.setSize(600, 300);
        window.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setResizable(true);
        window.add(createInputPanel(), BorderLayout.CENTER);
        window.add(createResultPanel(), BorderLayout.SOUTH);
        window.setVisible(true);
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
                        resultTextArea.setText("Сума не може бути відємною");
                    }
                    resultTextArea.setText("Сума не може бути відємною");
                } catch (NumberFormatException exception){
                    resultTextArea.setText("Некоректно введена сума");
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
                }
            }
        });
        return sumTextField;
    }

    private JTextField CreateAmountTextField(){
        JTextField amountTextField = new JTextField();
        amountTextField.setPreferredSize(new Dimension(160, 25));
        amountTextField.setLocation(50,100);
        amountTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                sum.setAmount(Double.parseDouble(amountTextField.getText()));
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                sum.setAmount(Double.parseDouble(amountTextField.getText()));
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                sum.setAmount(Double.parseDouble(amountTextField.getText()));
            }
        });

        return amountTextField;
    }

    private JTextField createPriceTextField(){
        JTextField priceTextField = new JTextField();
        priceTextField.setPreferredSize(new Dimension(160, 25));
        priceTextField.setLocation(50,100);
        try {
            priceTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                sum.setAmount(Double.parseDouble(priceTextField.getText()));
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                sum.setAmount(Double.parseDouble(priceTextField.getText()));
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                sum.setAmount(Double.parseDouble(priceTextField.getText()));
            }
            });
        } catch (Exception e){
            resultTextArea.setText("Некоректно введена ціна");
        }

        return priceTextField;
    }

    private JTextArea createResultTextArea(){
        JTextArea resultTextArea = new JTextArea();
        resultTextArea.setPreferredSize(new Dimension(400, 100));
        resultTextArea.setLineWrap(true);
        resultTextArea.setEditable(false);
        return resultTextArea;
    }

    private JPanel createInputPanel(){
        JPanel inputPanel = new JPanel(new MigLayout());
        inputPanel.setSize(400, 30);
        inputPanel.add(new JLabel("Введіть суму: "));
        try {
            inputPanel.add(createSumTextField());
        } catch (Exception e){
            resultTextArea.setText("Некоректно введена сума");
        }
        inputPanel.add(createFirstConvertButton(),"wrap");
        inputPanel.add(new JLabel("Введіть кількість: "));
        inputPanel.add(CreateAmountTextField(), "wrap");
        inputPanel.add(new JLabel("Введіть ціну: "));
        inputPanel.add(createPriceTextField(),"wrap");
        inputPanel.add(new JLabel("Виберіть розмір ПДВ: "));
        inputPanel.add(createTaxBox());
        return inputPanel;
    }


    private JPanel createResultPanel(){
        JPanel resultPanel = new JPanel();
        resultPanel.setSize(600, 200);
        resultPanel.add(resultTextArea);
        return resultPanel;
    }
    private JButton createFirstConvertButton(){
        JButton firstConvertButton = new JButton("Перетворити");
        ImageIcon convertIcon = new ImageIcon("C:\\Users\\Юрій\\IdeaProjects\\NumberToWordUA\\src\\convert.png");
        firstConvertButton.setIcon(convertIcon);
        firstConvertButton.setPreferredSize(new Dimension(160, 25));
        firstConvertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resultTextArea.setText(sum.toText());
            }
        });
        return firstConvertButton;
    }

    private JComboBox createTaxBox(){
        String[] taxes = {"ПДВ 20%", "ПДВ 7%", "ПДВ 0%"};
        sum.setTax(0.2);
        sum.setTaxName("20%");
        JComboBox taxBox = new JComboBox(taxes);
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

}
