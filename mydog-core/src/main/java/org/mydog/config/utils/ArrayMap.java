package org.mydog.config.utils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Vector;


public class ArrayMap<K , V> extends LinkedHashMap<K, List<V>> {

	private static final long serialVersionUID = -3443539285123988233L;
	
	public void putItem(K k , V v){
		List<V> values = get(k);
		if(null == values){
			values = new Vector<>();
			super.put(k, values) ;
		}
		values.add(v) ;
	}
}
