// Pedestrian Detection
//

#include "stdafx.h"
#include "common.h"
#include <stdlib.h>
#include <fstream>
#include <sstream>
#include <rpcndr.h>

#define RESIZED_X_VALUE 64
#define RESIZED_Y_VALUE 128

#define CROPPED_X_VALUE 100
#define CROPPED_Y_VALUE 200

#define CROPPED_TEST_X_VALUE 64
#define CROPPED_TEST_Y_VALUE 128

#define CELL_SIZE 8

#define NR_FEATURES 4284

const String IMAGE_TEST_NAME = "000009.png";


//Bounding Box for pedestrian
typedef struct
{
	char objectName[30];
	float xTop;
	float yTop;
	float xBottom;
	float yBottom;

}BBoxObject;

void showHistogram(const std::string& name, int* hist, const int  hist_cols, const int hist_height)
{
	Mat imgHist(hist_height, hist_cols, CV_8UC3, CV_RGB(255, 255, 255)); // constructs a white image

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
		line(imgHist, p1, p2, CV_RGB(255, 0, 255)); // histogram bins colored in magenta
	}

	imshow(name, imgHist);
}


//read from file the coordinates of an object
BBoxObject findTheObject(String fnameOld)
{
	//read from file the labels
	const char* fname = fnameOld.c_str();

	std::ifstream file(fname, std::ios::in);

	if (!file.good())
	{
		std::cout << "File could not open!" << std::endl;
		exit(0);
	}

	BBoxObject bbox;

	float no_value1, no_value2, no_value3;

	std::string line;
	bool find = false;

	while (getline(file, line))
	{
		if (line.find("Pedestrian") == 0)
		{
			std::istringstream ss(line);
			ss >> bbox.objectName >> no_value1 >> no_value2 >> no_value3 >> bbox.xTop >> bbox.yTop >> bbox.xBottom >> bbox.yBottom;
			find = true;
			break;
		}
	}

	//if the image doesn't contain a pedestrian, then the first object will be taken as label
	if (!find)
	{
		file.clear();
		file.seekg(0, std::ios::beg);
		getline(file, line);
		std::istringstream ss(line);
		ss >> bbox.objectName >> no_value1 >> no_value2 >> no_value3 >> bbox.xTop >> bbox.yTop >> bbox.xBottom >> bbox.yBottom;
	}

	return bbox;
	
}

//a function to represent the bounding box
//can be used
Mat doBBox(Mat image)
{
	Mat imageBBox = image.clone();

	BBoxObject bbox = findTheObject("NULL");

	for (int row = bbox.yTop; row < bbox.yBottom; row++)
	{
		imageBBox.at<Vec3b>(row, bbox.xTop) = Vec3b(0, 0, 0);
		imageBBox.at<Vec3b>(row, bbox.xBottom) = Vec3b(0, 0, 0);
	}

	for (int col = bbox.xTop; col < bbox.xBottom; col++)
	{
		imageBBox.at<Vec3b>(bbox.yTop, col) = Vec3b(0, 0, 0);
		imageBBox.at<Vec3b>(bbox.yBottom, col) = Vec3b(0, 0, 0);
	}
	
	return imageBBox;

}

//crop the interest area (pedestrian or other object in image)
Mat cropImage(Mat image, BBoxObject bbox)
{
	Mat croppedImage;
	
	int height = abs(bbox.yBottom - bbox.yTop + 1);
	int width = abs(bbox.xBottom - bbox.xTop + 1);

	int cropValueX = (bbox.xTop + CROPPED_X_VALUE) > image.cols ? image.cols - bbox.xTop - 1 : CROPPED_X_VALUE;
	int cropValueY = (bbox.yTop + CROPPED_Y_VALUE) > image.rows ? image.rows - bbox.yTop - 1 : CROPPED_Y_VALUE;
	Mat ROI(image, Rect(bbox.xTop, bbox.yTop, cropValueX, cropValueY));

	Mat bufferImage;

	ROI.copyTo(bufferImage);

	resize(bufferImage, croppedImage, Size(RESIZED_X_VALUE, RESIZED_Y_VALUE), true);

	
	return croppedImage;

	
}

