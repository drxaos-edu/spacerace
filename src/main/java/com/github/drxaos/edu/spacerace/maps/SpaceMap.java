package com.github.drxaos.edu.spacerace.maps;

import com.github.drxaos.edu.spacerace.bodies.ComputerSpaceship;
import com.github.drxaos.edu.spacerace.bodies.PlayerSpaceship;
import com.github.drxaos.edu.spacerace.bodies.Ufo;
import com.github.drxaos.edu.spacerace.bodies.Wall;
import com.github.drxaos.edu.spacerace.graphics.ISpriteFactory;
import com.github.drxaos.spriter.Spriter;
import com.github.drxaos.spriter.SpriterUtils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
    Карта, содержит в себе все объекты, находящиеся в космическом пространстве
 */
public class SpaceMap {
    private static int[][] ai_map = new int[100][100];
    private final PlayerSpaceship player;
    private final ComputerSpaceship computer;
    private List<Wall> walls = new ArrayList<>();
    private List<Ufo> ufos = new ArrayList<>();

    public SpaceMap(ISpriteFactory spriteFactory) throws IOException {
        player = createPlayer(spriteFactory);
        computer = createComputer(spriteFactory);
        computer.setMap(this);
    }

    private PlayerSpaceship createPlayer(ISpriteFactory spriteFactory) throws IOException {
        Spriter.Sprite greenPlayerSprite = spriteFactory.getGreenPlayerSprite();
        return new PlayerSpaceship(greenPlayerSprite, spriteFactory.getTailCloneSprite(greenPlayerSprite));

    }

    private ComputerSpaceship createComputer(ISpriteFactory spriteFactory) throws IOException {
        Spriter.Sprite redPlayerSprite = spriteFactory.getRedPlayerSprite();
        return new ComputerSpaceship(redPlayerSprite, spriteFactory.getTailCloneSprite(redPlayerSprite));
    }

    public void create(Spriter.Sprite ufoPrototype, Spriter.Sprite wallPrototype, Spriter.Sprite starPrototype) throws IOException {
        createMap(ufoPrototype, wallPrototype, starPrototype);
        markMap();
    }

    private void createMap(Spriter.Sprite ufoPrototype, Spriter.Sprite wallPrototype, Spriter.Sprite starPrototype) throws IOException {
        BufferedImage map_image = SpriterUtils.loadImageFromResource("/map.png");
        // Загружаем карту и расставляем объекты
        for (int y = 0; y < 100; y++) {
            for (int x = 0; x < 100; x++) {
                int[] pixel = new int[4]; // RGBA
                map_image.getData().getPixel(x, y, pixel);
                // схлапываем RGB-составляющие цвета в 3-битное число
                int type = (pixel[0] & 1) + ((pixel[1] & 1) << 1) + ((pixel[2] & 1) << 2);
                switch (type) {
                    case (0):
                        // Черный - Стена
                        Wall wall = new Wall(wallPrototype.createGhost().setPos(x, y).setVisible(true));
                        wall.setX(x);
                        wall.setY(y);
                        walls.add(wall);

                        ai_map[x][y] = -1; // стена
                        break;
                    case (1):
                        // Красный - Красный корабль
                        computer.setPos();
                        computer.setAngle(-Math.PI / 2);
                        computer.setX(x);
                        computer.setY(y);
                        computer.setVelocityX(0);
                        computer.setVelocityY(0);
                        break;
                    case (2):
                        // Зеленый - Зеленый корабль
                        player.setPos();
                        player.setAngle(-Math.PI / 2);
                        player.setAngle();
                        player.setAngle(-Math.PI / 2);
                        player.setX(x);
                        player.setY(y);
                        player.setVelocityX(0);
                        player.setVelocityY(0);
                        break;
                    case (3):
                        // Желтый - Звезды
                        // у всех звезд разный размер, поэтому вместо createGhost() - clone() (у каждой звезды своя копия изображения)
                        starPrototype.clone().setPos(x, y).setWidthProportional(Math.random() * 0.4 + 0.4).setVisible(true);
                        break;
                    case (4):
                        // Синий - НЛО
                        Ufo ufo = new Ufo(ufoPrototype.createGhost().setPos(x, y).setVisible(true));
                        ufo.setX(x);
                        ufo.setY(y);
                        ufo.setVelocityX(0);
                        ufo.setVelocityY(0);
                        ufos.add(ufo);
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
    }

    private void markMap() {
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
                    if (ai_map[x + 1][y] > 0 && ai_map[x + 1][y] < min) {
                        min = ai_map[x + 1][y];
                    }
                    if (ai_map[x][y + 1] > 0 && ai_map[x][y + 1] < min) {
                        min = ai_map[x][y + 1];
                    }
                    if (ai_map[x - 1][y] > 0 && ai_map[x - 1][y] < min) {
                        min = ai_map[x - 1][y];
                    }
                    if (ai_map[x][y - 1] > 0 && ai_map[x][y - 1] < min) {
                        min = ai_map[x][y - 1];
                    }

                    if (min > 0 && min < Integer.MAX_VALUE && ai_map[x][y] == 0) {
                        ai_map[x][y] = min + 1;
                        cells++;
                    }
                }
            }
        }
    }

    public int get(int x, int y) {
        return ai_map[x][y];
    }

    public List<Wall> getWalls() {
        return walls;
    }

    public List<Ufo> getUfos() {
        return ufos;
    }

    public PlayerSpaceship getPlayer() {
        return player;
    }

    public ComputerSpaceship getComputer() {
        return computer;
    }
}
