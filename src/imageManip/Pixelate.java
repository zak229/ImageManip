package imageManip;
//import processing.core.PApplet;
import processing.core.*;
@SuppressWarnings("serial")


public class Pixelate extends PApplet{
	PImage img;
	PImage imgCopy;
	PGraphics pg;
	int x, y;
	//array to store copy of the "pixels" of the image
	int[] imagePixelsCopy;
	int[][] imageRGBCopy;
	int[][][] imageRGBCopy3;

	public void setup() {
		// load the image from file
		//img = loadImage("IMG_7567.jpg");
		//img = loadImage("IMG_8369.JPG");
		img = loadImage("IMG_7482.JPG");
		if (img == null) {
			System.err.print("The image you are trying to use is inaccessible.\n" +
					"If using Eclipse, put your image in the src directory, \n" +
					"or specify the full path.\n\n") ;
			System.exit(-1);
		}
		// set the canvas size to match the image size
		size(img.width, img.height);
		pg = createGraphics(img.width, img.height);
		// get the pixels from the image into
		// a pixels[] array of colors
		loadPixels();
		pg.loadPixels();
		//copy the image pixels to a duplicate array for manipulation
		imagePixelsCopy = new int [pixels.length];
		for (int i =0; i < pixels.length; i++){
			imagePixelsCopy[i] = img.pixels[i];
		}
		//copy image into matrix with dimension width x (height*3) using rgb values
		imageRGBCopy = new int [img.width][img.height*3];
		getRGB(imageRGBCopy);
		//copy image to 3d matrix with dimensions width x height x 3
		//[][][0] is red, [][][1] is green, [][][2] is blue
		imageRGBCopy3 = new int [img.width][img.height][3];
		getRGB(imageRGBCopy3);
		//display image in the canvas
		//image(img, 0, 0);
		
		

	}

	public void draw() {
		//display image in the canvas on each iteration of the draw method
		//image(img, 0, 0);

		//wait for a key press and perform corresponding transformation
		//pixel();
		if (keyPressed  && key == 'p') {
			pixel();		
		}
		if (keyPressed  && key == 'd') {
			pixel3();		
		}
		if (keyPressed  && key == 'l') {
			pixel11x11();		
		}
		if (keyPressed  && key == 'm') {
			pixel25x25();		
		}
		if (keyPressed  && key == 'q') {			
			pixelImageBy10();			
		}
		if (keyPressed  && key == 'w') {
			pixelImageBy20();		
		}
		if (keyPressed  && key == 'e') {
			pixelImageBy40();		
		}
		if (keyPressed  && key == 'r') {
			pixelImageBy80();		
		}
		if (keyPressed  && key == 'a') {
			pixeloImageBy10();		
		}
	}	



	/**
	 * This method blurs the image by applying 3x3 averaging filter to it.
	 * Each pixel's RGB value is replaced the the average RGD value in its 
	 * 3x3 neighborhood. 
	 */
	public void blur () {
		int pixBlue, pixRed, pixGreen;
		for (int w = 1; w < img.width-1; w++) {
			for (int h = 1; h < img.height-1; h++) {
				pixBlue = 0;
				pixRed = 0;
				pixGreen = 0;
				for (int wi = -1; wi <= 1; wi++ )
					for (int hi = -1; hi<= 1; hi++) {
						pixBlue += blue(imagePixelsCopy [(h+hi)*img.width + w+wi ]); 
						pixRed += red(imagePixelsCopy [(h+hi)*img.width + w+wi ]); 
						pixGreen += green(imagePixelsCopy [(h+hi)*img.width + w+wi ]); 
					}
				pixels[h*img.width + w] = color(pixRed/9, pixGreen/9, pixBlue/9);
			}
		}
		updatePixels();
	}
	
