package launcher;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

public class EconomyWarsLauncher {

	public static void main(String[] args) {
		Runtime rt = Runtime.instance();

		Profile profile = new ProfileImpl();
		profile.setParameter(Profile.CONTAINER_NAME, "Main-Container");
		profile.setParameter(Profile.MAIN_HOST, "localhost");

		ContainerController mainContainer = rt.createMainContainer(profile);
		generateAgent(3, mainContainer);

	}

	public static void generateAgent(int num, ContainerController container) {
		Object[] args = new Object[0];

		createAgent(container, "rma", "jade.tools.rma.rma", args);
		createAgent(container, "sniffer", "jade.tools.sniffer.Sniffer", args);

		createAgent(container, "eco", "agents.EconomyAgent.EconomyAgent", args);

		for (int i = 0; i < num; i++) {
			createAgent(container, "company" + i + 1, "agents.CompanyAgent.CompanyAgent", args);
		}
	}

	public static void createAgent(ContainerController container, String name, String agentClass, Object[] args) {
		AgentController ac = null;
		try {
			ac = container.createNewAgent(name, agentClass, args);
			ac.start();
		} catch (StaleProxyException e) {
			e.printStackTrace();
		}
	}

}