package main;

import main.bonus.Bonus;
import main.blocks.Block;
import main.bonus.BonusType;
import main.blocks.BlockFactory;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import static main.Constants.RANDOM;

/**
 * @author Alexey
 */
public class Field {

    private final int rowsCount = 20;
    private final int columnsCount = 20;
    private final int height;
    private final List<List<Block>> content;
    private final Map<Block, Color> oldColors;
    private final Color backColor;
    private final BufferedImage buffer;
    private Block selected;

    public Field(int width, int height, int blockSize, Color backColor) {
        this.height = height - blockSize;
        this.backColor = backColor;
        buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        content = new ArrayList<>();
        oldColors = new HashMap<>();
    }

    public boolean blockSelected() {
        return selected != null;
    }

    private Block getBlockByLocation(int x, int y) {
        if (content != null) {
            for (List<Block> column : content) {
                for (Block block : column) {
                    if (block.contains(x, y)) {
                        return block;
                    }
                }
            }
        }
        return null;
    }

    public void fallDown() {
        removeEmptyColumns();
        for (int columnIndex = 0; columnIndex < content.size(); ++columnIndex) {
            List<Block> column = content.get(columnIndex);
            for (int rowIndex = 0; rowIndex < column.size(); ++rowIndex) {
                Block block = column.get(rowIndex);
                block.setY(height - rowIndex * Block.SIZE);
                block.setX(columnIndex * Block.SIZE);
                block.clearNeighbors();
                int leftColumnIndex = columnIndex - 1;
                int rightColumnIndex = columnIndex + 1;
                int topRowIndex = rowIndex + 1;
                int bottomRowIndex = rowIndex - 1;
                if (topRowIndex < column.size()) {
                    block.setTop(column.get(topRowIndex));
                }
                if (bottomRowIndex >= 0) {
                    block.setBottom(column.get(bottomRowIndex));
                }
                if (leftColumnIndex >= 0) {
                    List<Block> leftColumn = content.get(leftColumnIndex);
                    if (leftColumn.size() > rowIndex) {
                        block.setLeft(leftColumn.get(rowIndex));
                    }
                }
                if (rightColumnIndex < content.size()) {
                    List<Block> rightColumn = content.get(rightColumnIndex);
                    if (rightColumn.size() > rowIndex) {
                        block.setRight(rightColumn.get(rowIndex));
                    }
                }
            }
        }
    }

    private void removeEmptyColumns() {
        for (Iterator<List<Block>> iterator = content.iterator(); iterator.hasNext();) {
            List<Block> column = iterator.next();
            if (column.isEmpty()) {
                iterator.remove();
            }
        }
    }

    public void handleMove(int x, int y) {
        restoreColors();
        clearSelection();
        Block block = getBlockByLocation(x, y);
        if (block != null) {
            if (hasNeighbors(block)) {
                selectNei(block);
            }
        }
    }

    public void create(int level, int addBonus) {
        content.clear();
        createBlocks(level);
        createBonuses(level, addBonus);
        fallDown();
    }

    private void createBonuses(int level, int addBonus) {
        for (int i = 0; i < level - 1 + addBonus; ++i) {
            int clm = Constants.RANDOM.nextInt(columnsCount);
            int rw = Constants.RANDOM.nextInt(columnsCount);
            BonusType type = BonusType.createWithProbability(RANDOM.nextDouble());
            content.get(clm).get(rw).setBonus(new Bonus(type, Block.SIZE));
        }
    }

    private void createBlocks(int level) {
        for (int columnIndex = 0; columnIndex < columnsCount; columnIndex++) {
            List<Block> column = new ArrayList<>();
            for (int rowIndex = 0; rowIndex < rowsCount; rowIndex++) {
                Block block = BlockFactory.createNew(RANDOM, Block.SIZE, level);
                column.add(block);
            }
            content.add(column);
        }
    }

    public int getDeletedBlocksCount() {
        int score = 0;
        for (List<Block> column : content) {
            for (Iterator<Block> iterator = column.iterator(); iterator.hasNext();) {
                Block block = iterator.next();
                if (block.isDeleted()) {
                    iterator.remove();
                    ++score;
                }
            }
        }
        return score;
    }

    private void clearSelection() {
        for (List<Block> column : content) {
            for (Block bl : column) {
                bl.setSelected(false);
            }
        }
    }

    private void restoreColors() {
        if (!oldColors.isEmpty()) {
            for (Map.Entry<Block, Color> entrySet : oldColors.entrySet()) {
                Block b = entrySet.getKey();
                Color color = entrySet.getValue();
                b.setColor(color);
            }
            oldColors.clear();
        }
    }

