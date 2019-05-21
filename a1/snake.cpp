/*
CS 349 A1 Skeleton Code - Snake

- - - - - - - - - - - - - - - - - - - - - -

Commands to compile and run:

    g++ -o snake snake.cpp -L/usr/X11R6/lib -lX11 -lstdc++
    ./snake

Note: the -L option and -lstdc++ may not be needed on some machines.
*/

#include <iostream>
#include <sstream>
#include <list>
#include <cstdlib>
#include <sys/time.h>
#include <math.h>
#include <stdio.h>
#include <unistd.h>
#include <string>

/*
 * Header files for X functions
 */
#include <X11/Xlib.h>
#include <X11/Xutil.h>

using namespace std;

/*
 * Global game state variables
 */
const int Border = 1;
const int BufferSize = 10;
int FPS = 30;
int speed = 5;
const int width = 800;
const int height = 600;
int score = 0;
int lives = 1;
bool startScreen = true;
bool paused = false;
bool gameover = false;

/*
 * Information to draw on the window.
 */
struct XInfo {
	Display	 *display;
	int		 screen;
	Window	 window;
	GC		 gc[3];
	int		width;		// size of window
	int		height;
    XColor  color;
    Colormap colormap;
};


/*
 * Function to put out a message on error exits.
 */
void error( string str ) {
  cerr << str << endl;
  exit(0);
}

// get microseconds
unsigned long now() {
	timeval tv;
	gettimeofday(&tv, NULL);
	return tv.tv_sec * 1000000 + tv.tv_usec;
}


/*
 * An abstract class representing displayable things.
 */
class Displayable {
	public:
		virtual void paint(XInfo &xinfo) = 0;
};

class Fruit : public Displayable {
	public:
		virtual void paint(XInfo &xinfo) {
            XFillArc(xinfo.display, xinfo.window, xinfo.gc[2],
           x, y, fruitsize, fruitsize, 0, 360 * 64);
			// XFillRectangle(xinfo.display, xinfo.window, xinfo.gc[2], x, y, fruitsize, fruitsize);
        }

        Fruit() {
            fruitsize = 25;
            // ** ADD YOUR LOGIC **
            // generate the x and y value for the fruit
            x = (rand() % 32) * fruitsize;
            y = (rand() % 24) * fruitsize;
        }

        int getX() {
			return x;
		}

		int getY() {
			return y;
		}

    private:
        int x;
        int y;
        int fruitsize;
};

Fruit *fruit = new Fruit();

class Life : public Displayable {
	public:
		virtual void paint(XInfo &xinfo) {
            XDrawLine(xinfo.display, xinfo.window, xinfo.gc[2], x, y+lifeSize/2, x+lifeSize, y+lifeSize/2);
            XDrawLine(xinfo.display, xinfo.window, xinfo.gc[2], x+lifeSize/2, y, x+lifeSize/2, y+lifeSize);
			// XFillRectangle(xinfo.display, xinfo.window, xinfo.gc[0], x, y, liveSize, liveSize);
        }

        Life() {
            lifeSize = 25;
            x = (rand() % 32) * lifeSize;
            y = (rand() % 24) * lifeSize;
            while (x == fruit->getX() && y == fruit->getX() ||
                    (x == 100) && (y == 200) ||
                    (x == 100 + lifeSize) && (y == 200)) {
                        x = (rand() % 32) * lifeSize;
                        y = (rand() % 24) * lifeSize;
            }
        }

        int getX() {
			return x;
		}

		int getY() {
			return y;
		}

    private:
        int x;
        int y;
        int lifeSize;
};
Life *life = new Life();

class Poison : public Displayable {
	public:
		virtual void paint(XInfo &xinfo) {
            XDrawLine(xinfo.display, xinfo.window, xinfo.gc[0], x, y, x+poisonSize, y+poisonSize);
            XDrawLine(xinfo.display, xinfo.window, xinfo.gc[0], x+poisonSize, y, x, y+poisonSize);
           //  XDrawArc(xinfo.display, xinfo.window, xinfo.gc[0],
           // x - (poisonSize / 2), y - (poisonSize / 2), poisonSize, poisonSize, 0, 360 * 64);
			// XFillRectangle(xinfo.display, xinfo.window, xinfo.gc[0], x, y, liveSize, liveSize);
        }

