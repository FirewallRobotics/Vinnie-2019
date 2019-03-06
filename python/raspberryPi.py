import numpy as np
import cv2


import cv2
import numpy
import math
from networktables import NetworkTablesInstance
from enum import Enum

def TrackTheHatchPanel(frame, sd):
    HatchLower= (0,0,0)
    HatchUpper = (0,0,0)
    if sd.getNumber("Track", 0):
        HatchLower= (0,103,105)
        HatchUpper = (150,255,255) #hatch panel
        sd.putNumber("Tracking", 0)
    else if sd.getNumber("Track", 1):
        HatchLower= (16,18,108)
        HatchUpper = (32,52,127)
        sd.putNumber("Tracking", 1)
    else:
        print("Could not get smartdashboard value, using hatch panel")
        HatchLower= (0,103,105)
        HatchUpper = (150,255,255)
        sd.putNumber("Tracking", 2)
    #frame = cv2.flip(frame, 1)

    #Blur out the Image
    #blurred = cv2.GaussianBlur(frame, (11,11), 0)
    hsv = cv2.cvtColor(frame, cv2.COLOR_BGR2HSV)
    
    #Make a mask for the pixals that meet yhe HSV filter 
    #then run a bunch of dolations and
    #erosions to remove any small blobs still in the mask
    mask = cv2.inRange(hsv, HatchLower, HatchUpper)
    mask = cv2.erode(mask, None, iterations = 2)
    mask = cv2.dilate(mask, None, iterations = 2)
    
    #find the Contours in the mask and initialize the
    #current (x,y) center of the ball
    a, cnts, b = cv2.findContours(mask, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_NONE)
    center = None
    #only do stuff if a single contor was found
    if len(cnts) > 0:
        #find the largest contour in the mask, then use it
        #to compute the minimum enclosing circle and centroid
        c = max(cnts, key=cv2.contourArea)
        ((x,y), radius) = cv2.minEnclosingCircle(c)
        M = cv2.moments(c)
        center = (int(M["m10"] / M["m00"]), int (M["m01"] / M["m00"]))

        #if the dectected contour has a radius big enough, we will send it
        if radius > 15:
            #draw a circle around the target and publish values to smart dashboard
            cv2.circle(frame, (int(x), int(y)), int(radius), (255,255,8), 2)
            cv2.circle(frame, center, 3, (0,0,225), -1)
            sd.putNumber('X',x)
            sd.putNumber('Y',y)
            sd.putNumber('R', radius)
            print("X: " + repr(round(x, 1)) + " Y: " + repr(round(y, 1)) + " Radius: " + repr(round(radius, 1)))

        else:
            print("WTF")
            #let the RoboRio Know no target has been detected with -1
            sd.putNumber('X', -1)
            sd.putNumber('Y', -1)
            sd.putNumber('R', -1)
            
    

cap = cv2.VideoCapture(0)
#HatchPanel = HatchPanelPipeline()
ntinst = NetworkTablesInstance.getDefault()
ntinst.startClientTeam(team)
SmartDashBoardValues = ntinst.getTable('SmartDashboard')

while(True):
    # Capture frame-by-frame
    ret, frame = cap.read()

    # Our operations on the frame come here
    TrackTheHatchPanel(frame, SmartDashBoardValues, ntinst)
    cv2.imshow('frame',frame)
    #print(type(mask))
    #res = cv2.bitwise_and(frame,frame, mask=mask) 
    #cv2.imshow('frame',frame)
    #cv2.imshow('mask',mask)
    #cv2.imshow('res',res) 

    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

# When everything done, release the capture
cap.release()
cv2.destroyAllWindows()