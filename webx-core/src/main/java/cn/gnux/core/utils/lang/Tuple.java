package cn.gnux.core.utils.lang;


import java.io.Serializable;
import java.util.Arrays;

public class Tuple implements Cloneable, Serializable{
	private static final long serialVersionUID = -7689304393482182157L;
	
	private Object[] members;
	
	public Tuple(Object... members) {
		this.members = members;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(int index){
		return (T) members[index];
	}
	
	@Override
	public String toString() {
		return Arrays.toString(members);
	}
	
	@Override
	public Tuple clone() {
		try {
			return (Tuple) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}
}
