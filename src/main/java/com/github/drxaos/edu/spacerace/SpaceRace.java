package com.github.drxaos.edu.spacerace;

import com.github.drxaos.spriter.Spriter;
import com.github.drxaos.spriter.SpriterUtils;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class SpaceRace {

    // при наложении спрайты с меньшим номером слоя перекрываются спрайтами с большим номером слоя
    final static int
            LAYER_BG = 0,
            LAYER_STAR = LAYER_BG + 50,
            LAYER_OBJECTS = 500,
            LAYER_SHIP = LAYER_OBJECTS,
            LAYER_SHIP_TAIL = LAYER_SHIP - 100,
            LAYER_WALL = LAYER_OBJECTS,
            LAYER_UFO = LAYER_WALL + 50;

    // углы, координаты, скорости кораблей
    static double
            player_a, player_x, player_y, player_vx, player_vy,
            computer_a, computer_x, computer_y, computer_vx, computer_vy;

    // спрайты кораблей
    static Spriter.Sprite
            player_green,
            player_green_tail,
            player_red,
            player_red_tail;

    // коордитаны астероидов
    static double[]
            wall_x = new double[10000],
            wall_y = new double[10000];

    // коордитаны и скорости НЛО
    static double[]
            ufo_x = new double[10000],
            ufo_y = new double[10000],
            ufo_vx = new double[10000],
            ufo_vy = new double[10000];

    // спрайты НЛО
    static Spriter.Sprite[] ufo = new Spriter.Sprite[10000];

    public static void main(String[] args) throws Exception {

        // TODO Отрефакторить логику игры, используя ООП и принципы S.O.L.I.D.

        // 1. Составить словарь предметной области
        // 2. Выстроить иерархию классов и пакетов
        // 3. Описать свойства и поведение классов
        // 4. Найти код не вошедший в поведение классов и добавить вспомогательные классы
        //      a. Создание и связывание объектов
        //      b. Поведение внешней среды
        //      c. Ввод-вывод данных
        //      d. и т.д.

        // Библиотека Spriter дана для примера интеграции кода со сторонними библиотеками.
        // Подумайте, как отделить графическое представление и ввод от игровой логики.

        // ------------------------------------------------------------------

        /* https://en.wikipedia.org/wiki/Sprite_(computer_graphics)
        Спрайты (sprites) - это изображения, которые используются объектами для их же визуализации.
        Каждый объект связан с ассоциированным к себе спрайтом. Это или одиночное изображение,
        или изображение, состоящее из многих частей изображений (кадров). Для каждого образца
        объекта, программа рисует соответствующее изображение на экране в позиции (x,y) образца объекта.
        Если спрайт имеет кадры, то они последовательно повторяются, чтобы получить эффект анимации. */

        Spriter spriter = new Spriter("Space Race");
        // Размер окна 15x15, размер корабля или предмета 1x1, размер игрового поля 100x100
        // Координатные оси направлены вправо и вниз
        spriter.setViewportWidth(15);
        spriter.setViewportHeight(15);

        // Загружаем и показываем надпись "Loading..."
        spriter.setBackgroundColor(Color.BLACK);
        spriter.beginFrame();
        spriter.createSprite(SpriterUtils.loadImageFromResource("/loading.png"), 367 / 2, 62 / 2, 5);
        spriter.endFrame();
        spriter.pause(); // останавливаем отрисовку и загружаем все остальное

        // Фон повторяется, чтобы заполнить все игровое поле
        BufferedImage background_image = SpriterUtils.loadImageFromResource("/background.jpg");
        // Создаем прототип спрайта, устанавливаем размеры и помещаем на фоновый слой
        Spriter.Sprite background = spriter.createSpriteProto(background_image, 512, 512).setWidth(25).setHeight(25).setLayer(LAYER_BG);
        for (int x = 0; x <= 100; x += 25) {
            for (int y = 0; y <= 100; y += 25) {
                // Создаем "облегченный" экземпляр спрайта фона и ставим в координаты x,y
                // ("облегченный" - означает, что изображение одно для всех копии, для экономии памяти)
                // Прототипы по-умолчанию невидимы, поэтому устанавливаем флаг видимости в true
                background.createGhost().setPos(x, y).setVisible(true);
            }
        }

        // Загружаем картинки
        BufferedImage player_green_image = SpriterUtils.loadImageFromResource("/player-green.png");
        BufferedImage player_red_image = SpriterUtils.loadImageFromResource("/player-red.png");
        BufferedImage tail_image = SpriterUtils.loadImageFromResource("/tail.png");
        BufferedImage ufo_image = SpriterUtils.loadImageFromResource("/ufo.png");
        BufferedImage star_image = SpriterUtils.loadImageFromResource("/star.png");
        BufferedImage meteor_image = SpriterUtils.loadImageFromResource("/meteor.png");
        BufferedImage map_image = SpriterUtils.loadImageFromResource("/map.png");

        // Объекты
        Spriter.Sprite ufoPrototype = spriter.createSpriteProto(ufo_image, 45, 45).setWidth(1).setHeight(1).setLayer(LAYER_UFO);
        Spriter.Sprite wallPrototype = spriter.createSpriteProto(meteor_image, 50, 50).setWidth(1).setHeight(1).setLayer(LAYER_WALL);
        Spriter.Sprite starPrototype = spriter.createSpriteProto(star_image, 50, 50).setWidth(0.5).setHeight(0.5).setLayer(LAYER_STAR);

        // Корабли
        player_green = spriter.createSprite(player_green_image, 40, 50, 1).setLayer(LAYER_SHIP);
        player_red = spriter.createSprite(player_red_image, 40, 50, 1).setLayer(LAYER_SHIP);

        // Шлейфы кораблей
        Spriter.Sprite tailPrototype = spriter.createSpriteProto(tail_image, 41, 8).setWidth(0.4).setHeight(0.2).setX(-0.2).setLayer(LAYER_SHIP_TAIL);
        // setParent закрепляет спрайт на другом спрайте и центром координат для него становится середина родительского
        player_green_tail = tailPrototype.clone().setParent(player_green).setVisible(true);
        player_red_tail = tailPrototype.clone().setParent(player_red).setVisible(true);

        // Загружаем карту и расставляем объекты
        int wall_counter = 0;
        int ufo_counter = 0;
        for (int y = 0; y < 100; y++) {
            for (int x = 0; x < 100; x++) {
                int[] pixel = new int[4]; // RGBA
                map_image.getData().getPixel(x, y, pixel);
                // схлапываем RGB-составляющие цвета в 3-битное число
                int type = (pixel[0] & 1) + ((pixel[1] & 1) << 1) + ((pixel[2] & 1) << 2);
                switch (type) {
                    case (0):
                        // Черный - Стена
                        wallPrototype.createGhost().setPos(x, y).setAngle(Math.random() * Math.PI * 2).setVisible(true);
                        wall_x[wall_counter] = x;
                        wall_y[wall_counter] = y;
                        wall_counter++;
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
                        starPrototype.clone().setPos(x, y).setAngle(Math.random() * Math.PI * 2).setWidthProportional(Math.random() * 0.4 + 0.4).setVisible(true);
                        break;
                    case (4):
                        // Синий - НЛО
                        ufo[ufo_counter] = ufoPrototype.createGhost().setPos(x, y).setAngle(Math.random() * Math.PI * 2).setVisible(true);
                        ufo_x[ufo_counter] = x;
                        ufo_y[ufo_counter] = y;
                        ufo_vx[ufo_counter] = 0;
                        ufo_vy[ufo_counter] = 0;
                        ufo_counter++;
                        break;
                    case (5):
                        // Фиолетовый - Фиолетовый корабль
                        break;
                    case (6):
                        // Голубой - Финиш
                        break;
                    case (7):
                        // Белый - Пусто
                        break;
                }
            }
        }


        // Объект для считывания клавиш, нажимаемых пользователем
        Spriter.Control control = spriter.getControl();

        // Если отрисовка тормозит, то можно не выставлять случайные углы объектам (setAngle) - это значительно ускорит игру
        spriter.setDebug(true);

        // Отрисовываем все что загрузили
        spriter.unpause();

        while (true) {
            spriter.beginFrame(); // синхронизация логики и потока отрисовки, чтобы не было графических "артефактов"

            if (control.isKeyDown(KeyEvent.VK_LEFT)) {
                // влево
                player_a -= 0.06;
                player_green.setAngle(player_a);
            }
            if (control.isKeyDown(KeyEvent.VK_RIGHT)) {
                // вправо
                player_a += 0.06;
                player_green.setAngle(player_a);
            }
            if (control.isKeyDown(KeyEvent.VK_UP)) {
                // ускорение
                player_vx += Math.cos(player_a) * 0.005;
                player_vy += Math.sin(player_a) * 0.005;
                player_green_tail.setVisible(true);
            } else {
                // нет ускорения - нет шлейфа
                player_green_tail.setVisible(false);
            }

            // Двигаем корабль игрока
            player_x += player_vx;
            player_y += player_vy;
            player_green.setPos(player_x, player_y);

            // Двигаем корабль компьютера
            computer_x += computer_vx;
            computer_y += computer_vy;
            player_red.setPos(computer_x, computer_y);

            // Камера следует за игроком
            spriter.setViewportShift(player_x, player_y);

            // Столкновения игрока с астероидами
            for (int i = 0; i < wall_counter; i++) {
                double deltaX = wall_x[i] - player_x;
                double deltaY = wall_y[i] - player_y;
                double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
                // если расстояние <= 1, значит объекты столкнулись
                if (distance <= 1) {
                    // расчет изменения скоростей шаров при соударении
                    double dx = wall_x[i] - player_x;
                    double dy = wall_y[i] - player_y;
                    double angle = Math.atan2(dy, dx);
                    double targetX = player_x + Math.cos(angle);
                    double targetY = player_y + Math.sin(angle);
                    double ax = (targetX - wall_x[i]);
                    double ay = (targetY - wall_y[i]);
                    // изменяется скорость только у игрока
                    player_vx -= ax;
                    player_vy -= ay;
                    // гасим скорость, чтобы игрока не уносило обратно
                    player_vx *= 0.7;
                    player_vy *= 0.7;
                }
            }

            // Столкновения игрока с НЛО
            for (int i = 0; i < ufo_counter; i++) {
                double deltaX = ufo_x[i] - player_x;
                double deltaY = ufo_y[i] - player_y;
                double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
                // если расстояние <= 1, значит объекты столкнулись
                if (distance <= 1) {
                    // расчет изменения скоростей шаров при соударении
                    double dx = ufo_x[i] - player_x;
                    double dy = ufo_y[i] - player_y;
                    double angle = Math.atan2(dy, dx);
                    double targetX = player_x + Math.cos(angle);
                    double targetY = player_y + Math.sin(angle);
                    double ax = (targetX - ufo_x[i]);
                    double ay = (targetY - ufo_y[i]);
                    // изменяется скорость игрока
                    player_vx -= ax;
                    player_vy -= ay;
                    // гасим скорость, чтобы игрока не уносило обратно
                    player_vx *= 0.7;
                    player_vy *= 0.7;
                    // изменяется скорость НЛО
                    ufo_vx[i] += ax;
                    ufo_vy[i] += ay;
                }
            }

            // Столкновения компьютера с астероидами
            for (int i = 0; i < wall_counter; i++) {
                double deltaX = wall_x[i] - computer_x;
                double deltaY = wall_y[i] - computer_y;
                double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
                // если расстояние <= 1, значит объекты столкнулись
                if (distance <= 1) {
                    // расчет изменения скоростей шаров при соударении
                    double dx = wall_x[i] - computer_x;
                    double dy = wall_y[i] - computer_y;
                    double angle = Math.atan2(dy, dx);
                    double targetX = computer_x + Math.cos(angle);
                    double targetY = computer_y + Math.sin(angle);
                    double ax = (targetX - wall_x[i]);
                    double ay = (targetY - wall_y[i]);
                    // изменяется скорость только у компьютера
                    computer_vx -= ax;
                    computer_vy -= ay;
                    // гасим скорость, чтобы корабль не уносило обратно
                    computer_vx *= 0.7;
                    computer_vy *= 0.7;
                }
            }

            // Столкновения компьютера с НЛО
            for (int i = 0; i < ufo_counter; i++) {
                double deltaX = ufo_x[i] - computer_x;
                double deltaY = ufo_y[i] - computer_y;
                double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
                // если расстояние <= 1, значит объекты столкнулись
                if (distance <= 1) {
                    // расчет изменения скоростей шаров при соударении
                    double dx = ufo_x[i] - computer_x;
                    double dy = ufo_y[i] - computer_y;
                    double angle = Math.atan2(dy, dx);
                    double targetX = computer_x + Math.cos(angle);
                    double targetY = computer_y + Math.sin(angle);
                    double ax = (targetX - ufo_x[i]);
                    double ay = (targetY - ufo_y[i]);
                    // изменяется скорость компьютера
                    computer_vx -= ax;
                    computer_vy -= ay;
                    // гасим скорость, чтобы корабль не уносило обратно
                    computer_vx *= 0.7;
                    computer_vy *= 0.7;
                    // изменяется скорость НЛО
                    ufo_vx[i] += ax;
                    ufo_vy[i] += ay;
                }
            }

            // Столкновения игрока с компьютером
            double deltaX = player_x - computer_x;
            double deltaY = player_y - computer_y;
            double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
            // если расстояние <= 1, значит объекты столкнулись
            if (distance <= 1) {
                // расчет изменения скоростей шаров при соударении
                double dx = player_x - computer_x;
                double dy = player_y - computer_y;
                double angle = Math.atan2(dy, dx);
                double targetX = computer_x + Math.cos(angle);
                double targetY = computer_y + Math.sin(angle);
                double ax = (targetX - player_x);
                double ay = (targetY - player_y);
                // изменяются скорости кораблей
                computer_vx -= ax;
                computer_vy -= ay;
                player_vx += ax;
                player_vy += ay;
                // гасим скорости
                computer_vx *= 0.7;
                computer_vy *= 0.7;
                player_vx *= 0.7;
                player_vy *= 0.7;
            }

            for (int i = 0; i < ufo_counter; i++) {
                // Двигаем НЛО
                ufo_x[i] += ufo_vx[i];
                ufo_y[i] += ufo_vy[i];
                // гасим скорость НЛО, чтобы он быстрее остановился
                ufo_vx[i] *= 0.9;
                ufo_vy[i] *= 0.9;
                ufo[i].setPos(ufo_x[i], ufo_y[i]);
            }

            spriter.endFrame(); // конец синхронизации
            Thread.sleep(30);
        }
    }
}
