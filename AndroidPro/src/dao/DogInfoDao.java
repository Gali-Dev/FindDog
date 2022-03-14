package dao;

public class DogInfoDao {
	private DogInfoDao() {}
	private static DogInfoDao instance=new DogInfoDao();
	public static DogInfoDao getInstance() {
		return instance;
	}
	
	
}
