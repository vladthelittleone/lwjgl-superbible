# lwjgl-superbible
Samples from [OpenGL SuperBible](http://www.openglsuperbible.com/) by Graham Sellers Richard S. Wright Nicholas Haemel using lwjgl.

## Listenings

* [Listening 2.7](https://github.com/vladthelittleone/lwjgl-superbible/blob/master/src/main/java/Listening2_7.java)
* [Listening 2.9](https://github.com/vladthelittleone/lwjgl-superbible/blob/master/src/main/java/Listening2_9.java)
* [Listening 3.1-3.4](https://github.com/vladthelittleone/lwjgl-superbible/blob/master/src/main/java/Listening3_1.java)

## Set library path

**For IntelliJ IDEA** 

* Select *Run > Edit Configuration > Application*
* Set VM option -Djava.library.path=**target/natives** 
* Run application

## Set up for MacOS

You must set second parameter of Display.create() method as in example:

```
ContextAttribs attrs = new ContextAttribs(3, 2).withForwardCompatible(true).withProfileCore(true)
Display.create(new PixelFormat(), attrs);
```
