// OpenCVApplication.cpp : Defines the entry point for the console 
// application.
//

#include "stdafx.h"
#include "common.h"
#include <random>

typedef struct
{
	Mat image;
	Point p;
	Vec3b rgb;

}MouseParams;

typedef struct
{
	double r_i;
	double c_i;

}CenterOfMass;

typedef struct
{
	int x, y; 
	byte c; 
}my_point;


void testOpenImage()
{
	char fname[MAX_PATH];
	while(openFileDlg(fname))
	{
		Mat src;
		src = imread(fname);
		imshow("image",src);
		waitKey();
	}
}

void testOpenImagesFld()
{   
	char folderName[MAX_PATH];
	if (openFolderDlg(folderName)==0)
		return;
	char fname[MAX_PATH];
	FileGetter fg(folderName,"bmp");
	while(fg.getNextAbsFile(fname))
	{
		Mat src;
		src = imread(fname);
		imshow(fg.getFoundFileName(),src);
		if (waitKey()==27) //ESC pressed
			break;
	}
}

void testImageOpenAndSave()
{
	Mat src, dst;

	// Read the image
	src = imread("Images/Lena_24bits.bmp", CV_LOAD_IMAGE_COLOR);	

	if (!src.data)	// Check for invalid input
	{
		printf("Could not open or find the image\n");
		return;
	}

	// Get the image resolution
	Size src_size = Size(src.cols, src.rows);

	// Display window
	const char* WIN_SRC = "Src"; //window for the source image
	namedWindow(WIN_SRC, CV_WINDOW_AUTOSIZE);
	cvMoveWindow(WIN_SRC, 0, 0);

	//window for the destination (processed)image
	const char* WIN_DST = "Dst";  
	namedWindow(WIN_DST, CV_WINDOW_AUTOSIZE);
	cvMoveWindow(WIN_DST, src_size.width + 10, 0);

	//converts the source image to a grayscale one
	cvtColor(src, dst, CV_BGR2GRAY); 

	//writes the destination to file
	imwrite("Images/Lena_24bits_gray.bmp", dst); 

	imshow(WIN_SRC, src);
	imshow(WIN_DST, dst);

	printf("Press any key to continue ...\n");
	waitKey(0);
}

void testNegativeImage()
{
	char fname[MAX_PATH];
	while(openFileDlg(fname))
	{
		double t = (double)getTickCount(); // Get the current time [s]
		
		Mat src = imread(fname,CV_LOAD_IMAGE_GRAYSCALE);

		int height = src.rows;
		int width = src.cols;

		Mat dst = Mat(height,width,CV_8UC1);

		// Asa se acceseaaza pixelii individuali pt. o imagine cu 8 biti/pixel
		// Varianta ineficienta (lenta)
		for (int i=0; i<height; i++)
		{
			for (int j=0; j<width; j++)
			{
				uchar val = src.at<uchar>(i,j);
				uchar neg = MAX_PATH-val;
				dst.at<uchar>(i,j) = neg;
			}
		}

		// Get the current time again and compute the time difference [s]
		t = ((double)getTickCount() - t) / getTickFrequency();
		// Print (in the console window) the processing time in [ms] 
		printf("Time = %.3f [ms]\n", t * 1000);

		imshow("input image",src);
		imshow("negative image",dst);
		waitKey();
	}
}

void testParcurgereSimplaDiblookStyle()
{
	char fname[MAX_PATH];
	while (openFileDlg(fname))
	{
		Mat src = imread(fname, CV_LOAD_IMAGE_GRAYSCALE);
		int height = src.rows;
		int width = src.cols;
		Mat dst = src.clone();

		double t = (double)getTickCount(); // Get the current time [s]

		// the fastest approach using the “diblook style”
		uchar *lpSrc = src.data;
		uchar *lpDst = dst.data;
		int w = (int) src.step; // no dword alignment is done !!!
		for (int i = 0; i<height; i++)
			for (int j = 0; j < width; j++) {
				uchar val = lpSrc[i*w + j];
				lpDst[i*w + j] = 255 - val;
			}

		// Get the current time again and compute the time difference [s]
		t = ((double)getTickCount() - t) / getTickFrequency();
		// Print (in the console window) the processing time in [ms] 
		printf("Time = %.3f [ms]\n", t * 1000);

		imshow("input image",src);
		imshow("negative image",dst);
		waitKey();
	}
}

void testColor2Gray()
{
	char fname[MAX_PATH];
	while(openFileDlg(fname))
	{
		Mat src = imread(fname);

		int height = src.rows;
		int width = src.cols;

		Mat dst = Mat(height,width,CV_8UC1);
		
		// Asa se acceseaaza pixelii individuali pt. o imagine RGB 24 biti/pixel
		// Varianta ineficienta (lenta)
		for (int i=0; i<height; i++)
		{
			for (int j=0; j<width; j++)
			{
				Vec3b v3 = src.at<Vec3b>(i,j);
				uchar b = v3[0];
				uchar g = v3[1];
				uchar r = v3[2];
				dst.at<uchar>(i,j) = (r+g+b)/3;
			}
		}
		
		imshow("input image",src);
		imshow("gray image",dst);
		waitKey();
	}
}

void testBGR2HSV()
{
	char fname[MAX_PATH];
	while (openFileDlg(fname))
	{
		Mat src = imread(fname);
		int height = src.rows;
		int width = src.cols;

		// Componentele d eculoare ale modelului HSV
		Mat H = Mat(height, width, CV_8UC1);
		Mat S = Mat(height, width, CV_8UC1);
		Mat V = Mat(height, width, CV_8UC1);

		// definire pointeri la matricele (8 biti/pixeli) folosite la afisarea componentelor individuale H,S,V
		uchar* lpH = H.data;
		uchar* lpS = S.data;
		uchar* lpV = V.data;

		Mat hsvImg;
		cvtColor(src, hsvImg, CV_BGR2HSV);

		// definire pointer la matricea (24 biti/pixeli) a imaginii HSV
		uchar* hsvDataPtr = hsvImg.data;

		for (int i = 0; i<height; i++)
		{
			for (int j = 0; j<width; j++)
			{
				int hi = i*width * 3 + j * 3;
				int gi = i*width + j;

				lpH[gi] = hsvDataPtr[hi] * 510 / 360;		// lpH = 0 .. 255
				lpS[gi] = hsvDataPtr[hi + 1];			// lpS = 0 .. 255
				lpV[gi] = hsvDataPtr[hi + 2];			// lpV = 0 .. 255
			}
		}

		imshow("input image", src);
		imshow("H", H);
		imshow("S", S);
		imshow("V", V);

		waitKey();
	}
}

