package fr.choicegame.event;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class TestEventTest {

	/*
	 * input
	 */
	private String event;
	/*
	 * Expected number of errors
	 */
	private int errors;
	
	public TestEventTest(String event, int errors) {
		this.event = event;
		this.errors = errors;
	}
	
	@Parameters(name = "{index} : {0}, {1} errors") 
    public static Collection<Object[]> dt() {
    	Object[][] data = new Object[][]{
    		{"TRIGGER TRIG1",0},
    		{"TRIGGER TRIG1 ON",0},
    		{"TRIGGER TRIG1 OFF",0},
    		{"TRIGGER TRIG1 1",1},
    		{"TRIGGER",1},
    		{"TRIGGER TRIG1 TRIG2 ON",1},
    	};//TODO write tests
    	return Arrays.asList(data);
    }

	@Test
	public void test() {
		ArrayList<String> serrors = EventComputer.testEvent(event);
		System.out.println("EVENT:"+event);
		for(String err:serrors)
			System.out.println(err);
		assertEquals("Number of errors expected : "+errors,errors,serrors.size());
	}

}
