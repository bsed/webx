package cn.gnux.core.utils;


import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

/**
 * 一些通用的函数
 * @author 
 *
 */
public class ObjectUtil {
	/**
	 * 比较两个对象是否相等。<br>
	 * 相同的条件有两个，满足其一即可：<br>
	 * 1. obj1 == null && obj2 == null;
	 * 2. obj1.equals(obj2)
	 * @param obj1 对象1
	 * @param obj2 对象2
	 * @return 是否相等
	 * 1、若两个对象都为null，则返回true
	 * 2、若{@code obj1}对象不为null，则调用{@code obj1}对象相应的{@link Object#equals(Object)}函数进行判断，返回判断结果
	 * 3、对于基本类实现了{@link Object#equals(Object)}的话都会先判断类型是否匹配，类型不匹配返回false，可参考{@link String#equals(Object)}
	 * 4、 关于如何利用此函数比较自定义对象可下载源代码，参考测试代码中的{@link ObjectUtilsTest#testIsEquals()}
	 */
	public static boolean isEquals(Object obj1, Object obj2) {
		//return (obj1 != null) ? (obj1.equals(obj2)) : (obj2 == null);
		return obj1 == null ? obj2 == null : obj1.equals(obj2);
	}
	
	/**
	 * 计算对象长度，如果是字符串调用其length函数，集合类调用其size函数，数组调用其length属性，其他可遍历对象 遍历计算长度
	 * @param obj 被计算长度的对象
	 * @return 长度
	 */
	public static int length(Object obj) {
		if (obj == null) {
			return 0;
		}
		if (obj instanceof CharSequence) {
			return ((CharSequence) obj).length();
		}
		if (obj instanceof Collection) {
			return ((Collection<?>) obj).size();
		}
		if (obj instanceof Map) {
			return ((Map<?, ?>) obj).size();
		}

		int count;
		if (obj instanceof Iterator) {
			Iterator<?> iter = (Iterator<?>) obj;
			count = 0;
			while (iter.hasNext()) {
				count++;
				iter.next();
			}
			return count;
		}
		if (obj instanceof Enumeration) {
			Enumeration<?> enumeration = (Enumeration<?>) obj;
			count = 0;
			while (enumeration.hasMoreElements()) {
				count++;
				enumeration.nextElement();
			}
			return count;
		}
		if (obj.getClass().isArray() == true) {
			return Array.getLength(obj);
		}
		return -1;
	}
	
	/**
	 * 对象中是否包含元素
	 * @param obj 对象
	 * @param element 元素
	 * @return 是否包含
	 */
	public static boolean contains(Object obj, Object element) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof String) {
			if (element == null) {
				return false;
			}
			return ((String) obj).contains(element.toString());
		}
		if (obj instanceof Collection) {
			return ((Collection<?>) obj).contains(element);
		}
		if (obj instanceof Map) {
			return ((Map<?, ?>) obj).values().contains(element);
		}

		if (obj instanceof Iterator) {
			Iterator<?> iter = (Iterator<?>) obj;
			while (iter.hasNext()) {
				Object o = iter.next();
				if (isEquals(o, element)) {
					return true;
				}
			}
			return false;
		}
		if (obj instanceof Enumeration) {
			Enumeration<?> enumeration = (Enumeration<?>) obj;
			while (enumeration.hasMoreElements()) {
				Object o = enumeration.nextElement();
				if (isEquals(o, element)) {
					return true;
				}
			}
			return false;
		}
		if (obj.getClass().isArray() == true) {
			int len = Array.getLength(obj);
			for (int i = 0; i < len; i++) {
				Object o = Array.get(obj, i);
				if (isEquals(o, element)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 检查对象是否为null
	 * @param obj 对象
	 * @return 是否为null
	 */
	public static boolean isNull(Object  obj) {
		return null == obj;
	}
	
	/**
	 * 检查对象是否不为null
	 * @param obj 对象
	 * @return 是否为null
	 */
	public static boolean isNotNull(Object  obj) {
		return null != obj;
	}
	/**
	 * long数组转换成Long数组
	 * 
	 * @param src
	 * @return Long[] dest
	 */
	public static Long[] transformLongArray(long[] src) {
		Long[] dest = new Long[src.length];
		for(int i=0;i<src.length;i++) {
			dest[i] = src[i];
		}
		return dest;
	}
	
	/**
	 * Long数组转换成long数组
	 * 
	 * @param src
	 * @return
	 */
	public static long[] transformLongArray(Long[] src) {
		long[] dest = new long[src.length];
		for(int i=0;i<src.length;i++) {
			dest[i] = src[i];
		}
		return dest;
	}
	
	/**
	 * int数组转换成Integer数组
	 * 
	 * @param src
	 * @return
	 */
	public static Integer[] transformIntArray(int[] src){
		Integer[] dest = new Integer[src.length];
		for(int i=0; i< src.length;i++) {
			dest[i] =src[i];
		}
		return dest;
	}
	
	/**
	 * Integer数组转换成int数组
	 * 
	 * @param src
	 * @return
	 */
	public static int[] transfromIntArray(Integer[] src) {
		int[] dest = new int[src.length];
		for(int i=0;i<src.length;i++) {
			dest[i] =src[i];
		}
		return dest;
	}
	/**
	 * 比较两个值的大小<br>
	 * 关于比较的结果:
	 *  v1大于v2返回1 <br>
	 *  v1等于v2返回0 <br>
	 *  v1小于v2返回-1<br>
	 *  
	 * @param v1
	 * @param v2
	 * @return
	 * 1、 若v1为null，v2为null，则相等 <br>
	 * 2、 若v1为null，v2不为null，则v1小于v2 <br>
	 * 3、 若v1不为null，v2为null，则v1大于v2 <br>
	 * 4、 若v1、v2均不为null，则利用v1的{@link Comparable#compareTo(Object)}判断，参数为v2 <br>
	 */
	@SuppressWarnings("unchecked")
	public static <V> int compare(V v1, V v2) {
		return v1 == null ? (v2 == null ? 0 : -1) : (v2 == null ? 1 : ((Comparable<V>)v1).compareTo(v2));
	}
}
