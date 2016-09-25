package com.github.drxaos.edu.spacerace.models;

import com.github.drxaos.spriter.Spriter;

/**
 * Created by kotvaska on 25.09.2016.
 */
public class Core {
    // при наложении спрайты с меньшим номером слоя перекрываются спрайтами с большим номером слоя
    public final static int
            LAYER_BG = 0,
            LAYER_STAR = LAYER_BG + 50,
            LAYER_OBJECTS = 500,
            LAYER_SHIP = LAYER_OBJECTS,
            LAYER_SHIP_TAIL = LAYER_SHIP - 100,
            LAYER_WALL = LAYER_OBJECTS,
            LAYER_UFO = LAYER_WALL + 50;

    // углы, координаты, скорости кораблей
    public static double
            player_a, player_x, player_y, player_vx, player_vy,
            computer_a, computer_x, computer_y, computer_vx, computer_vy;

    // спрайты кораблей
    public static Spriter.Sprite
            player_green,
            player_green_tail,
            player_red,
            player_red_tail;

    // коордитаны астероидов
    public static double[]
            wall_x = new double[10000],
            wall_y = new double[10000];

    // коордитаны и скорости НЛО
    public static double[]
            ufo_x = new double[10000],
            ufo_y = new double[10000],
            ufo_vx = new double[10000],
            ufo_vy = new double[10000];

    // спрайты НЛО
    public static Spriter.Sprite[] ufo = new Spriter.Sprite[10000];

    // карта для разметки пути компьютера
    public static int[][] ai_map = new int[100][100];

}
