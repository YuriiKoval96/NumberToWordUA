package com.company;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public class Sum {

    private String sum;
    static private int number_part_counter = 0;
    private double tax;
    private String taxName;
    private double price;
    private double amount;

    private final String[][] ones = {
            {"", ""},
            {"один ", "одна "},
            {"два ", "дві "},
            {"три ", "три "},
            {"чотири ", "чотири "},
            {"пять ", "пять "},
            {"шість ", "шість "},
            {"сім ", "сім "},
            {"вісім ", "вісім "},
            {"дев'ять ", "дев'ять "},
    };
    private final String[] dozens = {
            "десять ",
            "одинадцять ",
            "дванадцять ",
            "тринадцять ",
            "чотирнадцять ",
            "п'ятнадцять ",
            "шістнадцять ",
            "сімнадцять ",
            "вісімнадцять ",
            "дев'ятнадцять "
    };
    private final String[] tens = {
            "",
            "",
            "двадцять ",
            "тридцять ",
            "сорок ",
            "п'ятдесят ",
            "шістдесят ",
            "сімдесят ",
            "вісімдесят ",
            "дев'яносто "
    };

    private final String[] hundreds = {
            "",
            "сто ",
            "двісті ",
            "триста ",
            "чотириста ",
            "п'ятсот ",
            "шістсот ",
            "сімсот ",
            "вісімсот ",
            "дев'ятсот "
    };

    private final String[][] additions = {
            {"гривня", "гривні", "гривень"},
            {"тисяча ", "тисячі ", "тисяч "},
            {"мільйон ", "мільйони ", "мільйонів "},
    };

    public Sum(){

    }

    public void setSum(String sum) throws NegativeValueException, ValueIsTooBigException {
        BigDecimal x = new BigDecimal(sum);
        if (x.signum() < 0) {
            throw new NegativeValueException();
        }
        if (x.compareTo(new BigDecimal(999999999.99)) == 1){
            throw new ValueIsTooBigException();
        }
        this.sum = String.format("%.2f", x);
    }

    public void countSum() throws ValueIsTooBigException {
        BigDecimal x = new BigDecimal(Double.toString(this.amount * this.price));
        if (x.compareTo(new BigDecimal(999999999.99)) == 1){
            throw new ValueIsTooBigException();
        }
        this.sum = String.format("%.2f", x);
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public void setTaxName(String taxName) {
        this.taxName = taxName;
    }

    public void setPrice(double price) throws NegativeValueException, ValueIsTooBigException {
        this.price = price;
        if (!Double.toString(this.amount).isEmpty() && !Double.toString(this.price).isEmpty()){
            if (this.amount * this.price > 999999999.99){
                throw new ValueIsTooBigException();
            }
        }
        if (price < 0){
            throw new NegativeValueException();
        }
    }

    public double getPrice() {
        return price;
    }

    public void setAmount(double amount) throws NegativeValueException, ValueIsTooBigException {
        this.amount = amount;
        if (!Double.toString(this.amount).isEmpty() && !Double.toString(this.price).isEmpty()){
            if (this.amount * this.price > 999999999.99){
                throw new ValueIsTooBigException();
            }
        }
        if (amount < 0){
            throw new NegativeValueException();
        }
    }

    public double getAmount() {
        return amount;
    }

    public String partToText(String number){
        StringBuilder builder = new StringBuilder();
        int counter = -1;
        if (number.equals("000") && number_part_counter == 1){

        } else {
            builder.insert(0, addAddition(number_part_counter,
                    Character.getNumericValue(number.charAt(number.length() - 1))));
        }
        for (int i = number.length() - 1; i >= 0; i--){
            counter++;
            if (counter == 0 && number_part_counter !=2){
                builder.insert(0, ones[Character.getNumericValue(number.charAt(i))][1]);
                continue;
            }else if (counter == 0 && number_part_counter == 2) {
                builder.insert(0, ones[Character.getNumericValue(number.charAt(i))][0]);
                continue;
            }
            if (counter == 1 && number.charAt(i) == '1'){
                builder.delete(0, builder.capacity());
                builder.insert(0, additions[number_part_counter][2]);
                builder.insert(0, dozens[Character.getNumericValue(number.charAt(i + 1))]);
                continue;
            } else if (counter == 1 && number.charAt(i) != '1') {
                builder.insert(0, tens[Character.getNumericValue(number.charAt(i))]);
                continue;
            }
            if (counter == 2){
                builder.insert(0, hundreds[Character.getNumericValue(number.charAt(i))]);
                continue;
            }
        }
        number_part_counter++;
        return builder.toString();
    }

    public String toText(){
        StringBuilder builder = new StringBuilder();
        String sumUAH = sum.substring(0, sum.indexOf(','));
        while (sumUAH.length() > 3) {
            builder.insert(0, partToText(sumUAH.substring(sumUAH.length() - 3, sumUAH.length())));
            sumUAH = sumUAH.substring(0, sumUAH.length() - 3);
        }
        builder.insert(0, partToText(sumUAH))
                .append(" " + addCoins() + " коп.")
                .append(" у тому числі ПДВ (" + taxName + ") " + addTax() + " грн.");
        number_part_counter = 0;
        return firstLetterToUpperCase(builder.toString());
    }


    private String addAddition(int counter, int number ){
        String result = "";
        if (counter == 0){
            if (number == 1){
                result = additions[0][0];
            } else if (number >= 2 && number <= 4){
                result = additions[0][1];
            } else {
                result = additions[0][2];
            }
        }
        if (counter == 1){
            if (number == 1){
                result = additions[1][0];
            } else if (number >= 2 && number <= 4){
                result = additions[1][1];
            } else {
                result = additions[1][2];
            }
        }

        if (counter == 2){
            if (number == 1){
                result = additions[2][0];
            } else if (number >= 2 && number <= 4){
                result = additions[2][1];
            } else {
                result = additions[2][2];
            }
        }
        return result;
    }

    private String firstLetterToUpperCase(String string){
        char[] chars = string.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        return new String(chars);
    }

    private @NotNull
    String addCoins(){
       return sum.substring(sum.indexOf(',') + 1, sum.indexOf(',') + 3);
    }

    private String addTax(){
        BigDecimal result = new BigDecimal(Double.parseDouble(sum.replace(',','.'))
                -(Double.parseDouble(sum.replace(',','.')) / (1 + tax)));
        return String.format("%.2f", result);
    }
}
