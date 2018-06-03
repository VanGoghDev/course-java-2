package ru.firsov.javacore;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        Team team = new Team("Horny Pirates!");
        Course course = new Course(team);
        course.doIt();
        team.showResults();
    }
}
