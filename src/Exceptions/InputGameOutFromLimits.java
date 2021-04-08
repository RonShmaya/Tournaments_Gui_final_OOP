package Exceptions;

public class InputGameOutFromLimits extends Exception {

	public InputGameOutFromLimits(int numOutFromLimit, String kindGame) {
		super("Exception---> in the game " + kindGame + "\n" + " it's Does not make sense score like "
				+ numOutFromLimit);
	}

	public InputGameOutFromLimits(String kindGame, int scoreOne, int scoreTwo) {
		super("Exception---> in the game " + kindGame + " the scores ---> " + scoreOne + "," + scoreTwo + "\n"
				+ " Does not make sense");
	}

	public InputGameOutFromLimits(String kindGame, int scoreOne, int scoreTwo, String kindOfSituation) {
		super("Exception---> in the game " + kindGame + "->" + kindOfSituation + "\n" + " the scores ---> " + scoreOne
				+ "," + scoreTwo + " Does not make sense");
	}

}