void testResize()
{
	char fname[MAX_PATH];
	while(openFileDlg(fname))
	{
		Mat src;
		src = imread(fname);
		Mat dst1,dst2;
		//without interpolation
		resizeImg(src,dst1,320,false);
		//with interpolation
		resizeImg(src,dst2,320,true);
		imshow("input image",src);
		imshow("resized image (without interpolation)",dst1);
		imshow("resized image (with interpolation)",dst2);
		waitKey();
	}
}

void testCanny()
{
	char fname[MAX_PATH];
	while(openFileDlg(fname))
	{
		Mat src,dst,gauss;
		src = imread(fname,CV_LOAD_IMAGE_GRAYSCALE);
		double k = 0.4;
		int pH = 50;
		int pL = (int) k*pH;
		GaussianBlur(src, gauss, Size(5, 5), 0.8, 0.8);
		Canny(gauss,dst,pL,pH,3);
		imshow("input image",src);
		imshow("canny",dst);
		waitKey();
	}
}

void testVideoSequence()
{
	VideoCapture cap("Videos/rubic.avi"); // off-line video from file
	//VideoCapture cap(0);	// live video from web cam
	if (!cap.isOpened()) {
		printf("Cannot open video capture device.\n");
		waitKey(0);
		return;
	}
		
	Mat edges;
	Mat frame;
	char c;

	while (cap.read(frame))
	{
		Mat grayFrame;
		cvtColor(frame, grayFrame, CV_BGR2GRAY);
		Canny(grayFrame,edges,40,100,3);
		imshow("source", frame);
		imshow("gray", grayFrame);
		imshow("edges", edges);
		c = cvWaitKey(0);  // waits a key press to advance to the next frame
		if (c == 27) {
			// press ESC to exit
			printf("ESC pressed - capture finished\n"); 
			break;  //ESC pressed
		};
	}
}


void testSnap()
{	
	// open the deafult camera (i.e. the built in web cam)
	VideoCapture cap(0); 

	if (!cap.isOpened()) // openenig the video device failed
	{
		printf("Cannot open video capture device.\n");
		return;
	}

	Mat frame;
	char numberStr[256];
	char fileName[256];
	
	// video resolution
	Size capS = Size((int)cap.get(CV_CAP_PROP_FRAME_WIDTH),
		(int)cap.get(CV_CAP_PROP_FRAME_HEIGHT));

	// Display window
	const char* WIN_SRC = "Src"; //window for the source frame
	namedWindow(WIN_SRC, CV_WINDOW_AUTOSIZE);
	cvMoveWindow(WIN_SRC, 0, 0);

	const char* WIN_DST = "Snapped"; //window for showing the snapped frame
	namedWindow(WIN_DST, CV_WINDOW_AUTOSIZE);
	cvMoveWindow(WIN_DST, capS.width + 10, 0);

	char c;
	int frameNum = -1;
	int frameCount = 0;

	for (;;)
	{
		cap >> frame; // get a new frame from camera
		if (frame.empty())
		{
			printf("End of the video file\n");
			break;
		}

		++frameNum;
		
		imshow(WIN_SRC, frame);

		c = cvWaitKey(10);  // waits a key press to advance to the next frame
		if (c == 27) {
			// press ESC to exit
			printf("ESC pressed - capture finished");
			break;  //ESC pressed
		}
		if (c == 115){ //'s' pressed - snapp the image to a file
			frameCount++;
			fileName[0] = NULL;
			sprintf(numberStr, "%d", frameCount);
			strcat(fileName, "Images/A");
			strcat(fileName, numberStr);
			strcat(fileName, ".bmp");
			bool bSuccess = imwrite(fileName, frame);
			if (!bSuccess) 
			{
				printf("Error writing the snapped image\n");
			}
			else
				imshow(WIN_DST, frame);
		}
	}

}

void MyCallBackFunc(int event, int x, int y, int flags, void* param)
{
	//More examples:http://opencvexamples.blogspot.com/2014/01/detect-mouse-clicks-and-moves-on-image.html
	Mat* src = (Mat*)param;
	if (event == CV_EVENT_LBUTTONDOWN)
		{
			printf("Pos(x,y): %d,%d  Color(RGB): %d,%d,%d\n",
				x, y,
				(int)(*src).at<Vec3b>(y, x)[2],
				(int)(*src).at<Vec3b>(y, x)[1],
				(int)(*src).at<Vec3b>(y, x)[0]);
		}
}

void testMouseClick()
{
	Mat src;
	// Read image from file 
	char fname[MAX_PATH];
	while (openFileDlg(fname))
	{
		src = imread(fname);
		//Create a window
		namedWindow("My Window", 1);

		//set the callback function for any mouse event
		setMouseCallback("My Window", MyCallBackFunc, &src);

		//show the image
		imshow("My Window", src);

		// Wait until user press some key
		waitKey(0);
	}
}

