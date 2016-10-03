package com.github.drxaos.edu.spacerace;

import com.github.drxaos.edu.spacerace.controllers.BaseControls;
import com.github.drxaos.edu.spacerace.controllers.MapCreator;
import com.github.drxaos.edu.spacerace.models.BaseSprite;
import com.github.drxaos.edu.spacerace.models.BasicGame;
import com.github.drxaos.spriter.Spriter;
import com.github.drxaos.spriter.SpriterUtils;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import static com.github.drxaos.edu.spacerace.models.Core.*;

public class SpaceRace {

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

        BasicGame game = new BasicGame(new Spriter(GAME_NAME));
        // Размер окна 15x15, размер корабля или предмета 1x1, размер игрового поля 100x100
        // Координатные оси направлены вправо и вниз
        game.setWindowSize(15, 15);
        //spriter.setViewportWidth(50);
        //spriter.setViewportHeight(50);

        // Загружаем и показываем надпись "Loading..."
        game.setBackground(Color.BLACK);
        game.startDraw();

        BaseSprite loading = new BaseSprite("/loading.png");
        loading.setImageCenterX(367 / 2);
        loading.setImageCenterY(62 / 2);
        loading.setObjectWidth(5);
        loading.add(game.getSpriter());

        game.stopDraw();
        game.pauseDraw();

        loading.getSprite().setVisible(false);

        // Фон повторяется, чтобы заполнить все игровое поле
        // Создаем прототип спрайта, устанавливаем размеры и помещаем на фоновый слой
        BaseSprite background = new BaseSprite("/background.jpg");
        background.setLayer(LAYER_BG);
        background.setImageCenterX(512);
        background.setImageCenterY(512);
        background.setWidth(25);
        background.setHeight(25);
        background.addProtoWithAllSetProperties(game.getSpriter());
        for (int x = 0; x <= 100; x += 25) {
            for (int y = 0; y <= 100; y += 25) {
                // Создаем "облегченный" экземпляр спрайта фона и ставим в координаты x,y
                // ("облегченный" - означает, что изображение одно для всех копии, для экономии памяти)
                // Прототипы по-умолчанию невидимы, поэтому устанавливаем флаг видимости в true
                background.makeVisible(x, y);
            }
        }

        // Загружаем картинки
        BufferedImage tail_image = SpriterUtils.loadImageFromResource("/tail.png");
        BufferedImage map_image = SpriterUtils.loadImageFromResource("/map.png");
        BaseSprite trg = new BaseSprite("/point.png");
        trg.setImageCenterX(256 / 2);
        trg.setImageCenterY(256 / 2);
        trg.setObjectWidth(0.5);
        trg.add(game.getSpriter());

        // Объекты
        BaseSprite player_green = new BaseSprite("/player-green.png");
        player_green.setLayer(LAYER_SHIP);
        player_green.setImageCenterX(40);
        player_green.setImageCenterY(50);
        player_green.setObjectWidth(1);
        player_green.setObjectHeight(1);
        BaseSprite player_red = new BaseSprite("/player-red.png");
        player_red.setLayer(LAYER_SHIP);
        player_red.setImageCenterX(40);
        player_red.setImageCenterY(50);
        player_red.setObjectWidth(1);
        player_red.setObjectHeight(1);
        BaseSprite ufoSprite = new BaseSprite("/ufo.png");
        ufoSprite.setLayer(LAYER_UFO);
        ufoSprite.setImageCenterX(45);
        ufoSprite.setImageCenterY(45);
        ufoSprite.setObjectWidth(1);
        ufoSprite.setObjectHeight(1);
        BaseSprite starSprite = new BaseSprite("/star.png");
        starSprite.setLayer(LAYER_STAR);
        starSprite.setImageCenterX(50);
        starSprite.setImageCenterY(50);
        starSprite.setObjectWidth(0.5);
        starSprite.setObjectHeight(0.5);
        BaseSprite meteorSprite = new BaseSprite("/meteor.png");
        meteorSprite.setLayer(LAYER_WALL);
        meteorSprite.setImageCenterX(50);
        meteorSprite.setImageCenterY(50);
        meteorSprite.setObjectWidth(1);
        meteorSprite.setObjectHeight(1);

        ufoSprite.addProto(game.getSpriter());
        starSprite.addProto(game.getSpriter());
        meteorSprite.addProto(game.getSpriter());

        // Корабли
        player_green.add(game.getSpriter());
        player_red.add(game.getSpriter());

        // Шлейфы кораблей
        BaseSprite tailPrototype = new BaseSprite("/tail.png");
        tailPrototype.setLayer(LAYER_SHIP_TAIL);
        meteorSprite.setImageCenterX(41);
        meteorSprite.setImageCenterY(8);
        meteorSprite.setWidth(0.4);
        meteorSprite.setHeight(0.2);
        meteorSprite.setX(-0.2);
        tailPrototype.addProtoWithAllSetProperties(game.getSpriter());

        // setParent закрепляет спрайт на другом спрайте и центром координат для него становится середина родительского
        player_green_tail = tailPrototype.makeVisibleForParent(player_green);
        player_red_tail = tailPrototype.makeVisibleForParent(player_red);

        HashMap<Integer, BaseSprite> spriteHashMap = new HashMap<Integer, BaseSprite>();
        spriteHashMap.put(0, meteorSprite);
        spriteHashMap.put(3, starSprite);
        spriteHashMap.put(4, ufoSprite);
        MapCreator mapCreator = new MapCreator(map_image, spriteHashMap);
        mapCreator.add(game.getSpriter());

        // Объект для считывания клавиш, нажимаемых пользователем
        Spriter.Control control = game.getSpriter().getControl();

