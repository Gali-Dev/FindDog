package vo;

import java.util.Date;

public class DogInfo {
	private int no;
	private int dogNo;
	private String dogName;
	private String dogInfo;
	private String dogkinds;
	
	public DogInfo(){
		
	}

	public DogInfo(int dogNo, String dogName, String dogInfo, String dogkinds) {
		super();
		this.dogNo = dogNo;
		this.dogName = dogName;
		this.dogInfo = dogInfo;
		this.dogkinds = dogkinds;
	}

	public DogInfo(int no, int dogNo, String dogName, String dogInfo, String dogkinds) {
		super();
		this.no = no;
		this.dogNo = dogNo;
		this.dogName = dogName;
		this.dogInfo = dogInfo;
		this.dogkinds = dogkinds;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public int getDogNo() {
		return dogNo;
	}

	public void setDogNo(int dogNo) {
		this.dogNo = dogNo;
	}

	public String getDogName() {
		return dogName;
	}

	public void setDogName(String dogName) {
		this.dogName = dogName;
	}

	public String getDogInfo() {
		return dogInfo;
	}

	public void setDogInfo(String dogInfo) {
		this.dogInfo = dogInfo;
	}

	public String getDogkinds() {
		return dogkinds;
	}

	public void setDogkinds(String dogkinds) {
		this.dogkinds = dogkinds;
	}

	@Override
	public String toString() {
		return "DogInfo [no=" + no + ", dogNo=" + dogNo + ", dogName=" + dogName + ", dogInfo=" + dogInfo
				+ ", dogkinds=" + dogkinds + "]";
	}
	
	
	
}