/* Histogram display function - display a histogram using bars (simlilar to L3 / PI)
Input:
name - destination (output) window name
hist - pointer to the vector containing the histogram values
hist_cols - no. of bins (elements) in the histogram = histogram image width
hist_height - height of the histogram image
Call example:
showHistogram ("MyHist", hist_dir, 255, 200);
*/
void showHistogram(const std::string& name, int* hist, const int  hist_cols,
	const int hist_height)
{	
	// constructs a white image
	Mat imgHist(hist_height, hist_cols, CV_8UC3, CV_RGB(255, 255, 255)); 

	//computes histogram maximum
	int max_hist = 0;
	for (int i = 0; i<hist_cols; i++)
	if (hist[i] > max_hist)
		max_hist = hist[i];
	double scale = 1.0;
	scale = (double)hist_height / max_hist;
	int baseline = hist_height - 1;

	for (int x = 0; x < hist_cols; x++) {
		Point p1 = Point(x, baseline);
		Point p2 = Point(x, baseline - cvRound(hist[x] * scale));
		// histogram bins colored in magenta
		line(imgHist, p1, p2, CV_RGB(255, 0, 255)); 
	}

	imshow(name, imgHist);
}

/*LAB 1*/

/* Prob 3
Implement a function which changes the gray levels of an image 
by an additive factor*/
void changeGrayLevelByAdditiveFactor(){

	Mat src, dst;

	const int factor = 40;

	char fname[MAX_PATH];
	while (openFileDlg(fname))
	{
		src = imread(fname, CV_LOAD_IMAGE_GRAYSCALE);

		int height = src.rows;
		int width = src.cols;
		dst = src.clone();


		for (int i = 0; i < height; i++)
		{
			for (int j = 0; j < width; j++)
			{
				uchar value = src.at<uchar>(i, j);
				int newValue = value + factor;
				if ( newValue > 255)
				{
					dst.at<uchar>(i, j) = 255;
				}
				else
				{
					dst.at<uchar>(i, j) = newValue;
				}

			}
		}

		imshow("input image", src);
		imshow("gray image", dst);
		waitKey();

	}


}

/*5. Create a color image of dimension 256 x 256. Divide it into 4 squares
and color the squares from top to bottom, left to right as: white, red,
green, yellow.*/
void createAColorImage(){

	Mat src, dst;

	const int rows = 256;
	const int cols = 256;

	//create the image
	src = Mat(rows, cols, CV_8UC3);

	for (int i = 0; i < rows / 2; i++)
	{
		for (int j = 0; j < cols / 2; j++)
		{
			src.at<Vec3b>(i, j) = Vec3b(255, 255, 255);
		}
	}

	for (int i = 0; i < rows / 2; i++)
	{
		for (int j = cols / 2; j < cols; j++)
		{
			src.at<Vec3b>(i, j) = Vec3b(0, 0, 255);
		}
	}

	for (int i = rows / 2; i < rows; i++)
	{
		for (int j = 0; j < cols / 2; j++)
		{
			src.at<Vec3b>(i, j) = Vec3b(0, 255, 0);
		}
	}

	for (int i = rows / 2; i < rows; i++)
	{
		for (int j = cols / 2; j < cols; j++)
		{
			src.at<Vec3b>(i, j) = Vec3b(0, 255, 255);
		}
	}

	imshow("input image", src);
	waitKey();
}


/*4. Implement a function which changes the gray levels of an image by a 
multiplicative factor. Save the resulting image.*/
void changeGrayLevelByMultiplicativeFactor(){

	Mat src, dst;

	const int factor = 2;

	char fname[MAX_PATH];
	while (openFileDlg(fname))
	{
		src = imread(fname, CV_LOAD_IMAGE_GRAYSCALE);

		int height = src.rows;
		int width = src.cols;
		dst = src.clone();


		for (int i = 0; i < height; i++)
		{
			for (int j = 0; j < width; j++)
			{
				uchar value = src.at<uchar>(i, j);
				int newValue = value * factor;
				if (newValue > 255)
				{
					dst.at<uchar>(i, j) = 255;
				}
				else
				{
					dst.at<uchar>(i, j) = newValue;
				}

			}
		}
		
		//save the image
		imwrite("Images\My_image.bmp", dst);

		imshow("input image", src);
		imshow("gray image", dst);
		waitKey();

	}


}

/*6. Create a 3x3 float matrix, determine its inverse and print it.*/
void determineInverse(){

	float vals[] = {0, 0, 15, 10, 2, 4, 3, 1, 7};
	Mat mat = Mat(3, 3, CV_32FC1, vals);

	Mat inverse = Mat(3, 3, CV_32FC1);

	//determinant
	float det = 
		mat.at<float>(0, 0) * mat.at<float>(1, 1) * mat.at<float>(2, 2) +
		mat.at<float>(1, 0) * mat.at<float>(2, 1) * mat.at<float>(0, 2) +
		mat.at<float>(2, 0) * mat.at<float>(0, 1) * mat.at<float>(1, 2) -
		mat.at<float>(0, 2) * mat.at<float>(1, 1) * mat.at<float>(2, 0) +
		mat.at<float>(1, 2) * mat.at<float>(2, 1) * mat.at<float>(0, 0) +
		mat.at<float>(0, 1) * mat.at<float>(1, 0) * mat.at<float>(2, 2);

	float invdet = 1 / det;

	inverse.at<float>(0, 0) = (mat.at<float>(1, 1) * mat.at<float>(2, 2) 
		- mat.at<float>(2, 1) * mat.at<float>(1, 2)) * invdet;
	inverse.at<float>(0, 1) = (mat.at<float>(0, 2) * mat.at<float>(2, 1) 
		- mat.at<float>(0, 1) * mat.at<float>(2, 2)) * invdet;
	inverse.at<float>(0, 2) = (mat.at<float>(0, 1) * mat.at<float>(1, 2) 
		- mat.at<float>(0, 2) * mat.at<float>(1, 1)) * invdet;
	inverse.at<float>(1, 0) = (mat.at<float>(1, 2) * mat.at<float>(2, 0) 
		- mat.at<float>(1, 0) * mat.at<float>(2, 2)) * invdet;
	inverse.at<float>(1, 1) = (mat.at<float>(0, 0) * mat.at<float>(2, 2) 
		- mat.at<float>(0, 2) * mat.at<float>(2, 0)) * invdet;
	inverse.at<float>(1, 2) = (mat.at<float>(1, 0) * mat.at<float>(0, 2) 
		- mat.at<float>(0, 0) * mat.at<float>(1, 2)) * invdet;
	inverse.at<float>(2, 0) = (mat.at<float>(1, 0) * mat.at<float>(2, 1) 
		- mat.at<float>(2, 0) * mat.at<float>(1, 1)) * invdet;
	inverse.at<float>(2, 1) = (mat.at<float>(2, 0) * mat.at<float>(0, 1) 
		- mat.at<float>(0, 0) * mat.at<float>(2, 1)) * invdet;
	inverse.at<float>(2, 2) = (mat.at<float>(0, 0) * mat.at<float>(1, 1)
		- mat.at<float>(1, 0) * mat.at<float>(0, 1)) * invdet;

	//show the inverse
	std::cout << inverse;
	int c;
	scanf("%d", &c);

}

