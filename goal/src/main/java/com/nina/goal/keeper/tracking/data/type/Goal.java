package com.nina.goal.keeper.tracking.data.type;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Goal {
	private UUID id;
	private String name;
	private String description;
	private goalStatus status;

	@JsonCreator
	public Goal(@JsonProperty("name") String name, @JsonProperty("description") String description, 
			@JsonProperty("status") goalStatus status) {
		this.name = name;
		this.description = description;
		this.setId(UUID.randomUUID());
		this.setStatus(status);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public goalStatus getStatus() {
		return status;
	}

	public void setStatus(goalStatus status) {
		this.status = status;
	}

	public enum goalStatus{set, inprogress, blocked, pending, done};
}
