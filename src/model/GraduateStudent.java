package model;

public class GraduateStudent {
	private int ssn;
	private String name;
	private int age;
	private String gender;
	private String degreeProgram;
	private String ssnAdvisor;
	private String projectNumber;

	public GraduateStudent(int ssn, String name, int age, String gender,
			String degreeProgram, String ssnAdvisor, String projectNumber) {
		super();
		this.ssn = ssn;
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.degreeProgram = degreeProgram;
		this.ssnAdvisor = ssnAdvisor;
		this.projectNumber = projectNumber;
	}

	public int getSsn() {
		return ssn;
	}

	public void setSsn(int ssn) {
		this.ssn = ssn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDegreeProgram() {
		return degreeProgram;
	}

	public void setDegreeProgram(String degreeProgram) {
		this.degreeProgram = degreeProgram;
	}

	public String getSsnAdvisor() {
		return ssnAdvisor;
	}

	public void setSsnAdvisor(String ssnAdvisor) {
		this.ssnAdvisor = ssnAdvisor;
	}

	public String getProjectNumber() {
		return projectNumber;
	}

	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}

}