//Lab2 - Color spaces

/*
 * Ex1.
 */
void copyRGBChannels()
{
	Mat src, dstR, dstG, dstB;

	char fname[MAX_PATH];
	while (openFileDlg(fname))
	{
		src = imread(fname, CV_LOAD_IMAGE_COLOR);

		int height = src.rows;
		int width = src.cols;

		dstR = Mat(height, width, CV_8UC3);
		dstG = Mat(height, width, CV_8UC3);
		dstB = Mat(height, width, CV_8UC3);

		for (int i = 0; i < height; i++)
		{
			for (int j = 0; j < width; j++)
			{
					Vec3b channels = src.at<Vec3b>(i, j);
					uchar channelB = channels[0];
					uchar channelG = channels[1];
					uchar channelR = channels[2];

					dstR.at<Vec3b>(i, j) = Vec3b(0, 0, channelR);
					dstG.at<Vec3b>(i, j) = Vec3b(0, channelG, 0);
					dstB.at<Vec3b>(i, j) = Vec3b(channelB, 0, 0);
			}
		}

		imshow("input image", src);
		imshow("imageR", dstR);
		imshow("imageG", dstG);
		imshow("imageB", dstB);
		waitKey();

	}
	
}

void color2Grayscale()
{
	Mat src, dst;

	char fname[MAX_PATH];
	while (openFileDlg(fname))
	{
		src = imread(fname, CV_LOAD_IMAGE_COLOR);

		int height = src.rows;
		int width = src.cols;

		dst = Mat(height, width, CV_8UC1);

		for (int i = 0; i < height; i++)
		{
			for (int j = 0; j < width; j++)
			{
				Vec3b channels = src.at<Vec3b>(i, j);
				uchar channelB = channels[0];
				uchar channelG = channels[1];
				uchar channelR = channels[2];

				uchar grayValue = (channelB + channelG + channelR) / 3;

				dst.at<uchar>(i, j) = grayValue;
			}
		}

		imshow("input image", src);
		imshow("gray image", dst);
		waitKey();

	}
	
}

void Grayscale2Binary()
{
	Mat src, dst;

	float threshold;

	std::cin >> threshold;

	char fname[MAX_PATH];
	while (openFileDlg(fname))
	{
		src = imread(fname, CV_LOAD_IMAGE_GRAYSCALE);

		int height = src.rows;
		int width = src.cols;

		dst = Mat(height, width, CV_8UC1);

		for (int i = 0; i < height; i++)
		{
			for (int j = 0; j < width; j++)
			{
				uchar value = src.at<uchar>(i, j);

				if (value >= threshold)
				{
					dst.at<uchar>(i, j) = 255;
				}
				else
				{
					dst.at<uchar>(i, j) = 0;
				}

				
			}
		}


		imshow("input image", src);
		imshow("binary image", dst);
		waitKey();

	}
	
}

void BGR2HSV()
{
	Mat src, dstH, dstS, dstV;

	char fname[MAX_PATH];
	while (openFileDlg(fname))
	{
		src = imread(fname, CV_LOAD_IMAGE_COLOR);

		int height = src.rows;
		int width = src.cols;

		dstH = Mat(height, width, CV_8UC1);
		dstS = Mat(height, width, CV_8UC1);
		dstV = Mat(height, width, CV_8UC1);

		for (int i = 0; i < height; i++)
		{
			for (int j = 0; j < width; j++)
			{
				Vec3b channels = src.at<Vec3b>(i, j);
				uchar channelB = channels[0];
				uchar channelG = channels[1];
				uchar channelR = channels[2];

				float r = (float)channelR / 255;
				float g = (float)channelG / 255;
				float b = (float)channelB / 255;

				float max_rg = max(r,g);
				float max = max(max_rg, b);

				float min_rg = min(r, g);
				float min = min(min_rg, b);

				float chroma = max - min;

				//value
				float value = max;

				//saturation
				float saturation = 0.0f;
				if (value!=0)
				{
					saturation = chroma / value;
				}
				else
				{
					saturation = 0;
				}

				//hue
				float hue = 0.0f;
				if (chroma!=0)
				{
					if (max == r)
					{
						hue = 60 * (g - b) / chroma;
					}
					else if (max == g)
					{
						hue = 120 + 60 * (b - r) / chroma;
					}
					else if (max == b)
					{
						hue = 240 + 60 * (r - g) / chroma;
					}
				}
				else
				{
					hue = 0;
				}
				if (hue < 0)
				{
					hue = hue + 360;
				}

				dstH.at<uchar>(i, j) = hue * 255 / 360;
				dstS.at<uchar>(i, j) = saturation * 255;
				dstV.at<uchar>(i, j) = value * 255;
			}
		}

		imshow("input image", src);
		imshow("imageH", dstH);
		imshow("imageS", dstS);
		imshow("imageV", dstV);
		waitKey();

	}
	

}

