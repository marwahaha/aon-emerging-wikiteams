/**
 * 
 */
package internetz;

import java.util.HashMap;
import java.util.Map;

/**
 * Task is a collection of a three-element set of skill, number of work units,
 * and work done.
 * 
 * @since 1.0
 * 
 * @author Oskar Jarczyk
 */
public class Task {

	private static int COUNT = 0;

	private String name;
	private int id;

	private Map<String, TaskInternals> skills = new HashMap<String, TaskInternals>();

	public Task() {
		this.id = COUNT++;
		this.name = "Task_" + COUNT;
		say("Task object " + this + " created");
	}

	public void workOnTask(Agent agent, Strategy.SkillChoice strategy) {
		switch (strategy) {
		case PROPORTIONAL_TIME_DIVISION:
			for (TaskInternals __skill : skills.values()) {
				int n = skills.size();
				double delta = agent.getAgentInternals(
						__skill.getSkill().getName()).getExperience().percentage;
				ProportionalTimeDivision.increment(__skill, n, delta);
			}
			;
		case GREEDY_ASSIGNMENT_BY_TASK:
			;
		case CHOICE_OF_AGENT:
			;
		}
	}

	public void addSkill(String key, TaskInternals taskInternals) {
		skills.put(key, taskInternals);
	}

	public TaskInternals getSkill(String key) {
		return skills.get(key);
	}

	private void say(String s) {
		PjiitOutputter.say(s);
	}

	public String toString() {
		return "Task " + id + " " + name;
	}

	public synchronized void initialize() {
		setId(++COUNT);
		TaskSkillsPool.fillWithSkills(this);
		say("Task object initialized with id: " + this.id);
	}

	public Map<String, TaskInternals> getSkills() {
		return skills;
	}

	public void setSkills(Map<String, TaskInternals> skills) {
		this.skills = skills;
	}

	public synchronized void setId(int id) {
		this.id = id;
	}

	public synchronized int getId() {
		return this.id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
