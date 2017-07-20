package model;

public class Professor {
	private String ssn;
	private String name;
	private int age;
	private String gender;
	private String rank;
	private String researchSpecialty;
	private String departmentName;

	public Professor(String ssn, String name, int age, String gender,
			String rank, String researchSpecialty, String departmentName) {
		super();
		this.ssn = ssn;
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.rank = rank;
		this.researchSpecialty = researchSpecialty;
		this.departmentName = departmentName;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
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

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getResearchSpecialty() {
		return researchSpecialty;
	}

	public void setResearch_specialty(String researchSpecialty) {
		this.researchSpecialty = researchSpecialty;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartment_name(String departmentName) {
		this.departmentName = departmentName;
	}

}
