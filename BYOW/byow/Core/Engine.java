package byow.Core;

//import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.awt.Color.BLACK;

public class Engine {
    //TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 33;
    Point playerposition = new Point(0, 0);
    Point goalposition = new Point(0, 0);
    Boolean gamestart = false;
    TETile[][] map;
    ArrayList<Point> en;

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() throws IOException {
        //      creates main menu
        drawMain();
//      if key typed is n
        StringBuilder seed = new StringBuilder();
        String saveSequence = "";
//        File f = new File("load.txt");
//        BufferedWriter writer = new BufferedWriter(new FileWriter(f));
        while (true) {
            while (StdDraw.hasNextKeyTyped()) {
                String character = String.valueOf(StdDraw.nextKeyTyped());
                seed = seed.append(character);
                startWorld(character);
                if (gamestart) {
                    //ter.renderFrame(map);
                    for (Point e: en) {
                        if (e.x == playerposition.x && e.y == playerposition.y) {
                            map[playerposition.x][playerposition.y] = Tileset.ENEMY;
                            //ter.renderFrame(map);
                            showTile(map);
                            gamestart = false;
                        }
                    }
                    if (goalposition.x == playerposition.x && goalposition.y == playerposition.y) {
                        map[playerposition.x][playerposition.y] = Tileset.GOAL;
                        //ter.renderFrame(map);
                        showTile(map);
                        gamestart = false;
                    }
                    movekeyboradCharacter(character);
                    //ter.renderFrame(map);
                    showTile(map);
                }
                if (!gamestart) {
                    if (character.equals("s") || character.equals("S")) {
                        System.out.println("End of seed");
                        String randomSeed = seed.toString();
                        System.out.println(randomSeed);
                        map = interactWithInputString(randomSeed);
                        gamestart = true;
                        showTile(map);
                    }
                }
                if (character.equals("q")) {
                    Pattern p = Pattern.compile(":q");
                    Matcher m = p.matcher(seed.toString());
                    if (m.find()) {
                        File f = new File("./load.txt");
                        BufferedWriter writer = new BufferedWriter(new FileWriter(f));
                        saveSequence = seed.delete(seed.length() - 2, seed.length()).toString();
                        writer.write(saveSequence);
                        writer.close();
                        System.exit(0);
                    }
                }
                if (character.equals("Q")) {
                    Pattern p = Pattern.compile(":Q");
                    Matcher m = p.matcher(seed.toString());
                    if (m.find()) {
                        File f = new File("./load.txt");
                        BufferedWriter writer = new BufferedWriter(new FileWriter(f));
                        saveSequence = seed.delete(seed.length() - 2, seed.length()).toString();
                        writer.write(saveSequence);
                        writer.close();
                        System.exit(0);
                    }
                } else {
                    StdDraw.clear(BLACK);
                    StdDraw.text(0.5, 0.7, "Please enter a seed");
                    StdDraw.text(0.5, 0.45, "The seed is: " + seed.toString());
                    StdDraw.text(0.5, 0.08, "press 's' to start!");
                }
            }
        }
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        // Fill out this method so that it run the engine using the input
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.
        StringBuilder saveFin = new StringBuilder();
        if (input.charAt(0) == 'N' || input.charAt(0) == 'n') {
            Pattern p = Pattern.compile("[0-9]+");
            Matcher m = p.matcher(input);
            long seed = 0;
            if (m.find()) {
                seed = Long.parseLong(m.group());
            }
            initialmapMaker(seed);
            int sbl = startS(input);
            String fin = input.substring(sbl);
            saveFin.append(input.substring(0, sbl));
            for (int i = 0; i < fin.length(); i++) {
                char c = fin.charAt(i);
                String character = Character.toString(c);
                if (character.equals("q")) {
                    File f = new File("./load.txt");
                    try {
                        BufferedWriter writer = new BufferedWriter(new FileWriter(f));
                        String loadSeed = saveFin.delete(saveFin.length() - 2,
                                saveFin.length()).toString();
                        writer.write(loadSeed);
                        writer.close();
                        //System.exit(0);
                    } catch (IOException e) {
                        System.out.println(e);
                    }
                }
                if (character.equals("Q")) {
                    File f = new File("./load.txt");
                    try {
                        BufferedWriter writer = new BufferedWriter(new FileWriter(f));
                        String loadSeed = saveFin.delete(saveFin.length() - 2,
                                saveFin.length()).toString();
                        writer.write(loadSeed);
                        writer.close();
                        //System.exit(0);
                    } catch (IOException e) {
                        System.out.println(e);
                    }
                } else {
                    saveFin.append(moveCharacter(character));
                }
            }

        } else {
            try {
                FileReader fr = new FileReader("./load.txt");
                BufferedReader br = new BufferedReader(fr);
                String loadText = br.readLine();
                if (loadText.equals("")) {
                    StdDraw.clear(BLACK);
                    StdDraw.text(0.5, 0.5, "There is no world to load");
                } else {
                    //ter.initialize(WIDTH, HEIGHT);
                    String ip = input;
                    String landi = loadText + input;
                    map = interactWithInputString(landi);
                    ////ter.renderFrame(map);
                }
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        //ter.initialize(WIDTH, HEIGHT);
        TETile[][] finalWorldFrame = map;
        //ter.renderFrame(map);
        return finalWorldFrame;

    }
    private void showTile(TETile [][] w) {
        while ((int) StdDraw.mouseX() >= 0 && (int) StdDraw.mouseX() <= 79) {
            StdDraw.setPenColor(new Color(186, 78, 229));
            int mousex = (int) StdDraw.mouseX();
            int mousey = (int) StdDraw.mouseY();
            String toShow = w[mousex][mousey].description();
            StdDraw.text(2, HEIGHT - 1, toShow);
            StdDraw.show();
            //ter.renderFrame(w);
            if (StdDraw.hasNextKeyTyped()) {
                break;
            }

        }
    }
    private String moveCharacter(String p) {
        String character = p;
        if (character.equals("W") || character.equals("w")) {
            int currX = playerposition.x;
            int currY = playerposition.y;
            if (!map[currX][currY + 1].description().equals("wall")) {
                map[currX][currY] = Tileset.FLOOR2;
                map[currX][currY + 1] = Tileset.PLAYER;
                playerposition = new Point(currX, currY + 1);
            }
        }
        if (character.equals("a") || character.equals("A")) {
            int currX = playerposition.x;
            int currY = playerposition.y;
            if (!map[currX - 1][currY].description().equals("wall")) {
                map[currX][currY] = Tileset.FLOOR2;
                map[currX - 1][currY] = Tileset.PLAYER;
                playerposition = new Point(currX - 1, currY);
            }
        }
        if (character.equals("s") || character.equals("S")) {
            int currX = playerposition.x;
            int currY = playerposition.y;
            if (!map[currX][currY - 1].description().equals("wall")) {
                map[currX][currY] = Tileset.FLOOR2;
                map[currX][currY - 1] = Tileset.PLAYER;
                playerposition = new Point(currX, currY - 1);
            }
        }
        if (character.equals("d") || character.equals("d")) {
            int currX = playerposition.x;
            int currY = playerposition.y;
            if (!map[currX + 1][currY].description().equals("wall")) {
                map[currX][currY] = Tileset.FLOOR2;
                map[currX + 1][currY] = Tileset.PLAYER;
                playerposition = new Point(currX + 1, currY);
            }
        }
        return character;
    }
    private void movekeyboradCharacter(String p) {
        String character = p;
        if (character.equals("W") || character.equals("w")) {
            int currX = playerposition.x;
            int currY = playerposition.y;
            if (!map[currX][currY + 1].description().equals("wall")) {
                map[currX][currY] = Tileset.FLOOR2;
                map[currX][currY + 1] = Tileset.PLAYER;
                playerposition = new Point(currX, currY + 1);
            }
        }
        if (character.equals("a") || character.equals("A")) {
            int currX = playerposition.x;
            int currY = playerposition.y;
            if (!map[currX - 1][currY].description().equals("wall")) {
                map[currX][currY] = Tileset.FLOOR2;
                map[currX - 1][currY] = Tileset.PLAYER;
                playerposition = new Point(currX - 1, currY);
            }
        }
        if (character.equals("s") || character.equals("S")) {
            int currX = playerposition.x;
            int currY = playerposition.y;
            if (!map[currX][currY - 1].description().equals("wall")) {
                map[currX][currY] = Tileset.FLOOR2;
                map[currX][currY - 1] = Tileset.PLAYER;
                playerposition = new Point(currX, currY - 1);
            }
        }
        if (character.equals("d") || character.equals("D")) {
            int currX = playerposition.x;
            int currY = playerposition.y;
            if (!map[currX + 1][currY].description().equals("wall")) {
                map[currX][currY] = Tileset.FLOOR2;
                map[currX + 1][currY] = Tileset.PLAYER;
                playerposition = new Point(currX + 1, currY);
            }
        }
    }
    private void drawMain() {
        StdDraw.clear(BLACK);
        StdDraw.setPenColor(186, 78, 229);
        Font font1 = new Font("VCR OSD Mono", Font.BOLD, 27);
        StdDraw.setFont(font1);
        StdDraw.text(0.5, 0.7, "CS 61B: BUILD YOUR OWN WORLD");
        Font font2 = new Font("VCR OSD Mono", Font.PLAIN, 20);
        StdDraw.setFont(font2);
        StdDraw.text(0.5, 0.35, "New Game (N)");
        StdDraw.text(0.5, 0.3, "Load Game (L)");
        StdDraw.text(0.5, 0.25, "Quit (Q)");
    }
    private void startWorld(String p) throws IOException {
        String character = p;
        if (character.equals("n") || character.equals("N")) {
            System.out.println("Please enter a random seed");
            StdDraw.clear(BLACK);
            StdDraw.text(0.5, 0.7, "Please enter a seed");
        }
        if (character.equals("l") || character.equals("L")) {
            FileReader fr = new FileReader("./load.txt");
            BufferedReader br = new BufferedReader(fr);
            String loadText = br.readLine();
            if (loadText.equals("")) {
                StdDraw.clear(BLACK);
                StdDraw.text(0.5, 0.5, "There is no world to load");
            } else {
                //ter.initialize(WIDTH, HEIGHT);
                map = interactWithInputString(loadText);
                gamestart = true;
                //ter.renderFrame(map);
            }
        }
    }
    private int startS(String s) {
        String input = s;
        int sbl = 0;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            String character = Character.toString(c);
            if (character.equals("s") || character.equals("S")) {
                sbl = i + 1;
            }
        }
        return sbl;
    }
    private void initialmapMaker(long seed) {
        MapGenerator g = new MapGenerator(seed, WIDTH, HEIGHT);
        //ter.initialize(WIDTH, HEIGHT);
        map = g.game;
        //ter.renderFrame(map);
        playerposition = g.character;
        goalposition = g.goal;
        en = g.enemies;
    }
}

