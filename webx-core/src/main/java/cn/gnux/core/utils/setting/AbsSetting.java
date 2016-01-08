package cn.gnux.core.utils.setting;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.nio.charset.Charset;

import cn.gnux.core.utils.Conver;
import cn.gnux.core.utils.StrUtil;
import cn.gnux.core.utils.setting.getter.IBasicGetter;


/**
 * 设定抽象类
 * @author 
 *
 */
public abstract class AbsSetting implements IBasicGetter{
	
	/** 本设置对象的字符集 */
	protected Charset charset;
	/** 是否使用变量 */
	protected boolean isUseVariable;
	/** 设定文件的URL */
	protected URL settingUrl;

	/**
	 * @return 配置文件大小（key的个数）
	 */
	public abstract int size();

	/**
	 * @return 是否为空
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	public abstract String getStr(String key);

	public String getStr(String key, String defaultValue) {
		final String value = getStr(key);
		if(StrUtil.isBlank(value)) {
			return defaultValue;
		}
		return value;
	}

	public Integer getInt(String key) {
		return getInt(key, null);
	}

	public Integer getInt(String key, Integer defaultValue) {
		return Conver.toInt(getStr(key), defaultValue);
	}

	public Short getShort(String key) {
		return getShort(key, null);
	}

	public Short getShort(String key, Short defaultValue) {
		return Conver.toShort(getStr(key), defaultValue);
	}

	public Boolean getBool(String key) {
		return getBool(key, null);
	}

	public Boolean getBool(String key, Boolean defaultValue) {
		return Conver.toBool(getStr(key), defaultValue);
	}

	public Long getLong(String key) {
		return getLong(key, null);
	}

	public Long getLong(String key, Long defaultValue) {
		return Conver.toLong(getStr(key), defaultValue);
	}

	public Character getChar(String key) {
		return getChar(key, null);
	}

	public Character getChar(String key, Character defaultValue) {
		return Conver.toChar(getStr(key), defaultValue);
	}

	public Double getDouble(String key) {
		return getDouble(key, null);
	}

	public Double getDouble(String key, Double defaultValue) {
		return Conver.toDouble(getStr(key), defaultValue);
	}

	public Byte getByte(String key) {
		return getByte(key, null);
	}

	public Byte getByte(String key, Byte defaultValue) {
		return Conver.toByte(getStr(key), defaultValue);
	}

	public BigDecimal getBigDecimal(String key) {
		return getBigDecimal(key, null);
	}

	public BigDecimal getBigDecimal(String key, BigDecimal defaultValue) {
		final String valueStr = getStr(key);
		if(StrUtil.isBlank(valueStr)) {
			return defaultValue;
		}
		
		try {
			return new BigDecimal(valueStr);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public BigInteger getBigInteger(String key) {
		return getBigInteger(key, null);
	}

	public BigInteger getBigInteger(String key, BigInteger defaultValue) {
		final String valueStr = getStr(key);
		if(StrUtil.isBlank(valueStr)) {
			return defaultValue;
		}
		
		try {
			return new BigInteger(valueStr);
		} catch (Exception e) {
			return defaultValue;
		}
	}
	
}
