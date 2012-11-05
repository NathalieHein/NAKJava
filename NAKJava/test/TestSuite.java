import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import de.nordakademie.nakjava.gamelogic.cardstest.CardTest;
import de.nordakademie.nakjava.gamelogic.winstrategies.TestWinStrategies;

@RunWith(Suite.class)
@SuiteClasses({ CardTest.class, TestWinStrategies.class })
public class TestSuite {
}
