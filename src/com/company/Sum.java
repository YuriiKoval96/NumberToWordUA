package com.company;

public class Sum {

    private double sum;

    public Sum(double sum) {
        this.sum = sum;
    }

    public String toText() {
        String sumToText = Double.toString(this.sum).substring(0, Double.toString(this.sum).indexOf('.'));
        char[] sumArray = sumToText.toCharArray();
        StringBuilder builder = new StringBuilder();
        int counter = 1;
        for (int i = sumArray.length - 1; i > 0; i--) {

            switch (counter){
                case 4:{
                    if (sumArray[i] == '0'|| (sumArray[i] >= '5' && sumArray[i] <='9') ||
                            sumArray[i - 1] == '1'){
                        builder.insert(0, " тисяч");
                    } else if (sumArray[i] == '1' && (sumArray[i - 1] !='1')){
                        builder.insert(0, " тисяча");
                    } else if (sumArray[i] >= '2' && sumArray[i] <= '4'){
                        builder.insert(0, " тисячі");
                    }
                } break;
                case 7:{
                    if (sumArray[i] == '0' || (sumArray[i] >= '5' && sumArray[i] <='9') ||
                            sumArray[i - 1] == '1'){
                        builder.insert(0, " мільйонів");
                    } else if (sumArray[i] == 1){
                        builder.insert(0, " мільйон");
                    } else if (sumArray[i] >= 2 && sumArray[i] <= 4){
                        builder.insert(0, " мільйони");
                    }
                } break;

            }

            if (counter == 1 && sumArray[i - 1] != '1') {
                counter++;
                builder.append(addOnes(sumArray[i]));
                continue;
            } else if (counter == 1 && sumArray[i - 1] == '1'){
                counter++;
                builder.append(defineDozens(sumArray[i]));
                continue;
            }

            if (counter == 2 || counter == 5){
                counter++;
                builder.insert(0, defineTens(sumArray[i]));
                continue;
            }

            if (counter == 3 || counter == 6 || counter == 9){
                counter++;
                builder.insert(0, defineHundreds(sumArray[i]));
                continue;
            }

            if (counter == 4){
                counter++;
                builder.insert(0, defineThousands(sumArray[i]));
                continue;
            }

            if (counter == 5 && sumArray[i - 1] != '1') {
                counter++;
                switch (sumArray[i]) {
                    case '0' -> builder.insert(0, " десять");
                    case '1' -> builder.insert(0, " одна");
                    case '2' -> builder.insert(0, " дві");
                    case '3' -> builder.insert(0, " три");
                    case '4' -> builder.insert(0, " чотири");
                    case '5' -> builder.insert(0, " п'ять");
                    case '6' -> builder.insert(0, " шість");
                    case '7' -> builder.insert(0, " сім");
                    case '8' -> builder.insert(0, " вісім");
                    case '9' -> builder.insert(0, " девя'ть");
                }
                continue;
            } else if (counter == 5 && sumArray[i - 1] == '1'){
                counter++;
                switch (sumArray[i]) {
                    case '0' -> builder.insert(0, " десять тисяч");
                    case '1' -> builder.insert(0, " одинадцять тисяч");
                    case '2' -> builder.insert(0, " дванадцять тисяч");
                    case '3' -> builder.insert(0, " тринадцять тисяч");
                    case '4' -> builder.insert(0, " чотирнадцять тисяч");
                    case '5' -> builder.insert(0, " п'ятнадцять тисяч");
                    case '6' -> builder.insert(0, " шістнадцять тисяч");
                    case '7' -> builder.insert(0, " сімнадцять тисяч");
                    case '8' -> builder.insert(0, " вісімнадцять тисяч");
                    case '9' -> builder.insert(0, " девя'тнадцять тисяч");
                }
                continue;
            }

        }
        return builder.toString();
    }

    private String addOnes(int numberPosition){

        String result = "";

        switch (numberPosition) {
            case '0' ->{  result = " гривень";}
            case '1' ->{  result = " одна гривня";}
            case '2' ->{  result = " дві гривні";}
            case '3' ->{  result = " три гривні";}
            case '4' ->{  result = " чотири гривні";}
            case '5' ->{  result = " п'ять гривень";}
            case '6' ->{  result = " шість гривень";}
            case '7' ->{  result = " сім гривень";}
            case '8' ->{  result = " вісім гривень";}
            case '9' ->{  result = " девя'ть гривень";}
        }

        return result;

    }

    private String defineDozens(int numberPosition){

        String result = "";

        switch (numberPosition) {
            case '0' ->{  result = " десять гривень";}
            case '1' ->{  result = " одинадцять гривень";}
            case '2' ->{  result = " дванадцять гривень";}
            case '3' ->{  result = " тринадцять гривень";}
            case '4' ->{  result = " чотирнадцять гривень";}
            case '5' ->{  result = " п'ятнадцять гривень";}
            case '6' ->{  result = " шістнадцять гривень";}
            case '7' ->{  result = " сімнадцять гривень";}
            case '8' ->{  result = " вісімнадцять гривень";}
            case '9' ->{  result = " девя'тнадцять гривень";}
        }

        return result;

    }

    private String defineTens(int numberPosition){

        String result = "";

        switch (numberPosition) {
            case '2' ->{  result = " двадцять";}
            case '3' ->{  result = " тридцять";}
            case '4' ->{  result = " сорок";}
            case '5' ->{  result = " п'ятдесят";}
            case '6' ->{  result = " шістдесят";}
            case '7' ->{  result = " сімдесят";}
            case '8' ->{  result = " вісімдесят";}
            case '9' ->{  result = " девя'носто";}
        }

        return result;

    }

    private String defineHundreds(int numberPosition){

        String result = "";

        switch (numberPosition) {
            case '1' ->{  result = " сто";}
            case '2' ->{  result = " двісті";}
            case '3' ->{  result = " триста";}
            case '4' ->{  result = " чотириста";}
            case '5' ->{  result = " п'ятсот";}
            case '6' ->{  result = " шістсот";}
            case '7' ->{  result = " сімсот";}
            case '8' ->{  result = " вісімсот";}
            case '9' ->{  result = " девя'тсот";}
        }

        return result;

    }

    private String defineThousands(int numberPosition){

        String result = "";

        switch (numberPosition) {
            case '1' ->{  result = " одна";}
            case '2' ->{  result = " дві";}
            case '3' ->{  result = " три";}
            case '4' ->{  result = " чотири";}
            case '5' ->{  result = " п'ять";}
            case '6' ->{  result = " шість";}
            case '7' ->{  result = " сім";}
            case '8' ->{  result = " вісім";}
            case '9' ->{  result = " девя'ть";}
        }

        return result;

    }

}



