package ssm;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Test;

/** 
 * @ClassName	Demo1.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月18日 下午9:57:25
 * @Version 	V1.0    
 */
public class Demo1 {

	@Test
	public void try1() {
		int[] x={12,35,8,7,2};
		Arrays.sort(x);
		for (int i : x) {
			System.out.print(i + " ");
		}
		
		Map<String, Map> map = null;
		List<List> list = null;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //sdf.parse(text).getTime();
        Timestamp.valueOf("1989-09-27");
	}
}
 