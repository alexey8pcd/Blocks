package main.blocks;

import java.util.Random;
import static main.Constants.COLORS;

/**
 * @author Alexey
 */
public class BlockFactory {

    public static Block createNew(Random random, int size, int level) {
        int chance = random.nextInt(100);
        if (level < 4) {
            return new Block(size, COLORS[random.nextInt(3)]);
        } else if (level < 7) {
            if (chance < 16) {
                return new Block(size, COLORS[4]);//16%
            } else {
                return createNew(random, size, 1);//28%
            }
        } else if (level < 10) {
            if (chance < 7) {
                return new Block(size, COLORS[5]);//7%
            } else if (chance < 14) {
                return new Block(size, COLORS[4]);//14%
            } else {
                return createNew(random, size, 1);//26,3%
            }
        } else if (chance < 76) {
            return createNew(random, size, 1);//25,3%
        } else if (chance < 88) {
            return new Block(size, COLORS[4]);//12%
        } else if (chance < 96) {
            return new Block(size, COLORS[5]);//8%
        } else {
            return new Block(size, COLORS[6]);//4%
        }
    }
}
