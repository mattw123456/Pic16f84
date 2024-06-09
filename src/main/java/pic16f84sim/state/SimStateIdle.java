package pic16f84sim.state;

import java.io.File;
import java.util.List;

import pic16f84sim.parser.LstParser;
import pic16f84sim.simulator.Simulator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimStateIdle implements ISimState {

	private static final Logger LOGGER = LogManager.getLogger(SimStateIdle.class);

	@Override
	public boolean isTransitionAllowed(ISimState state) {
		if(state instanceof SimStateFileLoaded) {
			return true;
		}
		if(state instanceof SimStateReset) {
			return true;
		}
		if(state instanceof SimStateContMode) {
			return true;
		}
		if(state instanceof SimStateStepMode) {
			return true;
		}
		return false;
	}

	@Override
	public void onEnteringState(Simulator simulator) {
		LOGGER.info("Entering state 'SimStateIdle'");
		simulator.getDebugConsole().onNext("Entering state 'SimStateIdle'");

		simulator.stopSimulation();
	}

	@Override
	public void onLeavingState(Simulator simulator) {
		LOGGER.info("Leaving state 'SimStateIdle'");
		simulator.getDebugConsole().onNext("Leaving state 'SimStateIdle'");
	}
}
