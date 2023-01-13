
Raspberry Pi Sense HAT Android App
======  

This Android app allows you to communicate with a Raspberry Pi Sense HAT, a hardware add-on board for the Raspberry Pi that includes a 8x8 RGB LED matrix, and allows you to monitor *pressure*, *humidity*, *temperature*, *orientation* and *movement*.


### Features
- Plot Roll, Pitch, and Yaw values in real time
- Set the colors of the LEDs on the Sense HAT
- Display real-time values from all of the sensors on the Sense HAT

---  

### Requirements
- Android Studio Dolphin or higher
- Raspberry Pi running Raspbian with a Sense HAT installed

---  

### Installation
- Download the code or clone this repository
- Run the [server](https://github.com/drfifonz/senseHat-iot-apps/tree/api/api-server) on Raspberry Pi:
```bash  
python -m server
```  
- Run the app on an emulator or a physical device running Android 12 (API 30) or higher


### Usage
#### Plotting Roll, Pitch, and Yaw
To plot the Roll, Pitch, and Yaw values in real time, tap the "Orientation Chart" button in the main menu.   
This will open a new screen where you can see the values being plotted in real time.

#### Setting LED Colors
To set the colors of the LEDs on the Sense HAT, tap the "Set LED Colors" button in the main menu.   This will open a new screen where you can choose the colors for each or multiple LEDs using a color picker.   
Once you have selected the LED's and chosen the color, tap the "Done" button to apply the changes.

#### Displaying Sensor Values
To display the real-time values from all of the sensors on the Sense HAT,   tap the "Display Sensor Values" button in the main menu.   
This will open a new screen where you can see the values being updated in real time.