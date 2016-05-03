package main.blocks;

import main.bonus.Bonus;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Block {

    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected Color color;
    protected boolean deleted;
    protected boolean selected;
    public static final int SIZE = 30;
    private static final Font FONT = new Font("Timer New Roman", Font.BOLD, 20);
    protected Block left;
    protected Block right;
    protected Block top;
    protected Block bottom;
    protected Bonus bonus;

    public void setLeft(Block left) {
        this.left = left;
    }

    public void setRight(Block right) {
        this.right = right;
    }

    public void setTop(Block top) {
        this.top = top;
    }

    public void setBottom(Block bottom) {
        this.bottom = bottom;
    }

    public Block getLeftByColor() {
        if (left != null && (bonus != null || left.bonus != null
                || left.color == color)) {
            return left;
        } else {
            return null;
        }
    }

    public Block getRightByColor() {
        if (right != null && (bonus != null || right.bonus != null
                || right.color == color)) {
            return right;
        } else {
            return null;
        }
    }

    public Block getBottomByColor() {
        if (bottom != null && (bonus != null || bottom.bonus != null
                || bottom.color == color)) {
            return bottom;
        } else {
            return null;
        }
    }

    public Block getTopByColor() {
        if (top != null && (bonus != null || top.bonus != null
                || top.color == color)) {
            return top;
        } else {
            return null;
        }
    }

    public Block(int size, Color color) {
        this.width = size;
        this.height = size;
        this.color = color;
    }

    public Block(int x, int y, int width, int height, Color color) {
        if (x < 0) {
            x = 0;
        }
        if (y < 0) {
            y = 0;
        }
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public void clearNeighbors() {
        this.top = null;
        this.bottom = null;
        this.left = null;
        this.right = null;
    }

    public Bonus getBonus() {
        return bonus;
    }

    public void setBonus(Bonus bonus) {
        this.bonus = bonus;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted() {
        deleted = true;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public static boolean exists(Block block) {
        return block != null && !block.deleted;
    }

    public static boolean selected(Block block) {
        return block != null && !block.selected;
    }

    public boolean rightOf(Block block) {
        return x == block.x + SIZE
                && y == block.y
                && (color == block.color || bonus != null);
    }

    public boolean leftOf(Block block) {
        return x + SIZE == block.x
                && y == block.y
                && (color == block.color || bonus != null);
    }

    public boolean topOf(Block block) {
        return y + SIZE == block.y
                && x == block.x
                && (color == block.color || bonus != null);
    }

    public boolean buttomOf(Block block) {
        return y == block.y + SIZE
                && x == block.x
                && (color == block.color || bonus != null);
    }

    public void setX(int x) {
        if (x < 0) {
            x = 0;
        }
        this.x = x;
    }

    public void setY(int y) {
        if (y < 0) {
            y = 0;
        }
        this.y = y;
    }

    public void setWidth(int w) {
        if (w <= 0) {
            w = 30;
        }
        this.width = w;
    }

    public void setHeight(int h) {
        if (h <= 0) {
            h = 30;
        }
        this.height = h;
    }

    public boolean contains(int x, int y) {
        return x >= this.x
                && y >= this.y
                && x < this.x + width
                && y < this.y + height;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getX() {
        return this.x;
    }

    public Color getColor() {
        return color;
    }

    public boolean isSelected() {
        return selected;
    }

    public int getY() {
        return this.y;
    }

    public void draw(Graphics g) {
        if (!deleted) {
            if (bonus == null) {
                if (selected) {
                    Color selectedColor = color.darker().darker();
                    g.setColor(selectedColor);
                } else {
                    g.setColor(color);
                }
                g.fillRoundRect(x, y, width, height, 3, 3);
                g.setColor(Color.BLACK);
                g.drawRoundRect(x, y, width, height, 3, 3);
            }else{
                if (selected) {
                    g.setColor(Color.WHITE);
                } else {
                    g.setColor(color);
                }
                g.setFont(FONT);
                g.drawString(bonus.getType().getName(), x + width / 3, y + height * 3 / 4);
                g.drawRect(this.x, this.y, this.width, this.height);
            }
        }
    }
}