//calculate gradients of the image using Sobel
void calculateGradientImages(Mat image, Mat* magnit, Mat* angle)
{
	image.convertTo(image, CV_32F);

	Mat gx, gy;

	Sobel(image, gx, CV_32F, 1, 0, 1);
	Sobel(image, gy, CV_32F, 0, 1, 1);

	//imshow("image gx", gx);
	//imshow("image gy", gy);

	Mat mag, ang;

	cartToPolar(gx, gy, mag, ang, 1);

	*magnit = mag;
	*angle = ang;
	
}

//calculate HOG
void calculateHOG(Mat magnitude, Mat angle, std :: vector<float> values, Mat* featureVector, int index)
{

	int dif = 0;
	float proportion = 0;

	float leftSideValue = 0;
	float rightSideValue = 0;

	int leftIndex = 0;
	int rightIndex = 0;

	for (int row = 0; row < CELL_SIZE; row++)
		for (int col = 0; col < CELL_SIZE; col++)
		{
			float magnitudeValue = magnitude.at<float>(row, col);
			float angleValue = angle.at<float>(row, col);


			if (angleValue == values[0])
			{
				dif = abs(angleValue - values[0]);
				leftIndex = 0;
				rightIndex = 1;
			}

			if (angleValue > values[8])
			{
				dif = abs(angleValue - 360);
				leftIndex = 0;
				rightIndex = 8;
			}
			for (int k = 0; k < CELL_SIZE; k++)
			{
				if (angleValue > values[k] && angleValue <= values[k + 1])
				{
					dif = abs(angleValue - values[k + 1]);
					leftIndex = k;
					rightIndex = k + 1;

					break;
				}
			}

			proportion = (40 - dif) / 40;
			leftSideValue = proportion * magnitudeValue;
			rightSideValue = (1 - proportion) * magnitudeValue;

			featureVector->at<float>(index, leftIndex) += leftSideValue;
			featureVector->at<float>(index, rightIndex) += rightSideValue;
			
		}

	
	
}

//calculate HOG call function
Mat calculateHOGcore(Mat magnitude, Mat angle)
{
	int height = magnitude.rows;
	int width = magnitude.cols;

	Mat featureVector(128, CELL_SIZE + 1, CV_32F);
	featureVector = 0.0;

	std::vector<float> values;

	for (int i = 0; i < CELL_SIZE + 1; i++)
	{
		values.push_back(i * 40);
	}

	int index = 0; 
	for (int row = 0; row < width; row+=CELL_SIZE)
		for (int col = 0; col < height; col+=CELL_SIZE)
		{
			Mat mag(magnitude, Rect(row, col, CELL_SIZE, CELL_SIZE));
			Mat ang(angle, Rect(row, col, CELL_SIZE, CELL_SIZE));

			calculateHOG(mag, angle, values, &featureVector, index);

			index++;
		}
	
	return featureVector;
}

//normalize the feature vectors and concatenate them
void normalizeVector(std :: vector<std::vector<float>> features, std::vector<float>* finalHistogram)
{
	float sum = 0;
	for (std::vector<float> v : features)
	{
		for (float f : v)
		{
			sum += f*f;
		}
	}
	float normalizedValue = sqrt(sum);

	for (std::vector<float> v : features)
	{
		for (float f : v)
		{
			float value = f / normalizedValue;
			finalHistogram->push_back(value);
		}
	}

}

std::vector<float> normalizeHistogram(Mat featureVector)
{
	std::vector<std::vector<float>> features;
	std::vector<float> finalHistogram;

	for (int i = 0; i < 119; i++)
	{
		features.push_back(featureVector.row(i));
		features.push_back(featureVector.row(i + 1));
		features.push_back(featureVector.row(i + 8));
		features.push_back(featureVector.row(i + 9));

		normalizeVector(features, &finalHistogram);

		features.clear();

	}

	return finalHistogram;
	
}


