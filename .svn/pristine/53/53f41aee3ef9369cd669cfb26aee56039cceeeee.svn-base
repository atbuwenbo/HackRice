package com.cuter.img;
public class Image {
	public double[][][] pixel;
	
	private int length;
	private int width;
	private int height;
	
	public Image(double[] pixel, int length, int width, int height) {
		this.pixel = new double[length][width][height];
		
		int index = 0;
		
		for (int i = 0; i < length; i++)
			for (int j = 0; j < width; j++)
				for (int k = 0; k < height; k++) {
					this.pixel[i][j][k] = pixel[index++];
				}
	}
	
	public Image(double[][][] pixel, int length, int width, int height) {
		this.pixel = pixel;
		this.length = length;
		this.width = width;
		this.height = height;
	}
	
	public int getLength() {
		return length;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
}
