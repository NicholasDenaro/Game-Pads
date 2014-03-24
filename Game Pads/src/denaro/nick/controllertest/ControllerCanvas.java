package denaro.nick.controllertest;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import net.java.games.input.Controller;

public class ControllerCanvas extends Canvas implements XBoxControllerListener
{
	public ControllerCanvas(XBoxController controller)
	{
		this.setPreferredSize(new Dimension(640,480));
		values=new float[XBoxController.LAST_BUTTON+1];
		this.controller=controller;
		
		x=0;
		y=0;
	}
	
	@Override
	public void paint(Graphics g1)
	{
		super.paint(g1);
		
		Graphics2D g=(Graphics2D)g1;
		
		g.setColor(Color.black);
		
		int xoffset=16;
		int yoffset=16;
		
		//Left stick
		g.drawOval(xoffset,yoffset+64,64,64);
		g.setColor(Color.gray);
		g.fillOval(xoffset,yoffset+64,(int)(64*values[XBoxController.LEFTSTICK_DOWN]),(int)(64*values[XBoxController.LEFTSTICK_DOWN]));
		g.setColor(Color.red);
		float deadzone=64*controller.leftAnalogDeadZone();
		g.drawOval((int)(xoffset+32-deadzone/2),(int)(yoffset+64+32-deadzone/2),(int)deadzone,(int)deadzone);
		g.setColor(Color.black);
		g.fillRect((int)(xoffset+32+32*values[XBoxController.LEFTSTICK_X])-2,(int)(yoffset+64+32+32*values[XBoxController.LEFTSTICK_Y])-2,4,4);
		
		//right stick
		g.drawOval(xoffset+128-32,yoffset+128,64,64);
		g.setColor(Color.gray);
		g.fillOval(xoffset+128-32,yoffset+128,(int)(64*values[XBoxController.RIGHTSTICK_DOWN]),(int)(64*values[XBoxController.RIGHTSTICK_DOWN]));
		g.setColor(Color.red);
		deadzone=64*controller.rightAnalogDeadZone();
		g.drawOval((int)(xoffset+128-deadzone/2),(int)(yoffset+128+32-deadzone/2),(int)deadzone,(int)deadzone);
		g.setColor(Color.black);
		g.fillRect((int)(xoffset+128-32+32+32*values[XBoxController.RIGHTSTICK_X])-2,(int)(yoffset+128+32+32*values[XBoxController.RIGHTSTICK_Y])-2,4,4);
		
		//left trigger
		g.drawRect(xoffset,yoffset,64,16);
		g.setColor(Color.red);
		deadzone=64*controller.triggerDeadZone();
		g.drawRect((int)(xoffset+deadzone),yoffset,0,16);
		float full=64*controller.triggerFull();
		g.drawRect((int)(xoffset+full),yoffset,0,16);
		g.setColor(Color.black);
		g.fillRect(xoffset,yoffset,(int)(64*values[XBoxController.TRIGGERS]),16);
		
		//right trigger
		g.drawRect(xoffset+128,yoffset,64,16);
		deadzone=64*controller.triggerDeadZone();
		g.setColor(Color.red);
		g.drawRect((int)(xoffset+128+deadzone),yoffset,0,16);
		g.drawRect((int)(xoffset+128+full),yoffset,0,16);
		g.setColor(Color.black);
		g.fillRect(xoffset+128,yoffset,(int)(64*-values[XBoxController.TRIGGERS]),16);
		
		//buttons!!
		//A
		g.drawOval(xoffset+128+32,yoffset+128-16-16,16,16);
		g.fillOval(xoffset+128+32,yoffset+128-16-16,(int)(16*values[XBoxController.A]),(int)(16*values[XBoxController.A]));
		//B
		g.drawOval(xoffset+128+32+16,yoffset+128-16-16-16,16,16);
		g.fillOval(xoffset+128+32+16,yoffset+128-16-16-16,(int)(16*values[XBoxController.B]),(int)(16*values[XBoxController.B]));
		//X
		g.drawOval(xoffset+128+16,yoffset+128-16-16-16,16,16);
		g.fillOval(xoffset+128+16,yoffset+128-16-16-16,(int)(16*values[XBoxController.X]),(int)(16*values[XBoxController.X]));
		//Y
		g.drawOval(xoffset+128+32,yoffset+128-16-16-16-16,16,16);
		g.fillOval(xoffset+128+32,yoffset+128-16-16-16-16,(int)(16*values[XBoxController.Y]),(int)(16*values[XBoxController.Y]));
		
		//back&start
		//back
		g.drawOval(xoffset+64+16,yoffset+64+32,16,8);
		g.fillOval(xoffset+64+16,yoffset+64+32,(int)(16*values[XBoxController.BACK]),(int)(8*values[XBoxController.BACK]));
		
		//start
		g.drawOval(xoffset+64+32+8,yoffset+64+32,16,8);
		g.fillOval(xoffset+64+32+8,yoffset+64+32,(int)(16*values[XBoxController.START]),(int)(8*values[XBoxController.START]));
		
		//D-pad
		g.drawOval(xoffset+32,yoffset+64+64,64,64);
		float data=values[XBoxController.DPAD];
		double dir=data*Math.PI*2;
		double xx=-Math.cos(dir);
		double yy=-Math.sin(dir);
		if(data!=0)
			g.drawLine(xoffset+32+32,yoffset+64+64+32,xoffset+32+32+(int)(xx*32),yoffset+64+64+32+(int)(yy*32));
		
		//Left Bumper
		g.drawRect(xoffset,yoffset+32,64,16);
		g.fillRect(xoffset,yoffset+32,(int)(64*values[XBoxController.LEFTBUMPER]),(int)(16*values[XBoxController.LEFTBUMPER]));
		
		//Right Bumper
		g.drawRect(xoffset+128,yoffset+32,64,16);
		g.fillRect(xoffset+128,yoffset+32,(int)(64*values[XBoxController.RIGHTBUMPER]),(int)(16*values[XBoxController.RIGHTBUMPER]));
		
		
		//draw the "player"
		if(controller.isInDeadZone(XBoxController.LEFTSTICK_X))
		{
			x+=values[XBoxController.LEFTSTICK_X];
		}
		if(controller.isInDeadZone(XBoxController.LEFTSTICK_Y))
		{
			y+=values[XBoxController.LEFTSTICK_Y];
		}
		
		g.fillOval((int)x-4,(int)y-4,8,8);
	}
	
	private float[] values;
	
	private XBoxController controller;
	
	private double x, y;

	@Override
	public void buttonPressed(XBoxButtonEvent event)
	{
		values[event.getButtonCode()]=event.pollData();
	}

	@Override
	public void buttonReleased(XBoxButtonEvent event)
	{
		values[event.getButtonCode()]=event.pollData();
	}

	@Override
	public void analogMoved(XBoxAnalogEvent event)
	{
		if(event.getButtonCode()!=XBoxController.TRIGGERS)
			values[event.getButtonCode()+1]=event.pollDataX();
		values[event.getButtonCode()]=event.pollDataY();
	}
}
