import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import de.nordakademie.nakjava.gamelogic.cardset.TestCardSet;
import de.nordakademie.nakjava.gamelogic.cardstest.CardTest;
import de.nordakademie.nakjava.gamelogic.client.TestServer;
import de.nordakademie.nakjava.gamelogic.client.TestTransformer;
import de.nordakademie.nakjava.gamelogic.gui.GUITest;
import de.nordakademie.nakjava.gamelogic.server.TestDeckPersister;
import de.nordakademie.nakjava.gamelogic.statemachine.TestStateMachine;
import de.nordakademie.nakjava.gamelogic.util.TestClassPathScanner;
import de.nordakademie.nakjava.gamelogic.util.TestDeepCopyUtility;
import de.nordakademie.nakjava.gamelogic.winstrategies.TestWinStrategies;

@RunWith(Suite.class)
@SuiteClasses({ CardTest.class, TestWinStrategies.class, GUITest.class,
		TestServer.class, TestCardSet.class, TestStateMachine.class,
		TestTransformer.class, TestClassPathScanner.class,
		TestDeepCopyUtility.class, TestDeckPersister.class })
public class TestSuite {
}
