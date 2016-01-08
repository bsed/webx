package cn.gnux.core.utils.bloomFilter.filter;


import cn.gnux.core.utils.HashUtil;



public class TianlFilter extends AbstractFilter {

	public TianlFilter(long maxValue, int machineNum) {
		super(maxValue, machineNum);
	}

	public TianlFilter(long maxValue) {
		super(maxValue);
	}

	@Override
	public long hash(String str) {
		return HashUtil.tianlHash(str) % size;
	}

}