        Poison() {
            poisonSize = 25;
            x = (rand() % 32) * poisonSize;
            y = (rand() % 24) * poisonSize;
            while ((x == fruit->getX() && y == fruit->getX()) ||
                    (x == life->getX() && y == life->getX()) ||
                    (x == 100) && (y == 200) ||
                    (x == 100 + poisonSize) && (y == 200)) {
                x = (rand() % 32) * poisonSize;
                y = (rand() % 24) * poisonSize;
            }
        }

        int getX() {
			return x;
		}

		int getY() {
			return y;
		}

    private:
        int x;
        int y;
        int poisonSize;
};

Poison *poison = new Poison();

class Score : public Displayable {
	public:
		virtual void paint(XInfo &xinfo) {
            XDrawString(xinfo.display, xinfo.window, xinfo.gc[0], 20, 40, "Score: ", 7);
            XDrawString(xinfo.display, xinfo.window, xinfo.gc[0], 80, 40, str_score.c_str(), str_score.length());
            XDrawString(xinfo.display, xinfo.window, xinfo.gc[0], 20, 60, "Speed: ", 7);
            XDrawString(xinfo.display, xinfo.window, xinfo.gc[0], 80, 60, str_speed.c_str(), str_speed.length());
            XDrawString(xinfo.display, xinfo.window, xinfo.gc[0], 20, 80, "Lives: ", 7);
            XDrawString(xinfo.display, xinfo.window, xinfo.gc[0], 80, 80, str_lives.c_str(), str_lives.length());
        }

        Score() {
            updateScore();
            updateSpeed();
            updateLives();
        }

        void updateScore() {
            ostringstream ss;
            ss << score;
            str_score = ss.str().c_str();
        }

        void updateSpeed() {
            ostringstream ss;
            ss << speed;
            str_speed = ss.str().c_str();
        }

        void updateLives() {
            ostringstream ss;
            ss << lives;
            str_lives = ss.str().c_str();
        }

        string getScore() {
            return str_score;
        }
    private:
        string str_score;
        string str_speed;
        string str_lives;
};

Score *sscore = new Score();

class SplashScreen : public Displayable {
	public:
		virtual void paint(XInfo &xinfo) {
            XDrawString(xinfo.display, xinfo.window, xinfo.gc[0], 250, 100, "*SNAKE*", 7);
            XDrawString(xinfo.display, xinfo.window, xinfo.gc[0], 250, 140, "Xueyao Yu", 9);
            XDrawString(xinfo.display, xinfo.window, xinfo.gc[0], 250, 160, "20622323", 8);
            XDrawString(xinfo.display, xinfo.window, xinfo.gc[0], 250, 200, "How to play?", 12);

            XFillArc(xinfo.display, xinfo.window, xinfo.gc[2], 250, 220, 25, 25, 0, 360 * 64);
            XDrawString(xinfo.display, xinfo.window, xinfo.gc[0], 290, 240, "- Fruit", 7);
            XDrawLine(xinfo.display, xinfo.window, xinfo.gc[2], 250, 250+25/2, 275, 250+25/2);
            XDrawLine(xinfo.display, xinfo.window, xinfo.gc[2], 250+25/2, 250, 250+25/2, 275);
            XDrawString(xinfo.display, xinfo.window, xinfo.gc[0], 290, 250+25/2, "- Life", 6);
            XDrawLine(xinfo.display, xinfo.window, xinfo.gc[0], 250, 275, 275, 300);
            XDrawLine(xinfo.display, xinfo.window, xinfo.gc[0], 275, 275, 250, 300);
            XDrawString(xinfo.display, xinfo.window, xinfo.gc[0], 290, 275+25/2, "- Poison", 8);
            XDrawString(xinfo.display, xinfo.window, xinfo.gc[0], 250, 320, "Arrow keys and/or WASD for direction and movement", 49);
            XDrawString(xinfo.display, xinfo.window, xinfo.gc[0], 250, 340, "p - pause/unpause", 17);
            XDrawString(xinfo.display, xinfo.window, xinfo.gc[0], 250, 360, "r - restart", 11);
            XDrawString(xinfo.display, xinfo.window, xinfo.gc[0], 250, 380, "q - quit", 8);
            XDrawString(xinfo.display, xinfo.window, xinfo.gc[0], 250, 400, "PRESS ENTER to start game!", 26);
        }

