package readability;

import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(new File(args[0]));
        StringBuilder str = new StringBuilder();
        while (scanner.hasNext()){
            str.append(scanner.next() + " ");
        }
        output(str.toString());
        scanner.close();
    }

    public static void output(String str){

        System.out.println("Words: " + countWord(str));
        System.out.println("Sentences: " + countSentences(str));
        System.out.println("Characters: " + countChar( str));
        System.out.println("Syllables: " + countSyllables(str));
        System.out.println("Polysyllables: " + countPolysyllable(str));
        System.out.print("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): ");
        Scanner scanner = new Scanner(System.in);
        String method = scanner.next();
        System.out.println();
        scanner.close();
        switch (method){
            case "ARI" : {
                coefficientARI(countChar( str), countWord(str),countSentences(str));
                break;
            }
            case "FK" : {
                coefficientFK( countWord(str),countSentences(str), countSyllables(str));
                break;
            }
            case "SMOG" : {
                coefficientSMOG(countSentences(str), countPolysyllable(str));
                break;
            }
            case "CL" : {
                coefficientCL(countChar( str), countWord(str), countSentences(str));
                break;
            }
            case  "all" : {
                double ageARI = coefficientARI(countChar( str), countWord(str),countSentences(str));
                double ageFK = coefficientFK( countWord(str),countSentences(str), countSyllables(str));
                double ageSMOG = coefficientSMOG(countSentences(str), countPolysyllable(str));
                double ageCL = coefficientCL(countChar( str), countWord(str), countSentences(str));
                System.out.println();
                averageAge(ageARI, ageFK, ageSMOG, ageCL );
                break;
            }

        }

    }
    public static void averageAge(double ageARI, double ageFK, double ageSMOG, double ageCL){
        double avrgAge = (ageARI + ageCL + ageFK + ageSMOG)/4;
        System.out.println("This text should be understood in average by " + avrgAge + " year olds.");
    }

    public static double coefficientARI(long chart, long word, long sen){
        DecimalFormat df = new DecimalFormat("#.00");
        df.setRoundingMode(RoundingMode.DOWN);
        double score = 4.71 * (double)chart/word + 0.5 * word/sen - 21.43;
        System.out.print("Automated Readability Index: " + df.format( score));
        int age = conclusionARI(score);
        return age;
    }

    public static double coefficientFK( long word, long sen, long syl){
        DecimalFormat df = new DecimalFormat("#.00");
        df.setRoundingMode(RoundingMode.DOWN);
        double score = 0.39*(double)word/sen + 11.8 * (double)syl/word - 15.59;
        System.out.print("Flesch–Kincaid readability tests: " + df.format( score));
        int age = conclusionARI(score);
        return age ;
    }

    public static double coefficientSMOG( long sen, long poly){
        DecimalFormat df = new DecimalFormat("#.00");
        df.setRoundingMode(RoundingMode.DOWN);
        double score = 1.043 * Math.sqrt(poly*(double)30/sen) + 3.1291;
        System.out.print("Simple Measure of Gobbledygook: " + df.format( score));
        int age = conclusionARI(score);
        return age ;
    }

    public static double coefficientCL(long chart, long word, long sen){
        DecimalFormat df = new DecimalFormat("#.00");
        df.setRoundingMode(RoundingMode.DOWN);
        double score = 0.0588 * (double)chart/word * 100 - 0.296 * (double)sen/word * 100 - 15.8;
        System.out.print("Coleman–Liau index: " + df.format( score));
        int age = conclusionARI(score);
        return age;
    }

    public static int conclusionARI(double score){
        score = Math.round(score);

        if (score < 2){
            System.out.println(" (about " + 6 + " year olds).");
            return 6;
        }
        if (score < 3){
            System.out.println(" about " + 7 + " year olds).");
            return 7;
        }
        if (score < 4 ){
            System.out.println(" (about " + 9 + " year olds).");
            return 9;
        }
        if (score < 5){
            System.out.println(" (about " + 10 + " year olds).");
            return 10;
        }
        if (score < 6){
            System.out.println(" (about " + 11 + " year olds).");
            return 11;
        }
        if (score < 7){
            System.out.println(" (about " + 12 + " year olds).");
            return 12;
        }
        if (score < 8){
            System.out.println(" (about " + 13 + " year olds).");
            return 13;
        }
        if (score < 9){
            System.out.println(" (about " + 14 + " year olds).");
            return 14;
        }
        if (score < 10){
            System.out.println(" (about " + 15 + " year olds).");
            return 15;
        }
        if (score < 11){
            System.out.println(" (about " + 16 + " year olds).");
            return 16;
        }
        if (score < 12){
            System.out.println(" (about " + 17 + " year olds).");
            return 17;
        }
        if (score < 13){
            System.out.println(" (about " + 18 + " year olds).");
            return 18;
        }
        if (score < 14){
            System.out.println(" (about " + 24 + " year olds).");
            return 24;
        }
        if (score >= 14){
            System.out.println(" (about " + 24 + " year olds and more).");
            return 24;
        }
        return 0;
    }

    public static long countSyllablesInWord(String word) {
        int s =0;
        String changeWord = word.replaceAll("[,!\\.\\?]", "").toLowerCase() + " ";
        if( changeWord.split("[aeyuio]{1,3}").length == 1 ||
                changeWord.replaceAll("[aeyuio]+", "").length() ==0){
            s = 1;
        }
        else {
            s = changeWord.split("[aeyuio]{1,3}").length - 1;
        }
        if(changeWord.charAt(changeWord.length()-2) == 'e' && s > 1){
            s--;
        }
        return  s;
    }

    public static  long countPolysyllable(String str){
        long polysyllables = 0;
        for (String word  : str.trim().split("[\\s]")) {
            if ( countSyllablesInWord(word) > 2){
                polysyllables ++;
            }
        }

        return polysyllables;
    }
    public static long countSyllables(String str){
        long syllables = 0;
        for (String word  : str.trim().split("[\\s]")) {
            syllables += countSyllablesInWord(word);
        }

        return syllables;
    }
    public static long countWord(String str){
        long words = 0L;
        words += str.split("\\s").length;
        return words;
    }

    public static long countSentences (String str){
        int sen = str.trim().split("[!\\.\\?]").length;
        String lastWord = str.split("[!\\.\\?]")[sen - 1];
        return lastWord.endsWith("[!\\.\\?]") ? str.split("[!\\.\\?\\S]").length-1 : sen ;
    }

    public static long countChar (String str){
        return str.split("[\\S]").length-1;
    }
}
