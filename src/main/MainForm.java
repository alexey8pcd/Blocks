package main;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class MainForm extends javax.swing.JFrame {

    private final Graphics2D graphics;

    private Game game;
    private Timer timer = new Timer(1000, new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (game.getGameMode() == GameMode.PLAY) {
                game.decreaseTime();
                showTime();
            }
            if (game.getGameMode() == GameMode.OFF) {
                String s = "";
                if (game.getTime() == 0) {
                    s = "Время вышло. Вы проиграли";
                } else {
                    if (game.getTotalScore() <= 0) {
                        s = "Недостаточно очков. Вы проиграли";
                    } else {
                        if (game.getLevel() == Constants.MAX_LEVEL) {
                            s = "Вы победили";
                        }
                    }
                }
                JOptionPane.showMessageDialog(null, s,
                        "Информация", JOptionPane.INFORMATION_MESSAGE);
                timer.stop();
            }
        }
    });
    private final String helpText = "<html>Для очистки поля"
            + "<br>нужно нажимать левой кнопкой мыши на блоках. "
            + "<br>Два или более соседних блока одного цвета уничтожаются."
            + "<br>За уничтоженные блоки начисляются очки."
            + "<br>Количество очков равно количеству уничтоженных блоков, "
            + "<br>возведенное в квадрат. На каждый уровень дается время."
            + "<br>5 видов бонусов: "
            + "<br><font color=\"red\">L</font> - уничтожается линия"
            + "<br><font color=\"red\">N</font> - уничтожаются вокруг бонуса."
            + "<br><font color=\"red\">C</font> - все блоки вокруг бонуса принимают цвет того блока, "
            + "который активировал."
            + "<br><font color=\"red\">2</font> - умножает полученные очки на 2"
            + "<br><font color=\"red\">3</font> - умножает полученные очки на 3"
            + "<br>Блоки с разными цветами можно менять местами."
            + "<br>Для этого нужно нажать правой кнопкой мыши на одном блоке,"
            + "<br>затем правой кнопкой мыши на другом."
            + "<br>Автор: Алексей Овчаров";

    public MainForm() {
        initComponents();
        Dimension size = getToolkit().getScreenSize().getSize();
        setLocation(size.width / 2 - getWidth() / 2,
                size.height / 2 - getHeight() / 2);
        int blockSize = Math.min(paneDraw.getWidth(), paneDraw.getHeight()) / 20;
        int height = paneDraw.getHeight();
        int width = paneDraw.getWidth();
        graphics = (Graphics2D) paneDraw.getGraphics();
        game = new Game(width, height, blockSize, paneDraw.getBackground());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        paneDraw = new javax.swing.JPanel();
        bNewGame = new javax.swing.JButton();
        bInfo = new javax.swing.JButton();
        lLevelAndScore = new javax.swing.JLabel();
        lMessage = new javax.swing.JLabel();
        lReplaceInfo = new javax.swing.JLabel();
        lBonus = new javax.swing.JLabel();
        lTime = new javax.swing.JLabel();
        bPause = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Блоки");
        setForeground(java.awt.Color.white);
        setResizable(false);
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                formFocusGained(evt);
            }
        });

        paneDraw.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                paneDrawMouseMoved(evt);
            }
        });
        paneDraw.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                paneDrawMousePressed(evt);
            }
        });

        javax.swing.GroupLayout paneDrawLayout = new javax.swing.GroupLayout(paneDraw);
        paneDraw.setLayout(paneDrawLayout);
        paneDrawLayout.setHorizontalGroup(
            paneDrawLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );
        paneDrawLayout.setVerticalGroup(
            paneDrawLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );

        bNewGame.setText("Новая игра");
        bNewGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bNewGameActionPerformed(evt);
            }
        });

        bInfo.setText("Помощь");
        bInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bInfoActionPerformed(evt);
            }
        });

        lBonus.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        lTime.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        bPause.setText("Пауза");
        bPause.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bPauseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(paneDraw, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bNewGame, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bPause, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lTime, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lMessage, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lLevelAndScore, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lReplaceInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lBonus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(paneDraw, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bNewGame)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bInfo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bPause)
                .addGap(18, 18, 18)
                .addComponent(lTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(lLevelAndScore, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lReplaceInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lBonus, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void showReplaceCount() {
        lReplaceInfo.setText("<html>Перестановок осталось: "
                + game.getReplaceCount());
    }

    private void showTime() {
        int minutes = game.getTime() / 60;
        int seconds = game.getTime() % 60;
        String sec = seconds < 10 ? "0" + seconds : String.valueOf(seconds);
        lTime.setText(String.valueOf(minutes) + ":" + sec);
    }

    private void showLevelAndScore() {
        this.lLevelAndScore.setText("<html>Уровень: " + game.getLevel()
                + "<br>Счет: " + game.getTotalScore());
    }


    private void bNewGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bNewGameActionPerformed
        game.newGame();
        timer.start();
        game.draw(graphics);
        lMessage.setText(game.getMessageText());
        lBonus.setText(game.getBonusText());
        showLevelAndScore();
        showReplaceCount();

    }//GEN-LAST:event_bNewGameActionPerformed

    private void paneDrawMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_paneDrawMousePressed
        if (game.getGameMode() == GameMode.PLAY) {
            game.handleClick(evt);
            lMessage.setText(game.getMessageText());
            lBonus.setText(game.getBonusText());
            showLevelAndScore();
            showReplaceCount();
            game.draw(graphics);
        }
    }//GEN-LAST:event_paneDrawMousePressed

    private void bInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bInfoActionPerformed
        JOptionPane.showMessageDialog(null, helpText, "Помощь",
                JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_bInfoActionPerformed

    private void bPauseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bPauseActionPerformed
        if (game.getGameMode() == GameMode.PLAY) {
            lMessage.setText("Пауза");
            bPause.setText("Продолжить");
            game.setGameMode(GameMode.PAUSED);
            game.draw(graphics);
        } else if (game.getGameMode() == GameMode.PAUSED) {
            paneDraw.setVisible(true);
            lMessage.setText("Игра возобновлена");
            bPause.setText("Пауза");
            game.setGameMode(GameMode.PLAY);
            game.draw(graphics);
        }

    }//GEN-LAST:event_bPauseActionPerformed

    private void paneDrawMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_paneDrawMouseMoved
        game.handleMove(evt);
        game.draw(graphics);
    }//GEN-LAST:event_paneDrawMouseMoved

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
        game.draw(graphics);
    }//GEN-LAST:event_formFocusGained

    public static void main(String args[]) {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainForm mnf = new MainForm();
                mnf.setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bInfo;
    private javax.swing.JButton bNewGame;
    private javax.swing.JButton bPause;
    private javax.swing.JLabel lBonus;
    private javax.swing.JLabel lLevelAndScore;
    private javax.swing.JLabel lMessage;
    private javax.swing.JLabel lReplaceInfo;
    private javax.swing.JLabel lTime;
    private javax.swing.JPanel paneDraw;
    // End of variables declaration//GEN-END:variables
}
