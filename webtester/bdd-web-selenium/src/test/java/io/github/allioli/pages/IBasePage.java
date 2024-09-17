package io.github.allioli.pages;

public interface IBasePage {

    // Wait for element(s) that identify this page to be loaded
    public void await();

    // Wait for default elements of this page to be loaded
    public default void waitForDefaultElements() {
        // Not all pages need this method, providing default implementation
        System.out.println("Called default implementation of waitForDefaultElements");
    }
}
