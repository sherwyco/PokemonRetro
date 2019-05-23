package com.herokuapp.TileMaps;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import com.herokuapp.misc.GlobalVariables;
import com.herokuapp.sprite.SpriteSingle;

public class TilemapUtility {
  private ArrayList<Tile> tiles = new ArrayList<Tile>();

  public Tilemap loadMap(String filename) throws IOException {
    ArrayList<String> lines = new ArrayList<String>();
    int width = 0;
    int height = 0;

    BufferedReader reader = new BufferedReader(new FileReader(filename));
    while (true) {
      String line = reader.readLine();
      if (line == null) {
        reader.close();
        break;
      }

      if (!line.startsWith("#")) {
        lines.add(line);
        width = Math.max(width, line.length());
      }
    }

    // create Tile Map
    height = lines.size();

    // Make instances of tiles to be referenced to later on, doing this instead of making new
    // instance for each tile to boost performance
    tiles.add(new Tile(0, 0, "src/main/resources/sprites/grass.png", false));
    tiles.add(new Tile(0, 0, "src/main/resources/sprites/grass-water-right.png", true));
    tiles.add(new Tile(0, 0, "src/main/resources/sprites/grass-water-left.png", true));
    tiles.add(new Tile(0, 0, "src/main/resources/sprites/water.png", true));
    tiles.add(new Tile(0, 0, "src/main/resources/sprites/border.png", true));
    tiles.add(new Tile(0, 0, "src/main/resources/sprites/sand-TL.png", false));
    tiles.add(new Tile(0, 0, "src/main/resources/sprites/sand-T.png", false));
    tiles.add(new Tile(0, 0, "src/main/resources/sprites/sand-TR.png", false));
    tiles.add(new Tile(0, 0, "src/main/resources/sprites/sand-R.png", false));
    tiles.add(new Tile(0, 0, "src/main/resources/sprites/sand-BR.png", false));
    tiles.add(new Tile(0, 0, "src/main/resources/sprites/sand-B.png", false));
    tiles.add(new Tile(0, 0, "src/main/resources/sprites/sand-BL.png", false));
    tiles.add(new Tile(0, 0, "src/main/resources/sprites/sand-L.png", false));
    tiles.add(new Tile(0, 0, "src/main/resources/sprites/sand-center.png", false));
    tiles.add(new Tile(0, 0, "src/main/resources/sprites/grass.png", true));
    tiles.add(new Tile(0, 0, "src/main/resources/sprites/cave/mountain-TL.png", true));
    tiles.add(new Tile(0, 0, "src/main/resources/sprites/cave/mountain-T.png", true));
    tiles.add(new Tile(0, 0, "src/main/resources/sprites/cave/mountain-TR.png", true));
    tiles.add(new Tile(0, 0, "src/main/resources/sprites/cave/mountain-R.png", true));
    tiles.add(new Tile(0, 0, "src/main/resources/sprites/cave/mountain-BR.png", true));
    tiles.add(new Tile(0, 0, "src/main/resources/sprites/cave/mountain-B.png", true));
    tiles.add(new Tile(0, 0, "src/main/resources/sprites/cave/mountain-BL.png", true));
    tiles.add(new Tile(0, 0, "src/main/resources/sprites/cave/mountain-L.png", true));
    tiles.add(new Tile(0, 0, "src/main/resources/sprites/cave/mountain-center.png", true));
    tiles.add(new Tile_LevelChange(0, 0, "src/main/resources/sprites/cave/cave-entrance.png", false,
        20, 15, 3));

    Tilemap newMap = new Tilemap(width, height);
    for (int y = 0; y < height; y++) {
      String line = (String) lines.get(y);
      for (int x = 0; x < line.length(); x++) {
        char ch = line.charAt(x);



        if (ch == 'A') {
          // normal grass
          newMap.setTile(x, y, tiles.get(0));
        }
        if (ch == '>') {
          // water border right
          newMap.setTile(x, y, tiles.get(1));
        }
        if (ch == '<') {
          // water border left
          newMap.setTile(x, y, tiles.get(2));
        }
        if (ch == 'W') {
          // water
          newMap.setTile(x, y, tiles.get(3));
        }

        if (ch == 'X') {
          // border
          newMap.setTile(x, y, tiles.get(4));
        }

        if (ch == 'B') {
          // tall grass
          newMap.setTile(x, y, new Tile_TallGrass(0, 0,
              "src/main/resources/sprites/PokemonGrassAnimation.png", false));
        }

        // sand path---------------------------------
        if (ch == '1') {
          // sand top left
          newMap.setTile(x, y, tiles.get(5));
        }
        if (ch == '2') {
          // sand top
          newMap.setTile(x, y, tiles.get(6));
        }
        if (ch == '3') {
          // sand tr
          newMap.setTile(x, y, tiles.get(7));
        }
        if (ch == '4') {
          // sand r
          newMap.setTile(x, y, tiles.get(8));
        }
        if (ch == '5') {
          // sand br
          newMap.setTile(x, y, tiles.get(9));
        }
        if (ch == '6') {
          // sand b
          newMap.setTile(x, y, tiles.get(10));
        }
        if (ch == '7') {
          // sand bl
          newMap.setTile(x, y, tiles.get(11));
        }
        if (ch == '8') {
          // sand l
          newMap.setTile(x, y, tiles.get(12));
        }
        if (ch == '9') {
          // sand center
          newMap.setTile(x, y, tiles.get(13));
        }
        // sand path end----------------------------------

        if (ch == 'T') {
          // tree on top of grass
          newMap.setTile(x, y, tiles.get(14));
          SpriteSingle sprite = new SpriteSingle(x * 32, y * 32, 16 * GlobalVariables.GAME_SCALE,
              33 * GlobalVariables.GAME_SCALE, 0, -1 * 16 * GlobalVariables.GAME_SCALE,
              "src/main/resources/sprites/tree.png");
          newMap.addSprite(sprite);
        }

        // mountain and cave---------------------
        if (ch == 'q') {
          // mountain tl
          newMap.setTile(x, y, tiles.get(15));
        }
        if (ch == 'w') {
          // mountain t
          newMap.setTile(x, y, tiles.get(16));
        }
        if (ch == 'e') {
          // mountain tr
          newMap.setTile(x, y, tiles.get(17));
        }
        if (ch == 'r') {
          // mountain r
          newMap.setTile(x, y, tiles.get(18));
        }
        if (ch == 't') {
          // mountain br
          newMap.setTile(x, y, tiles.get(19));
        }
        if (ch == 'y') {
          // mountain b
          newMap.setTile(x, y, tiles.get(20));
        }
        if (ch == 'u') {
          // mountain bl
          newMap.setTile(x, y, tiles.get(21));
        }
        if (ch == 'i') {
          // mountain l
          newMap.setTile(x, y, tiles.get(22));
        }
        if (ch == 'o') {
          // mountain center
          newMap.setTile(x, y, tiles.get(23));
        }
        if (ch == 'p') {
          // cave entrance
          newMap.setTile(x, y, tiles.get(24));
        }
        // mountain and cave end----------------------


      }
    }
    return newMap;
  }

}
