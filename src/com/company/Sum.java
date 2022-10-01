package com.company;

import java.util.Locale;

public class Sum {

    private String sum;

    private String[][] ones = {
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
    private String dozens[] = {
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
    private String tens[] = {
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

    private String hundreds[] = {
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

    private String additions[][] = {
            {"гривня", "гривні", "гривень"},
            {"тисяча ", "тисячі ", "тисяч "},
            {"мільйон ", "мільйони ", "мільйонів "},
    };

    public Sum(String sum) {
        this.sum = sum;
    }

    public char[] sumToCharArray(){
        return sum.substring(0, sum.indexOf('.')).toCharArray();
    }

    public String toText() {
        char[] sum = sumToCharArray();
        int counter = 1;
        StringBuilder builder = new StringBuilder();
        builder.append("");
        for (int i = sum.length - 1; i >= 0; i--){

            builder.insert(0, addAddition(counter,Integer.parseInt(Character.toString(sum[i]))));

            if (counter == 1 || counter == 4){
                counter++;
                builder.insert(0, ones[Integer.parseInt(Character.toString(sum[i]))][1]);
                continue;
            }

            if (counter == 2 && sum[i] == '1'){
                counter++;
                builder.delete(0, builder.capacity());
                builder.append(additions[0][2]);
                builder.insert(0, dozens[Integer.parseInt(Character.toString(sum[i + 1]))]);
                continue;
            } else if (counter == 2 && sum[i] != '1') {
                counter++;
                builder.insert(0, tens[Integer.parseInt(Character.toString(sum[i]))]);
                continue;
            }

            if (counter == 3 || counter == 6 || counter == 9){
                counter++;
                builder.insert(0, hundreds[Integer.parseInt(Character.toString(sum[i]))]);
                continue;
            }

            if (counter == 5 && sum[i] == '1'){
                counter++;
                builder.delete(0, builder.toString().indexOf(' ') + 1);
                builder.delete(0, builder.toString().indexOf(' ') + 1);
                builder.insert(0, additions[1][2]);
                builder.insert(0, dozens[Integer.parseInt(Character.toString(sum[i + 1]))]);
                continue;
            } else if (counter == 5 && sum[i] != '1'){
                counter++;
                builder.insert(0, tens[Integer.parseInt(Character.toString(sum[i]))]);
                continue;
            }

            if (counter == 7){
                counter++;
                builder.insert(0, ones[Integer.parseInt(Character.toString(sum[i]))][0]);
                continue;
            }

            if (counter == 8 && sum[i] == '1'){
                counter++;
                builder.delete(0, builder.toString().indexOf(' ') + 1);
                builder.delete(0, builder.toString().indexOf(' ') + 1);
                builder.insert(0, additions[2][2]);
                builder.insert(0, dozens[Integer.parseInt(Character.toString(sum[i + 1]))]);
                continue;
            } else if (counter == 8 && sum[i] != '1'){
                counter++;
                builder.insert(0, tens[Integer.parseInt(Character.toString(sum[i]))]);
                continue;
            }

        }

        builder.append(" ");
        builder.append(addCoins());
        builder.append(" коп.");

        return firstLetterToUpperCase(builder.toString());
    }

    private String addAddition(int counter, int number ){
        String result = "";
        if (counter == 1){
            if (number == 1){
                result = additions[0][0];
            } else if (number >= 2 && number <= 4){
                result = additions[0][1];
            } else {
                result = additions[0][2];
            }
        }
        if (counter == 4){
            if (number == 1){
                result = additions[1][0];
            } else if (number >= 2 && number <= 4){
                result = additions[1][1];
            } else {
                result = additions[1][2];
            }
        }

        if (counter == 7){
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
        String result = new String(chars);
        return result;
    }

    private String addCoins(){
       return sum.substring(sum.indexOf('.') + 1, sum.indexOf('.') + 3);
    }





}



