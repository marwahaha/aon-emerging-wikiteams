package internetz;

import java.util.Random;

public abstract class TaskSkillsPool {

	public enum Method {
		STATIC_TABLE, LINEAR_KNN, SVM;
	}

	private static Skill[] skillSet = null;

	public static void instantiate() {
		say("initialized TaskSkillsPool");
	}

	public static void instantiate(Method method) {
		say("initialized TaskSkillsPool");

		if (method == Method.STATIC_TABLE) {

		}
	}

	public static Skill chose_random() {
		Random generator = new Random();
		int i = generator.nextInt(skillSet.length);
		return skillSet[i];
	}

	public static void fillWithSkills(Task task) {
		say("Task " + task + " filled with skills");
	}

	public static Skill[] get_skill_set(int count) {
		return skillSet;
	}

	private static void say(String s) {
		PjiitOutputter.say(s);
	}

}
