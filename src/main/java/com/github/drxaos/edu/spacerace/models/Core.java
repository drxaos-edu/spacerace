package com.github.drxaos.edu.spacerace.models;

/**
 * Created by kotvaska on 25.09.2016.
 */
public class Core {
    public final static String GAME_NAME = "Space Race";

    // при наложении спрайты с меньшим номером слоя перекрываются спрайтами с большим номером слоя
    public final static int
            LAYER_BG = 0,
            LAYER_STAR = LAYER_BG + 50,
            LAYER_OBJECTS = 500,
            LAYER_SHIP = LAYER_OBJECTS,
            LAYER_SHIP_TAIL = LAYER_SHIP - 100,
            LAYER_WALL = LAYER_OBJECTS,
            LAYER_UFO = LAYER_WALL + 50;

    // коордитаны астероидов
    public static BaseSprite[] wall = new BaseSprite[10000];

    // спрайты НЛО
    public static Player[] ufo = new Player[10000];

    // карта для разметки пути компьютера
    public static int[][] ai_map = new int[100][100];

}
