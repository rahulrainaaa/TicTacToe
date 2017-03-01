package app.project.tictactoe.Utils;

import app.project.tictactoe.R;

public class GameUtil {

    public static int getImgRes(int mark) {
        if (mark == 0) {
            return R.drawable.zero;
        } else if (mark == 1) {
            return R.drawable.cross;
        }
        return -1;
    }

}