        // Отрисовываем все что загрузили
        game.playDraw();

        while (true) {
            game.startDraw(); // синхронизация логики и потока отрисовки, чтобы не было графических "артефактов"

            BaseControls baseControls = new BaseControls(player_green);
            if (control.isKeyDown(KeyEvent.VK_LEFT) || control.isKeyDown(KeyEvent.VK_RIGHT)) {
                baseControls.setDirection(KeyEvent.KEY_PRESSED);
            }
            baseControls.setAcceleration(control.isKeyDown(KeyEvent.VK_UP));

            // Двигаем корабль игрока
            player_x += player_vx;
            player_y += player_vy;
            player_green.getSprite().setPos(player_x, player_y);

            // Камера следует за игроком
            game.getSpriter().setViewportShift(player_x, player_y);

            // Искусственно уменьшаем отрыв компьютера от игрока, чтобы было интереснее играть
            int player_step = ai_map[(int) Math.round(player_x)][(int) Math.round(player_y)];
            int computer_step = ai_map[(int) Math.round(computer_x)][(int) Math.round(computer_y)];
            if (computer_step - player_step > 20) {
                double deltaX = computer_x - player_x;
                double deltaY = computer_y - player_y;
                double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
                if (distance > 15) { // корабль компьютера сейчас не видно
                    computer_step = player_step + 20; // надо отставать не больше чем на 20 шагов
                    // ищем подходящее место для телепортации
                    for (int x = 1; x < 99; x++) {
                        for (int y = 1; y < 99; y++) {
                            if (ai_map[x][y] == computer_step) {
                                deltaX = x - player_x;
                                deltaY = y - player_y;
                                distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
                                if (distance > 15) { // только если игрок не видит эту клетку
                                    computer_x = x;
                                    computer_y = y;
                                }
                            }
                        }
                    }
                }
            }

            // Корабль компьютера ищет минимальный номер в прямой видимости
            int target_x = 0;
            int target_y = 0;
            int min = Integer.MAX_VALUE;
            // смотрим в разные стороны и ищем минимальный номер
            for (int a = 0; a < 18; a++) { // 18 шагов по 20 градусов
                double search_x = computer_x;
                double search_y = computer_y;
                while (true) {
                    int sx = (int) Math.round(search_x);
                    int sy = (int) Math.round(search_y);
                    if (ai_map[sx][sy] < 0) {
                        // стена, дельше не смотрим
                        break;
                    }
                    if (ai_map[sx][sy] < min) {
                        // запоминаем минимум
                        min = ai_map[sx][sy];
                        target_x = sx;
                        target_y = sy;
                    }
                    // продвигаемся в выбранном направлении
                    search_x += Math.cos(Math.PI * 2 / 18 * a) * 0.3;
                    search_y += Math.sin(Math.PI * 2 / 18 * a) * 0.3;
                }
            }
            // Цель компьютера (для отладки)
            //trg.setPos(target_x, target_y).setVisible(true);

            // текущая скорость
            double computer_current_velocity = Math.sqrt(computer_vx * computer_vx + computer_vy * computer_vy);
            double computer_current_velocity_angle = Math.atan2(computer_vy, computer_vx);

            // азимут до цели
            double target_angle = Math.atan2(target_y - computer_y, target_x - computer_x);

            // поправка от текущего угла
            double angle_shift = target_angle - computer_a;

            // поправка от текущего угла скорости
            double velocity_angle_shift = target_angle - computer_current_velocity_angle;

            // нормализуем поправку
            while (angle_shift > Math.PI) {
                angle_shift -= Math.PI * 2;
            }
            while (angle_shift < -Math.PI) {
                angle_shift += Math.PI * 2;
            }

            if (angle_shift > 0.1) {
                // направо
                computer_a += 0.06;
            }
            if (angle_shift < -0.1) {
                // налево
                computer_a -= 0.06;
            }
            // ускоряемся, если скорость < 0.3 или если она направлена не на цель
            boolean should_accelerate = (computer_current_velocity < 0.3 || Math.cos(velocity_angle_shift) < 0.6);
            if (Math.abs(angle_shift) < Math.PI / 4 && should_accelerate) {
                // угол незначительный, можно включать ускорение
                computer_vx += Math.cos(computer_a) * 0.005;
                computer_vy += Math.sin(computer_a) * 0.005;
                player_red_tail.setVisible(true);
            } else {
                // нет ускорения - нет шлейфа
                player_red_tail.setVisible(false);
            }
            player_red.getSprite().setAngle(computer_a);

            // Двигаем корабль компьютера
            computer_x += computer_vx;
            computer_y += computer_vy;
            player_red.getSprite().setPos(computer_x, computer_y);

            // Наблюдение за кораблем компьютера (для отладки)
            //spriter.setViewportShift(computer_x, computer_y);

            // Столкновения игрока с астероидами
            for (int i = 0; i < mapCreator.getWall_counter(); i++) {
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
            for (int i = 0; i < mapCreator.getUfo_counter(); i++) {
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
            for (int i = 0; i < mapCreator.getWall_counter(); i++) {
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
            for (int i = 0; i < mapCreator.getUfo_counter(); i++) {
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

            for (int i = 0; i < mapCreator.getUfo_counter(); i++) {
                // Двигаем НЛО
                ufo_x[i] += ufo_vx[i];
                ufo_y[i] += ufo_vy[i];
                // гасим скорость НЛО, чтобы он быстрее остановился
                ufo_vx[i] *= 0.9;
                ufo_vy[i] *= 0.9;
                ufo[i].setPos(ufo_x[i], ufo_y[i]);
            }

            game.stopDraw(); // конец синхронизации
            Thread.sleep(30);
        }
    }
}
