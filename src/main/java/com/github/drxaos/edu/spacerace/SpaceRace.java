package com.github.drxaos.edu.spacerace;

import com.github.drxaos.spriter.Spriter;
import com.github.drxaos.spriter.SpriterUtils;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class SpaceRace {

    final static int LAYER_BG = 0;
    final static int LAYER_SHIP = 350;
    final static int LAYER_SHIP_TAIL = 300;

    static double player_a, player_x, player_y, player_v;
    static double computer1_a, computer1_x, computer1_y, computer1_v;
    static double computer2_a, computer2_x, computer2_y, computer2_v;
    static double computer3_a, computer3_x, computer3_y, computer3_v;
    static double[] asteroid_a, asteroid_x, asteroid_y, asteroid_v;
    static double[] wall_a, wall_x, wall_y;

    public static void movePlayer() {

    }

    public static void main(String[] args) throws Exception {

        // TODO Отрефакторить в ООП так, чтобы код можно было читать как рассказ об игре

        // 1. Составить словарь предметной области
        // 2. Выстроить иерархию классов и пакетов
        // 3. Описать свойства и поведение классов
        // 4. Найти код не вошедший в поведение классов и добавить вспомогательные классы
        //      a. Создание и связывание объектов
        //      b. Поведение внешней среды
        //      c. Ввод-вывод данных
        //      d. и т.д.


        Spriter spriter = new Spriter("Space Race");

        BufferedImage background_image = SpriterUtils.loadImageFromResource("/background.jpg");
        Spriter.Sprite backgroung = spriter.createSprite(background_image, 512, 512, 4).setLayer(LAYER_BG);

        BufferedImage player_green_image = SpriterUtils.loadImageFromResource("/player-green.png");
        BufferedImage player_red_image = SpriterUtils.loadImageFromResource("/player-red.png");
        BufferedImage player_orange_image = SpriterUtils.loadImageFromResource("/player-orange.png");
        BufferedImage player_purple_image = SpriterUtils.loadImageFromResource("/player-purple.png");
        BufferedImage tail_image = SpriterUtils.loadImageFromResource("/tail.png");
        BufferedImage ufo_image = SpriterUtils.loadImageFromResource("/ufo.png");

        Spriter.Sprite player_green = spriter.createSprite(player_green_image, 40, 50, 0.15).setLayer(LAYER_SHIP);
        Spriter.Sprite player_green_tail = spriter.createSprite(tail_image, 41, 8, 0.08, 0.035).setParent(player_green).setX(-0.02).setLayer(LAYER_SHIP_TAIL);
        Spriter.Sprite player_red = spriter.createSprite(player_red_image, 40, 50, 0.15).setLayer(LAYER_SHIP);
        Spriter.Sprite player_red_tail = spriter.createSprite(tail_image, 41, 8, 0.08, 0.035).setParent(player_red).setX(-0.02).setLayer(LAYER_SHIP_TAIL);
        Spriter.Sprite player_orange = spriter.createSprite(player_orange_image, 40, 50, 0.15).setLayer(LAYER_SHIP);
        Spriter.Sprite player_orange_tail = spriter.createSprite(tail_image, 41, 8, 0.08, 0.035).setParent(player_orange).setX(-0.02).setLayer(LAYER_SHIP_TAIL);
        Spriter.Sprite player_purple = spriter.createSprite(player_purple_image, 40, 50, 0.15).setLayer(LAYER_SHIP);
        Spriter.Sprite player_purple_tail = spriter.createSprite(tail_image, 41, 8, 0.08, 0.035).setParent(player_purple).setX(-0.02).setLayer(LAYER_SHIP_TAIL);

        player_green.setAngle(1);
        player_red.setPos(0.5, 0.5);
        player_orange.setPos(-0.5, 0.5);
        player_purple.setPos(-0.5, -0.5);

        Spriter.Control control = spriter.getControl();

        while (true) {
            if (control.isKeyDown(KeyEvent.VK_LEFT)) {
                player_a -= 0.03;
                player_green.setAngle(player_a);
            }
            if (control.isKeyDown(KeyEvent.VK_RIGHT)) {
                player_a += 0.03;
                player_green.setAngle(player_a);
            }

            Thread.sleep(30);
        }
    }

}