void isInside()
{
	Mat img;

	int row, col;

	std::cin >> row;
	std::cin >> col;

	char fname[MAX_PATH];
	while (openFileDlg(fname))
	{
		img = imread(fname, CV_LOAD_IMAGE_GRAYSCALE);

		int height = img.rows;
		int width = img.cols;

		if ( row >= 0 && row < height && col >= 0 && col < width)
		{
			std::cout << "Is inside!";
			
		}
		else
		{
			std::cout << "Is not inside!";
		}
	}
	
}

//Lab 3 - Geometrical features


int calculateArea(Mat img, Vec3b rgb)
{
	int area = 0;

	int height = img.rows;
	int width = img.cols;

	for (int r = 0; r < height; r++)
		for (int c = 0; c < width; c++)
		{
			Vec3b color = img.at<Vec3b>(r, c);

			if (color == rgb)
			{
				area++;
			}

		}
	
	return area;
	
}

CenterOfMass calculateCenterOfMass(Mat img, Vec3b rgb)
{
	double r_i = 0.0f , c_i = 0.0f;

	int height = img.rows;
	int width = img.cols;

	CenterOfMass center_of_mass;

	for (int r = 0; r < height; r++)
		for (int c = 0; c < width; c++)
		{
			Vec3b color = img.at<Vec3b>(r, c);
			if (color == rgb)
			{
				r_i += r;
				c_i += c;
			}

		}

	r_i = r_i / calculateArea(img, rgb);
	c_i = c_i / calculateArea(img, rgb);

	center_of_mass.r_i = r_i;
	center_of_mass.c_i = c_i;

	return center_of_mass;
	
}

double calculateAxisOfElongation(Mat img, Vec3b rgb)
{
	int height = img.rows;
	int width = img.cols;

	CenterOfMass center_of_mass = calculateCenterOfMass(img, rgb);

	double num = 0.0f;
	double denom1 = 0.0f , denom2 = 0.0f;

	double axisValue;

	for (int r = 0; r < height; r++)
		for (int c = 0; c < width; c++)
		{
			Vec3b color = img.at<Vec3b>(r, c);

			if (color == rgb)
			{
				num += (r - center_of_mass.r_i)*(c - center_of_mass.c_i);
				denom1 += (c - center_of_mass.c_i)*(c - center_of_mass.c_i);
				denom2 += (r - center_of_mass.r_i)*(r - center_of_mass.r_i);
			}

		}

	axisValue = (atan2(2 * num, denom1 - denom2) / 2.0f);

	return axisValue;
}

boolean findWhitePixel(int r, int c, Mat img)
{	
	Vec3b color;

	for (int i = -1; i <= 1; i++)
		for (int j = -1; j <= 1; j++)
		{
			color = img.at<Vec3b>(r + i, c + j);
			if (color == Vec3b(255, 255, 255))
			{
				return true;

			}
		}

	return false;
	
}

int calculatePerimeter(Mat img, Mat dst, Vec3b rgb)
{
	int perimeter = 0;

	int height = img.rows;
	int width = img.cols;

	for (int r = 0; r < height; r++)
		for (int c = 0; c < width; c++)
		{
			Vec3b color = img.at<Vec3b>(r, c);

			if (color == rgb)
			{
				if (findWhitePixel(r,c,img))
				{
					dst.at<Vec3b>(r, c) = Vec3b(0, 0, 0);
					perimeter++;
				}
				
			}

		}

	return perimeter;
	
}

double calculateThinnessRatio(Mat img, Mat dst, Vec3b rgb)
{	
	int area = calculateArea(img, rgb);
	int perimeter = calculatePerimeter(img, dst, rgb);

	double T = 4 * PI * ( (double)area / (perimeter * perimeter));
	
	return T;
		
}

double calculateAspectRatio(Mat img, Vec3b rgb)
{
	double R = 0;

	int c_max = INT_MIN;
	int r_max = INT_MIN;
	int c_min = INT_MAX;
	int r_min = INT_MAX;

	int height = img.rows;
	int width = img.cols;

	for (int r = 0; r < height; r++)
		for (int c = 0; c < width; c++)
		{
			Vec3b color = img.at<Vec3b>(r, c);
			if (color == rgb)
			{
				c_max = MAX(c_max, c);
				r_max = MAX(r_max, r);
				c_min = MIN(c_min, c);
				r_min = MIN(r_min, r);
			}

		}

	R = (double)(c_max - c_min + 1) / (r_max - r_min + 1);

	return R;	
}



void horizontalProjection(Mat img, Mat dst, Vec3b rgb)
{
	int height = img.rows;

	int width = img.cols;

	int nrCols;

	for (int r = 0; r < height; r++)
	{	
		nrCols = 0;
		for (int c = 0; c < width; c++)
		{
			Vec3b color = img.at<Vec3b>(r, c);
			if (color == rgb)
			{
				nrCols++;
			}

		}

		for (int j = 0; j < nrCols; j++)
		{
				dst.at<Vec3b>(r, j) = Vec3b(255, 255, 255);
		}
		
	}

	
}

void verticalProjection(Mat img, Mat dst, Vec3b rgb)
{
	int height = img.rows;

	int width = img.cols;
	int nrRows = 0;

	for (int c = 0; c < width; c++)
	{
		nrRows = 0;
		for (int r = 0; r < height; r++)
		{
			Vec3b color = img.at<Vec3b>(r, c);
			if (color == rgb)
			{
				nrRows++;
			}

		}

		for (int j = 0; j < nrRows; j++)
		{
			dst.at<Vec3b>(j, c) = Vec3b(255, 255, 255);
		}

	}
	
}

void DrawCross(Mat& img, Point p, int size, Scalar color, int thickness)
{
	line(img, Point(p.x - size / 2, p.y), Point(p.x + size / 2, p.y), color, thickness, 8);
	line(img, Point(p.x, p.y - size / 2), Point(p.x, p.y + size / 2), color, thickness, 8);
}

