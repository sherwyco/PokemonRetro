package com.herokuapp.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageManager {

  public BufferedImage getPokemonImage(String pokemon) {
    BufferedImage hostpokemon = null;
    try {
      hostpokemon = ImageIO.read(new File("src/main/resources/Pokemon/" + pokemon + ".png"));
    } catch (IOException e) {

      e.printStackTrace();
    }
    return hostpokemon;
  }

}
