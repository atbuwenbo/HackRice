package com.cuter.img;
import android.graphics.Bitmap;



public class BilateralFilter {

	public Image bfltColor(Image origin) {
		System.out.println("start filtering");
		
		int w = 5;
		double[] sigma = {3, 0.1};
		
		Image A = new ConversionLib().rgb2labConversion(origin);
		
		int[][] x = new int[2 * w + 1][2 * w + 1];
		int[][] y = new int[2 * w + 1][2 * w + 1];
		
		for (int i = -w; i <= w; ++i)
			for (int j = -w; j <= w; ++j) {
				x[i + w][j + w] = j;
				y[i + w][j + w] = i;
			}
		
		double G[][] = new double[2 * w + 1][2 * w + 1];
		
		for (int i = 0; i < 2 * w + 1; ++i)
			for (int j = 0; j < 2 * w + 1; ++j) {
				G[i][j] = Math.exp(-(x[i][j] * x[i][j] + y[i][j] * y[i][j]) / (2 * sigma[0] * sigma[0]));
			}
		
		sigma[1] = 100 * sigma[1];
		
		double[][][] I = new double[2 * w + 1][2 * w + 1][3];
		double[][] dL = new double[2 * w + 1][2 * w + 1];
		double[][] da = new double[2 * w + 1][2 * w + 1];
		double[][] db = new double[2 * w + 1][2 * w + 1];
		double[][] H = new double[2 * w + 1][2 * w + 1];
		double[][] F = new double[2 * w + 1][2 * w + 1];		
		
		for (int i = 0; i < A.getLength(); ++i)
			for (int j = 0; j < A.getWidth(); ++j) {
//				if (i % 5 == 0 && j % 100 == 0){
//					System.out.println(i + " " + j);
//					Log.e("i, j","i: " +  i + ", j:" + j);
//				}
				
				int iMin = i - w > 0 ? i - w : 0;
				int iMax = i + w < A.getLength() - 1 ? i + w : A.getLength() - 1;
				int jMin = j - w > 0 ? j - w : 0;
				int jMax = j + w < A.getWidth() - 1 ? j + w : A.getWidth() - 1;
				
				double a = A.pixel[i][j][0];
				double b = A.pixel[i][j][1];
				double c = A.pixel[i][j][2];
				
				double norm_F = 0;
				double sum_B1 = 0, sum_B2 = 0, sum_B3 = 0;
				
				for (int p = iMin; p <= iMax; ++p)
					for (int q = jMin; q <= jMax; ++q)
						for (int l = 0; l < 3; ++l) {
							I[p - iMin][q - jMin][l] = A.pixel[p][q][l];
					}
				
				for (int p = 0; p < iMax - iMin + 1; ++p)
					for (int q = 0; q < jMax - jMin + 1; ++q) {
						dL[p][q] = I[p][q][0] - a;
						da[p][q] = I[p][q][1] - b;
						db[p][q] = I[p][q][2] - c;
						
						H[p][q] = Math.exp(-(dL[p][q] * dL[p][q] + da[p][q] * da[p][q] + db[p][q] * db[p][q]) / (2 * sigma[1] * sigma[1]));
						
						F[p][q] = H[p][q] * G[p + iMin - i + w][q + jMin - j + w];
						norm_F += F[p][q];
						sum_B1 += F[p][q] * I[p][q][0];
						sum_B2 += F[p][q] * I[p][q][1];
						sum_B3 += F[p][q] * I[p][q][2];
					}
				
				A.pixel[i][j][0] = sum_B1 / norm_F;
				A.pixel[i][j][1] = sum_B2 / norm_F;
				A.pixel[i][j][2] = sum_B3 / norm_F;
			}
		
		A = new ConversionLib().lab2rgbConversion(A);
		
		System.out.println("filtering finished");
		
		return A;
	}
	
