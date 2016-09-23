package com.github.drxaos.edu.spacerace;

import java.io.IOException;
import com.github.drxaos.edu.pilots.*;
import com.github.drxaos.edu.sourcedata.*;
import com.github.drxaos.edu.spaceobjects.*;
import com.github.drxaos.spriter.Spriter;

/**
 * Отвечает за отрисовку объектов на карте
 */
public class Painter
{
    // Все приватные методы выделены для облегчения чтения кода.
    // Для концентрации на объекте а не на тонкостях его создания.
    
    private int ufoCounter = 0;
    private int asteroidCounter = 0;
    
    private Spriter spriter;
    private ISpriteSource spriteSource;
    
    private SpaceShip playerShip;
    private SpaceShip compShip;
    private SpaceShip[] ufos;
    private SpaceObject[] asteroids;
    
    private int[][] compRoute;
    
    public Painter(ISpriteSource spriteSource, Spriter spriter, int size) throws IOException
    { 
        this.spriteSource = spriteSource;
        this.spriter = spriter;
        
        Spriter.Sprite tailPrototype = spriteSource.getTail(spriter); // прототип шлейфа
        this.playerShip = getShip(spriteSource.getShipA(spriter), tailPrototype);
        this.compShip = getShip(spriteSource.getShipB(spriter), tailPrototype);
        
        int spaceObjectsAmount = size * 100;
        
        ufos = new SpaceShip[spaceObjectsAmount];
        
        asteroids = new SpaceObject[spaceObjectsAmount];
        for (int i = 0; i < asteroids.length; i++)
            asteroids[i] = new SpaceObject();
        
        compRoute = new int[size][size];
        
        loadMaAndSetObjects(compRoute);
    }
    
    private SpaceShip getShip(Spriter.Sprite ship, Spriter.Sprite taipPrototype)
    {
        Spriter.Sprite tail = spriteSource.getAttachedTail(taipPrototype, ship);
        return new SpaceShip(ship, tail);
    }
    
    private void loadMaAndSetObjects(int[][] compRoute) throws IOException
    {
        int length = compRoute.length;
        for (int y = 0; y < length; y++) {
            for (int x = 0; x < length; x++) {
                int type = GetType(x, y);
                DoWithType(x, y, type);
            }
        }
        prepareCompRoute();
    }

    private int GetType(int x, int y) throws IOException
    {
        int[] pixel = spriteSource.getPixel(x, y);
        // схлапываем RGB-составляющие цвета в 3-битное число
        int type = (pixel[0] & 1) + ((pixel[1] & 1) << 1) + ((pixel[2] & 1) << 2);
        return type;
    }
    
    private void DoWithType(int x, int y, int type) throws IOException
    {
        switch (type) {
            case (0):
                IfBlackPaintWall(x, y);
                break;
            case (1):
                IfRedPaintCompShip(x, y);
                break;
            case (2):
                IfGreenPaintPlayerShip(x, y);
                break;
            case (3):
                IfYellowPaintStars(x, y);
                break;
            case (4):
                IfDarkBluePaintUfo(x, y);
                break;
            case (5):
                IfPurpleDoNothing(x, y);
                break;
            case (6):
                IfBlueMarkFinish(x, y);
                break;
            case (7):
                IfWhiteMarkEmpty(x, y);
                break;
        }
    }

    private void IfBlackPaintWall(int x, int y) throws IOException
    {
        spriteSource.getWall(spriter).createGhost().setPos(x, y).setVisible(true);
        asteroids[asteroidCounter].setPos(x, y);
        asteroidCounter++;
        compRoute[x][y] = -1; // стена
    }

    private void IfRedPaintCompShip(int x, int y)
    {
        double angle = -Math.PI / 2;
        compShip.setPos(x, y).setAngle(angle).stop();
    }
    
    private void IfGreenPaintPlayerShip(int x, int y)
    {
        double angle = -Math.PI / 2;
        playerShip.setPos(x, y).setAngle(angle).stop();
    }
    
    private void IfYellowPaintStars(int x, int y) throws IOException
    {
        spriteSource.getStar(spriter)
            .clone() // у всех звезд разный размер, поэтому вместо createGhost() - clone()
            .setPos(x, y)
            .setWidthProportional(Math.random() * 0.4 + 0.4)
            .setVisible(true);
    }
    
    private void IfDarkBluePaintUfo(int x, int y) throws IOException
    {
        Spriter.Sprite ufo = spriteSource.getUfo(spriter)
                .createGhost()
                .setPos(x, y)
                .setVisible(true);
        
        ufos[ufoCounter] = new SpaceShip(ufo);
        ufos[ufoCounter].setPos(x, y);
        ufos[ufoCounter].stop();
        ufoCounter++;
    }
    
    private void IfBlueMarkFinish(int x, int y)
    {
        compRoute[x][y] = 1; // цель
    }
    
    private void IfPurpleDoNothing(int x, int y)
    {
    }
    
    private void IfWhiteMarkEmpty(int x, int y)
    {
        compRoute[x][y] = 0; // неразмеченная клетка
    }
    