void displayElongationAxis(Mat dst, double xc, double yc, double teta)
{
	int delta = 30; // arbitrary value
	Point P1, P2;
	P1.x = xc- delta;
	P1.y = yc - (int)(delta*tan(teta)); // teta is the elongation angle in radians
	P2.x = xc + delta;
	P2.y = yc + (int)(delta*tan(teta));
	line(dst, P1, P2, Scalar(0, 0, 0), 1, 8);

}

void onMouse(int event, int x, int y, int flags, void* param)
{

	//MouseParams* mp = (MouseParams*)param;

	//Mat &src = mp->image;

	Mat* src_pointer = (Mat*)param;
	Mat src = *src_pointer;

	Mat dst = src.clone();

	Mat dst_proj = Mat(src.rows, src.cols, CV_8UC3);

	Vec3b rgb;
	//take the coordinates

	if (event == CV_EVENT_LBUTTONDOWN)
	{
		//mp->p = Point(y, x);

		rgb = src.at<Vec3b>(y, x);

		printf("Pos(x,y): %d,%d  Color(RGB): %d,%d,%d\n",
			x, y,
			(int)(src).at<Vec3b>(y, x)[2],
			(int)(src).at<Vec3b>(y, x)[1],
			(int)(src).at<Vec3b>(y, x)[0]);

		std::cout << "Area is " << calculateArea(src, rgb) << std::endl;

		CenterOfMass center_of_mass = calculateCenterOfMass(src, rgb);

		std::cout << "Center of mass " << center_of_mass.r_i << " " << center_of_mass.c_i << std::endl;

		std::cout << "Axis of elongation " << calculateAxisOfElongation(src, rgb) * 180 / PI << std::endl;

		std::cout << "Perimeter is: " << calculatePerimeter(src, dst, rgb) << std::endl;

		std::cout << "Thinness Ratios is " << calculateThinnessRatio(src, dst, rgb) << std::endl;

		std::cout << "Aspect Ratio is " << calculateAspectRatio(src, rgb) << std::endl;

		std::cout << "Display center of mass " << std::endl;

		DrawCross(dst, Point(center_of_mass.c_i, center_of_mass.r_i), 20, Scalar(255, 255, 255), 2);

		std::cout << "Display axis of elongation " << std::endl;

		displayElongationAxis(dst, center_of_mass.c_i, center_of_mass.r_i, calculateAxisOfElongation(src, rgb));

		horizontalProjection(src, dst_proj, rgb);

		verticalProjection(src, dst_proj, rgb);

		imshow("New Image", dst);
		imshow(" New New Imgae", dst_proj);

 	}
}



void displayGeometricalFeatures()
{
	Mat src;
	// Read image from file 
	char fname[MAX_PATH];
	MouseParams mp;

	while (openFileDlg(fname))
	{
		src = imread(fname);
		//Create a window
		namedWindow("My Window", 1);

		//set the callback function for any mouse event
		setMouseCallback("My Window", onMouse, (void*)&src);
		
		//show the image
		imshow("My Window", src);

		// Wait until user press some key
		waitKey(0);
	}
	
}


void keepObjects(double threshold, int option)
{	
	Mat src, dst;
	// Read image from file 
	char fname[MAX_PATH];
	std::vector<Vec3b> colors;
	double object_features;

	while (openFileDlg(fname))
	{
		src = imread(fname);

		int height = src.rows;
		int width = src.cols;

		dst = Mat(height, width, CV_8UC3);

		for (int r = 0; r < height; r++)
			for (int c = 0; c < width; c++)
			{
				Vec3b pixel = src.at<Vec3b>(r, c);
				
				if (std::find(colors.begin(), colors.end(), pixel) == colors.end() && pixel != Vec3b(255, 255, 255))
				{	

					if (option)
					{
						object_features = calculateArea(src, pixel);
					}
					else
					{
						object_features = calculateAxisOfElongation(src, pixel);
					}
						

					if (object_features <= threshold)
					{
						calculatePerimeter(src, dst, pixel);
					}
					colors.push_back(pixel);

				}
			}

		imshow("New image", dst);

		waitKey(0);
	}
	
}

//Lab 4 - Label objects

void colorImage(Mat labels, int label, Mat dst)
{
	std::default_random_engine gen;
	std::uniform_int_distribution<int> d(0, 255);
	Vec3b newColor;
	uchar r, g, b;

	std::map <int, Vec3b> labelsToColors;
	for (int i = 1; i <= label; i++)
	{
		r = d(gen);
		g = d(gen);
		b = d(gen);

		newColor = Vec3b(b, g, r);
		labelsToColors[i] = newColor;
	}

	
	for (int i = 0; i < labels.rows; i++)
	{
		for (int j = 0; j < labels.cols; j++)
		{
			uchar pixel = labels.at<uchar>(i, j);
			if (labelsToColors.count(pixel))
			{
				Vec3b color = labelsToColors.find(pixel)->second;
				dst.at<Vec3b>(i, j) = color;
			}
			else
			{
				dst.at<Vec3b>(i, j) = 255;
			}
			
			
		}
	}


}

std::vector<Point2i> findNeighborsN8(Mat img, Point2i q)
{	
	uchar point;
	std::vector<Point2i> neighbors;

	for (int i = -1; i <= 1; i++)
		for (int j = -1; j <= 1; j++)
		{
			neighbors.push_back(Point2i(q.x + i, q.y + j));
			
		}

	return neighbors;
	
}

std::vector<Point2i> findNeighborsN4(Mat img, Point2i q)
{
	std::vector<Point2i> neighbors;

	int di[4] = { -1, 0, 1, 0 };
	int dj[4] = { 0, -1, 0, 1 };

	for (int k = 0; k < 4; k++)
	{
		neighbors.push_back(Point2i(q.x + di[k], q.y + dj[k]));
	}

	return neighbors;

}

