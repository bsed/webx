package cn.gnux.core.utils.bloomFilter.filter;

import cn.gnux.core.utils.HashUtil;



/**
 * 默认Bloom过滤器，使用Java自带的Hash算法
 * @author 
 *
 */
public class DefaultFilter extends AbstractFilter {

	public DefaultFilter(long maxValue, int MACHINENUM) {
		super(maxValue, MACHINENUM);
	}
	
	public DefaultFilter(long maxValue) {
		super(maxValue);
	}
	
	@Override
	public long hash(String str) {
		return HashUtil.javaDefaultHash(str) % size;
	}
}