    public int getBlocksCount() {
        int nb = 0;
        for (List<Block> column : content) {
            nb += column.size();
        }
        return nb;
    }

    public void draw(Graphics graphics) {
        Graphics temp = buffer.getGraphics();
        temp.setColor(backColor);
        temp.fillRect(0, 0, buffer.getWidth(), buffer.getHeight());
        for (List<Block> column : content) {
            for (Block block : column) {
                block.draw(temp);
            }
        }
        if (selected != null) {
            temp.setColor(Color.WHITE);
            temp.drawRect(selected.getX(), selected.getY(),
                    selected.getWidth(), selected.getHeight());
        }
        graphics.drawImage(buffer, 0, 0, null);
    }

    public int deleteNei(Block centerBlock) {
        int multiply = 1;
        if (centerBlock != null) {
            Block top = centerBlock.getTopByColor();
            Block bottom = centerBlock.getBottomByColor();
            Block left = centerBlock.getLeftByColor();
            Block right = centerBlock.getRightByColor();
            centerBlock.setDeleted();
            if (Block.exists(top)) {
                multiply *= activeBonus(top, centerBlock.getColor());
                deleteNei(top);
            }
            if (Block.exists(bottom)) {
                multiply *= activeBonus(bottom, centerBlock.getColor());
                deleteNei(bottom);
            }
            if (Block.exists(left)) {
                multiply *= activeBonus(left, centerBlock.getColor());
                deleteNei(left);
            }
            if (Block.exists(right)) {
                multiply *= activeBonus(right, centerBlock.getColor());
                deleteNei(right);
            }
        }
        return multiply;
    }

    private void selectNei(Block centerBlock) {
        if (centerBlock != null) {
            Block top = centerBlock.getTopByColor();
            Block bottom = centerBlock.getBottomByColor();
            Block left = centerBlock.getLeftByColor();
            Block right = centerBlock.getRightByColor();
            centerBlock.setSelected(true);
            if (Block.selected(top)) {
                selectNei(top);
                select(top, centerBlock.getColor());
            }
            if (Block.selected(bottom)) {
                selectNei(bottom);
                select(bottom, centerBlock.getColor());
            }
            if (Block.selected(left)) {
                selectNei(left);
                select(left, centerBlock.getColor());
            }
            if (Block.selected(right)) {
                selectNei(right);
                select(right, centerBlock.getColor());
            }
        }
    }

    private void select(Block block, Color color) {
        Bonus bonus = block.getBonus();
        if (bonus != null) {
            int rowIndex = (height - block.getY()) / Block.SIZE;
            int columnIndex = block.getX() / Block.SIZE;
            int leftClm = columnIndex;
            int rightClm = columnIndex;
            switch (bonus.getType()) {
                case NEARED:
                    selectBonusNeared(columnIndex, leftClm,
                            rightClm, rowIndex, color);
                    return;
                case LINE:
                    selectBonusLine(rowIndex, color);
                    return;
                case MIX_COLOR:
                    selectBonuxMixColor(block, columnIndex, leftClm,
                            rightClm, rowIndex, color);
            }
        }
    }

    private void selectBonusNeared(int columnIndex, int leftClm,
            int rightClm, int rowIndex, Color color) {
        if (columnIndex > 0) {
            --leftClm;
        }
        if (columnIndex < content.size() - 1) {
            ++rightClm;
        }
        for (int i = leftClm; i <= rightClm; ++i) {
            List<Block> column = content.get(i);
            Block bl;
            if (column.size() > rowIndex) {
                if (rowIndex > 0) {
                    bl = column.get(rowIndex - 1);
                    selectBlockAndNeighbors(bl, color);
                }
                bl = column.get(rowIndex);
                bl.setSelected(true);
                selectBlockAndNeighbors(bl, color);
            }
            if (column.size() > rowIndex + 1) {
                bl = column.get(rowIndex + 1);
                bl.setSelected(true);
                selectBlockAndNeighbors(bl, color);
            }
        }
    }

    private void selectBonusLine(int rowIndex, Color color) {
        for (int columnI = 0; columnI < content.size(); ++columnI) {
            List<Block> column = content.get(columnI);
            if (column.size() > rowIndex) {
                Block bl = column.get(rowIndex);
                bl.setSelected(true);
                selectBlockAndNeighbors(bl, color);
            }
        }
    }

