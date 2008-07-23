package robot;

import frsf.cidisi.faia.simulator.GoalBasedAgentSimulator;
import frsf.cidisi.faia.simulator.SearchBasedAgentSimulator;

public class Principal {

    /**
     * @param args
     */
    public static void main(String[] args) {
        AgenteRobot pacman = new AgenteRobot();

        AmbienteRobot ambiente = new AmbienteRobot();

        SearchBasedAgentSimulator simu = new SearchBasedAgentSimulator(ambiente, pacman);

        simu.start();


    }
}
