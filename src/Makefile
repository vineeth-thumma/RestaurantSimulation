# This is a very simple makefile for building the Restaurant Simulator
# project when using Java on stdlinux. Feel free to add any improvements:
# e.g. pattern rules, automatic tracking of dependencies, etc. There
# is a lot of info about "make" on the web.

# Java compiler
JAVAC = javac

# Java compiler flags
JAVAFLAGS = -g 

# Creating a .class file
COMPILE = $(JAVAC) $(JAVAFLAGS)

# One of these should be the "main" class listed in Runfile
CLASS_FILES = Restaurant.class Manager.class Cook.class Diner.class Machine.class Order.class PrepareOrder.class Table.class Timer.class

# The first target is the one that is executed when you invoke
# "make". 

all: $(CLASS_FILES) 

# The line describing the action starts with <TAB>
%.class : %.java
	$(COMPILE) $<