    private void selectBonuxMixColor(Block block, int columnIndex,
            int leftClm, int rightClm, int rowIndex, Color color) {
        block.setSelected(true);
        if (columnIndex > 0) {
            --leftClm;
        }
        if (columnIndex < content.size() - 1) {
            ++rightClm;
        }
        for (int i = leftClm; i <= rightClm; ++i) {
            List<Block> column = content.get(i);
            if (column.size() > rowIndex) {
                if (rowIndex > 0) {
                    saveAndReplaceColorAndSelectNeighbors(column.get(rowIndex - 1), color);
                }
                saveAndReplaceColorAndSelectNeighbors(column.get(rowIndex), color);
            }
            if (column.size() > rowIndex + 1) {
                saveAndReplaceColorAndSelectNeighbors(column.get(rowIndex + 1), color);
            }
        }
    }

    private void selectBlockAndNeighbors(Block block, Color color) {
        block.setSelected(true);
        if (block.getColor() == color) {
            selectNei(block);
        }
    }

    private void saveAndReplaceColorAndSelectNeighbors(Block block, Color color) {
        if (block.getColor() != color) {
            oldColors.put(block, block.getColor());
        }
        block.setColor(color);
        selectNei(block);
    }

    private int activeBonus(Block block, Color color) {
        Bonus bonus = block.getBonus();
        if (bonus != null) {
            int rowIndex = (height - block.getY()) / Block.SIZE;
            int columnIndex = block.getX() / Block.SIZE;
            int leftClm = columnIndex;
            int rightClm = columnIndex;
            switch (bonus.getType()) {
                case NEARED:
                    activeNeared(columnIndex, leftClm, rightClm, rowIndex);
                    break;
                case LINE:
                    activeLine(rowIndex);
                    break;
                case MIX_COLOR:
                    activeMixColor(bonus, columnIndex, leftClm, rightClm, rowIndex, color);
                    break;
                case TIMES_2:
                    return 2;
                case TIMES_3:
                    return 3;
            }
        }
        return 1;
    }

    private void activeNeared(int columnIndex, int leftClm, int rightClm, int rowIndex) {
        if (columnIndex > 0) {
            --leftClm;
        }
        if (columnIndex < content.size() - 1) {
            ++rightClm;
        }
        for (int i = leftClm; i <= rightClm; ++i) {
            List<Block> column = content.get(i);
            if (column.size() > rowIndex) {
                if (rowIndex > 0) {
                    column.get(rowIndex - 1).setDeleted();
                }
                column.get(rowIndex).setDeleted();
            }
            if (column.size() > rowIndex + 1) {
                column.get(rowIndex + 1).setDeleted();
            }
        }
    }

    private void activeLine(int rowIndex) {
        for (int columnI = 0; columnI < content.size(); ++columnI) {
            List<Block> column = content.get(columnI);
            if (column.size() > rowIndex) {
                column.get(rowIndex).setDeleted();
            }
        }
    }

    private void activeMixColor(Bonus sb, int columnIndex, int leftClm,
            int rightClm, int rowIndex, Color color) {
        if (columnIndex > 0) {
            --leftClm;
        }
        if (columnIndex < content.size() - 1) {
            ++rightClm;
        }
        for (int i = leftClm; i <= rightClm; ++i) {
            List<Block> column = content.get(i);
            if (column.size() > rowIndex) {
                if (rowIndex > 0) {
                    column.get(rowIndex - 1).setColor(color);
                }
                column.get(rowIndex).setColor(color);
            }
            if (column.size() > rowIndex + 1) {
                column.get(rowIndex + 1).setColor(color);
            }
        }
    }

    private boolean hasNeighbors(Block theBlock) {
        if (theBlock != null) {
            return theBlock.getTopByColor() != null
                    || theBlock.getLeftByColor() != null
                    || theBlock.getBottomByColor() != null
                    || theBlock.getRightByColor() != null;
        } else {
            return true;
        }
    }

    public boolean allBlocksAlone() {
        for (List<Block> row : content) {
            for (Block block : row) {
                if (hasNeighbors(block)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void clearSelected() {
        selected = null;
    }

    public void selectBlock(int x, int y) {
        selected = getBlockByLocation(x, y);
        if (selected != null && selected.getBonus() != null) {
            selected = null;
        }
    }

    public boolean replace(int x, int y) {
        Block replaced = getBlockByLocation(x, y);
        if (replaced != null && replaced.getBonus() != null
                && replaced != selected
                && replaced.getColor() != selected.getColor()) {
            Color color = selected.getColor();
            selected.setColor(replaced.getColor());
            replaced.setColor(color);
            selected = null;
            return true;
        }
        return false;
    }

    public boolean isBlockAlone(int x, int y) {
        Block block = getBlockByLocation(x, y);
        return hasNeighbors(block);
    }

    public int deleteBlock(int x, int y) {
        Block block = getBlockByLocation(x, y);
        if (block == null || block.getBonus() != null) {
            return 0;
        }
        return deleteNei(block);
    }

}
