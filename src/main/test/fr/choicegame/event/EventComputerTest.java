package fr.choicegame.event;

import static org.junit.Assert.*;

import java.io.File;
import java.net.URISyntaxException;

import org.junit.Test;

import fr.choicegame.Game;
import fr.choicegame.Loader;

public class EventComputerTest {

	@Test
	public void testEventCalledExample1() {
		Game game = new Game(null, null); // TODO loader
		Event ev = new Event(loadEvent("eventtest1.txt"), game.getEventComputer());
		
		ev.action(0, 0, false);
		
		assertEquals("TRIGTEST1",true,game.getTrigger("TRIGTEST1"));
		assertEquals("TESTVAR2",10,game.getGlobalVariable("TESTVAR2"));
		assertEquals("TESTVAR3",165,game.getGlobalVariable("TESTVAR3"));
		assertEquals("TRIGTEST2",false,game.getTrigger("TRIGTEST2"));
		assertEquals("TRIGTEST3",true,game.getTrigger("TRIGTEST3"));
	}
	
	private String loadEvent(String fileName){
		String path = null;
		try {
			path = new File(this.getClass().getResource(fileName).toURI()).getAbsolutePath();
		} catch (URISyntaxException e) {
			fail("Unable to load test file");
		}
		String eventText = Loader.loadTextAsset(path);
		assertNotEquals(null, eventText);
		return eventText;
	}


}
