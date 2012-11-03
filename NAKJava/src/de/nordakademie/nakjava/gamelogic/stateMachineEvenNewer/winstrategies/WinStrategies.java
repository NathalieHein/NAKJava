package de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.WinStrategy;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.genericRoundCheckers.ArtifactChecker;
import de.nordakademie.nakjava.util.classpathscanner.ClassAcceptor;
import de.nordakademie.nakjava.util.classpathscanner.ClasspathScanner;

public class WinStrategies {
	private Map<String, WinStrategy> strategies;

	private static WinStrategies instance;

	private WinStrategies(List<AbstractWinStrategy> strategies) {
		this.strategies = new HashMap();

		for (AbstractWinStrategy strategy : strategies) {
			this.strategies.put(strategy.getClass().getSimpleName(), strategy);
		}
	}

	public Set<String> getStrategies() {
		return strategies.keySet();
	}

	public WinStrategy getStrategyForName(String name) {
		return strategies.get(name);
	}

	public static WinStrategies getInstance() {
		return instance;
	}

	public static void loadWinStrategies() {
		List<Class<?>> classes = ClasspathScanner
				.findClasses(
						"de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies",
						"de.nordakademie.nakjava.gamelogic.winstrategies",
						new ClassAcceptor() {

							@Override
							public boolean acceptClass(Class<?> clazz) {
								de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.WinStrategy annotation = clazz
										.getAnnotation(de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.WinStrategy.class);
								return (AbstractWinStrategy.class
										.isAssignableFrom(clazz) && annotation != null);

							}
						});

		List<AbstractWinStrategy> instances = new LinkedList<>();

		for (Class<?> clazz : classes) {
			de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.WinStrategy annotation = clazz
					.getAnnotation(de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.WinStrategy.class);
			AbstractWinStrategy instance = null;
			try {
				instance = (AbstractWinStrategy) clazz.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}

			List<WinCheck> winChecks = new LinkedList<>();
			for (ArtifactChecker aChecker : annotation.artifactCheckers()) {
				winChecks
						.add(new de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.genericRoundCheckers.impl.ArtifactChecker(
								aChecker.target(), aChecker.artifacts(),
								aChecker.comparator(), aChecker.count(),
								aChecker.operator()));
			}

			instance.setChecks(winChecks);
			instances.add(instance);
		}

		instance = new WinStrategies(instances);
	}

}
