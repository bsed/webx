/**
 * Project Name: webx-core
 * File Name: ObejctUtil.java
 * Package Name: cn.gnux.core.utils
 * Date: 2016年2月2日下午1:49:13
 * Copyright (c) 2016, kelvin@gnux.cn All Rights Reserved.
 *
*/

package cn.gnux.core.utils;

public class ObejctUtil {
	/**
	 * 比较两个对象是否相等
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 * 1、若两个对象都为null，则返回true
	 * 2、若{@code actual}对象不为null，则调用{@code actual}对象相应的{@link Object#equals(Object)}函数进行判断，返回判断结果
	 * 3、对于基本类实现了{@link Object#equals(Object)}的话都会先判断类型是否匹配，类型不匹配返回false，可参考{@link String#equals(Object)}
	 * 4、 关于如何利用此函数比较自定义对象可下载源代码，参考测试代码中的{@link ObjectUtilsTest#testIsEquals()}
	 */
	public static boolean isEquals(Object actual, Object expected) {
        return actual == null ? expected == null : actual.equals(expected);
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
	public static <V> int compare(V v1, V v2) {
		return v1 == null ? (v2 == null ? 0 : -1) : (v2 == null ? 1 : ((Comparable)v1).compareTo(v2));
	}
}
