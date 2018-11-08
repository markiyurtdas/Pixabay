package com.dev.marki.pixabay.Util;

/**
 * Created by marki on 07.02.2018.
 */

public class Settings {
    private static int perPage =10;

    public static int getPerPage() {
        return perPage;
    }

    public static void setPerPage(int number) {
        perPage = number;
    }
}