	public void pixeloImageBy10(){
		int pixelSize = (img.width/10)*(img.height/10);
		int red, green, blue;
		for (int w = (img.width/20)-1; w <= img.width-(img.width/20)-1; w+=(img.width/10)) {
			for (int h = (img.height/20)-1; h <= img.height-(img.height/20)-1; h+=(img.height/10)) {		
				blue = 0;
				red = 0;
				green = 0;
				for (int wi = (img.width/-20)+1; wi <= (img.width/10)-img.width/20; wi++ ){
					for (int hi = (img.height/-20)+1; hi<= (img.height/10)-img.height/20; hi++) {		
						red += (imagePixelsCopy[(h+hi)*img.width + w+wi]>>16 & 0xFF); 
						green += (imagePixelsCopy[(h+hi)*img.width + w+wi]>>8 & 0xFF); 
						blue += (imagePixelsCopy[(h+hi)*img.width + w+wi] & 0xFF); 
					}
				}
				red= red/pixelSize;
				green= green/pixelSize;
				blue= blue/pixelSize;
				for (int wi = img.width/-20+1; wi <= img.width/10-img.width/20; wi++ ){
					for (int hi = img.height/-20+1; hi<= img.height/10-img.height/20; hi++) {		
						pixels[(h+hi)*img.width + w+wi] = color(red, green, blue); 
					}
				}
			}							
		}
		updatePixels();
	}
	
	public void pixel () {
		int red, green, blue;
		for (int h = 0; h < img.height-1; h++) {
			for (int w = 0; w < img.width-1; w++) {
				red = imageRGBCopy[w][(3*h)];
				green= imageRGBCopy[w][(3*h)+1];
				blue= imageRGBCopy[w][(3*h)+2];
				//color point = color((imageRGBCopy[w][3*h], imageRGBCopy[w][3*h+1], imageRGBCopy[w][3*h+2]));
				//System.out.printf("%d, %d, %d\n",red,green,blue);
				//pixels[h*img.width + w] = color(imageRGBCopy[w][3*h]),(imageRGBCopy[w][3*h+1]),(imageRGBCopy[w][3*h+2]));
				pixels[h*img.width + w] = color(red, green, blue);
			}
		}
		updatePixels();
	}
	
	public void pixel3 () {
		int red, green, blue;
		for (int h = 0; h < img.height; h++) {
			for (int w = 0; w < img.width; w++) {
				red = imageRGBCopy3[w][h][0];
				green= imageRGBCopy3[w][h][1];
				blue= imageRGBCopy3[w][h][2];
				//color point = color((imageRGBCopy[w][3*h], imageRGBCopy[w][3*h+1], imageRGBCopy[w][3*h+2]));
				//System.out.printf("%d, %d, %d\n",red,green,blue);
				//pixels[h*img.width + w] = color(imageRGBCopy[w][3*h]),(imageRGBCopy[w][3*h+1]),(imageRGBCopy[w][3*h+2]));
				pixels[h*img.width + w] = color(red, green, blue);
			}
		}
		updatePixels();
	}
	
	public void pixel11x11(){
		int [][][] pixel11 = copy(imageRGBCopy3);
		int red, green, blue;
		for (int w = 5; w <= img.width-6; w+=11) {
			for (int h = 5; h <= img.height-6; h+=11) {
				blue = 0;
				red = 0;
				green = 0;
				for (int wi = -5; wi <= 5; wi++ ){
					for (int hi = -5; hi<= 5; hi++) {
						red += pixel11[w+wi][h+hi][0]; 
						green += pixel11[w+wi][h+hi][1]; 
						blue += pixel11[w+wi][h+hi][2]; 
					}
				}
				red= red/121;
				green= green/121;
				blue= blue/121;						
				for (int wi = -5; wi <= 5; wi++ ){
					for (int hi = -5; hi<= 5; hi++) {
						pixel11[w+wi][h+hi][0]=red; 
						pixel11[w+wi][h+hi][1]=green; 
						pixel11[w+wi][h+hi][2]=blue; 
					}
				}
			}							
		}
		for (int h = 0; h < img.height; h++) {
			for (int w = 0; w < img.width; w++) {
				red = pixel11[w][h][0];
				green= pixel11[w][h][1];
				blue= pixel11[w][h][2];
				pixels[h*img.width + w] = color(red, green, blue);
			}
		}	
		updatePixels();
	}
	
