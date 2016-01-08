package cn.gnux.core.utils.setting.getter;


/**
 * 数组类型的Get接口
 * @author 
 *
 */
public interface IArrayGetter {
	/*-------------------------- 数组类型 start -------------------------------*/
	String[] getStrs(String key);
	
	Integer[] getInts(String key);
	
	Short[] getShorts(String key);
	
	Boolean[] getBools(String key);
	
	Long[] getLongs(String key);
	
	Character[] getChars(String key);
	
	Double[] getDoubles(String key);
	
	Byte[] getBytes(String key);
	/*-------------------------- 数组类型 end -------------------------------*/	
}
