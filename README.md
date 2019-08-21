#  Project Talon

This is a javafx program I created in about a day in order to automate tasks using native input. This project utilizes the java robot class to accomplish that. The application moves the mouse in human like curves and has varying delays for mouse/keyboard inputs.

---
The image below is the main application window (currently not resizable). First you press add region. A screenshot is taken of your current display. You then select a square region in which you want to add to the task queue where the mouse will move to (see second image). You can then select the second delay between tasks, whether or not the mouse clicks, etc. Once you are done you press start and the program will begin executing the queue. The top box shows all selected regions with their x and y coordinates on the screen. There is also a progress bar which shows the current progress of the tasks on the list. This project was never intended to have very clean or efficient/commented code. This was more of a one day project to automate some mundane tasks I wanted to do.

---
Warning:
As mentioned this program uses native input so it will quite literally move your mouse and press keys as if it were a person. While the program is moving your mouse you will have no control of its movement. I added default hardcoded delays between tasks of a few seconds in order to allow the movement of the mouse to stop the program if necessary.

---
<img src="https://i.imgur.com/FDwDg0S.png" width="500" height="500">

Below is an image of the region selection popup. The selection region is marked in red on selection.

<img src="https://i.imgur.com/VimwlMM.png" width="500" height="500">