std::vector<Point2i> findNeighborNp(Mat img, Point2i q)
{
	std::vector<Point2i> neighbors;

	int di[4] = { 0, -1, -1, -1 };
	int dj[4] = { -1, -1, 0, 1 };

	for (int k = 0; k < 4; k++)
	{
		neighbors.push_back(Point2i(q.x + di[k], q.y + dj[k]));
	}

	return neighbors;
	
}

void BFS(Mat img, Mat labels)
{
	double t = (double)getTickCount();

	uchar label = 0;

	std::queue<Point2i> Q;

	int height = img.rows;
	int width = img.cols;

	labels = Mat::zeros(height, width, CV_8UC1);

	Mat dst = Mat(labels.rows, labels.cols, CV_8UC3);

	std::vector<Point2i> neighbors;

	for (int i = 0; i < height; i++)
	{
		for (int j = 0; j < width; j++)
		{
			if (img.at<uchar>(i,j) == 0 && labels.at<uchar>(i,j) == 0)
			{
				label++;
				labels.at<uchar>(i, j) = label;
				Q.push(Point2i(i, j));

				while (!Q.empty())
				{
					Point2i q = Q.front();
					Q.pop();
					neighbors = findNeighborsN8(img, q);
					for (auto &n : neighbors) 
					{
						if (img.at<uchar>(n.x,n.y) == 0 && labels.at<uchar>(n.x,n.y) == 0)
						{
							labels.at<uchar>(n.x, n.y) = label;
							Q.push(n);
						}
					}
				}
			}
		}
	}

	t = ((double)getTickCount() - t) / getTickFrequency();

	printf("Time = %.3f [ms]\n", t * 1000);

	colorImage(labels, (int)label, dst);

	imshow("New image", dst);


}

void giveLabelBFS()
{
	
	Mat labels, img;

	char fname[MAX_PATH];

	while (openFileDlg(fname))
	{
		img = imread(fname, CV_LOAD_IMAGE_GRAYSCALE);

		BFS(img, labels);
			
		waitKey(0);
	}

	
}

void equivalenceClasses(Mat img, Mat labels)
{
	double t = (double)getTickCount();

	int label = 0, newLabel = 0;

	labels = Mat::zeros(img.rows, img.cols, CV_8UC1);

	int height = img.rows;
	int width = img.cols;

	std::vector<std::vector<int>> edges;

	std::vector<Point2i> neighbors;

	std::queue<int> Q;

	Mat dst = Mat(labels.rows, labels.cols, CV_8UC3);

	for (int i = 0; i < height; i++)
	{
		for (int j = 0; j < width; j++)
		{
			if (img.at<uchar>(i, j) == 0 && labels.at<uchar>(i, j) == 0)
			{
				std::vector<int> L;

				neighbors = findNeighborNp(img, Point2i(i, j));

				for (auto &n : neighbors)
				{
					if (labels.at<uchar>(n.x, n.y) > 0)
					{
						L.push_back(labels.at<uchar>(n.x, n.y));
					}
				}
				if (L.size() == 0)
				{
					label++;
					labels.at<uchar>(i, j) = label;
				}
				else
				{
					auto min_elem = std::min_element(L.begin(), L.end());
					int x = *min_elem;
					labels.at<uchar>(i, j) = x;
					for (auto &y : L)
					{
						if ( y != x )
						{
							edges.resize(label + 1);
							edges[x].push_back(y);
							edges[y].push_back(x);
						}
					}
				}


			}
		}
	}

	colorImage(labels, label, dst);
	imshow("Partial image", dst);

	std::vector<int> newLabels(label+1, 0);

	for (int i = 1; i <= label; i++ )
	{
		if (newLabels[i] == 0)
		{
			newLabel++;
			newLabels[i] = newLabel;
			Q.push(i);

			while(!Q.empty())
			{
				int x = Q.front();
				Q.pop();

				for (auto &y : edges[x])
				{
					if (newLabels[y] == 0)
					{
						newLabels[y] = newLabel;
						Q.push(y);
					}
				}
			}

		}
	}

	for (int i = 0; i < height; i++)
		for (int j = 0; j < width; j++)
		{
			labels.at<uchar>(i, j) = newLabels[labels.at<uchar>(i, j)];
		}

	t = ((double)getTickCount() - t) / getTickFrequency();

	printf("Time = %.3f [ms]\n", t * 1000);

	colorImage(labels, newLabel,dst);
	imshow("New image", dst);

}

void giveLabelEquivalenceClasses()
{
	Mat labels, img;

	char fname[MAX_PATH];

	while (openFileDlg(fname))
	{
		img = imread(fname, CV_LOAD_IMAGE_GRAYSCALE);

		equivalenceClasses(img, labels);

		waitKey(0);
	}
	
}

