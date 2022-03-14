package vo;

public class Member {
	private int no;
	private String id;
	private String pw;
	private String name;
	private String owner;
	private String region;
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	@Override
	public String toString() {
		return "Member [no=" + no + ", id=" + id + ", pw=" + pw + ", name=" + name + ", owner=" + owner + ", region="
				+ region + "]";
	}
	public Member(String id, String pw, String name, String owner) {
		super();
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.owner = owner;
	}
	public Member() {}
}
