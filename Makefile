MAKEFLAGS += --silent
INCLUDES = -I  /usr/lib/swi-prolog/include/ -I /usr/lib/jvm/default-java/include -I /usr/lib/jvm/default-java/include/linux
PRELOAD=/usr/lib/libswipl.so:/snap/core20/1518/usr/lib/x86_64-linux-gnu/libtinfo.so.6.2


all: header javac lib

javac:  
	javac ./Annotations/*.java 
	javac AnnsProcessor.java 
	javac -processor AnnsProcessor Probability.java

header:
	    javac -h .  Probability.java

lib: 
		g++  -Wno-unused-result $(INCLUDES) -shared -fPIC -o libPrologFromCpp.so init.cpp cplint.cpp

run:
		LD_PRELOAD=$(PRELOAD) LD_LIBRARY_PATH=./ java Probability
clean:
	@rm -rf *.class ./Annotations/*.class *.so cplint.cpp cplint.pl
		

