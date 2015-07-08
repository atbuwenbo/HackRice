package com.cuter.img;

public class ConversionLib {

	Image data;
	Image rgb2labResult;
	Image lab2rgbResult;
	
	public ConversionLib(){
		
	}
	
//	public static void main(String[] args){
//		double [][][] test = new double[2][2][3];
//		test[0][0][0]= 30.0;
//		test[0][0][1] = 30.0;
//		test[0][0][2] = 30.0;
//		test[0][1][0]= 60.0;
//		test[0][1][1] = 60.0;
//		test[0][1][2] = 60.0;
//		test[1][0][0]= 90.0;
//		test[1][0][1] = 90.0;
//		test[1][0][2] = 90.0;
//		test[1][1][0]= 120.0;
//		test[1][1][1] = 120.0;
//		test[1][1][2] = 120.0;
//		Image testImage = new Image(test, 2, 2, 3);
//		
//		
//		Image rgb2labI = new ConversionLib().rgb2labConversion(testImage);
//		Image lab2rgbI = new ConversionLib().lab2rgbConversion(rgb2labI);
//	}
	
	public Image rgb2labConversion(Image image){
		
		this.data = image;
		
		this.rgb2labResult = new Image(new double[image.getLength()][image.getWidth()][image.getHeight()],
				image.getLength(), 
				image.getWidth(), 
				image.getHeight());
		
		
		for (int i = 0; i < this.data.getLength(); i++)
			for (int j = 0; j < this.data.getWidth(); j++)
				{
					double []rgb = new double[3];
					rgb[0] = this.data.pixel[i][j][0];
					rgb[1] = this.data.pixel[i][j][1];
					rgb[2] = this.data.pixel[i][j][2];
					double []xyz = new double[3];
					double []lab = new double[3];
					xyz = ConversionLib.sRGB2XYZ(rgb);
					lab = ConversionLib.XYZ2Lab(xyz);
//					this.rgb2lab(this.data.pixel[i][j][0],
//							this.data.pixel[i][j][1],
//							this.data.pixel[i][j][2], lab);
					this.rgb2labResult.pixel[i][j][0] = lab[0];
					this.rgb2labResult.pixel[i][j][1] = lab[1];
					this.rgb2labResult.pixel[i][j][2] = lab[2];
				}
		
		return this.rgb2labResult;
	}
	
	
	public Image lab2rgbConversion(Image image){
		
		this.data = image;
		
		this.lab2rgbResult = new Image(new double[image.getLength()][image.getWidth()][image.getHeight()],
				image.getLength(), 
				image.getWidth(), 
				image.getHeight());
		
		
		for (int i = 0; i < this.data.getLength(); i++)
			for (int j = 0; j < this.data.getWidth(); j++)
				{
					double []lab = new double[3];
					lab[0] = this.data.pixel[i][j][0];
					lab[1] = this.data.pixel[i][j][1];
					lab[2] = this.data.pixel[i][j][2];
					double []xyz = new double[3];
					double []rgb = new double[3];
					xyz = ConversionLib.Lab2XYZ(lab);
					rgb = ConversionLib.XYZ2sRGB(xyz);
					this.lab2rgbResult.pixel[i][j][0] = rgb[0];
					this.lab2rgbResult.pixel[i][j][1] = rgb[1];
					this.lab2rgbResult.pixel[i][j][2] = rgb[2];
				}
		
		return this.lab2rgbResult;
	}
	
	
	
//	
//	private void rgb2lab(double R, double G, double B, double []lab) {
//
//
//		double r, g, b, X, Y, Z, fx, fy, fz, xr, yr, zr;
//		double Ls, as, bs;
//		double eps = 216.f/24389.f;
//		double k = 24389.f/27.f;
//
//		double Xr = 0.964221f;  // reference white D50
//		double Yr = 1.0f;
//		double Zr = 0.825211f;
//
//		// RGB to XYZ
//		r = R/255.0; //R 0..1
//		g = G/255.0; //G 0..1
//		b = B/255.0; //B 0..1
//		
//		// assuming sRGB (D65)
//		if (r <= 0.04045)
//			r = r/12;
//		else
//			r = (double) Math.pow((r+0.055)/1.055,2.4);
//		
//
//		if (g <= 0.04045)
//			g = g/12;
//		else
//			g = (double) Math.pow((g+0.055)/1.055,2.4);
//
//
//		if (b <= 0.04045)
//
//			b = b/12;
//
//		else
//
//			b = (double) Math.pow((b+0.055)/1.055,2.4);
//
//		
//
//		
//
//		X =  0.436052025f*r     + 0.385081593f*g + 0.143087414f *b;
//
//		Y =  0.222491598f*r     + 0.71688606f *g + 0.060621486f *b;
//
//		Z =  0.013929122f*r     + 0.097097002f*g + 0.71418547f  *b;
//
//		
//
//		// XYZ to Lab
//
//		xr = X/Xr;
//
//		yr = Y/Yr;
//
//		zr = Z/Zr;
//
//				
//
//		if ( xr > eps )
//
//			fx =  (double) Math.pow(xr, 1/3.);
//
//		else
//
//			fx = (double) ((k * xr + 16.) / 116.);
//
//		 
//
//		if ( yr > eps )
//
//			fy =  (double) Math.pow(yr, 1/3.);
//
//		else
//
//		fy = (double) ((k * yr + 16.) / 116.);
//
//		
//
//		if ( zr > eps )
//
//			fz =  (double) Math.pow(zr, 1/3.);
//
//		else
//
//			fz = (double) ((k * zr + 16.) / 116);
//
//		
//
//		Ls = ( 116 * fy ) - 16;
//
//		as = 500*(fx-fy);
//
//		bs = 200*(fy-fz);
//
//		
//
//		lab[0] = (double) (2.55*Ls + .5);
//
//		lab[1] = (double) (as + .5); 
//
//		lab[2] = (double) (bs + .5);       
//
//	} 
//	
	
