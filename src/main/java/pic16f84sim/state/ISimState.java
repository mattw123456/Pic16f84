package pic16f84sim.state;

import pic16f84sim.simulator.Simulator;

public interface ISimState {

	public boolean isTransitionAllowed(ISimState state);
	public void onEnteringState(Simulator simulator);
	public void onLeavingState(Simulator simulator);
}