        SplashScreen() {}
};

class GameoverScreen : public Displayable {
	public:
		virtual void paint(XInfo &xinfo) {
            if (lives == 0) {
                XDrawString(xinfo.display, xinfo.window, xinfo.gc[0], 250, 100, "GameOver!", 9);
            }
            XDrawString(xinfo.display, xinfo.window, xinfo.gc[0], 250, 140, "Your Score is: ", 15);
            XDrawString(xinfo.display, xinfo.window, xinfo.gc[2], 375, 140, sscore->getScore().c_str(), sscore->getScore().length());
            if (lives != 0) {
                XDrawString(xinfo.display, xinfo.window, xinfo.gc[0], 250, 170, "Press ENTER to continue", 23);
            }
            XDrawString(xinfo.display, xinfo.window, xinfo.gc[0], 250, 190, "Press r to restart", 18);
            XDrawString(xinfo.display, xinfo.window, xinfo.gc[0], 250, 210, "Press q to quit", 15);
        }

        GameoverScreen() {}
};

list<Displayable *> dList;           // list of Displayables
SplashScreen *splashscreen = new SplashScreen();
GameoverScreen *gameoverScreen = new GameoverScreen();

class SnakeBlock : public Displayable {
    public:
        virtual void paint(XInfo &xinfo) {
            // string grey = "#A9A9A9";
            // xinfo.colormap = DefaultColormap(xinfo.display, 0);
         	// XParseColor(xinfo.display, xinfo.colormap, grey.c_str(), &xinfo.color);
         	// XAllocColor(xinfo.display, xinfo.colormap, &xinfo.color);
            XFillRectangle(xinfo.display, xinfo.window, xinfo.gc[1], x, y, blockSize, blockSize);
            // string black = "#000000";
            // xinfo.colormap = DefaultColormap(xinfo.display, 0);
         	// XParseColor(xinfo.display, xinfo.colormap, black.c_str(), &xinfo.color);
         	// XAllocColor(xinfo.display, xinfo.colormap, &xinfo.color);
            // XDrawRectangle(xinfo.display, xinfo.window, xinfo.gc[0], x, y, blockSize, blockSize);
        }

        SnakeBlock(int x, int y): x(x), y(y) {
            blockSize = 25;
        }

        int getX() {
			return x;
		}

		int getY() {
			return y;
		}
    private:
        int x;
        int y;
        int blockSize;

};

class Snake : public Displayable {
	public:
		virtual void paint(XInfo &xinfo) {
            list<SnakeBlock *>::const_iterator begin = sList.begin();
            list<SnakeBlock *>::const_iterator end = sList.end();
            while( begin != end ) {
        		SnakeBlock *block = *begin;
        		block->paint(xinfo);
                begin++;
        	}
        }

