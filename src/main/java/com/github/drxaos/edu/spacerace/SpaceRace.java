package com.github.drxaos.edu.spacerace;

import com.github.drxaos.edu.spacerace.bodies.ComputerSpaceship;
import com.github.drxaos.edu.spacerace.bodies.PlayerSpaceship;
import com.github.drxaos.edu.spacerace.bodies.Ufo;
import com.github.drxaos.edu.spacerace.graphics.ISpriteFactory;
import com.github.drxaos.edu.spacerace.graphics.SpriteFactory;
import com.github.drxaos.edu.spacerace.graphics.UI;
import com.github.drxaos.edu.spacerace.maps.SpaceMap;
import com.github.drxaos.edu.spacerace.physics.Collisions;
import com.github.drxaos.spriter.Spriter;

import java.io.IOException;

// Главный класс, представляющий собой фасад
public class SpaceRace {

    // при наложении спрайты с меньшим номером слоя перекрываются спрайтами с большим номером слоя
    private static final String GAME_NAME = "SpaceRace";
    private final static int VIEWPORT_WIDTH = 15;
    private final static int VIEWPORT_HEIGHT = 15;

    private Spriter spriter = new Spriter(GAME_NAME);
    private ISpriteFactory spriteFactory = new SpriteFactory(spriter);
    private UI ui = new UI(spriteFactory);
    // карта для разметки пути компьютера
    private SpaceMap map;
    private Collisions collisions = new Collisions();

    public static void main(String[] args) throws Exception {
        new SpaceRace().run();
    }

    // Собственно, сам фасад
    private void run() throws IOException, InterruptedException {
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

        ui.setViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);

        ui.showLoadingScreen();
        ui.pauseDrawing();
        ui.hideLoadingScreen();
        ui.createBackground();
        map = createMap();
        ui.unpauseDrawing();

        loop();
    }

    private SpaceMap createMap() throws IOException {
        Spriter.Sprite ufoPrototype = spriteFactory.getUfoPrototypeSprite();
        Spriter.Sprite wallPrototype = spriteFactory.getWallPrototypeSprite();
        Spriter.Sprite starPrototype = spriteFactory.getStarPrototypeSprite();
        Spriter.Sprite trg = spriteFactory.getTrgSprite();

        SpaceMap map = new SpaceMap(spriteFactory);
        map.create(ufoPrototype, wallPrototype, starPrototype);

        return map;
    }

    private void loop() throws InterruptedException {
        // Объект для считывания клавиш, нажимаемых пользователем
        PlayerSpaceship player = map.getPlayer();
        ComputerSpaceship computer = map.getComputer();

        while (true) {
            ui.beginFrame(); // синхронизация логики и потока отрисовки, чтобы не было графических "артефактов"

            ui.handleKeyEvents(player);
            player.move();
            ui.moveViewport(player);
            computer.decreaseGap(player);
            computer.move();
            collisions.handle(player, computer, map.getUfos(), map.getWalls());
            moveUfos();

            ui.endFrame(); // конец синхронизации
            Thread.sleep(30);
        }
    }

    private void moveUfos() {
        map.getUfos().forEach(Ufo::move);
    }
}
