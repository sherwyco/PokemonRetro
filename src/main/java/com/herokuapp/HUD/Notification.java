package com.herokuapp.HUD;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;



public class Notification {
  // placeholder system to spawn pokemon
  String[] pokemonz = {"charmander", "charmander", "charmander", "charmander", "charmander",
      "charmander", "charmander", "charmander", "charmander", "charmander", "charmander",
      "charmander", "jolteon", "rayquaza", "gigalith", "lucario", "lugia", "zeraora",
      "Superdreadnought Rail Cannon Gustav Max"}; // the last one is a yugioh card
  Random random = new Random();
  String foundPokemon;
  // ------------------------------------

  Color bgColor = new Color(53, 112, 22);
  int notifWidth = 1920;
  int notifX = 0;
  int notifY = 1050;


  int notifHeight = 200;

  String message;
  BufferedImage pokemon;

  public Notification(String foundPokemonName) {
    foundPokemon = foundPokemonName;
    message = "You encountered a " + foundPokemon + "!";

    try {
      pokemon = ImageIO.read(new File("src/main/resources/Pokemon/" + foundPokemon + ".png"));
    } catch (IOException e) {
      // TODO Auto-generated catch block
      System.out.println(foundPokemon + ".png" + " not found");
      e.printStackTrace();
    }
  }


  public void draw(Graphics g) {
    g.setColor(bgColor);
    g.fillRoundRect(notifX, notifY - notifHeight, notifWidth, notifHeight, 30, 30);

    g.setColor(Color.YELLOW);
    g.fillRoundRect(notifX + notifWidth - 350, notifY - notifHeight + 55, 200, 50, 30, 30);
    g.setColor(Color.WHITE);
    g.drawString("Battle", notifX + notifWidth - 315, notifY - notifHeight + 100);

    g.setColor(Color.WHITE);
    g.drawString(message, notifX + 100, notifY - notifHeight + 100);
    g.drawImage(pokemon, notifX + notifWidth - 130, notifY - notifHeight + 30, 100, 100, null);
  }
}
