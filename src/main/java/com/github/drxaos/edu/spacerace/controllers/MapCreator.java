package com.github.drxaos.edu.spacerace.controllers;

import com.github.drxaos.edu.spacerace.models.BaseSprite;
import com.github.drxaos.spriter.Spriter;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import static com.github.drxaos.edu.spacerace.models.Core.*;

/**
 * Created by kotvaska on 25.09.2016.
 */
public class MapCreator implements Creator {
    private BufferedImage map_image;
    private HashMap<Integer, BaseSprite> hashMap;

    private int wall_counter = 0;
    private int ufo_counter = 0;

    public MapCreator(BufferedImage map_image) {
        this.map_image = map_image;
    }

    public MapCreator(BufferedImage map_image, HashMap<Integer, BaseSprite> hashMap) {
        this.map_image = map_image;
        this.hashMap = hashMap;
    }

    @Override
    public void add(Spriter spriter) {
        if (hashMap != null) {
            add(spriter, hashMap);
        }
    }

    /**
     *  Create map
     * @param spriter
     * @param hashMap map where key is case for sprite
     */
    public void add(Spriter spriter, HashMap<Integer, BaseSprite> hashMap) {
        for (int y = 0; y < 100; y++) {
            for (int x = 0; x < 100; x++) {
                int[] pixel = new int[4]; // RGBA
                map_image.getData().getPixel(x, y, pixel);
                // схлапываем RGB-составляющие цвета в 3-битное число
                int type = (pixel[0] & 1) + ((pixel[1] & 1) << 1) + ((pixel[2] & 1) << 2);
                switch (type) {
                    case (0):
                        // Черный - Стена
                        hashMap.get(0).makeVisible(x, y);
                        wall_x[wall_counter] = x;
                        wall_y[wall_counter] = y;
                        wall_counter++;

                        ai_map[x][y] = -1; // стена
                        break;
                    case (1):
                        // Красный - Красный корабль
                        player_red.setPos(x, y);
                        computer_a = -Math.PI / 2;
                        computer_x = x;
                        computer_y = y;
                        computer_vx = 0;
                        computer_vy = 0;
                        break;
                    case (2):
                        // Зеленый - Зеленый корабль
                        player_green.setPos(x, y).setAngle(-Math.PI / 2);
                        player_a = -Math.PI / 2;
                        player_x = x;
                        player_y = y;
                        player_vx = 0;
                        player_vy = 0;
                        break;
                    case (3):
                        // Желтый - Звезды
                        // у всех звезд разный размер, поэтому вместо createGhost() - clone() (у каждой звезды своя копия изображения)
                        hashMap.get(3).makeVisibleClone(x, y, Math.random() * 0.4 + 0.4);
                        break;
                    case (4):
                        // Синий - НЛО
                        hashMap.get(4).makeVisible(x, y);
                        ufo[ufo_counter] = hashMap.get(4).getSprite();
                        ufo_x[ufo_counter] = x;
                        ufo_y[ufo_counter] = y;
                        ufo_vx[ufo_counter] = 0;
                        ufo_vy[ufo_counter] = 0;
                        ufo_counter++;
                        break;
                    case (5):
                        // Фиолетовый
                        break;
                    case (6):
                        // Голубой - Финиш
                        ai_map[x][y] = 1; // цель
                        break;
                    case (7):
                        // Белый - Пусто
                        ai_map[x][y] = 0; // неразмеченная клетка
                        break;
                }
            }
        }

        applyWaveAlgorithm();

    }

    private void applyWaveAlgorithm() {
        // Размечаем карту для управления кораблем компьютера
        // Применен "Волновой алгоритм" для окрестности фон Неймана
        // https://ru.wikipedia.org/wiki/%D0%90%D0%BB%D0%B3%D0%BE%D1%80%D0%B8%D1%82%D0%BC_%D0%9B%D0%B8
        int cells = 1;
        while (cells > 0) {
            cells = 0; // если новых размеченных клеток не будет, значит надо выйти из цикла

            for (int y = 1; y < 99; y++) {
                for (int x = 1; x < 99; x++) {

                    if (ai_map[x][y] < 0) {
                        // -1 - это стены, пропускаем
                        continue;
                    }

                    // ищем рядом наименьшую пронумерованную клетку, и присваиваем текущей следующий номер
                    int min = Integer.MAX_VALUE;

                    // смотрим вправо, вниз, влево, вверх
                    if (ai_map[x + 1][y + 0] > 0 && ai_map[x + 1][y + 0] < min) {
                        min = ai_map[x + 1][y + 0];
                    }
                    if (ai_map[x + 0][y + 1] > 0 && ai_map[x + 0][y + 1] < min) {
                        min = ai_map[x + 0][y + 1];
                    }
                    if (ai_map[x - 1][y + 0] > 0 && ai_map[x - 1][y + 0] < min) {
                        min = ai_map[x - 1][y + 0];
                    }
                    if (ai_map[x + 0][y - 1] > 0 && ai_map[x + 0][y - 1] < min) {
                        min = ai_map[x + 0][y - 1];
                    }

                    if (min > 0 && min < Integer.MAX_VALUE && ai_map[x][y] == 0) {
                        ai_map[x][y] = min + 1;
                        cells++;
                    }
                }
            }
        }

    }

    public void print() {
        // Вывод карты на консоль
        if (ai_map != null) {
            for (int y = 0; y < 100; y++) {
                for (int x = 0; x < 100; x++) {
                    System.out.print(String.format("%03d", ai_map[x][y]) + " ");
                }
                System.out.println();
            }
        }
    }

    public int getUfo_counter() {
        return ufo_counter;
    }

    public int getWall_counter() {
        return wall_counter;
    }

    public void setUfo_counter(int ufo_counter) {
        this.ufo_counter = ufo_counter;
    }

    public void setWall_counter(int wall_counter) {
        this.wall_counter = wall_counter;
    }

    public void setHashMap(HashMap<Integer, BaseSprite> hashMap) {
        this.hashMap = hashMap;
    }
}
