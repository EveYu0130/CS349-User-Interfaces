Description:

    This game "Snake" is a classic 2D video game where the objective is to control a line as it grows in length while preventing it from hitting the bounds or itself.
    This program our is written in C++, using XLib.

    The general rules of the game are:
    
        - The game screen displays a snake  always in motion and a fruit at a fixed point on the screen.
        - The direction of the snake can be controlled (by arrows keys) in that it can switch its direction by either turning left or right at a time.
        - The objective of the snake is to eat the target fruit, which makes it grow in length. Conventionally, there is always only one fruit on the screen.
        - As the snake eats the fruit, it disappears, and another one appears at a random location.
        - The snake can die by eating itself (when it collides with itself) or by hitting the edge of the screen or any other obstacles. When the snake dies, a game-end screen should appear.
        - The game uses a range of 1-10 to specify the speed of the snake and keep track of a score that updates over the course of the game. The growth of score is decided by the speed of game. (eg. grows 1 up with speed 1).

Controls/Keys:
    
    - Press arrow keys and/or W,A,S,D for direction and movement
    - p - pause/unpause
    - r - restart
    - q - quit
    - ENTER - start/continue
    
Enhancements:
    There are two Power-ups:
        - Life - if the snake eats a life, it will gain an extra life.
        - Poison - if the snake eats a poison, it will lost a life.

Development Environment:
    Snake is designed with C++ and Xlib, running on an XServer (macOS XQuartz). 
    It is compiled and tested with g++ 4.9.4 on the UW student environment.