package model;

public class Project {
	private int projectNumber;
	private String projectName;
	private String startingDate;
	private String endingDate;
	private double budget;
	private String piName;

	public Project(int projectNumber, String projectName, String startingDate,
			String endingDate, double budget, String piName) {
		super();
		this.projectNumber = projectNumber;
		this.projectName = projectName;
		this.startingDate = startingDate;
		this.endingDate = endingDate;
		this.budget = budget;
		this.piName = piName;
	}

	public int getProjectNumber() {
		return projectNumber;
	}

	public void setProjectNumber(int projectNumber) {
		this.projectNumber = projectNumber;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getStartingDate() {
		return startingDate;
	}

	public void setStartingDate(String startingDate) {
		this.startingDate = startingDate;
	}

	public String getEndingDate() {
		return endingDate;
	}

	public void setEndingDate(String endingDate) {
		this.endingDate = endingDate;
	}

	public double getBudget() {
		return budget;
	}

	public void setBudget(double budget) {
		this.budget = budget;
	}

	public String getPiName() {
		return piName;
	}

	public void setPiName(String piName) {
		this.piName = piName;
	}

}
