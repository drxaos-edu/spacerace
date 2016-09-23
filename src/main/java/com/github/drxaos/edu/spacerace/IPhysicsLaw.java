package com.github.drxaos.edu.spacerace;

import com.github.drxaos.edu.spaceobjects.MovableSpaceObject;
import com.github.drxaos.edu.spaceobjects.SpaceObject;
import com.github.drxaos.edu.spaceobjects.SpaceShip;

/**
 * Представляет собой объект управляющий другими объектами на карте.
 * Выделен в интерфейс, для гибкости в случае необходимости изменения
 * поведения при взаимодействии обхектов на карте
 */
public interface IPhysicsLaw
{
    /**
     * Двигает объекты из массива объектов на карте
     * @param obj - массив объектов
     * @param objCounter - текущиее число объектов
     */
    public void moveMovableSpaceObjects(SpaceShip[] obj, int objCounter);
    
    /**
     * Описывает столкновение двух движимых объектов
     */
    public void impactMovableSpaceObjects(
            MovableSpaceObject obj1, MovableSpaceObject obj2);
    
    /**
     * Описывает столкновение движимого объекта с движимыми объектами 
     * из массива объектов на карте 
     * @param obj - объект
     * @param objs - массив объектов
     * @param objsCounter - текущиее число объектов
     */
    public void impactMovableSpaceObjects(
            MovableSpaceObject obj, MovableSpaceObject[] objs, int objsCounter);
    
    /**
     * Описывает столкновение движимого объекта со стационарными объектами 
     * из массива объектов на карте 
     * @param obj - объект
     * @param objs - массив объектов
     * @param objsCounter - текущиее число объектов
     */
    public void impactMovableWithSpaceObject(
            MovableSpaceObject obj1, SpaceObject[] objы, int objыCounter);
}
