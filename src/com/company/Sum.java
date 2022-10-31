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

    private static final String[][] ONES = {
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
    private static final String[] DOZENS = {
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
    private static final String[] TENS = {
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

    private static final String[] HUNDREDS = {
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

    private static final String[][] ADDITIONS = {
            {"гривня", "гривні", "гривень"},
            {"тисяча ", "тисячі ", "тисяч "},
            {"мільйон ", "мільйони ", "мільйонів "},
    };

    public void setSum(String sum) throws NegativeValueException, TooBigSumException {
        BigDecimal x = new BigDecimal(sum);
        if (x.signum() < 0) {
            throw new NegativeValueException();
        }
        if (x.compareTo(new BigDecimal(999999999.99)) == 1){
            throw new TooBigSumException();
        }
        this.sum = String.format("%.2f", x);
    }

    public void countSum() throws TooBigSumException {
        BigDecimal x = new BigDecimal(Double.toString(this.amount * this.price));
        if (x.compareTo(new BigDecimal(999999999.99)) == 1){
            throw new TooBigSumException();
        }
        this.sum = String.format("%.2f", x);
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public void setTaxName(String taxName) {
        this.taxName = taxName;
    }

    public void setPrice(double price) throws NegativeValueException, TooBigSumException, TooBigPriceException {
        this.price = price;
        if (!Double.toString(this.amount).isEmpty() && !Double.toString(this.price).isEmpty()){
            if (this.amount * this.price > 999999999.99){
                throw new TooBigSumException();
            }
        }
        if (price < 0){
            throw new NegativeValueException();
        }
        if (price > 999999999.99){
            throw new TooBigPriceException();
        }
    }

    public double getPrice() {
        return price;
    }

    public void setAmount(double amount) throws NegativeValueException, TooBigSumException, TooBigAmountException {
        this.amount = amount;
        if (!Double.toString(this.amount).isEmpty() && !Double.toString(this.price).isEmpty()){
            if (this.amount * this.price > 999999999.99){
                throw new TooBigSumException();
            }
        }
        if (amount < 0){
            throw new NegativeValueException();
        }
        if (amount > 999999999.99){
            throw new TooBigAmountException();
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
                builder.insert(0, ONES[Character.getNumericValue(number.charAt(i))][1]);
                continue;
            }else if (counter == 0 && number_part_counter == 2) {
                builder.insert(0, ONES[Character.getNumericValue(number.charAt(i))][0]);
                continue;
            }
            if (counter == 1 && number.charAt(i) == '1'){
                builder.delete(0, builder.capacity());
                builder.insert(0, ADDITIONS[number_part_counter][2]);
                builder.insert(0, DOZENS[Character.getNumericValue(number.charAt(i + 1))]);
                continue;
            } else if (counter == 1 && number.charAt(i) != '1') {
                builder.insert(0, TENS[Character.getNumericValue(number.charAt(i))]);
                continue;
            }
            if (counter == 2){
                builder.insert(0, HUNDREDS[Character.getNumericValue(number.charAt(i))]);
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
            if (number == 1){
                result = ADDITIONS[counter][0];
            } else if (number >= 2 && number <= 4){
                result = ADDITIONS[counter][1];
            } else {
                result = ADDITIONS[counter][2];
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
