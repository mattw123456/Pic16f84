package pic16f84sim.state;

import pic16f84sim.parser.LstParser;
import pic16f84sim.simulator.Simulator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class SimStateFileLoaded implements ISimState {

    private static final Logger LOGGER = LogManager.getLogger(SimStateFileLoaded.class);

    @Override
    public boolean isTransitionAllowed(ISimState state) {
        if(state instanceof SimStateReset) {
            return true;
        }
        return false;
    }

    @Override
    public void onEnteringState(Simulator simulator) {
        LOGGER.info("Entering state 'SimStateFileLoaded'");
        simulator.getDebugConsole().onNext("Entering state 'SimStateFileLoaded'");

        LstParser parser = new LstParser(simulator.getCurrentLstFile());
        List<String> codeLines = parser.parse();
        simulator.setCurrentCode(codeLines);

        simulator.changeState(new SimStateReset());
    }

    @Override
    public void onLeavingState(Simulator simulator) {
        LOGGER.info("Leaving state 'SimStateFileLoaded'");
        simulator.getDebugConsole().onNext("Leaving state 'SimStateFileLoaded'");
    }
}
