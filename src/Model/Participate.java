package Model;

import java.util.List;

import Model.Person.eStatus;

public interface Participate {
	void statusParticipate(eStatus ans);

	eStatus getStatusParticipate();

	List<Integer> getListScores();

	void setListScores(List<Integer> scores);

}
