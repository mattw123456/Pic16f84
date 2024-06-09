package pic16f84sim.state;

import pic16f84sim.simulator.Simulator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimStateStepMode implements ISimState {

    private static final Logger LOGGER = LogManager.getLogger(SimStateStepMode.class);

    @Override
    public boolean isTransitionAllowed(ISimState state) {
        if(state instanceof SimStateIdle) {
            return true;
        }
        if(state instanceof SimStateFileLoaded) {
            return true;
        }
        return false;
    }

    @Override
    public void onEnteringState(Simulator simulator) {
        LOGGER.info("Entering state 'SimStateStepMode'");
        simulator.getDebugConsole().onNext("Entering state 'SimStateStepMode'");

        simulator.executeSingleInstruction();
        simulator.changeState(new SimStateIdle());
    }

    @Override
    public void onLeavingState(Simulator simulator) {
        LOGGER.info("Leaving state 'SimStateStepMode'");
        simulator.getDebugConsole().onNext("Leaving state 'SimStateStepMode'");
    }
}
