package com.github.drxaos.edu.spacerace;

import com.github.drxaos.edu.spacerace.models.Player;
import com.github.drxaos.edu.spacerace.view.MapCreator;
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
        Player player_green = new Player("/player-green.png");
        player_green.setLayer(LAYER_SHIP);
        player_green.setImageCenterX(40);
        player_green.setImageCenterY(50);
        player_green.setObjectWidth(1);
        player_green.setObjectHeight(1);
        Player player_red = new Player("/player-red.png");
        player_red.setLayer(LAYER_SHIP);
        player_red.setImageCenterX(40);
        player_red.setImageCenterY(50);
        player_red.setObjectWidth(1);
        player_red.setObjectHeight(1);
        Player ufoSprite = new Player("/ufo.png");
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
        BaseSprite player_green_tail = new BaseSprite("");
        player_green_tail.setSprite(tailPrototype.makeVisibleForParent(player_green));
        BaseSprite player_red_tail = new BaseSprite("");
        player_red_tail.setSprite(tailPrototype.makeVisibleForParent(player_red));

        HashMap<Integer, BaseSprite> spriteHashMap = new HashMap<Integer, BaseSprite>();
        spriteHashMap.put(0, meteorSprite);
        spriteHashMap.put(1, player_green);
        spriteHashMap.put(2, player_red);
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

            if (control.isKeyDown(KeyEvent.VK_LEFT)) {
                // влево
                player_green.addSpeed(-0.06);
            }
            if (control.isKeyDown(KeyEvent.VK_RIGHT)) {
                // вправо
                player_green.addSpeed(0.06);
            }
            if (control.isKeyDown(KeyEvent.VK_UP)) {
                // ускорение
                player_green.addAcceleration(Math.cos(player_green.getSpeed()) * 0.005,
                        Math.sin(player_green.getSpeed()) * 0.005);

                player_green_tail.setVisibility(true);
            } else {
                // нет ускорения - нет шлейфа
                player_green_tail.setVisibility(false);
            }

            // Двигаем корабль игрока
            player_green.move();

            // Камера следует за игроком
            game.moveCamera(player_green.getX(), player_green.getY());

            // Искусственно уменьшаем отрыв компьютера от игрока, чтобы было интереснее играть
            player_green.overheadCompetitive(player_red);

            // Корабль компьютера ищет минимальный номер в прямой видимости
            int target_x = 0;
            int target_y = 0;
            int min = Integer.MAX_VALUE;
            // смотрим в разные стороны и ищем минимальный номер
            for (int a = 0; a < 18; a++) { // 18 шагов по 20 градусов
                double search_x = player_red.getX();
                double search_y = player_red.getY();
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
            double computer_current_velocity
                    = Math.sqrt(Math.pow(player_red.getVx(), 2) + Math.pow(player_red.getVy(), 2));
            double computer_current_velocity_angle
                    = Math.atan2(player_green.getVy(), player_green.getVx());

            // азимут до цели
            double target_angle = Math.atan2(target_y - player_red.getY(), target_x - player_red.getX());

            // поправка от текущего угла
            double angle_shift = target_angle - player_red.getSpeed();

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
                player_red.addSpeed(0.06);
            }
            if (angle_shift < -0.1) {
                // налево
                player_red.addSpeed(-0.06);
            }
            // ускоряемся, если скорость < 0.3 или если она направлена не на цель
            boolean should_accelerate = (computer_current_velocity < 0.3 || Math.cos(velocity_angle_shift) < 0.6);
            if (Math.abs(angle_shift) < Math.PI / 4 && should_accelerate) {
                // угол незначительный, можно включать ускорение
                player_red.addAcceleration(Math.cos(player_red.getSpeed()) * 0.005, Math.sin(player_red.getSpeed()) * 0.005);
                player_red_tail.setVisibility(true);
            } else {
                // нет ускорения - нет шлейфа
                player_red_tail.setVisibility(false);
            }
            player_red.rotate(player_red.getSpeed());

            // Двигаем корабль компьютера
            player_red.move();

            // Наблюдение за кораблем компьютера (для отладки)
            //spriter.setViewportShift(computer_x, computer_y);

            // Столкновения игрока с астероидами
            for (int i = 0; i < mapCreator.getWall_counter(); i++) {
                player_green.collapse(wall[i], 0.7, 0.7, 0);
            }

            // Столкновения игрока с НЛО
            for (int i = 0; i < mapCreator.getUfo_counter(); i++) {
                player_green.collapse(ufo[i], 0.7, 0.7, 1);
            }

            // Столкновения компьютера с астероидами
            for (int i = 0; i < mapCreator.getWall_counter(); i++) {
                player_red.collapse(wall[i], 0.7, 0.7, 0);
            }

            // Столкновения компьютера с НЛО
            for (int i = 0; i < mapCreator.getUfo_counter(); i++) {
                player_red.collapse(ufo[i], 0.7, 0.7, 1);
            }

            // Столкновения игрока с компьютером
            player_green.collapse(player_red, 0.7, 0.7, 1);

            for (int i = 0; i < mapCreator.getUfo_counter(); i++) {
                // Двигаем НЛО
                ufo[i].move();
                // гасим скорость НЛО, чтобы он быстрее остановился
                ufo[i].multiplyAcceleration(0.9, 0.9);
                ufo[i].positionate();
            }

            game.stopDraw(); // конец синхронизации
            Thread.sleep(30);
        }
    }
}
