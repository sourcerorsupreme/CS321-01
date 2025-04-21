/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package laststand;

/**
 *
 * @author sourc
 * Interface for running tests within the game
 * This allows different test classes to be accessed uniformly
 */
public interface TestRunner {
    /**
     * Run all tests implemented by this test runner
     */
    void runAllTests();
    
    /**
     * Get the name of this test runner
     * @return The name of the test runner
     */
    String getTestName();
}