	public static double[] Lab2XYZ(double[] Lab) {
		double[] XYZ = new double[3];
		double L, a, b;
		double fx, fy, fz;
		double Xn, Yn, Zn;
		Xn = 95.04;
		Yn = 100;
		Zn = 108.89;

		L = Lab[0];
		a = Lab[1];
		b = Lab[2];

		fy = (L + 16) / 116;
		fx = a / 500 + fy;
		fz = fy - b / 200;

		if (fx > 0.2069) {
		XYZ[0] = Xn * Math.pow(fx, 3);
		} else {
		XYZ[0] = Xn * (fx - 0.1379) * 0.1284;
		}

		if ((fy > 0.2069) || (L > 8)) {
		XYZ[1] = Yn * Math.pow(fy, 3);
		} else {
		XYZ[1] = Yn * (fy - 0.1379) * 0.1284;
		}

		if (fz > 0.2069) {
		XYZ[2] = Zn * Math.pow(fz, 3);
		} else {
		XYZ[2] = Zn * (fz - 0.1379) * 0.1284;
		}

		return XYZ;
		}

	private static double[] XYZ2sRGB(double[] XYZ) {
		double[] sRGB = new double[3];
		double X, Y, Z;
		double dr, dg, db;
		X = XYZ[0];
		Y = XYZ[1];
		Z = XYZ[2];

		dr = 0.032406 * X - 0.015371 * Y - 0.0049895 * Z;
		dg = -0.0096891 * X + 0.018757 * Y + 0.00041914 * Z;
		db = 0.00055708 * X - 0.0020401 * Y + 0.01057 * Z;

		if (dr <= 0.00313) {
		dr = dr * 12.92;
		} else {
		dr = Math.exp(Math.log(dr) / 2.4) * 1.055 - 0.055;
		}

		if (dg <= 0.00313) {
		dg = dg * 12.92;
		} else {
		dg = Math.exp(Math.log(dg) / 2.4) * 1.055 - 0.055;
		}

		if (db <= 0.00313) {
		db = db * 12.92;
		} else {
		db = Math.exp(Math.log(db) / 2.4) * 1.055 - 0.055;
		}

		dr = dr * 255;
		dg = dg * 255;
		db = db * 255;

		dr = Math.min(255, dr);
		dg = Math.min(255, dg);
		db = Math.min(255, db);

//		sRGB[0] = (double) (dr + 0.5);
//		sRGB[1] = (double) (dg + 0.5);
//		sRGB[2] = (double) (db + 0.5);
		sRGB[0] = dr;
		sRGB[1] = dg;
		sRGB[2] = db;

		return sRGB;
		}
	
	public static double[] sRGB2XYZ(double[] sRGB) {
		double[] XYZ = new double[3];
		double sR, sG, sB;
		sR = sRGB[0];
		sG = sRGB[1];
		sB = sRGB[2];
		sR /= 255;
		sG /= 255;
		sB /= 255;

		if (sR <= 0.04045) {
		sR = sR / 12.92;
		} else {
		sR = Math.pow(((sR + 0.055) / 1.055), 2.4);
		}

		if (sG <= 0.04045) {
		sG = sG / 12.92;
		} else {
		sG = Math.pow(((sG + 0.055) / 1.055), 2.4);
		}

		if (sB <= 0.04045) {
		sB = sB / 12.92;
		} else {
		sB = Math.pow(((sB + 0.055) / 1.055), 2.4);
		}

		XYZ[0] = 41.24 * sR + 35.76 * sG + 18.05 * sB;
		XYZ[1] = 21.26 * sR + 71.52 * sG + 7.2 * sB;
		XYZ[2] = 1.93 * sR + 11.92 * sG + 95.05 * sB;

		return XYZ;
		}
	public static double[] XYZ2Lab(double[] XYZ) {
		double[] Lab = new double[3];
		double X, Y, Z;
		X = XYZ[0];
		Y = XYZ[1];
		Z = XYZ[2];
		double Xn, Yn, Zn;
		Xn = 95.04;
		Yn = 100;
		Zn = 108.89;
		double XXn, YYn, ZZn;
		XXn = X / Xn;
		YYn = Y / Yn;
		ZZn = Z / Zn;

		double fx, fy, fz;

		if (XXn > 0.008856) {
		fx = Math.pow(XXn, 0.333333);
		} else {
		fx = 7.787 * XXn + 0.137931;
		}

		if (YYn > 0.008856) {
		fy = Math.pow(YYn, 0.333333);
		} else {
		fy = 7.787 * YYn + 0.137931;
		}

		if (ZZn > 0.008856) {
		fz = Math.pow(ZZn, 0.333333);
		} else {
		fz = 7.787 * ZZn + 0.137931;
		}

		Lab[0] = 116 * fy - 16;
		Lab[1] = 500 * (fx - fy);
		Lab[2] = 200 * (fy - fz);

		return Lab;
		}
	
	
	
}
