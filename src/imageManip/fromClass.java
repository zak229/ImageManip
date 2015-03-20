package imageManip;
import processing.core.PApplet;
import processing.core.PImage;
@SuppressWarnings("serial")
public class fromClass extends PApplet{


	/**
	 * This program demonstrates some basic image manipulations. 
	 * 
	 * Run the program and press and hold the following keys to
	 * see the transformations:
	 * h - horizontal flip
	 * v - vertical flip
	 * f - horizontal and vertical flip
	 * r - use only red intensity
	 * g - use only green intensity
	 * b - use only blue intensity 
	 * d - blur the image using 3x3 averaging
	 * z - something strange 
	 * 
	 * @author Joanna Klukowska
	 *
	 */



	PImage img;
	PImage imgCopy;
	int x, y;
	//array to store copy of the "pixels" of the image
	int[] imagePixelsCopy;

	public void setup() {
		// load the image from file
		img = loadImage("IMG_7567.jpg");
		if (img == null) {
			System.err.print("The image you are trying to use is inaccessible.\n" +
					"If using Eclipse, put your image in the src directory, \n" +
					"or specify the full path.\n\n") ;
			System.exit(-1);
		}
		// set the canvas size to match the image size
		size(img.width, img.height);

		// get the pixels from the image into
		// a pixels[] array of colors
		loadPixels();

		//copy the image pixels to a duplicate array for manipulation
		imagePixelsCopy = new int [pixels.length];
		for (int i =0; i < pixels.length; i++){
			imagePixelsCopy[i] = img.pixels[i];
		}

		//display image in the canvas
		image(img, 0, 0);

	}

	public void draw() {
		//display image in the canvas on each iteration of the draw method
		image(img, 0, 0);

		//wait for a key press and perform corresponding transformation

		if (keyPressed  && key == 'f') {
			diagonalFlip();		
		}

		if (keyPressed == true && key == 'v') {
			verticalFlip();
		}

		if (keyPressed == true && key == 'h') {
			horizontalFlip();
		}

		if (keyPressed == true && key == 'r') {
			onlyRed();
		}

		if (keyPressed == true && key == 'g') {
			onlyGreen();
		}

		if (keyPressed == true && key == 'b') {
			onlyBlue();
		}

		if (keyPressed == true && key == 'd') {
			blur();
		}

		//this is a strange transformation that applies the blur idea
		//but uses the integer representation of the RGB values (instead of 
		//the three integers) for averaging
		if (keyPressed == true && key == 'z') {
			for (int w = 1; w < img.width-1; w++) {
				for (int h = 1; h < img.height-1; h++) {
					int pixValue = 0;
					for (int wi = -1; wi <= 1; wi++ )
						for (int hi = -1; hi<= 1; hi++)
							pixValue += imagePixelsCopy [(h+hi)*img.width + w+wi ]; 
					pixValue = pixValue/9;
					pixels[h*img.width + w] = pixValue;
				}
			}

			updatePixels();
		}
	}

	/**
	 * This method flips the pixels of the image along the horizontal
	 * and vertical direction. 
	 * For the 1D array image representation this is equivalent to
	 * reversing the array. 
	 */
	private void diagonalFlip() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = imagePixelsCopy[pixels.length-1-i]; 
		}
		updatePixels();		
	}

	/**
	 * This method flips the pixels of the image switching right and left
	 * (along the vertical axis).
	 * It requires reversing every single row of the image.
	 */
	private void horizontalFlip() {
		for (int w = 0; w < img.width; w++) {
			for (int h = 0; h < img.height; h++) {
				pixels[h*img.width + w] = imagePixelsCopy[h*img.width + img.width-1-w];
			}
		}
		updatePixels();
	}

	/**
	 * This method flips the pixels of the image switching top and bottom
	 * (along the horizontal axis).
	 * It requires reversing every single column of the image.
	 */
	public void verticalFlip () {
		for (int w = 0; w < img.width; w++) {
			for (int h = 0; h < img.height; h++) {
				pixels[h*img.width + w] = imagePixelsCopy[(img.height-1-h)*img.width + w];
			}
		}
		updatePixels();
	}

	/**
	 * This method removes the blue and green color channels using only red
	 * intensity to represent the image.
	 */
	public void onlyRed () {
		for (int w = 0; w < img.width; w++) {
			for (int h = 0; h < img.height; h++) {
				pixels[h*img.width + w] = color(red(imagePixelsCopy[h*img.width + w]),0,0);
			}
		}
		updatePixels();
	}

	/**
	 * This method removes the blue and red color channels using only green
	 * intensity to represent the image.
	 */
	public void onlyGreen() {
		for (int w = 0; w < img.width; w++) {
			for (int h = 0; h < img.height; h++) {
				pixels[h*img.width + w] = color(0,green(imagePixelsCopy[h*img.width + w]),0);
			}
		}
		updatePixels();
	}

	/**
	 * This method removes the red and green color channels using only blue
	 * intensity to represent the image.
	 */
	public void onlyBlue() {
		for (int w = 0; w < img.width; w++) {
			for (int h = 0; h < img.height; h++) {
				pixels[h*img.width + w] = color(0,0,blue(imagePixelsCopy[h*img.width + w]));
			}
		}
		updatePixels();
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

}


