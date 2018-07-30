package com.nina.goal.keeper.tracking.data.type;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GoalTree {

	List<Goal> parentGoal;
	List<Goal> childrenGoal;
	Goal currentGoal;

	@JsonCreator
	public GoalTree(@JsonProperty("ParentGoal") List<Goal> parentGoal,
			@JsonProperty("ChildrenGoal") List<Goal> childrenGoal,
			@JsonProperty("CurrentGoal") Goal currentGoal) {
		this.parentGoal = parentGoal;
		this.currentGoal = currentGoal;
		this.childrenGoal = childrenGoal;
	}

	public List<Goal> getParentGoal() {
		return parentGoal;
	}

	public void setParentGoal(List<Goal> parentGoal) {
		this.parentGoal = parentGoal;
	}

	public List<Goal> getChildrenGoal() {
		return childrenGoal;
	}

	public void setChildrenGoal(List<Goal> childrenGoal) {
		this.childrenGoal = childrenGoal;
	}

	public Goal getCurrentGoal() {
		return currentGoal;
	}

	public void setCurrentGoal(Goal currentGoal) {
		this.currentGoal = currentGoal;
	}

}
