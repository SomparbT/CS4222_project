package model;

public class Department {
	private int departmentNumber;
	private String departmentName;
	private String mainOffice;
	private String chairmanName;

	public Department(int departmentNumber, String departmentName,
			String mainOffice, String chairmanName) {
		super();
		this.departmentNumber = departmentNumber;
		this.departmentName = departmentName;
		this.mainOffice = mainOffice;
		this.chairmanName = chairmanName;
	}

	public int getDepartmentNumber() {
		return departmentNumber;
	}

	public void setDepartmentNumber(int departmentNumber) {
		this.departmentNumber = departmentNumber;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getMainOffice() {
		return mainOffice;
	}

	public void setMainOffice(String mainOffice) {
		this.mainOffice = mainOffice;
	}

	public String getChairmanName() {
		return chairmanName;
	}

	public void setChairmanName(String chairmanName) {
		this.chairmanName = chairmanName;
	}

}