	public void pixel25x25(){
		int [][][] pixel25 = copy(imageRGBCopy3);
		int red, green, blue;
		for (int w = 12; w <= img.width-13; w+=25) {
			for (int h = 12; h <= img.height-13; h+=25) {
				blue = 0;
				red = 0;
				green = 0;
				for (int wi = -12; wi <= 12; wi++ ){
					for (int hi = -12; hi<= 12; hi++) {
						red += pixel25[w+wi][h+hi][0]; 
						green += pixel25[w+wi][h+hi][1]; 
						blue += pixel25[w+wi][h+hi][2]; 
					}
				}
				red= red/625;
				green= green/625;
				blue= blue/625;						
				for (int wi = -12; wi <= 12; wi++ ){
					for (int hi = -12; hi<= 12; hi++) {
						pixel25[w+wi][h+hi][0]=red; 
						pixel25[w+wi][h+hi][1]=green; 
						pixel25[w+wi][h+hi][2]=blue; 
					}
				}
			}							
		}
		for (int h = 0; h < img.height; h++) {
			for (int w = 0; w < img.width; w++) {
				red = pixel25[w][h][0];
				green= pixel25[w][h][1];
				blue= pixel25[w][h][2];
				pixels[h*img.width + w] = color(red, green, blue);
			}
		}	
		updatePixels();
	}
	
	
	public void pixelImageBy10(){
		int pixelSize = (img.width/10)*(img.height/10);
		int [][][] pixelBy10 = copy(imageRGBCopy3);
		int red, green, blue;
		for (int w = (img.width/20)-1; w <= img.width-(img.width/20)-1; w+=(img.width/10)) {
			for (int h = (img.height/20)-1; h <= img.height-(img.height/20)-1; h+=(img.height/10)) {		
				blue = 0;
				red = 0;
				green = 0;
				for (int wi = (img.width/-20)+1; wi <= (img.width/10)-img.width/20; wi++ ){
					for (int hi = (img.height/-20)+1; hi<= (img.height/10)-img.height/20; hi++) {		
						red += pixelBy10[w+wi][h+hi][0]; 
						green += pixelBy10[w+wi][h+hi][1]; 
						blue += pixelBy10[w+wi][h+hi][2]; 
					}
				}
				red= red/pixelSize;
				green= green/pixelSize;
				blue= blue/pixelSize;
				for (int wi = img.width/-20+1; wi <= img.width/10-img.width/20; wi++ ){
					for (int hi = img.height/-20+1; hi<= img.height/10-img.height/20; hi++) {		
						pixelBy10[w+wi][h+hi][0]=red; 
						pixelBy10[w+wi][h+hi][1]=green; 
						pixelBy10[w+wi][h+hi][2]=blue; 
					}
				}
			}							
		}
		pg.beginDraw();
		for (int h = 0; h < img.height; h++) {
			for (int w = 0; w < img.width; w++) {
				red = pixelBy10[w][h][0];
				green= pixelBy10[w][h][1];
				blue= pixelBy10[w][h][2];
				pixels[h*img.width + w] = color(red, green, blue);
				pg.pixels[h*img.width + w] = color(red, green, blue);
			}
		}	
		pg.updatePixels();
		pg.endDraw();		
		pg.save("pgby10.jpg");
		updatePixels();
	}
	
