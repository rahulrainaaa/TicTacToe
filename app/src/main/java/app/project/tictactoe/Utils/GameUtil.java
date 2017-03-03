package app.project.tictactoe.Utils;

import app.project.tictactoe.R;

public class GameUtil {

    public static int getImgRes(int mark) {
        int res = android.R.color.transparent;
        if (mark == 0) {
            res = R.drawable.zero;     // if mark = 0
        } else if (mark == 1) {
            res = R.drawable.cross;    // if mark = 1
        }
        return res;     // if mark = -1
    }

}
