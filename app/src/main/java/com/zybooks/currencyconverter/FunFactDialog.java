package com.zybooks.currencyconverter;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
/*
* Fun Fact Implemented!
* */
public class FunFactDialog {
    //Dialog that shows the fun fact
    public static void show(Context context, String currency) {
        String funFact = getFunFactForCurrency(currency);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Fun Fact about " + currency)
                .setMessage(funFact)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }
    //Different funfacts based on the currency clicked
    private static String getFunFactForCurrency(String currency) {
        switch (currency) {
            case "United States Dollar":
                return "The United States Dollar is the most widely used currency in the world.";
            case "Euro":
                return "The Euro is the official currency of the Eurozone, which consists of 19 of the 27 member states of the European Union.";
            case "Japanese Yen":
                return "The Japanese Yen is the third most traded currency in the world, and its symbol, ¥, is derived from the character 円, which means 'circle' or 'round.'";
            case "British Pound Sterling":
                return "The British Pound Sterling, often referred to as the Pound, is the oldest currency still in use today. Its symbol, £, comes from the letter 'L,' an abbreviation for the ancient Roman unit of weight, libra.";
            case "Australian Dollar":
                return "Australia leads the way when it comes to the security and durability of its money. It was the first country to issue polymer banknotes in 1988.";
            case "Canadian Dollar":
                return "The Canadian Dollar has many nicknames including 'toonie', 'huard', and buck'!";
            case "Swiss Franc":
                return "The Swiss Franc is the only version of the franc still issued in Europe. Switzerland has a long-standing policy of neutrality and has not been involved in any military conflict since 1815.";
            case "Chinese Yuan":
                return "The Chinese Yuan, also known as the Renminbi, is the official currency of the People's Republic of China. It is abbreviated as CNY.";
            case "Swedish Krona":
                return "The Swedish Krona has been the official currency of Sweden since 1873. The word 'krona' means 'crown' in Swedish.";
            case "New Zealand Dollar":
                return "The New Zealand Dollar is sometimes informally known as the 'kiwi' because the native bird, the kiwi, is a national symbol of New Zealand.";
            default:
                return "No fun fact available for this currency.";
        }
    }
}
