package com.herokuapp.TileMaps;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import com.herokuapp.misc.GlobalVariables;
import com.herokuapp.sprite.SpriteSingle;

public class TilemapUtility {
  // private LinkedList tiles = new LinkedList();

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
    Tilemap newMap = new Tilemap(width, height);
    for (int y = 0; y < height; y++) {
      String line = (String) lines.get(y);
      for (int x = 0; x < line.length(); x++) {
        char ch = line.charAt(x);
        // int tile = ch - 'A';
        // && tile < tiles.size() ?????
        // if(tile >=0 && tile < 5) {
        // newMap.setTile(x, y, (Image) tiles.get(tile));;
        // }

        if (ch == 'A') {
          Tile grass = new Tile(x * 32, y * 32, "src/main/resources/sprites/grass.png", false);
          newMap.setTile(x, y, grass);
        }
        if (ch == '>') {
          Tile grass =
              new Tile(x * 32, y * 32, "src/main/resources/sprites/grass-water-right.png", true);
          newMap.setTile(x, y, grass);
        }
        if (ch == '<') {
          Tile grass =
              new Tile(x * 32, y * 32, "src/main/resources/sprites/grass-water-left.png", true);
          newMap.setTile(x, y, grass);
        }
        if (ch == 'W') {
          Tile grass = new Tile(x * 32, y * 32, "src/main/resources/sprites/water-right.png", true);
          newMap.setTile(x, y, grass);
        }
        if (ch == 'V') {
          Tile grass = new Tile(x * 32, y * 32, "src/main/resources/sprites/water-left.png", true);
          newMap.setTile(x, y, grass);
        }
        if (ch == 'X') {
          Tile grass = new Tile(x * 32, y * 32, "src/main/resources/sprites/border.png", true);
          newMap.setTile(x, y, grass);
        }
        if (ch == 'B') {
          Tile grass =
              new Tile(x * 32, y * 32, "src/main/resources/sprites/tallgrass.png", false, true);
          newMap.setTile(x, y, grass);
        }


        if (ch == '1') {
          Tile grass = new Tile(x * 32, y * 32, "src/main/resources/sprites/sand-TL.png", false);
          newMap.setTile(x, y, grass);
        }
        if (ch == '2') {
          Tile grass = new Tile(x * 32, y * 32, "src/main/resources/sprites/sand-T.png", false);
          newMap.setTile(x, y, grass);
        }
        if (ch == '3') {
          Tile grass = new Tile(x * 32, y * 32, "src/main/resources/sprites/sand-TR.png", false);
          newMap.setTile(x, y, grass);
        }
        if (ch == '4') {
          Tile grass = new Tile(x * 32, y * 32, "src/main/resources/sprites/sand-R.png", false);
          newMap.setTile(x, y, grass);
        }
        if (ch == '5') {
          Tile grass = new Tile(x * 32, y * 32, "src/main/resources/sprites/sand-BR.png", false);
          newMap.setTile(x, y, grass);
        }
        if (ch == '6') {
          Tile grass = new Tile(x * 32, y * 32, "src/main/resources/sprites/sand-B.png", false);
          newMap.setTile(x, y, grass);
        }
        if (ch == '7') {
          Tile grass = new Tile(x * 32, y * 32, "src/main/resources/sprites/sand-BL.png", false);
          newMap.setTile(x, y, grass);
        }
        if (ch == '8') {
          Tile grass = new Tile(x * 32, y * 32, "src/main/resources/sprites/sand-L.png", false);
          newMap.setTile(x, y, grass);
        }
        if (ch == '9') {
          Tile grass =
              new Tile(x * 32, y * 32, "src/main/resources/sprites/sand-center.png", false);
          newMap.setTile(x, y, grass);
        }

        if (ch == 'T') {
          Tile grass = new Tile(x * 32, y * 32, "src/main/resources/sprites/grass.png", true);
          newMap.setTile(x, y, grass);
          SpriteSingle sprite = new SpriteSingle(x * 32, y * 32, 16 * GlobalVariables.GAME_SCALE,
              33 * GlobalVariables.GAME_SCALE, 0, -1 * 16 * GlobalVariables.GAME_SCALE,
              "src/main/resources/sprites/tree.png");
          newMap.addSprite(sprite);
        }



      }
    }
    return newMap;
  }

}
