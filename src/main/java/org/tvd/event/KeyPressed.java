package org.tvd.event;

public class KeyPressed {

    public boolean left;
    public boolean right;
    public boolean up;
    public boolean down;
    public boolean space;
    public boolean enter;
    public boolean pause;
    public boolean lighting = true;
    public boolean back_slash;
    public boolean mini_map = true;

    public boolean isMovePressed() {

        return left || right || up || down;
    }
}
