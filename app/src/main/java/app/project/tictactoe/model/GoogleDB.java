package app.project.tictactoe.model;


public class GoogleDB {

    private int aa;
    private int ab;
    private int ac;
    private int ba;
    private int bb;
    private int bc;
    private int ca;
    private int cb;
    private int cc;

    private int won;
    private int player;
    private int gameStatus;

    public void setData(GoogleDB gdb) {
        aa = gdb.aa;
        ab = gdb.ab;
        ac = gdb.ac;
        ba = gdb.ba;
        bb = gdb.bb;
        bc = gdb.bc;
        ca = gdb.ca;
        cb = gdb.cb;
        cc = gdb.cc;
        won = gdb.won;
        player = gdb.player;
        gameStatus = gdb.gameStatus;
    }

    public int getAa() {
        return aa;
    }

    public void setAa(int aa) {
        this.aa = aa;
    }

    public int getAb() {
        return ab;
    }

    public void setAb(int ab) {
        this.ab = ab;
    }

    public int getAc() {
        return ac;
    }

    public void setAc(int ac) {
        this.ac = ac;
    }

    public int getBa() {
        return ba;
    }

    public void setBa(int ba) {
        this.ba = ba;
    }

    public int getBb() {
        return bb;
    }

    public void setBb(int bb) {
        this.bb = bb;
    }

    public int getBc() {
        return bc;
    }

    public void setBc(int bc) {
        this.bc = bc;
    }

    public int getCa() {
        return ca;
    }

    public void setCa(int ca) {
        this.ca = ca;
    }

    public int getCb() {
        return cb;
    }

    public void setCb(int cb) {
        this.cb = cb;
    }

    public int getCc() {
        return cc;
    }

    public void setCc(int cc) {
        this.cc = cc;
    }

    public int getWon() {
        return won;
    }

    public void setWon(int won) {
        this.won = won;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public int getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(int gameStatus) {
        this.gameStatus = gameStatus;
    }
}