		void move(XInfo &xinfo) {
            int sleepTime = 20000 + (10 - speed) * 6000;
            if (now() - lastMove < sleepTime) {
                usleep(sleepTime);
            } else {
                SnakeBlock *head = sList.front();
                SnakeBlock *newHead = NULL;
                switch (direction)
                {
                case 1: // up
                    newHead = new SnakeBlock(head->getX(), head->getY() - blockSize);
                    break;
                case 2: // down
                    newHead = new SnakeBlock(head->getX(), head->getY() + blockSize);
                    break;
                case 3: // left
                    newHead = new SnakeBlock(head->getX() - blockSize, head->getY());
                    break;
                case 4: // right
                    newHead = new SnakeBlock(head->getX() + blockSize, head->getY());
                    break;
                }
                if (newHead->getX() < 0 || newHead->getX() >= 800 || newHead->getY() < 0 || newHead->getY() >= 600) {
                    didHitObstacle();
                } else if (newHead->getX() == fruit->getX() && newHead->getY() == fruit->getY()) {
                    didEatFruit();
                } else if (newHead->getX() == life->getX() && newHead->getY() == life->getY()) {
                    didEatLife();
                } else if (newHead->getX() == poison->getX() && newHead->getY() == poison->getY()) {
                    didEatPoison();
                } else if (collidesWithBody(newHead)) {
                    didHitObstacle();
                } else {
                    sList.push_front(newHead);
                    SnakeBlock *temp = sList.back();
                    sList.pop_back();
                    delete(temp);
                }
                lastMove = now();
            }

            // ** ADD YOUR LOGIC **
            // Here, you will be performing collision detection between the snake,
            // the fruit, and the obstacles depending on what the snake lands on.
		}

		int getX() {
			return x;
		}

		int getY() {
			return y;
		}

        int getDirection() {
            return direction;
        }

        /*
         * ** ADD YOUR LOGIC **
         * Use these placeholder methods as guidance for implementing the snake behaviour.
         * You do not have to use these methods, feel free to implement your own.
         */
        void didEatFruit() {
            SnakeBlock *newHead = new SnakeBlock(fruit->getX(), fruit->getY());
            sList.push_front(newHead);
            delete(fruit);
            fruit = new Fruit();
            score += speed;
            sscore->updateScore();
        }

        void didEatLife() {
            SnakeBlock *newHead = new SnakeBlock(life->getX(), life->getY());
            sList.push_front(newHead);
            SnakeBlock *temp = sList.back();
            sList.pop_back();
            delete(life);
            life = new Life();
            lives++;
            sscore->updateLives();
        }

        void didEatPoison() {
            lives--;
            sscore->updateLives();
            if (lives == 0) {
                gameover = true;
                dList.pop_front(); // remove snake
                dList.pop_front(); // remove fruit
                dList.pop_front(); // remove sscore
                dList.pop_front(); // remove life
                dList.push_front(gameoverScreen);
            }
            SnakeBlock *newHead = new SnakeBlock(poison->getX(), poison->getY());
            sList.push_front(newHead);
            SnakeBlock *temp = sList.back();
            sList.pop_back();
            delete(poison);
            poison = new Poison();
        }

        void didHitObstacle() {
            lives--;
            sscore->updateLives();
            gameover = true;
            dList.pop_front(); // remove snake
            dList.pop_front(); // remove fruit
            dList.pop_front(); // remove sscore
            dList.pop_front(); // remove life
            dList.push_front(gameoverScreen);
        }

        bool collidesWithBody(SnakeBlock *newHead) {
            list<SnakeBlock *>::const_iterator begin = sList.begin();
        	list<SnakeBlock *>::const_iterator end = sList.end();

        	while( begin != end ) {
        		SnakeBlock *block = *begin;
        		if (newHead->getX() == block->getX() && newHead->getY() == block->getY()) {
                    return true;
                } else {
                    begin++;
                }
        	}
            return false;
        }

        void turnLeft() {
            if (direction == 1 || direction == 2) {
                direction = 3;
            }
        }

        void turnRight() {
            if (direction == 1 || direction == 2) {
                direction = 4;
            }
        }

        void turnUp() {
            if (direction == 3 || direction == 4) {
                direction = 1;
            }
        }

        void turnDown() {
            if (direction == 3 || direction == 4) {
                direction = 2;
            }
        }

