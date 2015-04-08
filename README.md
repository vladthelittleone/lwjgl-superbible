# lwjgl-superbible
Samples from superbible using lwjgl.

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
