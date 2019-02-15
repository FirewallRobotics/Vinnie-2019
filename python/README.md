# Raspberry Pi Setup

## frc.json 

* This file contains the details/settings that are loaded by the python script upon boot.
* It needs to be copied to any new raspberryPi and placed in /boot
* From your laptop:
  * `scp frc.json pi@frcvision.local:.`
* On the raspberryPi
  * `sudo cp ~pi/frc.json /boot/.`
  * reboot and the raspberryPi camera and the Microsoft USB Camera should be available