		Snake(int x, int y): x(x), y(y) {
            direction = 4;
            blockSize = 25;
            lastMove = now();
            SnakeBlock *block1 = new SnakeBlock(x, y);
            SnakeBlock *block2 = new SnakeBlock(x+blockSize, y);
            sList.push_front(block1);
            sList.push_front(block2);
		}

        ~Snake() {
            list<SnakeBlock *>::const_iterator begin = sList.begin();
            list<SnakeBlock *>::const_iterator end = sList.end();
            while( begin != end ) {
        		SnakeBlock *block = *begin;
        		delete(block);
                begin++;
        	}
        }

	private:
		int x;
		int y;
		int blockSize;
        // 1:up, 2:down, 3:left, 4:right
        int direction;
        list<SnakeBlock *> sList;
        unsigned long lastMove;
};

Snake *snake = new Snake(100, 200);


/*
 * Initialize X and create a window
 */
void initX(int argc, char *argv[], XInfo &xInfo) {
	XSizeHints hints;
	unsigned long white, black;

   /*
	* Display opening uses the DISPLAY	environment variable.
	* It can go wrong if DISPLAY isn't set, or you don't have permission.
	*/
	xInfo.display = XOpenDisplay( "" );
	if ( !xInfo.display )	{
		error( "Can't open display." );
	}

   /*
	* Find out some things about the display you're using.
	*/
	xInfo.screen = DefaultScreen( xInfo.display );

    string grey = "#D3D3D3";
    xInfo.colormap = DefaultColormap(xInfo.display, 0);
 	XParseColor(xInfo.display, xInfo.colormap, grey.c_str(), &xInfo.color);
 	XAllocColor(xInfo.display, xInfo.colormap, &xInfo.color);

	white = XWhitePixel( xInfo.display, xInfo.screen );
	black = XBlackPixel( xInfo.display, xInfo.screen );

	hints.x = 100;
	hints.y = 100;
	hints.width = 800;
	hints.height = 600;
	hints.flags = PPosition | PSize;

	xInfo.window = XCreateSimpleWindow(
		xInfo.display,				// display where window appears
		DefaultRootWindow( xInfo.display ), // window's parent in window tree
		hints.x, hints.y,			// upper left corner location
		hints.width, hints.height,	// size of the window
		Border,						// width of window's border
		black,						// window border colour
		xInfo.color.pixel );					// window background colour

	XSetStandardProperties(
		xInfo.display,		// display containing the window
		xInfo.window,		// window whose properties are set
		"animation",		// window's title
		"Animate",			// icon's title
		None,				// pixmap for the icon
		argv, argc,			// applications command line args
		&hints );			// size hints for the window


	/*
	 * Create Graphics Contexts
	 */

    XFontStruct* font_info;
    /* try to load the given font. */
    string font_name = "-*-helvetica-*-r-*-*-16-*-*-*-*-*-*-*";
    font_info = XLoadQueryFont(xInfo.display, font_name.c_str());
    if (!font_info) {
        fprintf(stderr, "XLoadQueryFont: failed loading font '%s'\n", font_name.c_str());
    }


	int i = 0;
	xInfo.gc[i] = XCreateGC(xInfo.display, xInfo.window, 0, 0);
    XSetFont(xInfo.display, xInfo.gc[i], font_info->fid);
	XSetForeground(xInfo.display, xInfo.gc[i], BlackPixel(xInfo.display, xInfo.screen));
	XSetBackground(xInfo.display, xInfo.gc[i], WhitePixel(xInfo.display, xInfo.screen));
	XSetFillStyle(xInfo.display, xInfo.gc[i], FillSolid);
	XSetLineAttributes(xInfo.display, xInfo.gc[i],
	                     3, LineSolid, CapButt, JoinRound);



    i = 1;
    xInfo.gc[i] = XCreateGC(xInfo.display, xInfo.window, 0, 0);
    string green = "#53750b";
    xInfo.colormap = DefaultColormap(xInfo.display, 0);
    XParseColor(xInfo.display, xInfo.colormap, green.c_str(), &xInfo.color);
    XAllocColor(xInfo.display, xInfo.colormap, &xInfo.color);
    XSetFont(xInfo.display, xInfo.gc[i], font_info->fid);
	XSetForeground(xInfo.display, xInfo.gc[i], xInfo.color.pixel);
	XSetBackground(xInfo.display, xInfo.gc[i], WhitePixel(xInfo.display, xInfo.screen));
	XSetFillStyle(xInfo.display, xInfo.gc[i], FillSolid);
	XSetLineAttributes(xInfo.display, xInfo.gc[i],
	                     1, LineSolid, CapButt, JoinRound);

     i = 2;
     xInfo.gc[i] = XCreateGC(xInfo.display, xInfo.window, 0, 0);
     string red = "#CC0000";
     xInfo.colormap = DefaultColormap(xInfo.display, 0);
     XParseColor(xInfo.display, xInfo.colormap, red.c_str(), &xInfo.color);
     XAllocColor(xInfo.display, xInfo.colormap, &xInfo.color);
     XSetFont(xInfo.display, xInfo.gc[i], font_info->fid);
     XSetForeground(xInfo.display, xInfo.gc[i], xInfo.color.pixel);
     XSetBackground(xInfo.display, xInfo.gc[i], WhitePixel(xInfo.display, xInfo.screen));
     XSetFillStyle(xInfo.display, xInfo.gc[i], FillSolid);
     XSetLineAttributes(xInfo.display, xInfo.gc[i],
                          2, LineSolid, CapButt, JoinRound);

    XSelectInput(xInfo.display, xInfo.window,
		ButtonPressMask | KeyPressMask |
		PointerMotionMask |
		EnterWindowMask | LeaveWindowMask |
		StructureNotifyMask);  // for resize events

	/*
	 * Put the window on the screen.
	 */
	XMapRaised( xInfo.display, xInfo.window );
	XFlush(xInfo.display);
}