	public Image addEdge(Image img) {
		System.out.println("start adding");
		
		double max_gradient = 0.2;
		double[] sharpness_levels = {3, 14};
		int quant_levels = 8;
		double min_edge_strength = 0.3;
		
		Image A = new ConversionLib().rgb2labConversion(img);
		
		double[][] GX = new double[img.getLength()][img.getWidth()];
		double[][] GY = new double[img.getLength()][img.getWidth()];
		double[][] G = new double[img.getLength()][img.getWidth()];
		double[][] S = new double[img.getLength()][img.getWidth()];
		
		for (int i = 0; i < img.getLength(); ++i)
			for (int j = 0; j < img.getWidth(); ++j) {
				if (j == 0)
					GX[i][j] = (A.pixel[i][j + 1][0] - A.pixel[i][j][0]) / 100.0;
				else if (j == img.getWidth() - 1)
					GX[i][j] = (A.pixel[i][j][0] - A.pixel[i][j - 1][0]) / 100.0;
				else
					GX[i][j] = (A.pixel[i][j + 1][0] - A.pixel[i][j - 1][0]) / 200.0;
				
				if (i == 0)
					GY[i][j] = (A.pixel[i + 1][j][0] - A.pixel[i][j][0]) / 100.0;
				else if (i == img.getLength() - 1)
					GY[i][j] = (A.pixel[i][j][0] - A.pixel[i - 1][j][0]) / 100.0;
				else
					GY[i][j] = (A.pixel[i + 1][j][0] - A.pixel[i - 1][j][0]) / 200.0;
				
				G[i][j] = Math.sqrt(GX[i][j] * GX[i][j] + GY[i][j] * GY[i][j]);
				
				if (G[i][j] > max_gradient)
					G[i][j] = max_gradient;
				
				G[i][j] /= max_gradient;
				
				S[i][j] = (sharpness_levels[1] - sharpness_levels[0]) * G[i][j] + sharpness_levels[0];
			}
		
		double[][] E = new double[img.getLength()][img.getWidth()];
		
		for (int i = 0; i < img.getLength(); ++i)
			for (int j = 0; j < img.getWidth(); ++j) {
				E[i][j] = G[i][j];
				if (E[i][j] < min_edge_strength)
					E[i][j] = 0;
			}
		
		double[][][] qB = new double[img.getLength()][img.getWidth()][img.getHeight()];
		double dq = 100.0 / (quant_levels - 1);
		
		for (int i = 0; i < img.getLength(); ++i)
			for (int j = 0; j < img.getWidth(); ++j)
				for (int k = 0; k < img.getHeight(); ++k) {
					qB[i][j][k] = A.pixel[i][j][k];
					if (k == 0) {
						qB[i][j][k] = (1.0 / dq) * qB[i][j][k];
						qB[i][j][k] = dq * Math.round(qB[i][j][k]);
						qB[i][j][k] = qB[i][j][k] + (dq / 2) * Math.tanh(S[i][j] * (A.pixel[i][j][k] - qB[i][j][k]));
					}
					A.pixel[i][j][k] = qB[i][j][k];
			}
		
		A = new ConversionLib().lab2rgbConversion(A);
		
		for (int i = 0; i < img.getLength(); ++i)
			for (int j = 0; j < img.getWidth(); ++j)
				for (int k = 0; k < img.getHeight(); ++k) {
					A.pixel[i][j][k] = (1 - E[i][j]) * A.pixel[i][j][k];
				}
		
		System.out.println("adding finished");
		
		return A;
	}
	
	public Image toGray(Image img, int style) {
		
		double base = Math.sqrt(255 * 255 * 3);
		
		double max = 0;
		
		for (int i = 0; i < img.getLength(); ++i)
			for (int j = 0; j < img.getWidth(); ++j) {
				double r = img.pixel[i][j][0];
				double g = img.pixel[i][j][1];
				double b = img.pixel[i][j][2];
				
				double t = Math.sqrt(r * r + g * g + b * b);
				
				if (t > max)
					max = t;
		}
		
		for (int i = 0; i < img.getLength(); ++i)
			for (int j = 0; j < img.getWidth(); ++j) {
				double r = img.pixel[i][j][0];
				double g = img.pixel[i][j][1];
				double b = img.pixel[i][j][2];
				
				double t = Math.sqrt(r * r + g * g + b * b);
				
				Color[] testc = null;
				
		        switch(style) {
	        	case 1:
	        		testc = new ColorRange().BatmanColor();
	        		break;
	        	case 2:
	        		testc = new ColorRange().TransformerColor();
	        		break;
	        	case 3:
	        		testc = new ColorRange().HelloKittyColor();
	        		break;
	        	case 4:
	        		testc = new ColorRange().CheGuevaraColor();
	        	default:
	        		break;
		        }
				
				int index = (int)(t / max * testc.length);
//				index--;
				if (index >= testc.length)
					index = testc.length - 1;
				if (index < 0)
					index = 0;
				
				img.pixel[i][j][0] = testc[testc.length - index - 1].getR();
				img.pixel[i][j][1] = testc[testc.length - index - 1].getG();
				img.pixel[i][j][2] = testc[testc.length - index - 1].getB();
			}
		
		return img;
	}
	
	private int max(int a, int b) {
		return a > b ? a : b;
	}
	
	public Bitmap bflt(Bitmap img, int style) {
		int height = img.getHeight();
        int width = img.getWidth();
        double ratio;
        Bitmap newBitmap;
        
        int temp = max(height, width);
        if (temp > 500) {
        	ratio = temp / 500.0;
        	newBitmap = ImageTools.zoomBitmap(img, (int)(width / ratio), (int)(height / ratio));
        }
        else
        	newBitmap = ImageTools.zoomBitmap(img, width, height);
        
        int newHeight = newBitmap.getHeight();
        int newWidth = newBitmap.getWidth();
        
        double[][][] pixel = new double[newHeight][newWidth][3];
        
        for (int i = 0; i < newHeight; i++)
        	for (int j = 0; j < newWidth; j++) {
        		int rgb = newBitmap.getPixel(j, i);
        		
        		pixel[i][j][0] = (rgb >> 16) & 0xFF;
        		pixel[i][j][1] = (rgb >> 8) & 0xFF;
        		pixel[i][j][2] = rgb & 0xFF;
        		
        	}
        
        Image t = bfltColor(new Image(pixel, newHeight, newWidth, 3));
        t = addEdge(t);
        
        switch(style) {
    	case 0:
    		break;
    	default:
    		t = toGray(t, style);
    		break;
        }
        
        
        for (int i = 0; i < newHeight; i++)
        	for (int j = 0; j < newWidth; j++) {
        		int r = (int)t.pixel[i][j][0];
        		int g = (int)t.pixel[i][j][1];
        		int b = (int)t.pixel[i][j][2];
        		newBitmap.setPixel(j, i, 0xFF000000 | (r << 16) | (g << 8) | b);
        	}
        
        return ImageTools.zoomBitmap(newBitmap, width, height);
	}

}
