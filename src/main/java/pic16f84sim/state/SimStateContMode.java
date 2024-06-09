package pic16f84sim.state;

import pic16f84sim.simulator.Simulator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimStateContMode implements ISimState {

    private static final Logger LOGGER = LogManager.getLogger(SimStateContMode.class);

    @Override
    public boolean isTransitionAllowed(ISimState state) {
        if(state instanceof SimStateFileLoaded) {
            return true;
        }
        if(state instanceof SimStateIdle) {
            return true;
        }
        if(state instanceof SimStateReset) {
            return true;
        }
        return false;
    }

    @Override
    public void onEnteringState(Simulator simulator) {
        LOGGER.info("Entering state 'SimStateContMode'");
        simulator.getDebugConsole().onNext("Entering state 'SimStateContMode'");

        new Thread(simulator).start();
    }

    @Override
    public void onLeavingState(Simulator simulator) {
        simulator.stopSimulation();

        LOGGER.info("Leaving state 'SimStateContMode'");
        simulator.getDebugConsole().onNext("Leaving state 'SimStateContMode'");
    }
}
