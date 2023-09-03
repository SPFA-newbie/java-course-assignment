package StringPack;

public class DataBaseStrings
{
	public final static String DataBaseDrive="com.mysql.cj.jdbc.Driver";
	public final static String DataBaseUrl="jdbc:mysql://localhost:3306/HomeWorkOfJava";

	public final static String SetSafetyMode ="SET SQL_SAFE_UPDATES = 0";

	public static String DataBaseUser="root";
	public static String DataBasePassword="SPFA";

	public static String InsertUser="insert into Teacher(`name`,`password`) values ";
	public static String InsertStudent="insert into Student(`id`,`name`,`password`) values ";
	public static String InsertEssay="insert into EssayQuestion(`name`,`text`,`answer`) values ";
	public static String InsertChoice="insert into ChoiceQuestion(`name`,`type`,`text`,`number`,`answer`," +
									  "`op1`,`op2`,`op3`,`op4`,`op5`,`op6`,`op7`,`op8`) values ";

	public static String DeleteStudent="delete from Student where `id`= ? ";
	public static String DeleteEssay="delete from EssayQuestion where `name`= ? ";
	public static String DeleteChoice="delete from ChoiceQuestion where `name`= ? ";

	public static String UpdateEssay="update EssayQuestion set `text`= ? ,`answer`= ? where `name`= ? ";
	public static String UpdateChoice="update ChoiceQuestion set `text`= ? ,`number`= ? ,`answer`= ? where `name`= ? ";
	public static String UpdateOption="update ChoiceQuestion set "+
									  "`op1`= ? ,`op2`= ? ,`op3`= ? ,`op4`= ? ," +
									  "`op5`= ? ,`op6`= ? ,`op7`= ? ,`op8`= ?  " +
			                          "where `name` = ?";

	public static String SearchUser="select `password` from Teacher where `name` = ? ";
	public static String SearchStudent="select `name`,`password` from Student where `id` = ?";
	public static String SearchEssay="select `name`,`text`,`answer` from EssayQuestion where `name` = ? ";
	public static String SearchChoice=
					"select `name`,`type`,`text`,`number`,`answer` from ChoiceQuestion where `name` = ? ";
	public static String[] SearchOption=
			{"",    "select `op1` from ChoiceQuestion where `name` = ? ",
					"select `op1`,`op2` from ChoiceQuestion where `name` = ? ",
					"select `op1`,`op2`,`op3` from ChoiceQuestion where `name` = ? ",
					"select `op1`,`op2`,`op3`,`op4` from ChoiceQuestion where `name` = ? ",
					"select `op1`,`op2`,`op3`,`op4`,`op5` from ChoiceQuestion where `name` = ? ",
					"select `op1`,`op2`,`op3`,`op4`,`op5`,`op6` from ChoiceQuestion where `name` = ? ",
					"select `op1`,`op2`,`op3`,`op4`,`op5`,`op6`,`op7` from ChoiceQuestion where `name` = ? ",
					"select `op1`,`op2`,`op3`,`op4`,`op5`,`op6`,`op7`,`op8` from ChoiceQuestion where `name` = ? "};

	public static String StudentList="select `id` from Student";
	public static String EssayQuestionList="select `name` from EssayQuestion";
	public static String ChoiceQuestionList="select `name` from ChoiceQuestion";

}