void paint(XInfo &xinfo) {
    list<Displayable *>::const_iterator begin = dList.begin();
	list<Displayable *>::const_iterator end = dList.end();

	XClearWindow( xinfo.display, xinfo.window );
    cout << "paint" << endl;

    XWindowAttributes windowInfo;
    XGetWindowAttributes(xinfo.display, xinfo.window, &windowInfo);
    unsigned int height = windowInfo.height;
    unsigned int width = windowInfo.width;

	// draw display list
	while( begin != end ) {
		Displayable *d = *begin;
		d->paint(xinfo);
		begin++;
	}
	XFlush( xinfo.display );
}

/*
 * Function to repaint a display list
 */
void repaint( XInfo &xinfo) {
	list<Displayable *>::const_iterator begin = dList.begin();
	list<Displayable *>::const_iterator end = dList.end();

	XClearWindow( xinfo.display, xinfo.window );

	// big black rectangle to clear background

	// draw display list
	while( begin != end ) {
		Displayable *d = *begin;
		d->paint(xinfo);
		begin++;
	}
	XFlush( xinfo.display );
}


void handleKeyPress(XInfo &xinfo, XEvent &event) {
	KeySym key;
	char text[BufferSize];

	/*
	 * Exit when 'q' is typed.
	 * This is a simplified approach that does NOT use localization.
	 */
	int i = XLookupString(
		(XKeyEvent *)&event, 	// the keyboard event
		text, 					// buffer when text will be written
		BufferSize, 			// size of the text buffer
		&key, 					// workstation-independent key symbol
		NULL );					// pointer to a composeStatus structure (unused)
	printf("Got key press -- %c\n", text[0]);
	if (text[0] == 'q') {
        delete(splashscreen);
        if (gameover) {
            delete(gameoverScreen);
            delete(snake);
            delete(fruit);
            delete(sscore);
            delete(life);
            delete(poison);
        }
		error("Terminating normally.");
	}
    if (startScreen) {
        if (key == XK_Return) {
            startScreen = false;
            dList.pop_front();
            dList.push_front(sscore);
            dList.push_front(fruit);
            dList.push_front(life);
            dList.push_front(poison);
            dList.push_front(snake);
        }
    } else {
        if (key == XK_Return && gameover && lives) {
            gameover = false;
            dList.pop_front(); // remove gameoverScreen
            delete(snake);
            delete(fruit);
            delete(life);
            delete(poison);
            fruit = new Fruit();
            life = new Life();
            poison = new Poison();
            snake = new Snake(100, 200);
            sscore->updateLives();
            dList.push_front(sscore);
            dList.push_front(fruit);
            dList.push_front(life);
            dList.push_front(poison);
            dList.push_front(snake);
        }
        if (text[0] == 'a' || key == XK_Left) {
            snake->turnLeft();
        }
        if (text[0] == 'd' || key == XK_Right) {
            snake->turnRight();
        }
        if (text[0] == 'w' || key == XK_Up) {
            snake->turnUp();
        }
        if (text[0] == 's' || key == XK_Down) {
            snake->turnDown();
        }
        if (text[0] == 'p') {
            paused = !paused;
        }
        if (text[0] == 'r') {
            if (gameover) {
                gameover = false;
                dList.pop_front(); // remove gameoverScreen
            } else {
                if (paused) {
                    paused = false;
                }
                dList.pop_front(); // remove snake
                dList.pop_front(); // remove poison
                dList.pop_front(); // remove life
                dList.pop_front(); // remove fruit
            }
            delete(snake);
            delete(fruit);
            delete(life);
            delete(poison);
            fruit = new Fruit();
            life = new Life();
            poison = new Poison();
            snake = new Snake(100, 200);
            score = 0;
            lives = 1;
            sscore->updateScore();
            sscore->updateLives();
            dList.push_front(sscore);
            dList.push_front(fruit);
            dList.push_front(life);
            dList.push_front(poison);
            dList.push_front(snake);
        }
    }
}

