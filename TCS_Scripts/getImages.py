import numpy as np
import urllib
import cv2
import time

URLS = ["http://192.168.43.10/picam/cam_pic.php",
		"http://192.168.43.7/picam/cam_pic.php",]
		
def url_to_image(url):
	resp = urllib.urlopen(url)
	image = np.asarray(bytearray(resp.read()), dtype="uint8")
	image = cv2.imdecode(image, cv2.IMREAD_COLOR)
	return image

image1 = url_to_image(URLS[0])
image2 = url_to_image(URLS[1])
cv2.imwrite('r.png',image1)
cv2.imwrite('l.png',image2)