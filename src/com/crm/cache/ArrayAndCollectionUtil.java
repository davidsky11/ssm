package com.crm.cache;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.Vector;

/**
 * 数组,集合常用方法
 * 
 * @author xiajun
 * 
 */
public class ArrayAndCollectionUtil {
	/**
	 * 
	 * @param o
	 *            要计算占用内存大小的对象
	 * @return 占用内存 单位byte
	 * @throws IOException
	 */
	public static int size(final Object o) throws IOException {
		if (o == null) {
			return 0;
		}
		ByteArrayOutputStream buf = new ByteArrayOutputStream(4096);
		ObjectOutputStream out = new ObjectOutputStream(buf);
		out.writeObject(o);
		out.flush();
		buf.close();

		return buf.size();
	}

	/**
	 * 集合克隆
	 * 
	 * @param list
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public static List listCopy(List list) throws IOException,
			ClassNotFoundException {
		if (list instanceof ArrayList) {
			return (List) ((ArrayList) list).clone();
		} else if (list instanceof Vector) {
			return (List) ((Vector) list).clone();
		} else {
			return (List) copy(list);
		}
	}

	/**
	 * 字符串数组转化成整形数组
	 * 
	 * @param strArray
	 * @return
	 */
	public static int[] toIntArr(String[] strArray) {
		int[] result = null;
		if (strArray == null || strArray.length == 0) {
			return null;
		} else {
			result = new int[strArray.length];
			for (int i = 0; i < strArray.length; i++) {
				try {
					result[i] = Integer.parseInt(strArray[i]);
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}
			return result;
		}
	}

	/**
	 * 在数组中是否有指定的字符串
	 * 
	 * @param strArray
	 * @param str
	 * @return
	 */
	public static boolean hasStr(String[] strArray, String str) {
		if (strArray == null || str == null) {
			return false;
		}
		for (int i = 0; i < strArray.length; i++) {
			if (strArray[i].equals(str)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 排序最深层List集合对象
	 * 
	 * @param lists
	 *            List的多层嵌套集合
	 */
	@SuppressWarnings("unchecked")
	public static void orderDeepestList(List listObject) {
		if (listObject.size() == 0) {
			return;
		}
		boolean deepest = true;
		for (int i = 0; i < listObject.size(); i++) {
			if (listObject.get(i) instanceof List) {
				deepest = false;
				ArrayAndCollectionUtil.orderDeepestList((List) listObject
						.get(i));
			}
		}
		if (deepest) {
			ArrayAndCollectionUtil.sortWithoutComparable(listObject);
		}
	}

	/**
	 * 删除list中除index位置的其他全部元素 （index从0开始）
	 * 
	 * @param list
	 * @param index
	 */
	@SuppressWarnings("unchecked")
	public static List<Object> cutList(List list, int index) {
		Object o = list.get(index);
		list.clear();
		list.add(o);
		return list;
	}

	/**
	 * 对可排序的List集合进行排序
	 * 
	 * @param listObject
	 */
	@SuppressWarnings("unchecked")
	private static void sortWithoutComparable(List listObject) {
		List<Comparable> comparables = new ArrayList<Comparable>();
		try {
			for (int i = 0; i < listObject.size(); i++) {
				comparables.add((Comparable) listObject.get(i));
			}
			Collections.sort(comparables);
			listObject.clear();
			for (int i = 0; i < comparables.size(); i++) {
				listObject.add(comparables.get(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 复制对象
	 * 
	 * @param o
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private static Object copy(final Object o) throws IOException,
			ClassNotFoundException {
		if (o == null) {
			return null;
		}
		ByteArrayOutputStream outbuf = new ByteArrayOutputStream(4096);
		ObjectOutput out = new ObjectOutputStream(outbuf);
		out.writeObject(o);
		out.flush();
		outbuf.close();
		ByteArrayInputStream inbuf = new ByteArrayInputStream(outbuf
				.toByteArray());
		ObjectInput in = new ObjectInputStream(inbuf);
		return in.readObject();
	}

	/**
	 * 集合的差
	 * 
	 * @param ls
	 * @param minuend
	 * @return
	 */
	public static List diff(List ls, List minuend) {
		List list = new ArrayList(Arrays.asList(new Object[ls.size()]));
		Collections.copy(list, ls);
		if (minuend != null && minuend.size() > 0) {
			list.removeAll(minuend);
		}
		return list;
	}
	
	public static int[] diff(int[] intArray, Set<Integer> minuend){
	    int[] r=null;
	    List<Integer> list=new ArrayList();
		if(intArray==null||intArray.length==0||minuend==null||minuend.size()==0){
			return intArray;
		}
		int length=intArray.length;
		for(int i=0;i<length;i++){
			if(!minuend.contains(intArray[i])){
				list.add(intArray[i]);
			}
		}
		int size=list.size();
		if(size>0){
			r=new int[size];
			for(int i=0;i<size;i++){
				r[i]=list.get(i);
			}
		}
		return r;
	}
	
	
	public static int[] diff(int[] intArray, List<Integer> minuend){
	    int[] r=null;
	    List<Integer> list=new ArrayList();
		if(intArray==null||intArray.length==0||minuend==null||minuend.size()==0){
			return intArray;
		}
		int length=intArray.length;
		for(int i=0;i<length;i++){
			if(!minuend.contains(intArray[i])){
				list.add(intArray[i]);
			}
		}
		int size=list.size();
		if(size>0){
			r=new int[size];
			for(int i=0;i<size;i++){
				r[i]=list.get(i);
			}
		}
		return r;
	}
	
	

}
