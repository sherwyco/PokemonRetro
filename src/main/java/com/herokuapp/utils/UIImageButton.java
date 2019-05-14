package com.herokuapp.utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;


public class UIImageButton extends UIObject {

  private BufferedImage images;
  private ClickListener clicker;
  private boolean with_image;
  private String text;

  public UIImageButton(float x, float y, int width, int height, BufferedImage images,
      ClickListener clicker) {
    super(x, y, width, height);
    this.images = images;
    this.clicker = clicker;
    with_image = true;
  }

  public UIImageButton(float x, float y, int width, int height, String text,
      ClickListener clicker) {
    super(x, y, width, height);
    this.clicker = clicker;
    with_image = false;
    this.text = text;


  }

  @Override
  public void tick() {}

  public void render(Graphics g) {
    drawBottons(g);
  }

  @Override
  public void onClick() {
    clicker.onClick();
  }

  private void drawBottons(Graphics g) {
    if (with_image) {
      if (hovering) {
        g.setColor(Color.GRAY.darker());
        g.fillRect((int) x - 5, (int) y - 5, width + 10, height + 10);
        g.setColor(Color.GRAY);
        if (images != null)
          g.drawImage(images, (int) x, (int) y, width, height, null);
      } else {
        g.setColor(Color.GRAY.brighter());
        g.fillRect((int) x - 5, (int) y - 5, width + 10, height + 10);
        g.setColor(Color.BLUE);
        if (images != null)
          g.drawImage(images, (int) x, (int) y, width, height, null);
      }
    } else {
      if (hovering) {
        g.setColor(Color.RED.brighter());
        g.fillRect((int) x - 5, (int) y - 5, width + 30, height + 30);
        g.setColor(Color.darkGray);
        g.fillRect((int) x, (int) y, width + 20, height + 20);
        g.drawString("button", (int) x, (int) y);


      } else {
        g.setColor(Color.GRAY.brighter());
        g.fillRect((int) x - 5, (int) y - 5, width + 30, height + 30);
        g.setColor(Color.BLUE);
        g.fillRect((int) x, (int) y, width + 20, height + 20);
        g.drawString("button", (int) x, (int) y);

      }



    }

  }



}
