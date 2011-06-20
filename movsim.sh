#!/bin/sh
#
#
# linux start script for movsim.org simulator
# add script to PATH for executables
# and customize the path to the jar file
#
# increase heap size for VM by -Xmx512m
#
#
#java -jar $HOME/bin/movsim.jar $@
java -jar movsim-0.1-jar-with-dependencies.jar $@

