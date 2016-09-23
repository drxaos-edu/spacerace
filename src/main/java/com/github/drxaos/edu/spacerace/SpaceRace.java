package com.github.drxaos.edu.spacerace;

import java.awt.*;
import java.io.IOException;
import com.github.drxaos.edu.sourcedata.*;
import com.github.drxaos.spriter.*;

/**
 * Основной класс и точка входа. Выполняет связку объектов и первоначальную настройку.
 */
public class SpaceRace {

    // Все приватные методы выделены для облегчения чтения кода.
    // Для концентрации на объекте а не на тонкостях его создания.
    
    private final static int SIZE = 100;
    private final static int REPEAT_STEP = 25;
    private static ISpriteSource spriteSource;
    
    public static void main(String[] args) throws Exception {

        IImageSource imageSource = new ImageSource();
        spriteSource = new SpriteSource(imageSource);
        
        Spriter spriter = GetSprinter();
        CreateBackground(spriter);

        Painter painter = new Painter(spriteSource, spriter, SIZE);
        paintAll(spriter, painter);

        IPhysicsLaw physicsLaw = new PhysicsLaw();
        painter.update(physicsLaw);
    }

    private static void paintAll(Spriter spriter, Painter map)
    {
        // Painter должен быть готов для выполения метода
        spriter.unpause();
    }

    private static Spriter GetSprinter() throws IOException
    {
        Spriter spriter = new Spriter("Space Race");
        spriter.setViewportWidth(15);
        spriter.setViewportHeight(15);
        spriter.setBackgroundColor(Color.BLACK);
        spriter.beginFrame();
        Spriter.Sprite loading = spriteSource.getLoadingTitle(spriter); // надпись "Loading..."
        spriter.endFrame();
        spriter.pause(); // останавливаем отрисовку и загружаем все остальное
        
        loading.setVisible(false);
        
        return spriter;
    }
    
    private static void CreateBackground(Spriter spriter) throws IOException
    {
        // Фон повторяется, чтобы заполнить все игровое поле
        // Создаем прототип спрайта, устанавливаем размеры и помещаем на фоновый слой
        // Координатные оси направлены вправо и вниз      
        
        for (int posX = 0; posX <= SIZE; posX += REPEAT_STEP) {
            for (int posY = 0; posY <= SIZE; posY += REPEAT_STEP) {
                
                // Создаем "облегченный" экземпляр спрайта фона и ставим в координаты x,y
                // ("облегченный" - означает, что изображение одно для всех копии, для экономии памяти)
                // Прототипы по-умолчанию невидимы, поэтому устанавливаем флаг видимости в true
                
                spriteSource.getBackground(spriter)
                    .createGhost()
                    .setPos(posX, posY)
                    .setVisible(true);
            }
        }
    }
}
