package ru.dvteam.itcollabhub;

import android.content.Context;
import android.util.TypedValue;

import androidx.core.content.ContextCompat;

public class UsersChosenTheme {
    private static int themeNum = 3;

    public static int getThemeNum() {
        return themeNum;
    }

    public static void setThemeNum(int themeNum) {
        UsersChosenTheme.themeNum = themeNum;
    }

    public static void setThemeActivity(Context context){
        switch (themeNum) {
            case (1):
                context.setTheme(R.style.Theme_ITCollabHub_Blue);
                break;
            case (2):
                context.setTheme(R.style.Theme_ITCollabHub_Green);
                break;
            case (3):
                context.setTheme(R.style.Theme_ITCollabHub_Brown);
                break;
            case (4):
                context.setTheme(R.style.Theme_ITCollabHub_PinkGold);
                break;
            case (5):
                context.setTheme(R.style.Theme_ITCollabHub_Ohra);
                break;
            case (6):
                context.setTheme(R.style.Theme_ITCollabHub_Red);
                break;
            case (7):
                context.setTheme(R.style.Theme_ITCollabHub_Orange);
                break;
            case (8):
                context.setTheme(R.style.Theme_ITCollabHub_Violete);
                break;
            case (9):
                context.setTheme(R.style.Theme_ITCollabHub_BlueGreen);
                break;
        }
    }
}