void borderTracingAlgorithm(Mat image, Mat dst)
{

	std::vector <my_point> contour;

	int dj[8] = { 1,  1,  0, -1, -1, -1, 0, 1 }; // rand (coordonata orizontala)
	int di[8] = { 0, -1, -1, -1,  0,  1, 1, 1 }; // coloana (coordonata verticala)

	byte dir = 7;
	int x_start, y_start;

	int height = image.rows;
	int width = image.cols;

	for (int i = 0; i < height; i++)
	{
		for (int j = 0; j < width; j++)
		{
			if (image.at<uchar>(i,j) == 0)
			{
				x_start = j;
				y_start = i;
				goto label;
			}
		}
	}

	label : std::cout << x_start << " " << y_start << std::endl;

	int n = 0; //indexul primul pixel de contur curent
	boolean finished = false;
	int j = x_start;
	int i = y_start;
	contour.push_back(my_point{ j,i,dir });
	byte d;

	int x, y;

	while(!finished)
	{
		if (dir % 2 == 0)
		{
			dir = (dir + 7) % 8;
		}
		else
		{
			dir = (dir + 6) % 8;
		}
		int k = 0;
		boolean out = false;
		while (k <= 7)
		{
			d = (dir + k) % 8;
			x = j + dj[d];
			y = i + di[d];

			if (image.at<uchar>(y,x) == 0)
			{
				dir = d;
				contour.push_back(my_point{ x, y, dir });
				j = x;
				i = y;
				n++;
				break;
			}
			k++;

		}

		cond :if (n > 1 && contour[0].x  == contour[n-1].x && 
				contour[1].x == contour[n].x     &&
				contour[0].y == contour[n - 1].y &&
				contour[1].y == contour[n].y)
		{
			finished = true;
			
		}
	}
	

	for (int i = 0; i < dst.rows; i++)
		for (int j = 0 ; j < dst.cols; j++)
		{
			dst.at<uchar>(i, j) = 255;
		}

	for (int i = 0; i < contour.size() - 1; i++)
	{
		dst.at<uchar>(contour[i].y, contour[i].x) = 0;
	}

	for (int i = 0; i < contour.size() - 2; i++)
	{
		printf("%d ", contour[i].c);
	}

	std::vector<byte> chain_code;

	for (int i =0 ; i < contour.size() - 2; i++)
	{
		byte value = ((contour[i + 1].c - contour[i].c) + 8) % 8;
		chain_code.push_back(value);
	}
	chain_code.push_back(((contour[i+1].c - contour[i].c) + 8) % 8);

	printf("\n");
	for (int i = 0; i < chain_code.size(); i++)
	{
		printf("%d ", chain_code[i]);
	}


	imshow("New Image", dst);
	
}

void borderTracingAlgorithmInit()
{	

	Mat img, dst;

	char fname[MAX_PATH];

	while (openFileDlg(fname))
	{
		img = imread(fname, CV_LOAD_IMAGE_GRAYSCALE);
		dst = img.clone();

		borderTracingAlgorithm(img, dst);

		waitKey(0);
	}


}

void drawObjectContour()
{
	
}

void chainCode()
{
	
}

void derivativeChainCode()
{
	
}

void reconstructsBorderFromChainCode()
{

	Mat image = Mat(255, 255, CV_8UC1);
	byte dir = 7;

	FILE* fp = fopen("Images/reconstruct.txt", "rt");

	if (!fp)
		printf("Error opening the text file !\n");

	int x, y, N,c;
	fscanf(fp, "%d", &x);
	fscanf(fp, "%d", &y);
	fscanf(fp, "%d", &N);

	for (int i = 0; i < N; i++)
	{
		image.at<uchar>(y, x) = 255;
		fscanf(fp, "%d", &c);
		
	}



}


int main()
{
	int op;
	do
	{
		system("cls");
		destroyAllWindows();
		printf("Menu:\n");
		printf(" 1 - Open image\n");
		printf(" 2 - Open BMP images from folder\n");
		printf(" 3 - Image negative - diblook style\n");
		printf(" 4 - BGR->HSV\n");
		printf(" 5 - Resize image\n");
		printf(" 6 - Canny edge detection\n");
		printf(" 7 - Edges in a video sequence\n");
		printf(" 8 - Snap frame from live video\n");
		printf(" 9 - Mouse callback demo\n");
		printf(" 10 - Negative image demo\n");
		printf(" 11 - Change gray level by an additive factor\n");
		printf(" 12 - Change gray level by a multiplicative factor\n");
		printf(" 13 - Create a random color image\n");
		printf(" 14 - The inverse of matrix\n");
		printf(" 15 - Copy RGB channels into 3 matrices\n");
		printf(" 16 - Convert a color image into a grayscale image\n");
		printf(" 17 - Convert a grayscale image into a binary image\n");
		printf(" 18 - BGR->HSV without cvtcolor\n");
		printf(" 19 - Check if pair (i,j) is inside the image\n");
		printf(" 20 - Geometrical features of one selected binary object\n");
		printf(" 21 - Keep objects that have their area < TH_area\n");
		printf(" 22 - Keep objects that have a specific orientation phi\n");
		printf(" 23 - Label objects using BFS\n");
		printf(" 24 - Label objects using equivalence classes\n");
		printf(" 25 - Tracing border object - draw the contour\n");
		printf(" 26 - Build the chain code of an image\n");
		printf(" 27 - Reconstructs the border of an object\n");
		printf(" 0 - Exit\n\n");
		printf("Option: ");
		scanf("%d",&op);
		switch (op)
		{
			case 1:
				testOpenImage();
				break;
			case 2:
				testOpenImagesFld();
				break;
			case 3:
				testParcurgereSimplaDiblookStyle(); //diblook style
				break;
			case 4:
				//testColor2Gray();
				testBGR2HSV();
				break;
			case 5:
				testResize();
				break;
			case 6:
				testCanny();
				break;
			case 7:
				testVideoSequence();
				break;
			case 8:
				testSnap();
				break;
			case 9:
				testMouseClick();
				break;
			case 10:
				testNegativeImage();
				break;
			case 11:
				changeGrayLevelByAdditiveFactor();
				break;
			case 12:
				changeGrayLevelByMultiplicativeFactor();
				break;
			case 13:
				createAColorImage();
				break;
			case 14:
				determineInverse();
				break;
			case 15:
				copyRGBChannels();
				break;
			case 16:
				color2Grayscale();
				break;
			case 17:
				Grayscale2Binary();
				break;
			case 18:
				BGR2HSV();
				break;
			case 19:
				isInside();
				break;
			case 20:
				displayGeometricalFeatures();
				break;
			case 21:
				double area;
				std::cout << "Give area: ";
				std ::cin >> area;
				keepObjects(area, 1);
				break;
			case 22:
				double phi;
				std::cout << "Give phi: ";
				std::cin >> phi;
				keepObjects(phi, 0);
				break;
			case 23:
				giveLabelBFS();
				break;
			case 24:
				giveLabelEquivalenceClasses();
				break;
			case 25:
				borderTracingAlgorithmInit();
				break;

		}
	}
	while (op!=0);
	return 0;

}
