package com.herokuapp.PokemonRetro;

import javax.swing.JFrame;
import com.herokuapp.Panels.GamePanel;

public class App {

  public static void main(String[] args) {

    JFrame window = new JFrame("Pokemon Retro");
    window.setContentPane(new GamePanel());
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setResizable(false);
    window.pack();
    window.setVisible(true);
  }

}
