package cn.gnux.core.utils.bloomFilter.filter;

import cn.gnux.core.utils.bloomFilter.bitMap.BitMap;
import cn.gnux.core.utils.bloomFilter.bitMap.IntMap;
import cn.gnux.core.utils.bloomFilter.bitMap.LongMap;

/**
 * 默认Bloom过滤器，使用Java的Hash算法
 * 
 * @author
 * 
 */
public abstract class AbstractFilter implements Filter {

	private BitMap bm = null;

	protected long size = 0;

	public AbstractFilter(long maxValue, int machineNum) {
		this.size = maxValue;

		switch (machineNum) {
		case BitMap.MACHINE32:
			bm = new IntMap((int) (size / machineNum));
			break;
		case BitMap.MACHINE64:
			bm = new LongMap((int) (size / machineNum));
			break;
		default:
			throw new RuntimeException("Error Machine number!");
		}
	}

	public AbstractFilter(long maxValue) {
		this(maxValue, BitMap.MACHINE32);
	}

	public boolean contains(String str) {
		return bm.contains(hash(str));
	}

	public void add(String str) {
		bm.add(hash(str));
	}

	public boolean containsAndAdd(String str) {
		final long hash = this.hash(str);
		if (bm.contains(hash)) {
			return true;
		}

		bm.add(hash);
		return false;
	}

	public abstract long hash(String str);
}