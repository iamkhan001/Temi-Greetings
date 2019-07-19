package com.nstudio.temigreetings.utils;

/**
 * Created by Imran on 08-Nov-17.
 */

public class ColorPicker {


    public static String getColor(char alphabet) {
        String color;
        switch (alphabet) {
            case '0':
            case 'A':
                color = "#2E8B57";
                break;
            case '1':
            case 'B':
                color = "#6495ED";
                break;
            case '2':
            case 'C':
                color = "#FF7F50";
                break;
            case '3':
            case 'D':
                color = "#006400";
                break;
            case '4':
            case 'E':
                color = "#9932CC";
                break;
            case '5':
            case 'F':
                color = "#8FBC8F";
                break;
            case '6':
            case 'G':
                color = "#483D8B";
                break;
            case '7':
            case 'H':
                color = "#1E90FF";
                break;
            case '8':
            case 'I':
                color = "#FFA500";
                break;
            case '9':
            case 'J':
                color = "#DAA520";
                break;
            case '+':
            case 'K':
                color = "#808080";
                break;
            case 'L':
                color = "#FF69B4";
                break;
            case 'M':
                color = "#008000";
                break;
            case 'N':
                color = "#4B0082";
                break;
            case 'O':
                color = "#F08080";
                break;
            case 'P':
                color = "#20B2AA";
                break;
            case 'Q':
                color = "#778899";
                break;
            case 'R':
                color = "#800000";
                break;
            case 'S':
                color = "#9370DB";
                break;
            case 'T':
                color = "#3CB371";
                break;
            case 'U':
                color = "#7B68EE";
                break;
            case 'V':
                color = "#6B8E23";
                break;
            case 'W':
                color = "#FF4500";
                break;
            case 'X':
                color = "#DA70D6";
                break;
            case 'Y':
                color = "#DB7093";
                break;
            case 'Z':
                color = "#4682B4";
                break;
            default:
                color = "#A0522D";
                break;
        }


        return color;

    }


}
