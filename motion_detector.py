import argparse
import datetime
from imutils.video import FPS
import imutils
import time
import cv2
import os
from skimage import io
import base64
import requests
 
# construct the argument parser and parse the arguments
ap = argparse.ArgumentParser()
ap.add_argument("-v", "--video", help="path to the video file")
ap.add_argument("-a", "--min-area", type=int, default=300, help="minimum area size")
args = vars(ap.parse_args())
 
# if the video argument is None, then we are reading from webcam
if args.get("video", None) is None:
	camera = cv2.VideoCapture(0)
	time.sleep(0.25)
 
# otherwise, we are reading from a video file
else:
	camera = cv2.VideoCapture(args["video"])
# initialize the first frame in the video stream
firstFrame = None
myFlag = 0
myCounter = 0
count = 0
while True:
	# grab the current frame and initialize the occupied/unoccupied
	# text
	(grabbed, frame) = camera.read()
	text = "Unoccupied"

	# if the frame could not be grabbed, then we have reached the end
	# of the video
	if not grabbed:
		break
 
	# resize the frame, convert it to grayscale, and blur it
	frame = imutils.resize(frame, width=300)
	gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
	gray = cv2.GaussianBlur(gray, (21, 21), 255)
 
	# if the first frame is None, initialize it
	if firstFrame is None:
		firstFrame = gray
		continue

	# compute the absolute difference between the current frame and
	# first frame
	frameDelta = cv2.absdiff(firstFrame, gray)
	thresh = cv2.threshold(frameDelta, 100, 255, cv2.THRESH_BINARY)[1]
 
	# dilate the thresholded image to fill in holes, then find contours
	# on thresholded image
	thresh = cv2.dilate(thresh, None, iterations=2)
	cnts = cv2.findContours(thresh.copy(), cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
	cnts = cnts[0] if imutils.is_cv2() else cnts[1] 

	# loop over the contours
	for c in cnts:
		# if the contour is too small, ignore it
		if cv2.contourArea(c) < args["min_area"]:
			continue
 
		# compute the bounding box for the contour, draw it on the frame,
		# and update the text
		(x, y, w, h) = cv2.boundingRect(c)
		cv2.rectangle(frame, (x, y), (x + w, y + h), (0, 255, 0), 2)
		text = "Occupied"
	    #If the frame is occupied for a long period, send more pictures of the moving object to the database
		if myFlag == 0 or count == 100:
		#Create a picutre from the current frame, than encoded to base64 and post it.
			cv2.imwrite("/home/david/Documents/PythonCameraProject/test.jpg", frame)
			image = open('/home/david/Documents/PythonCameraProject/test.jpg', 'rb') 
			image_read = image.read() 
			image_64_encode = base64.encodestring(image_read)
				
			#Get/Post to my current ip address	
			requests.get("http://192.168.100.2:8081/api/connect-firebase")
			requests.post('http://192.168.100.2:8081/api/save-data-firebase', json={'encodedImage': image_64_encode})
			myFlag = 1
			if count == 150:
				count = 0
		count = count + 1

			
	# draw the text and timestamp on the frame
	if text == "Unoccupied":
		myCounter += 1
	else:
		myCounter = 0
	if myCounter >= 100:
		myFlag = 0

	cv2.putText(frame, "Room Status: {}".format(text), (10, 20),
		cv2.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 255), 2)
	cv2.putText(frame, datetime.datetime.now().strftime("%A %d %B %Y %I:%M:%S%p"),
		(10, frame.shape[0] - 10), cv2.FONT_HERSHEY_SIMPLEX, 0.35, (0, 0, 255), 1)
 
	# show the frame and record if the user presses a key
	cv2.imshow("Security Feed", frame)
	cv2.imshow("Thresh", thresh)
	cv2.imshow("Frame Delta", frameDelta)
	key = cv2.waitKey(1) & 0xFF
 
	# if the `q` key is pressed, break from the lop
	if key == ord("q"):
		break
	cv2.waitKey(50)
 
# cleanup the camera and close any open windows
camera.release()
cv2.destroyAllWindows()
