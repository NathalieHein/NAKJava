import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import de.nordakademie.nakjava.gamelogic.cardstest.CardTest;
import de.nordakademie.nakjava.gamelogic.client.TestServer;
import de.nordakademie.nakjava.gamelogic.gui.GUITest;
import de.nordakademie.nakjava.gamelogic.winstrategies.TestWinStrategies;

@RunWith(Suite.class)
@SuiteClasses({ CardTest.class, TestWinStrategies.class, GUITest.class,
		TestServer.class })
public class TestSuite {
}
