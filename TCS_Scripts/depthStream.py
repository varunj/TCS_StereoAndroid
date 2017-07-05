import numpy as np
import urllib
import cv2
from matplotlib import pyplot as plt

'''
numDisparities: the disparity search range. For each pixel algorithm will find the best disparity from 0 (default minimum disparity) to numDisparities. The search range can then be shifted by changing the minimum disparity.
blockSize: the linear size of the blocks compared by the algorithm. The size should be odd (as the block is centered at the current pixel). Larger block size implies smoother, though less accurate disparity map. Smaller block size gives more detailed disparity map, but there is higher chance for algorithm to find a wrong correspondence.
ndisparities 	0-512 	steps of 16
SADWindowSize	5-255	odd
'''
	
URLS = ["http://192.168.43.7/picam/cam_pic.php",
        "http://192.168.43.10/picam/cam_pic.php",]

def url_to_image(url):
    resp = urllib.urlopen(url)
    image = np.asarray(bytearray(resp.read()), dtype="uint8")
    image = cv2.imdecode(image, cv2.IMREAD_COLOR)
    return image


# while True:
imageL = url_to_image(URLS[1])
imageR = url_to_image(URLS[0])
imageL = cv2.cvtColor(imageL, cv2.COLOR_BGR2GRAY)
imageR = cv2.cvtColor(imageR, cv2.COLOR_BGR2GRAY)


stereo = cv2.StereoBM(cv2.STEREO_BM_BASIC_PRESET,ndisparities=16, SADWindowSize=255)

disparity = stereo.compute(imageL,imageR, cv2.CV_32F)

plt.imshow(disparity, 'gray')
plt.show()

# cv2.namedWindow("disparity", 0)
# res = cv2.convertScaleAbs(disparity)
# cv2.imwrite("disparity", res);
# waitKey(0);