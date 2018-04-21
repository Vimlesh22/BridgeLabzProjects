
import java.sql.Connection;
//import java.sql.Statement;

import java.sql.PreparedStatement;


public class Test {

	public static void main(String[] args) throws Exception {
		
		Connection connection=DataBaseUtility.getConnection();
		PreparedStatement preparedStatement=connection.prepareStatement("insert into mytable values(?,?)");
		preparedStatement.setString(1, "Ramesh");
		preparedStatement.setString(2, "4");
		int rst=preparedStatement.executeUpdate();
		System.out.println(rst);
//		Statement st=connection.createStatement();
//		String sql="select * from mytable where roll_no=?";
//		st.execute(sql);

	}

}
