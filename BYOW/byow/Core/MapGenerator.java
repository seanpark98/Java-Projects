package byow.Core;

//import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
//import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class MapGenerator {
    //TERenderer ter = new TERenderer();
    ArrayList<Point> floors = new ArrayList<>();
    ArrayList<Point> enemies = new ArrayList<>();
    int floorarea = 0;
    Random r;
    int w;
    int h;
    TETile[][] game;
    Point character;
    Point goal;
    Boolean characterfound = false;
    Boolean goalfound = false;
    int enemiesfound = 0;

    public MapGenerator(long seed, int width, int height) {
        r = new Random(seed);
        w = width;
        h = height;
        worldGenerator();
        //ter.initialize(w, h);
        //TETile[][] ret = gameGenerator();
        //ter.renderFrame(ret);
    }

    public TETile[][] worldGenerator() {
        game = new TETile[w][h];
        for (int x = 0; x < w; x += 1) {
            for (int y = 0; y < h; y += 1) {
                game[x][y] = Tileset.GRASS;
            }
        }
        Point begin = randomStartTile();
        floors.add(begin);
        floorarea += 1;
        game[begin.x][begin.y] = Tileset.FLOOR2;
        Point start = begin;
        generateFloors(start);
        generateRooms();
        generateWall();
        generateStars();
        generatePlayer();
        generateEnemy();
        generateGoal();
        return game;
    }
    private void generateRooms() {
        int rn = randomRoomCount();
        for (int i = 0; i < rn; i++) {
            Point p = floors.get(randomFloor());
            generateRandomRoom(p, randomRoomLength());

        }
    }
    private void generateStars() {
        for (int i = 0; i < 40; i++) {
            Point star = randomStartTile();
            if (game[star.x][star.y].description().equals("space")) {
                game[star.x][star.y] = Tileset.STAR;
            } else {
                continue;
            }
        }
    }
    private void generateFloors(Point s) {
        if (floorarea >= 800) {
            return;
        } else {
            Boolean z = randomDirection();
            Boolean x = randomDirection();
            Point hp1 = generateRandomHallway(new Point(s.x, s.y), z, x, randomLength());
            generateFloors(hp1);
        }
    }

    private Point generateRandomRoom(Point p, int l) {
        int endx = p.x;
        int endy = p.y;
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < l; j++) {
                Point floor = new Point(p.x + j, p.y + i);
                if (floor.x <= 0 || floor.x >= 79 || floor.y <= 0 || floor.y >= 28) {
                    break;
                }
                if (floors.contains(floor)) {
                    break;
                }
                floors.add(floor);
                game[floor.x][floor.y] = Tileset.FLOOR2;
                floorarea += 1;
                endx = floor.x;
                endy = floor.y;

            }
        }
        return new Point(endx, endy);
    }

    //private Point genereateLHallway(TETile[][] w, Point p, Boolean b, Boolean u, int l) {
    //}
    private Point generateRandomHallway(Point p, Boolean b, Boolean u, int l) {
        if (b) {
            int end = p.x;
            if (u) {
                for (int i = 0; i < l; i++) {
                    Point floor = new Point(p.x + i, p.y);
                    if (floor.x <= 0 || floor.x >= 79 || floor.y <= 0 || floor.y >= 29) {
                        continue;
                    }
                    if (floors.contains(floor)) {
                        continue;
                    }
                    floors.add(floor);
                    game[floor.x][floor.y] = Tileset.FLOOR2;
                    floorarea += 1;
                    end = floor.x;
                }
            } else {
                for (int i = 0; i < l; i++) {
                    Point floor = new Point(p.x - i, p.y);
                    if (floor.x <= 0 || floor.x >= 79 || floor.y <= 0 || floor.y >= 29) {
                        continue;
                    }
                    if (floors.contains(floor)) {
                        continue;
                    }
                    floors.add(floor);
                    game[floor.x][floor.y] = Tileset.FLOOR2;
                    floorarea += 1;
                    end = floor.x;
                }
            }
            return new Point(end, p.y);
        } else {
            int end = p.y;
            if (u) {
                for (int i = 0; i < l; i++) {
                    Point floor = new Point(p.x, p.y + i);
                    if (floor.x <= 0 || floor.x >= 79 || floor.y <= 0 || floor.y >= 29) {
                        continue;
                    }
                    if (floors.contains(floor)) {
                        continue;
                    }
                    floors.add(floor);
                    game[floor.x][floor.y] = Tileset.FLOOR2;
                    floorarea += 1;
                    end = floor.y;
                }
            } else {
                for (int i = 0; i < l; i++) {
                    Point floor = new Point(p.x, p.y - i);
                    if (floor.x <= 0 || floor.x >= 79 || floor.y <= 0 || floor.y >= 29) {
                        continue;
                    }
                    if (floors.contains(floor)) {
                        continue;
                    }
                    floors.add(floor);
                    game[floor.x][floor.y] = Tileset.FLOOR2;
                    floorarea += 1;
                    end = floor.y;
                }
            }
            return new Point(p.x, end);
        }
    }
    private void generatePlayer() {
        while (!characterfound) {
            Point player = randomStartTile();
            if (game[player.x][player.y].description().equals("floor")) {
                game[player.x][player.y] = Tileset.PLAYER;
                characterfound = true;
                character = player;
            } else {
                continue;
            }
        }
    }
    private void generateEnemy() {
        while (enemiesfound < 3) {
            Point player = randomStartTile();
            if (game[player.x][player.y].description().equals("floor")) {
                enemies.add(player);
                enemiesfound += 1;
            } else {
                continue;
            }
        }
    }
    private void generateGoal() {
        while (!goalfound) {
            Point player = randomStartTile();
            if (game[player.x][player.y].description().equals("floor")) {
                goalfound = true;
                goal = player;
            } else {
                continue;
            }
        }
    }

    private void generateWall() {
        for (Point e: floors) {
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    if (game[e.x + i][e.y + j].description().equals("space")) {
                        game[e.x + i][e.y + j] = Tileset.WALL2;
                    }
                }
            }
        }
    }
    private Point randomStartTile() {
        int startx = r.nextInt(78);
        int starty = r.nextInt(30);
        return new Point(startx + 1, starty + 1);
    }
    private Boolean randomDirection() {
        //true is horizontal, //true is up
        //false is vertical, //false is down
        return r.nextBoolean();
    }

    private int randomLength() {
        return r.nextInt(9) + 1;
    }
    private int randomRoomCount() {
        return r.nextInt(20) + 10;
    }
    private int randomRoomLength() {
        return r.nextInt(5) + 1;
    }
    private int randomFloor() {
        return r.nextInt(floors.size() - 1);
    }

}
