package com.herokuapp.sprite;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Spritesheet {
	int x;
	int y;
	int width;
	int height;
	int rows;
	int columns;
	int count;
	int currentFrame = 0;
	String fileName;
	BufferedImage spritesheet;
	int pose = 0;
	boolean moving = false;
	BufferedImage[] sprites;
	int delay = 10;
	Animation[] anim;
	
	public Spritesheet(int x, int y, int width, int height, int rows, int columns, int count, String fileName) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.rows = rows;
		this. columns = columns;
		this.count = count;
		
		this.fileName = fileName;
		
		//spritesheet = Toolkit.getDefaultToolkit().getImage(fileName);
		try {
			spritesheet = ImageIO.read(new File(fileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(fileName + " not found");
			e.printStackTrace();
		}
		
		sprites = new BufferedImage[rows * columns];
		
//		for(int i = 0; i < rows; i++) {
//			for(int j = 0; j < columns; j++) {
//				sprites[(i*columns) + j] = spritesheet.getSubimage(j*spritesheet.getWidth()/columns, i*spritesheet.getHeight()/rows, width, height);
//			}
//		}
		
	
		
		
	}
	
	public void moveUp(int dist) {
		y -= dist;
		pose = 0;
		moving = true;		
	}
	
	public void moveDown(int dist) {
		y += dist;
		pose = 1;
		moving = true;		
	}

	public void moveLeft(int dist) {
		x -= dist;
		pose = 2;
		moving = true;
	}
	
	public void moveRight(int dist) {
		x += dist;
		pose = 3;
		moving = true;		
   }
	
	public void draw(Graphics g){

		g.drawImage(spritesheet, x, y, 150*2, 220*2, null);
		g.drawImage(sprites[currentFrame], 500, 500, width*3, height *3,  null);
		if(delay <= 0) {
			delay = 10;
			currentFrame++;
			if(currentFrame > rows * columns -1) {
				currentFrame = 0;
			}
		}
		delay--;
	}
	

}
