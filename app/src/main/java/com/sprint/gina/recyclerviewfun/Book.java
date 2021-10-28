package com.sprint.gina.recyclerviewfun;



public class Book {
    // a class: a collection of state and behavior that completely
    // describes something
    // state: fields (AKA attributes)
    // behavior: methods

    // object: an instance of the class
    // class like a blueprint, an object is a realization of the blueprint

    // members of a class include fields, methods, nested classes, constructors, etc.

    // state for a Book
    private String title;
    private String author;
    private int numPages;

    // constructors
    // we get default value constructor (DVC) for free

    // we can define our own constructors
    // explicit value constructors (EVC) accepts values for initialization
    public Book(String title, String paramAuthor, int paramNumPages) {
        // constructors are non-static
        // this refers to the invoking object
        // in a constructor, this is the object we are creating
        this.title = title; // shadowing: the param title has the same name as the field title
        this.author = paramAuthor;
        this.numPages = paramNumPages;
    }

    // as soon as you define your own constructor, you lose the free DVC
    public Book() {
        // typically initialize fields to standard default values
        title = "BLANK TITLE";
        author = "BLANK AUTHOR";
        numPages = -1;
    }

    // recall Object is the direct or the indirect super class of every class
    // it has some helpful methods, like toString()
    // toString() returns a string representation of an object whenever it is needed (e.g. println())
    // annotations: metainformation for the IDE and compiler
    // doesn't affect source
    // @Deprecated, @SuppressWarnings, @Override
    @Override
    public String toString() {
        // called implicitly when a string rep of an object is needed
        // DO NOT put printlns() in here
        // BUILD the string rep of the invoking object (this!!)
        return this.title + " by " + this.author;
    }

    // access modifiers
    // 1. public: accessible anywhere
    // 2. package-private: accessible anywhere within the package
    // 3. protected: package-private plus acessible for any subclass, even outside of the package
    // 4. private: accessible only within this class

    // rule of thumb: start with private and add more generous access as justified
    // only expose state and behavior as needed (and do it safely)

    // getters and setters
    // getter (AKA accessor)
    public String getTitle() {
        return title;
    }
    // setter (AKA mutators)
    public void setTitle(String newTitle) {
        title = newTitle; // may want to validate newTitle first??
    }
    // task 1: add the getters and setters for our other two fields
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getNumPages() {
        return numPages;
    }

    public void setNumPages(int numPages) {
        this.numPages = numPages;
    }
    // task 2: create a Point class and a PointTester class
    // Point represents a point in 2D space
    // x and y
    // toString(): example (0, 0)
    // exercise some constructors and methods

    // task 3: create a Circle class and a CircleTester class
    // Circle has a center (Point) and a radius (double)
    // composition: "has-a" relationship
    // inheritance: "is-a" relationship

}