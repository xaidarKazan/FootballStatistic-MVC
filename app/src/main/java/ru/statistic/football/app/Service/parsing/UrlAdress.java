package ru.statistic.football.app.Service.parsing;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class UrlAdress {
    public static boolean isExist(URL url) {
        // определение на существование URL адреса
        try {

            int response = ((HttpURLConnection) url.openConnection()).getResponseCode();

            if (response == 200) {
                System.out.println(" * URL is valid * ");
                return true;
            }

        } catch (IOException e) {
            System.out.println("Страницы с таким адресом не существует!");
            System.out.println("Исключение : " + e);
            return false;
        }
        return false;
    }
}