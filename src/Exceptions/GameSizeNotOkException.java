package Exceptions;

public class GameSizeNotOkException extends Exception {

	public GameSizeNotOkException(int sizeGameNeedToBe, int sizeGame, String kindGame) {
		super("Exception---> number of steps need to be " + sizeGameNeedToBe + " ," + "\n" + sizeGame
				+ " not ok for the game ---" + kindGame);
	}
}
