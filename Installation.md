# Quick installation guide #

Ensure Java 1.5 or later is installed (Linux/Windows)

Open a text console (Windows: click on Start, choose Run and enter command, then click OK).
Type the command java -version and press enter. Following should appear:

C:\>java -version
java version "1.5.0\_15"
Java(TM) 2 Runtime Environment, Standard Edition (build 1.5.0\_15-b04)
Java HotSpot(TM) Client VM (build 1.5.0\_15-b04, mixed mode)

If you see an output like

'JAVA' is not recognized as an internal or external command,
operable program or batch file.

or the Java version is before 1.5, then you have to install Java on your system.

Go to http://java.sun.com/ and download the latest Java Runtime Environment (JRE) for your platform. To find the JRE navigate towards the J2SE downloads. Install it and retry the java -version command as described above.

## Install the application (Windows) ##

Download the complete application. Create a new directory on your hard disk (such as C:\Tools\SetiBoincInf) and extract the contents of the downloaded .zip file into this new directory. Thats it.

Finally you could create a desktop icon to start SetiBoincInf. Navigate your explorer to the new directory where you just extracted the .zip into. RIGHT-click onto the run\_output.bat file and drag it over to your windows desktop (onto a free area). Then drop the file and choose 'Create shortcut...' from the popup menu. Name the shortcut SetiBoincInf.

## Install the application (Linux) ##

Download the complete application. Create a new directory on your harddisk (such as /home/myuser/SetiBoincInf) and extract the contents of the downloaded .zip file into this new directory.
Add the executable bit to the shell start scripts. Open a shell, change into the directory where you extracted the .zip file into, and execute the command: chmod u+x **.sh**

Finally you could create a desktop icon to start SetiBoincInf. How you have to do this depends on the window manager you use. Please refer to the documentation of your window manager.
Create a shortcut to the shell script run\_output.sh and name it SetiBoincInf.

## Configure before first usage (Linux/Windows) ##

Start the application by executing the run\_output.bat (Windows) or run\_output.sh (Linux) file (maybe use the shortcut you created before). On first startup the database tables are created silently.
Now open the Options menu. Click on File and choose Options. The options dialog appears.
Options dialog

You have to enter your complete Account ID. This ID is the account ID you got from the SETI@HOME project and which you use to attach to the project. The ID is used to authenticate against the SETI@HOME webserver when retrieving the data.
Press OK to leave the dialog. Now all is setup properly.