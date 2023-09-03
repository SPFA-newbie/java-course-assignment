package StringPack;

public class FileStrings
{
	public static String HTML_head="<!DOCTYPE html>\n<html>\n<head>\n" +
								   "<meta charset=\"utf-8\">\n" +
								   "<title>成绩单</title>\n</head>\n<body>"+
								   "<table border=\"1\">\n<tr>\n"+
								   "<td>排名</td>\n<td>学号</td>\n<td>姓名</td>\n<td>成绩</td>\n</tr>\n";
	public static String HTML_tail="</table>\n</body>\n</html>";

	public static String HTML_newLine(String no,String id,String name,String point)
	{
		return "<tr>\n<td>"+no+"</td>\n<td>"+id+"</td>\n<td>"+name+"</td>\n<td>"+point+"</td>\n</tr>\n";
	}
}
