package com.github.drxaos.edu.spacerace.controllers;

import com.github.drxaos.edu.spacerace.models.BaseSprite;

import java.awt.event.KeyEvent;

import static com.github.drxaos.edu.spacerace.models.Core.*;
import static com.github.drxaos.edu.spacerace.models.Core.player_green_tail;

/**
 * Created by kotvaska on 25.09.2016.
 */
public class BaseControls {
    private BaseSprite baseSprite;

    public BaseControls(BaseSprite baseSprite) {
        this.baseSprite = baseSprite;
    }

    public void setDirection(int key) {
        if (key == KeyEvent.VK_LEFT) {
            // влево
            player_a -= 0.06;
            baseSprite.getSprite().setAngle(player_a);
        }
        if (key == KeyEvent.VK_RIGHT) {
            // вправо
            player_a += 0.06;
            baseSprite.getSprite().setAngle(player_a);
        }
    }

    public void setAcceleration(boolean acceleration) {
        if (acceleration) {
            // ускорение
            player_vx += Math.cos(player_a) * 0.005;
            player_vy += Math.sin(player_a) * 0.005;
            player_green_tail.setVisible(true);
        } else {
            // нет ускорения - нет шлейфа
            player_green_tail.setVisible(false);
        }
    }

}
