package com.herokuapp.sprite;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Animation
{
	private BufferedImage[] images;
	private int     current = 0;
	private int     duration;
	private int     delay;
	
	public Animation(Spritesheet spritesheet, int column, int count, int duration)
	{
		images = new BufferedImage[count];
		
		for(int i = 0; i < count; i++)
			
			images[i] = spritesheet.spritesheet.getSubimage(column*spritesheet.width, i*spritesheet.height, 
															spritesheet.width, spritesheet.height);
		
		
		this.duration = duration;
		
		delay = duration;
	}
	
	
	public Image getCurrentImage() {
		if(delay == 0) {
			current++;
			if(current == images.length) current = 0;
			delay = duration;
		}
		delay--;
		
		return images[current];
	}
	
	public Image getStillImage() {
		return images[0];
	}
	

}
