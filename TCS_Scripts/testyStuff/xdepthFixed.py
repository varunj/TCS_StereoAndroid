import numpy as np
import urllib
import cv2
from matplotlib import pyplot as plt

'''
ndisparities 	0-512 	steps of 16		//32+1
SADWindowSize	5-255	odd				//125+1
'''
dispar = [0, 16, 32, 48, 64, 80, 96, 112, 128, 144, 160, 176, 192, 208, 224, 240, 256, 272, 288, 304, 320, 336, 352, 368, 384, 400, 416, 432, 448, 464, 480, 496, 512]
windowSize = [5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31, 33, 35, 37, 39, 41, 43, 45, 47, 49, 51, 53, 55, 57, 59, 61, 63, 65, 67, 69, 71, 73, 75, 77, 79, 81, 83, 85, 87, 89, 91, 93, 95, 97, 99, 101, 103, 105, 107, 109, 111, 113, 115, 117, 119, 121, 123, 125, 127, 129, 131, 133, 135, 137, 139, 141, 143, 145, 147, 149, 151, 153, 155, 157, 159, 161, 163, 165, 167, 169, 171, 173, 175, 177, 179, 181, 183, 185, 187, 189, 191, 193, 195, 197, 199, 201, 203, 205, 207, 209, 211, 213, 215, 217, 219, 221, 223, 225, 227, 229, 231, 233, 235, 237, 239, 241, 243, 245, 247, 249, 251, 253, 255]

imageL = cv2.imread('l.png')
imageR = cv2.imread('r.png')
imageL = cv2.cvtColor(imageL, cv2.COLOR_BGR2GRAY)
imageR = cv2.cvtColor(imageR, cv2.COLOR_BGR2GRAY)

for x in dispar:
	for y in windowSize:
		# stereo = cv2.StereoBM_create(cv2.STEREO_BM_BASIC_PRESET,ndisparities=x, SADWindowSize=y)
		stereo = cv2.StereoBM_create(numDisparities=x, blockSize=y)
		disparity = stereo.compute(imageL,imageR, cv2.CV_32F)
		cv2.imwrite('inv_d' + str(x) + '_w' + str(y) + '.png',disparity)
		print 'x=' + str(x) + ' y=' + str(y)