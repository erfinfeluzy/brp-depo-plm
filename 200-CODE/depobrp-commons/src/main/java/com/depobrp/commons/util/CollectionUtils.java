package com.depobrp.commons.util;

import java.util.Collection;

@SuppressWarnings("rawtypes")
public final class CollectionUtils extends org.apache.commons.collections.CollectionUtils{

	
	public static boolean isEmpty(Collection collection){
		if(collection == null) return true;
		
		return collection.size() == 0 ? true : false;
	}
	
	public static boolean isNotEmpty(Collection collection){
		return !isEmpty(collection);
	}

}