	public void pixelImageBy20(){
		int pixelSize = (img.width/20)*(img.height/20);
		int [][][] pixelBy20 = copy(imageRGBCopy3);
		int red, green, blue;
		for (int w = (img.width/40)-1; w <= img.width-(img.width/40)-1; w+=(img.width/20)) {
			for (int h = (img.height/40)-1; h <= img.height-(img.height/40)-1; h+=(img.height/20)) {		
				blue = 0;
				red = 0;
				green = 0;
				for (int wi = (img.width/-40)+1; wi <= (img.width/20)-img.width/40; wi++ ){
					for (int hi = (img.height/-40)+1; hi<= (img.height/20)-img.height/40; hi++) {		
						red += pixelBy20[w+wi][h+hi][0]; 
						green += pixelBy20[w+wi][h+hi][1]; 
						blue += pixelBy20[w+wi][h+hi][2]; 
					}
				}
				red= red/pixelSize;
				green= green/pixelSize;
				blue= blue/pixelSize;
				for (int wi = img.width/-40+1; wi <= img.width/20-img.width/40; wi++ ){
					for (int hi = img.height/-40+1; hi<= img.height/20-img.height/40; hi++) {		
						pixelBy20[w+wi][h+hi][0]=red; 
						pixelBy20[w+wi][h+hi][1]=green; 
						pixelBy20[w+wi][h+hi][2]=blue; 
					}
				}
			}							
		}
		pg.beginDraw();
		for (int h = 0; h < img.height; h++) {
			for (int w = 0; w < img.width; w++) {
				red = pixelBy20[w][h][0];
				green= pixelBy20[w][h][1];
				blue= pixelBy20[w][h][2];
				pixels[h*img.width + w] = color(red, green, blue);
				pg.pixels[h*img.width + w] = color(red, green, blue);
			}
		}	
		updatePixels();
		pg.updatePixels();
		pg.endDraw();		
		pg.save("pgby20.jpg");
	}
	
	public void pixelImageBy40(){
		int pixelSize = (img.width/40)*(img.height/40);
		int [][][] pixelBy40 = copy(imageRGBCopy3);
		int red, green, blue;
		for (int w = (img.width/80)-1; w <= img.width-(img.width/80)-1; w+=(img.width/40)) {
			for (int h = (img.height/80)-1; h <= img.height-(img.height/80)-1; h+=(img.height/40)) {		
				blue = 0;
				red = 0;
				green = 0;
				for (int wi = (img.width/-80)+1; wi <= (img.width/40)-img.width/80; wi++ ){
					for (int hi = (img.height/-80)+1; hi<= (img.height/40)-img.height/80; hi++) {		
						red += pixelBy40[w+wi][h+hi][0]; 
						green += pixelBy40[w+wi][h+hi][1]; 
						blue += pixelBy40[w+wi][h+hi][2]; 
					}
				}
				red= red/pixelSize;
				green= green/pixelSize;
				blue= blue/pixelSize;
				for (int wi = img.width/-80+1; wi <= img.width/40-img.width/80; wi++ ){
					for (int hi = img.height/-80+1; hi<= img.height/40-img.height/80; hi++) {		
						pixelBy40[w+wi][h+hi][0]=red; 
						pixelBy40[w+wi][h+hi][1]=green; 
						pixelBy40[w+wi][h+hi][2]=blue; 
					}
				}
			}							
		}
		pg.beginDraw();
		for (int h = 0; h < img.height; h++) {
			for (int w = 0; w < img.width; w++) { 
				red = pixelBy40[w][h][0];
				green= pixelBy40[w][h][1];
				blue= pixelBy40[w][h][2];
				pixels[h*img.width + w] = color(red, green, blue);
				pg.pixels[h*img.width + w] = color(red, green, blue);
			}
		}	
		updatePixels();
		pg.updatePixels();
		pg.endDraw();		
		pg.save("pgby40.jpg");
	}
	
