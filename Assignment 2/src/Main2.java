import java.io.*;
import java.sql.SQLOutput;
import java.util.Random;
import java.util.Scanner;

// constraint satisfaction

/*Sample image files provided for this program.
        Image1.raw      a little girl           225 x 180
        Image2.raw      hibiscus flower         300 x 200
        Image3.raw      boys in masks           500 x 500
        Image4.raw      Australian shepherd     500 x 350
        Image5.raw     trotting horse          500 x 500

 */

public class Main2 {


    public static void main(String[] args) throws IOException {
        /*Put a loop around all of this so that you can dither a different image file by whatever method
        you choose each time through the loop. This way, you can dither as many images as you
        want by as many methods as you want in one run of the program.*/

        Scanner scnr = new Scanner(System.in);
        System.out.println("Hit the Enter key to begin.");


        // while loop allows user to dither as many images as they choose
        while (scnr.hasNextLine()) {
            System.out.println("What is the input file name?");
            String inputFile = scnr.next();
            System.out.println("What is width of the input?");
            int w = scnr.nextInt();
            System.out.println("What is height of the input?");
            int h = scnr.nextInt();
            System.out.println("What is name of the output file?");
            String outputFile = scnr.next();
            InputStream inputStream = new FileInputStream(inputFile);
            OutputStream outputStream = new FileOutputStream(outputFile);
            System.out.println("What dithering method do you want to use?");
            System.out.print("1 for threshold");
            System.out.print(" 2 for random");
            System.out.print(" 3 for pattern");
            System.out.println(" 4 for error diffusion");
            int ditherMethod = scnr.nextInt();
            switch (ditherMethod) {
                case 1:
                    threshold(inputStream, outputStream, w, h);
                    break;
                case 2:
                    random(inputStream, outputStream, w, h);
                    break;
                case 3:
                    pattern(inputStream, outputStream, w, h);
                    break;
                case 4:
                    errDiff(inputStream, outputStream, w, h);
                    break;
                default:
                    System.out.println("Not a valid choice");
                    System.exit(1);
            }
        }
    }

    public static void threshold(InputStream inputStream, OutputStream outputStream, int w, int h) throws IOException {
        int r = 0;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                int pixel = inputStream.read();
                if (pixel < 128) {
                    outputStream.write(0);
                } else {
                    outputStream.write(255);
                }
            }
        }
    }

    public static void random(InputStream inputStream, OutputStream outputStream, int w, int h) throws IOException {
        /*In this method, you actually wouldn't have to store the pixel values in a 2d array -- you could just
        store the pixel value in a single int, as you do in the threshold method. But I declare and use a 2D array
        here because you have to do it that way in the pattern and error diffusion methods. This code with help you
        with those methods.*/
        Random rnd = new Random();
        int r;
        int[][] pixels = new int[h][w];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                pixels[i][j] = inputStream.read();
                System.out.print(pixels[i][j]);
                r = rnd.nextInt(256);
                if (pixels[i][j] < r) {
                    outputStream.write(0);
                    System.out.println(" ----- 0");
                } else {
                    outputStream.write(255);
                    System.out.println(" ----- 255");
                }
            }
        }
    }

    public static void pattern(InputStream inputStream, OutputStream outputStream, int w, int h) throws IOException {
        /*The pattern to apply is
        8 3 4
        6 1 2
        7 5 9
        Hard-code the pattern into a 2D array */

        int[][] mask = {{8,3,4}, {6,1,2}, {7,5,9}};
        int[][] pixels = new int[h][w];

        int row = 0;
        int col = 0;

        for (int i = 0; i < h; ++i) {
            for (int j = 0; j < w; j++) {
                // read pixels into 2D array
                pixels[i][j] = inputStream.read();
                System.out.print(pixels[i][j]);
                

                pixels[i][j] = (int) (pixels[i][j] / 25.6);
                // compare reduced pixel value to corresponding mask pattern value and write corresponding pixel (black or white) to output
                if (pixels[i][j] < mask[row][col]) {
                    outputStream.write(0);
                } else {
                    outputStream.write(255);
                }

                // iterate through mask pattern independent of pixel array iteration
                col++;
                if (col == 3) {
                    col = 0;
                    row++;
                }
                if (row == 3) {
                    row = 0;
                }
            }
        }
    }

    public static void errDiff(InputStream inputStream, OutputStream outputStream, int w, int h) throws IOException {
       /* The error diffusion pattern is

                p       7
        3       5       1

        Hard-code this pattern into a 2D array
        }*/

        int[][] pixels = new int[h][w];

        double difference = 0.0;


        // read pixels into 2D array
        for (int i = 0; i < h; ++i) {
            for (int j = 0; j < w; j++) {
                pixels[i][j] = inputStream.read();
            }
        }

        for (int i = 0; i < h; ++i) {
            for (int j = 0; j < w; j++) {
                // determines difference (error) according to whether or not pixel value is less than 128
                if (pixels[i][j] < 128) {
                    difference = pixels[i][j];
                }
                else {
                    difference = pixels[i][j] - 255;
                }


                // hard-coded pattern into position so that each index is affected by the correct corresponding value in the error diffusion pattern

                if (j < w -1) {             // while we are not in the last column (in order to avoid out of bounds trespassing)
                    pixels[i][j+1] = pixels[i][j+1] + (int)(7 * (difference / 16));
                    if (i < h - 1) {        // while we are not in the last row (in order to avoid out of bounds trespassing)
                        pixels[i+1][j+1] = pixels[i+1][j+1] + (int)(1 * (difference / 16));
                    }
                }
                if (i < h - 1) {            // while we are not in the last row (in order to avoid out of bounds trespassing)
                    pixels[i+1][j] = pixels[i+1][j] + (int)(5 * (difference / 16));
                    if (j != 0) {           // while we are not in the first column (in order to avoid out of bounds trespassing)
                        pixels[i+1][j-1] = pixels[i+1][j-1] + (int)(3 * (difference / 16));
                    }
                }
            }
        }

        // write into output image
        for (int i = 0; i < h; ++i) {
            for (int j = 0; j < w; j++) {
                System.out.print(pixels[i][j]);
                if (pixels[i][j] < 128) {
                    outputStream.write(0);
                }

                else {
                    outputStream.write(255);
                }
            }
        }

    }
}