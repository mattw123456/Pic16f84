package pic16f84sim.state;

import pic16f84sim.parser.LstParser;
import pic16f84sim.simulator.Simulator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class SimStateNoFileLoaded implements ISimState {

	private static final Logger LOGGER = LogManager.getLogger(SimStateNoFileLoaded.class);

	@Override
	public boolean isTransitionAllowed(ISimState state) {
		if(state instanceof SimStateFileLoaded) {
			return true;
		}
		return false;
	}

	@Override
	public void onEnteringState(Simulator simulator) {
		LOGGER.info("Entering state 'SimStateNoFileLoaded'");
		simulator.getDebugConsole().onNext("Entering state 'SimStateNoFileLoaded'");
	}

	@Override
	public void onLeavingState(Simulator simulator) {
		LOGGER.info("Leaving state 'SimStateNoFileLoaded'");
		simulator.getDebugConsole().onNext("Leaving state 'SimStateNoFileLoaded'");
	}
}
