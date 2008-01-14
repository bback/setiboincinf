@echo off

::First test to see if we are on NT or similar OS by seeing
::if the ampersand is interpreted as a command separator
> script echo 1234&rem
type script | find "rem"
if not errorlevel 1 goto WIN9X

:NT

::echo Running under NT
del script
::Get the current batch file's short path
for %%x in (%0) do set BatchPath=%%~dpsx
for %%x in (%BatchPath%) do set BatchPath=%%~dpsx
::echo BatchPath = %BatchPath%
goto TEST

:WIN9X
::echo Running under Win9X
::An assumption is made that the batch file is run by double-clicking.
::This means %0 is a short file name and path with no quotes
::Test for quotes by quoting %0
if not exist "%0" goto ERROR
:: Make a line fragment per http://www.ericphelps.com/batch/lines/frag-dbg.htm
echo e 100 "set BatchPath="> script
echo rcx>> script
echo e>> script
echo n ~temp.bat>> script
echo w>> script
echo q>>script
debug < script > nul
del script
::Change to the batch file's drive
%0\
::Change to the batch file's directory
cd %0\..
::Use the TRUENAME command to get the short path
truename | find ":" >> ~temp.bat
call ~temp.bat
del ~temp.bat
set BatchPath=%BatchPath%\
::echo BatchPath = %BatchPath%
goto TEST

:TEST
::Use the information
@set CLASSPATH=%BatchPath%boincinf.jar;%BatchPath%lib\hsqldb\hsqldb.jar;%BatchPath%lib\swt_2.1.3-win32\swt.jar;%BatchPath%lib\org.eclipse.ui.workbench_2.1.3\workbench.jar;%BatchPath%lib\org.eclipse.jface_2.1.3\jface.jar;%BatchPath%lib\org.eclipse.core.runtime_2.1.1\runtime.jar;%BatchPath%lib\org.eclipse.core.boot_2.1.3\boot.jar;%BatchPath%lib\xerces.jar
@set SWT_LIB_PATH=%BatchPath%lib\swt_2.1.3-win32
goto DONE

:ERROR
echo Insert error-handling code here
goto DONE

:DONE
