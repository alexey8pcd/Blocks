package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import static main.Constants.*;

/**
 * @author Alexey
 */
public class Game {

    private int totalScore;
    private int level;
    private int addBonus;
    private int replaceCount;
    private int perfectSerial;
    private int time;
    private String bonusText = "";
    private String messageText = "";
    private GameMode gameMode;
    private Field field;
    private static final Font FONT = new Font("Arial", Font.BOLD, 40);

    public Game(int width, int height, int blockSize, Color backColor) {
        gameMode = GameMode.OFF;
        field = new Field(width, height, blockSize, backColor);
    }

    public void newGame() {
        totalScore = 0;
        level = 1;
        addBonus = 0;
        perfectSerial = 0;
        gameMode = GameMode.PLAY;
        replaceCount = 3;
        time = START_TIME_PER_LEVEL[level - 1];
        field.create(level, addBonus);
        messageText = "Блоков осталось: " + field.getBlocksCount();
    }

    public void handleClick(MouseEvent evt) {
        switch (evt.getButton()) {
            case MouseEvent.BUTTON1:
                leftButtonClick(evt);
                return;
            case MouseEvent.BUTTON3:
                rightButtonClick(evt);
        }
    }

    private void leftButtonClick(MouseEvent evt) {
        if (gameMode == GameMode.PLAY) {
            field.clearSelected();
            int score = calculateScore(evt.getX(), evt.getY());
            if (score > 0) {
                bonusText = "<html>Получено очков: " + score;
                checkBonusesScore(score);
                field.fallDown();
                int blocksCount = field.getBlocksCount();
                if (field.allBlocksAlone()) {
                    endLevel(blocksCount);
                } else {
                    messageText = "Блоков осталось: " + blocksCount;
                }
            }
        }
    }

    private void checkBonusesScore(int score) {
        if (score >= SCORES_PER_BONUS[0] * MULTIPLY) {
            int bonusTime = 30;
            time += bonusTime;
            bonusText += "<br>Великолепно. Получено бонусное время!";
            int i = 0;
            while (score > SCORES_PER_BONUS[i] * MULTIPLY) {
                ++i;
            }
            --i;
            int bonusReplaceCount = BONUSES[0][i];
            int bonusCount = BONUSES[1][i];
            if (bonusReplaceCount > 0) {
                replaceCount += bonusReplaceCount;
                bonusText += "<br>Получено " + bonusReplaceCount + " перестановок";
            }
            if (bonusCount > 0) {
                addBonus += bonusCount;
                bonusText += "<br>Получео " + bonusCount + " бонусов";
            }
        }
    }

    private void endLevel(int amount) {
        int bonusPerTime = time * level;
        messageText = "<html>Бонус за время: " + bonusPerTime + "<br>";
        totalScore += bonusPerTime;
        int scoreForPerfect = 5000;
        if (amount == 0) {
            ++perfectSerial;
            if (perfectSerial >= 2) {
                int bonusScore = perfectSerial * scoreForPerfect;
                int bonusReplace = perfectSerial - 1;
                messageText += "Очищено " + perfectSerial + " подряд."
                        + "<br>Великолепно! +" + bonusScore + " очков."
                        + "<br> Получено +" + bonusReplace + " перестановок";
                totalScore += bonusScore;
                replaceCount += bonusReplace;
            } else {
                totalScore += scoreForPerfect;
                messageText += "Прекрасно! +" + scoreForPerfect + " очков";
            }
        } else {
            int ls = amount * 100 * level;
            perfectSerial = 0;
            messageText += "Осталось блоков: " + amount + "<br>Вы теряете "
                    + ls + " очков";
            totalScore -= ls;
        }
        if (totalScore < 0) {
            messageText = "<html>Вы проиграли";
            gameMode = GameMode.OFF;
        } else if (level == MAX_LEVEL) {
            gameMode = GameMode.OFF;
            messageText = "Победа";
        } else {
            resume();
        }
    }

    private void rightButtonClick(MouseEvent evt) {
        if (field.blockSelected()) {
            field.selectBlock(evt.getX(), evt.getY());
        } else if (replaceCount > 0
                && evt.getButton() == MouseEvent.BUTTON3
                && gameMode == GameMode.PLAY
                && field.replace(evt.getX(), evt.getY())) {
            --replaceCount;
        }
    }

    public void draw(Graphics graphics) {
        switch (gameMode) {
            case PAUSED:
                graphics.setColor(Color.BLACK);
                graphics.setFont(FONT);
                graphics.drawString("Игра остановлена", 100, 100);
            case PLAY:
                field.draw(graphics);
        }
    }

    private int calculateScore(int x, int y) {
        int multiplier = 1;
        if (!field.isBlockAlone(x, y)) {
            multiplier = field.deleteBlock(x, y);
        }
        int score = field.getDeletedBlocksCount();
        score *= score;
        score *= multiplier;
        totalScore += score;
        return score;
    }

    public String getBonusText() {
        return bonusText;
    }

    public String getMessageText() {
        return messageText;
    }

    public void handleMove(MouseEvent evt) {
        int x = evt.getX();
        int y = evt.getY();
        field.handleMove(x, y);
    }

    public int getReplaceCount() {
        return replaceCount;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void decreaseTime() {
        --time;
        if (time == 0) {
            gameMode = GameMode.OFF;

        }
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public int getLevel() {
        return level;
    }

    public int getTime() {
        return time;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    private void resume() {
        ++level;
        field.create(level, addBonus);
        addBonus = 0;
        time = START_TIME_PER_LEVEL[level - 1];
    }

}
