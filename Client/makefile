CFLAGS:=-c -Wall -Weffc++ -g -std=c++11 -Iinclude 
LDFLAGS:=-lboost_system -lboost_thread -pthread

all: BGRSclient

BGRSclient: bin/Client.o bin/Decoder.o bin/keyBoardParser.o bin/connectionHandler.o
	@echo 'Building target: BGRSclient'
	g++ -o bin/BGRSclient bin/Client.o bin/Decoder.o bin/keyBoardParser.o bin/connectionHandler.o  $(LDFLAGS)
	@echo 'Finished building target: BGRSclient'
	@echo ' '

bin/Client.o: src/Client.cpp
	g++ -g -Wall -Weffc++ -std=c++11 -c -Iinclude -o bin/Client.o src/Client.cpp

bin/Decoder.o: src/Decoder.cpp
	g++ -g -Wall -Weffc++ -std=c++11 -c -Iinclude -o bin/Decoder.o src/Decoder.cpp

bin/keyBoardParser.o: src/keyBoardParser.cpp
	g++ -g -Wall -Weffc++ -std=c++11 -c -Iinclude -o bin/keyBoardParser.o src/keyBoardParser.cpp

bin/connectionHandler.o: src/connectionHandler.cpp
	g++ -g -Wall -Weffc++ -std=c++11 -c -Iinclude -o bin/connectionHandler.o src/connectionHandler.cpp



clean:
	rm -f bin/*
