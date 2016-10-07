package com.github.drxaos.edu.spacerace.graphics;

import com.github.drxaos.spriter.Spriter;

import java.io.IOException;

/*
    Фабрика спрайтов
 */
public interface ISpriteFactory {
    Spriter.Sprite getTailPrototypeSprite() throws IOException;

    Spriter.Sprite getTailCloneSprite(Spriter.Sprite parent) throws IOException;

    Spriter.Sprite getStarPrototypeSprite() throws IOException;

    Spriter.Sprite getWallPrototypeSprite() throws IOException;

    Spriter.Sprite getUfoPrototypeSprite() throws IOException;

    Spriter.Sprite getLoadingSprite() throws IOException;

    Spriter.Sprite getGreenPlayerSprite() throws IOException;

    Spriter.Sprite getRedPlayerSprite() throws IOException;

    Spriter.Sprite getBackgroundPrototypeSprite() throws IOException;

    Spriter.Sprite getTrgSprite() throws IOException;

    Spriter getSpriter();
}