std::vector<float> calculateVectorFeatures(Mat image, BBoxObject bbox)
{
	Mat magnitude, angle;
	Mat croppedImage = cropImage(image, bbox);
	calculateGradientImages(croppedImage, &magnitude, &angle);
	Mat featureVector = calculateHOGcore(magnitude, angle);
	std::vector<float> finalHistogram = normalizeHistogram(featureVector);

	return finalHistogram;
	
}

void readImages(Mat* training_features, Mat* training_labels)
{
	String path_images("path");

	std::vector<String> fn_images;
	std::vector<String> fn_labels;
	std::vector<Mat> images;
	std::vector<int> labels;

	String path_labels("path");
	glob(path_images, fn_images, true);
	glob(path_labels, fn_labels, true);

	std::vector<std::vector<float>> features;
	int label = 0;

	Mat features_training(0, NR_FEATURES, CV_32F);

	for (long k = 0; k < fn_images.size(); k++)
	{
		std::cout << k << std::endl;

		Mat im = imread(fn_images[k]);
		if (im.empty()) continue;

		BBoxObject object = findTheObject(fn_labels[k]);

		std::vector <float> hog = calculateVectorFeatures(im, object);

		Mat row(1, NR_FEATURES, CV_32F);
		memcpy(row.data, hog.data(), hog.size() * sizeof(float));
		features_training.push_back(row);
		
		if (strcmp("Pedestrian", object.objectName) == 0)
		{
			label = 1;
		}else
		{
			label = 0;
		}
		labels.push_back(label);
		features.push_back(hog);

	}
	Mat labels_training(labels, true);

	*training_features = features_training;
	*training_labels = labels_training;


	std::cout << "Size of data: " << images.size() << std::endl;
	std::cout << "Size of data: " << labels.size() << std::endl;
	std::cout << "Size of data: " << features_training.rows << std::endl;

}

//train and predict
void doClassification(Mat training_data, Mat labels)
{
	std::cout << "Begin training" << std::endl;

	Ptr<ml::SVM> svm = ml::SVM::create();
	svm->setType(ml::SVM::C_SVC);
	svm->setKernel(ml::SVM::LINEAR);
	//svm->setGamma(0.001);
	svm->setC(1000000);

	svm->train(training_data, ml::ROW_SAMPLE, labels);

	std::cout << "training finished" << std::endl;

	Vec3b green(0, 255, 0), blue(255, 0, 0);

	String path_test = "path"

	Mat image = imread(path_test + IMAGE_TEST_NAME, CV_LOAD_IMAGE_COLOR);

	std::cout << image.rows << " " << image.cols;

	imshow("My image", image);

	std::cout << "Begin predict" << std::endl;

	for (int y = 0; y < image.rows; y+=RESIZED_Y_VALUE)
		for (int x = 0; x < image.cols; x+=RESIZED_X_VALUE)
		{	
			if ((x + RESIZED_X_VALUE) > image.cols) continue;
			if ((y + RESIZED_Y_VALUE) > image.rows) continue;

			Mat croppedImage(image, Rect(x, y, RESIZED_X_VALUE, RESIZED_Y_VALUE));
			resize(croppedImage, croppedImage, Size(RESIZED_X_VALUE, RESIZED_Y_VALUE), true);

			Mat magnitude, angle;
			calculateGradientImages(croppedImage, &magnitude, &angle);
			Mat featureVector = calculateHOGcore(magnitude, angle);
			const std::vector<float> finalHistogram = normalizeHistogram(featureVector);
			Mat histMatrix(finalHistogram);
			histMatrix = histMatrix.t();

			int response = svm->predict(histMatrix);

			if (response == 1)
			{
				circle(image, Point(x, y), 6, Scalar(255,0, 0), -1, 8);
			}else
			{
				circle(image, Point(x, y), 6, Scalar(0, 0, 255), -1, 8);
			}
			
			std::cout << response << std::endl;
		}

	imshow("Classif", image);
	waitKey(0);

}

int main()
{
	system("cls");
	destroyAllWindows();
	
	Mat training_data, labels;
	readImages(&training_data, &labels);
	std::cout << training_data.size() << std::endl;
	
	doClassification(training_data, labels);

	
	std::cout << labels.size() << std::endl;

	return 0;
}


