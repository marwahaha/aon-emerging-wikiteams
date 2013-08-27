package internetz;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import repast.simphony.context.Context;
import repast.simphony.context.DefaultContext;
import repast.simphony.context.space.graph.NetworkBuilder;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ISchedule;
import repast.simphony.engine.schedule.Schedule;
import repast.simphony.engine.schedule.ScheduleParameters;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.parameter.Parameters;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.graph.Network;
import repast.simphony.space.graph.RepastEdge;
import repast.simphony.space.projection.Projection;
import repast.simphony.util.ContextUtils;

public class InternetzCtx extends DefaultContext {

	private SimulationParameters simulationParameters = new SimulationParameters();
	private ModelFactory modelFactory = new ModelFactory();
	
	private TaskPool taskPool = new TaskPool();

	private void say(String s) {
		PjiitOutputter.say(s);
	}

	private boolean moreThanBasic() {
		return modelFactory.getComplexity() > 0;
	}

	public InternetzCtx() {
		super("InternetzCtx");
		try {
			PjiitLogger.init();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		say("Super object InternetzCtx loaded");
		say("Starting simulation with model: " + modelFactory.toString());

		// getting parameters of simulation
		say("Loading parameters");
		simulationParameters.init();
		
		NetworkBuilder<Object> netBuilder = new NetworkBuilder<Object>("teams",
				(Context<Object>) this, true);
		netBuilder.buildNetwork();

		for (int i = 0; i < simulationParameters.taskCount; i++) {
			Task task = new Task();
			say("Creating team..");
			taskPool.addTask("", task);
			say("Initializing team..");
			task.initialize();
		}

		Network<Team> teams = (Network<Team>) this.getProjection("teams");
		say("Projection teams (" + teams.getName() + ") exists and is size: "
				+ teams.size());
		//teams.
		Network<Skill> skills = (Network<Skill>) this.getProjection("skills");
		say("Projection skills (" + skills.getName() + ") exists and is size: "
				+ skills.size());
		Network<Competency> competencies = (Network<Competency>) this.getProjection("competencies");
		say("Projection competencies (" + competencies.getName() + ") exists and is size: "
				+ competencies.size());

		// --------------------------------------
		/*
		 * Network teams = (Network) this.getProjection("teams"); Network skills
		 * = (Network) this.getProjection("skills"); Network competencies =
		 * (Network) this.getProjection("competencies"); Network linkedin =
		 * null;
		 */
		Network linkedin = null;
		if (moreThanBasic())
			linkedin = (Network) this.getProjection("linkedin");

		addAgent(simulationParameters.agentCount, true);

		simulationParameters.algo = (String) param
				.getValue("emergenceAlgorithm");
		say("algo is " + simulationParameters.algo);

		System.out.println("Number of teams created "
				+ this.getObjects(Team.class).size());
		System.out.println("Number of agents created "
				+ this.getObjects(Agent.class).size());
		System.out.println("Algorithm tested: " + simulationParameters.algo);

		/*
		 * ISchedule schedule =
		 * RunEnvironment.getInstance().getCurrentSchedule(); ScheduleParameters
		 * schedParams; schedParams =
		 * ScheduleParameters.createAtEnd(ScheduleParameters
		 * .FIRST_PRIORITY);//.createOneTime(103); // OutputRecorder recorder =
		 * new OutputRecorder(this, simulation_mode);
		 * schedule.schedule(schedParams,this, "outputSNSData", "record");
		 */
		
		outputTeamNetworkData();
	}

	public void addAgent(int agentCnt, boolean concentrate) {
		// Parameters param = RunEnvironment.getInstance().getParameters();
		// this this = (this)ContextUtils.getContext(this);
		// Network teams = (Network) this.getProjection("teams");
		// Network skills = (Network) this.getProjection("skills");
		// Network competencies = (Network) this.getProjection("competencies");
		// Network sns = (Network) this.getProjection("linkedin");

		List<Agent> listAgent = NamesGenerator.getnames(agentCnt);
		for (int i = 0; i < agentCnt; i++) {
			Agent agent = listAgent.get(i);
			say(agent.toString());
			say("in add aggent i: " + i);
			this.add(agent);
		}
	}

	public void attributeMemes(Agent agent, ArrayList memes) {
		//
	}

	public boolean isItSuitable(Team team, Agent agent) {
		return false;// ............????
	}

	public Hashtable getTeams() {
		return allTeams;
	}

	public int getAgents() {
		return this.getObjects(Agent.class).size();
	}

	public int getSkills() {
		return this.getObjects(Skill.class).size();
	}

	@ScheduledMethod(start = 1, interval = 1)
	public void updatePageRnk() { // Adapted from the netlogo 'diffusion' code
									// (fingers crossed)
		//
		say("scheduled update page rank lunched");
	}

	// THIS IMPLEMENTS "NEVERENDING SEPTEMBER"
	// @ScheduledMethod(start = 550)
	// public void september() {
	// addAgent(100,true);
	// }
	
	private void outputTeamNetworkData(){
		Network teams = (Network) this.getProjection("teams");
		//Projection teams = this.getProjection("teams");
		Iterator allNodes = teams.getEdges().iterator();
		for (Object obj : teams.getNodes()) {
			say( "Team network data output --- " + ((Team) obj).toString() );
		}
	}

	@ScheduledMethod(start = 2000, priority = 0)
	private void outputSNSData() throws IOException {
//		Network sns = (Network) this.getProjection("linkedin");
//		StringBuilder dataToWrite = new StringBuilder();
//		dataToWrite.append("Source; Destination\n"); // This is the header for
//														// the csv file
//		Iterator allEdges = sns.getEdges().iterator();
//
//		for (Object obj : sns.getEdges()) {
//			if (obj instanceof RepastEdge) {
//				RepastEdge edge = (RepastEdge) obj;
//				Object src = edge.getSource();
//				Object tar = edge.getTarget();
//				// String srcName = idMap.get(src);
//				// String tarName = idMap.get(tar);
//				// double weight = edge.getWeight();
//				dataToWrite.append(((Agent) src).getId() + ";"
//						+ ((Agent) tar).getId() + "\n");
//				System.out.println(((Agent) src).getId() + ";"
//						+ ((Agent) tar).getId() + "\n");
//			}
//		}
//		// Now write this data to a file
//		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(
//				"socialnetwork.edgelist")));
//		bw.write(dataToWrite.toString());
//		bw.close();
	}

	// UNCOMMENT THE FOLLOWING TO GET A DECREASING INFLOW OF USERS IN THE SYSTEM
	@ScheduledMethod(start = 10, interval = 2)
	public void inflow() {
		say("scheduled method inflow lunched");
		// double time = RunEnvironment.getInstance().getCurrentSchedule()
		// .getTickCount();
		// if (time > 400)
		// agentsToAdd /= 2;
		// if (time > 800)
		// agentsToAdd = 0;
		// if (agentsToAdd > 0)
		// addAgent(agentsToAdd, false);
	}
}