void handleAnimation(XInfo &xinfo, int inside) {
    /*
     * ADD YOUR OWN LOGIC
     * This method handles animation for different objects on the screen and readies the next frame before the screen is re-painted.
     */
	snake->move(xinfo);
}

void eventLoop(XInfo &xinfo) {
	// Add stuff to paint to the display list

	XEvent event;
	unsigned long lastRepaint = 0;
	int inside = 0;

	while( true ) {
		/*
		 * This is NOT a performant event loop!
		 * It needs help!
		 */

		if (XPending(xinfo.display) > 0) {
			XNextEvent( xinfo.display, &event );
			cout << "event.type=" << event.type << "\n";
			switch( event.type ) {
				case KeyPress:
					handleKeyPress(xinfo, event);
					break;
				case EnterNotify:
					inside = 1;
					break;
				case LeaveNotify:
					inside = 0;
					break;
			}

		}
        unsigned long end = now();	// get time in microsecond
        if (end - lastRepaint > 1000000 / FPS) {
            if (!paused && !startScreen && !gameover) {
                handleAnimation(xinfo, inside);
                repaint(xinfo);
                lastRepaint = now();
            }
        }
        usleep(1000000 / FPS);
        // if (XPending(xinfo.display) == 0) {
		// 	usleep(1000000 / FPS);
		// }

	}
}


/*
 * Start executing here.
 *	 First initialize window.
 *	 Next loop responding to events.
 *	 Exit forcing window manager to clean up - cheesy, but easy.
 */
int main ( int argc, char *argv[] ) {
	XInfo xInfo;

    if (argc == 3) {
        if (atoi(argv[1]) >= 1 && atoi(argv[1]) <= 100) {
            FPS = atoi(argv[1]);
        }
        if (atoi(argv[2]) >= 1 && atoi(argv[2]) <= 10) {
            speed = atoi(argv[2]);
            sscore->updateSpeed();
        }
    }

	initX(argc, argv, xInfo);
    if (startScreen) {
        dList.push_front(splashscreen);
    }
    paint(xInfo);
	eventLoop(xInfo);
	XCloseDisplay(xInfo.display);
}
