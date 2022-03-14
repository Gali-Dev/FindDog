package vo;

public class LocationInfo extends DogInfo {
	private double lat;
	private double lng;
	private double distance;
	private String relationship;
	
	
	
	public String getRelationship() {
		return relationship;
	}
	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	public LocationInfo(double lat, double lng) {
		super();
		this.lat = lat;
		this.lng = lng;
	}
	public LocationInfo() {
		super();
	}
	@Override
	public String toString() {
		return "LocationInfo [lat=" + lat + ", lng=" + lng + ", distance=" + distance + ", relationship=" + relationship
				+ ", getRelationship()=" + getRelationship() + ", getDistance()=" + getDistance() + ", getLat()="
				+ getLat() + ", getLng()=" + getLng() + ", getNo()=" + getNo() + ", getDogNo()=" + getDogNo()
				+ ", getDogName()=" + getDogName() + ", getDogInfo()=" + getDogInfo() + ", getDogkinds()="
				+ getDogkinds() + ", toString()=" + super.toString() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + "]";
	}
	
	
}
