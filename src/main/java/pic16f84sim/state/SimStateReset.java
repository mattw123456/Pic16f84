package pic16f84sim.state;

import pic16f84sim.simulator.Simulator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimStateReset implements ISimState {

    private static final Logger LOGGER = LogManager.getLogger(SimStateReset.class);

    @Override
    public boolean isTransitionAllowed(ISimState state) {
        if(state instanceof SimStateIdle) {
            return true;
        }
        return false;
    }

    @Override
    public void onEnteringState(Simulator simulator) {
        LOGGER.info("Entering state 'SimStateReset'");
        simulator.getDebugConsole().onNext("Entering state 'SimStateReset'");

        simulator.stopSimulation();
        simulator.resetSimulation();

        simulator.changeState(new SimStateIdle());
    }

    @Override
    public void onLeavingState(Simulator simulator) {
        LOGGER.info("Leaving state 'SimStateReset'");
        simulator.getDebugConsole().onNext("Leaving state 'SimStateReset'");
    }
}