    public void update(IPhysicsLaw physicsLaw) throws InterruptedException
    {
        Pilot player = new Pilot(playerShip, spriter);
        AiPilot comp = new AiPilot(compShip, spriter);
        
        while (true) {
            spriter.beginFrame(); // синхронизация логики и потока отрисовки, чтобы не 
                                  // было графических "артефактов"
            
            player.checkControl();
            player.moveShip();
            cameraFollowShip(spriter, playerShip);
            
            teleportOneShipFromAnother(compRoute, playerShip, compShip);
            comp.tuneAutomaticRoute(compRoute);
            comp.moveShip();
            
            physicsLaw.impactMovableWithSpaceObject(playerShip, asteroids, asteroidCounter);
            physicsLaw.impactMovableSpaceObjects(playerShip, ufos, ufoCounter);
            
            physicsLaw.impactMovableWithSpaceObject(compShip, asteroids, asteroidCounter);
            physicsLaw.impactMovableSpaceObjects(compShip, ufos, ufoCounter);
            
            physicsLaw.impactMovableSpaceObjects(playerShip, compShip);
            
            physicsLaw.moveMovableSpaceObjects(ufos, ufoCounter);
 
            spriter.endFrame(); // конец синхронизации
            
            Thread.sleep(30);
        }
    }
    
    public void cameraFollowShip(Spriter spriter, SpaceShip ship)
    {
        spriter.setViewportShift(ship.getPosX(), ship.getPosY());
    }
    
    public void prepareCompRoute()
    {
        // Применен "Волновой алгоритм" для окрестности фон Неймана
        // https://ru.wikipedia.org/wiki/%D0%90%D0%BB%D0%B3%D0%BE%D1%80%D0%B8%D1%82%D0%BC_%D0%9B%D0%B8
        int cells = 1;
        while (cells > 0) {
            cells = 0; // если новых размеченных клеток не будет, значит надо выйти из цикла
            
            int limit = compRoute.length - 1;
            for (int y = 1; y < limit; y++) {
                for (int x = 1; x < limit; x++) {

                    if (compRoute[x][y] < 0) {
                        // -1 - это стены, пропускаем
                        continue;
                    }

                    // ищем рядом наименьшую пронумерованную клетку, 
                    // и присваиваем текущей следующий номер
                    int min = Integer.MAX_VALUE;

                    // смотрим вправо, вниз, влево, вверх
                    if (compRoute[x + 1][y + 0] > 0 && compRoute[x + 1][y + 0] < min) {
                        min = compRoute[x + 1][y + 0];
                    }
                    if (compRoute[x + 0][y + 1] > 0 && compRoute[x + 0][y + 1] < min) {
                        min = compRoute[x + 0][y + 1];
                    }
                    if (compRoute[x - 1][y + 0] > 0 && compRoute[x - 1][y + 0] < min) {
                        min = compRoute[x - 1][y + 0];
                    }
                    if (compRoute[x + 0][y - 1] > 0 && compRoute[x + 0][y - 1] < min) {
                        min = compRoute[x + 0][y - 1];
                    }

                    if (min > 0 && min < Integer.MAX_VALUE && compRoute[x][y] == 0) {
                        compRoute[x][y] = min + 1;
                        cells++;
                    }
                }
            }
        }
    }
    
    public void teleportOneShipFromAnother(
            int[][] route, SpaceShip base, SpaceShip toBeTeleported)
    {
        final int lagStep = 20; // надо отставать не больше чем на 20 шагов
        
        double basePosX = base.getPosX();
        double basePosY = base.getPosY();
        double toBeTeleportedPosX = toBeTeleported.getPosX();
        double toBeTeleportedPosY = toBeTeleported.getPosY();
        
        int baseStep = route[(int) Math.round(basePosX)][(int) Math.round(basePosY)];
        int toBeTeleportedStep = 
                route[(int) Math.round(toBeTeleportedPosX)][(int) Math.round(toBeTeleportedPosY)];
        
        if (toBeTeleportedStep - baseStep > lagStep) {
            
            double deltaX = toBeTeleportedPosX - basePosX;
            double deltaY = toBeTeleportedPosY - base.getPosY();
            double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
            
            if (shipDoesNotSeeAnother(distance)) {
                toBeTeleportedStep = baseStep + lagStep;
                teleport(route, toBeTeleportedStep, base, toBeTeleported);
            }
        }
    }
    
    private void teleport(
            int[][] compRoute, int compStep, SpaceShip base, SpaceShip toBeTeleported)
    {
        double deltaX;
        double deltaY;
        double distance;
        
        for (int x = 1; x < 99; x++) {
            for (int y = 1; y < 99; y++) {
                if (compRoute[x][y] == compStep) {
                    deltaX = x - base.getPosX();
                    deltaY = y - base.getPosY();
                    distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
                    if (shipDoesNotSeeAnother(distance)) {
                        toBeTeleported.setPos(x, y);
                    }
                }
            }
        }
    }

    private boolean shipDoesNotSeeAnother(double distance)
    {
        return distance > 15;
    }
}
