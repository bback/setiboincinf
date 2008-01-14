#!/bin/sh
shdirname=$(dirname "$0")
if [ "$shdirname" = "." ]
then
        shdir=$(pwd -P)
else
        shdir=${shdirname#.}
fi
#echo SHDIR is $shdir
JARDIR=$shdir
CLASSPATH="$JARDIR/boincinf.jar:$JARDIR/lib/hsqldb/hsqldb.jar:$JARDIR/lib/swt-2.1.3-linux-gtk/swt.jar:$JARDIR/lib/swt-2.1.3-linux-gtk/swt-pi.jar:$JARDIR/lib/org.eclipse.ui.workbench_2.1.3/workbench.jar:$JARDIR/lib/org.eclipse.jface_2.1.3/jface.jar:$JARDIR/lib/org.eclipse.core.runtime_2.1.1/runtime.jar:$JARDIR/lib/org.eclipse.core.boot_2.1.3/boot.jar:$JARDIR/lib/xerces.jar"
export CLASSPATH
java -Djava.library.path="$JARDIR/lib/swt-2.1.3-linux-gtk" boincinf.BoincNetStats
