package main;

import java.awt.Color;
import java.util.Random;

/**
 * @author Alexey
 */
public class Constants {

    public static final Random RANDOM = new Random();
    public static final int MAX_LEVEL = 12;
    public static final int MULTIPLY = 10000;
    public static final Color[] COLORS = {
        Color.RED,
        Color.GREEN,
        Color.BLUE,
        Color.YELLOW,
        Color.CYAN,
        Color.GRAY,
        Color.ORANGE,
        Color.PINK,
        Color.MAGENTA
    };
    public static final int[] START_TIME_PER_LEVEL = {
        120,
        100,
        80,
        180,
        160,
        140,
        240,
        220,
        200,
        300,
        280,
        260
    };

    public static final int[] SCORES_PER_BONUS = {
        1,//bonus time
        2,//bonus time+replace
        4,//bonus time+replace+bonus
        8,//bonus time+2*replace+bonus
        12,//bonus time+2*replace+2*bonus
        16,//bonus time+3*replace+2*bonus
        20,//bonus time+3*replace+3*bonus
        24,//bonus time+4*replace+3*bonus
        30,//bonus time+4*replace+4*bonus
        35,//bonus time+5*replace+4*bonus
        40,//...+5*r+5*b
        50,///7,7
        100,//10,10
        150//15,15
    };
    public static final int[][] BONUSES = {
        {0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 7, 10, 15},
        {0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 7, 10, 15}
    };

}
