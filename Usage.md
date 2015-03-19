# Usage #

The GUI application allows you to retrieve updates from SETI@HOME website. You can export your database and other users who work for the same account can import the data. You can delete unneeded entries by selecting them and choosing delete from menu (or popup menu).
All of this is really intuitive, try it out!

You can also run a single update by invoking the run\_update.bat file. This stores the retrieved data in the database without to show a GUI. This is useful if you want to start SetiBoincInf updates using the crontab, but there is another way to continuously run the updates.

To allow continuous updates, invoke the run\_updateloop.bat file. This text console program updates all X minutes and sleeps between the updates. To configure the sleep time change the run\_updateloop.bat file. The preconfigured default is 60 minutes.

(Note: Needless to say that Linux users execute the .sh files instead of the .bat files ;) ).