	public void pixelImageBy80(){
		int pixelSize = (img.width/80)*(img.height/80);
		int [][][] pixelBy80 = copy(imageRGBCopy3);
		int red, green, blue;
		for (int w = (img.width/160)-1; w <= img.width-(img.width/160)-1; w+=(img.width/80)) {
			for (int h = (img.height/160)-1; h <= img.height-(img.height/160)-1; h+=(img.height/80)) {		
				blue = 0;
				red = 0;
				green = 0;
				for (int wi = (img.width/-160)+1; wi <= (img.width/80)-img.width/160; wi++ ){
					for (int hi = (img.height/-160)+1; hi<= (img.height/80)-img.height/160; hi++) {		
						red += pixelBy80[w+wi][h+hi][0]; 
						green += pixelBy80[w+wi][h+hi][1]; 
						blue += pixelBy80[w+wi][h+hi][2]; 
					}
				}
				red= red/pixelSize;
				green= green/pixelSize;
				blue= blue/pixelSize;
				for (int wi = img.width/-160+1; wi <= img.width/80-img.width/160; wi++ ){
					for (int hi = img.height/-160+1; hi<= img.height/80-img.height/160; hi++) {		
						pixelBy80[w+wi][h+hi][0]=red; 
						pixelBy80[w+wi][h+hi][1]=green; 
						pixelBy80[w+wi][h+hi][2]=blue; 
					}
				}
			}							
		}
		pg.beginDraw();
		for (int h = 0; h < img.height; h++) {
			for (int w = 0; w < img.width; w++) {
				red = pixelBy80[w][h][0];
				green= pixelBy80[w][h][1];
				blue= pixelBy80[w][h][2];
				pixels[h*img.width + w] = color(red, green, blue);
				pg.pixels[h*img.width + w] = color(red, green, blue);
			}
		}	
		updatePixels();
		pg.updatePixels();
		pg.endDraw();		
		pg.save("pgby80.jpg");
	}

	public static void printMatrix(int [][] matrix){
		for(int col=0; col<matrix[0].length; col++){
			System.out.print("\n");
			for(int row = 0; row<matrix.length;row++){
				System.out.printf("%3d\t",matrix[row][col]);
			}
		}	
	}
	
	public static void printMatrixB(int [][] matrix){
		for(int row=0; row<matrix[0].length; row++){
			System.out.print("\n");
			for(int col = 0; col<3;col++){
				System.out.printf("%3d\t",matrix[col][row]);
			}
		}	
	}

	public void getRGB(int [][] matrix){
		for(int row=0; row<matrix[0].length; row++){
			for(int col = 0; col<matrix.length;col++){
				switch(row%3){
				//red
				case 0: matrix[col][row] = (int) img.pixels[(row/3)*img.width + col]>>16 & 0xFF;
				break;
				//green
				case 1: matrix[col][row] = (int) img.pixels[(row/3)*img.width + col]>>8 & 0xFF;
				break;
				//blue
				case 2:	matrix[col][row] = (int) img.pixels[(row/3)*img.width + col]& 0xFF;
				break;
				}
			}
		}
	}
	public void getRGB(int [][][] matrix){
		for(int row=0; row<matrix[0].length; row++){
			for(int col = 0; col<matrix.length;col++){
				//red
				matrix[col][row][0] = (int) img.pixels[row*img.width + col]>>16 & 0xFF;
				//green
				matrix[col][row][1] = (int) img.pixels[row*img.width + col]>>8 & 0xFF;
				//blue
				matrix[col][row][2] = (int) img.pixels[row*img.width + col] & 0xFF;
			}
		}
	}
	
	public static int [][][] copy(int[][][]matrix){
		int [][][] copy = new int [matrix.length][matrix[0].length][3];
		for(int row=0; row<matrix.length; row++){
			for(int col = 0; col<matrix[row].length;col++){
				copy[row][col][0] = matrix[row][col][0];
				copy[row][col][1] = matrix[row][col][1];
				copy[row][col][2] = matrix[row][col][2];
			}
		}	
		return copy;
	}	

}