package sort;

import java.util.Arrays;
/**
 * 排序的父类
 * 用于获取数组和打印数组
 * @author asd99
 *
 */
public class Sort {
	/**
	 * 获取一个长度100,数值0~999的数组
	 * @return
	 */
	public static int[] getArray(){
		return getArray(100);
	}

	/**
	 * 获取一个指定长度,数值0~长度*10-1的数组
	 * @param len 数组长度
	 * @return
	 */
	public static int[] getArray(int len) {
		int[] arr = new int[len];
		for(int i=0;i<arr.length;i++){
			arr[i] = (int)(Math.random()*10*len);
		}
		return arr;
	}
	
	/**
	 * 打印数组快捷方法
	 * @param arr
	 */
	public static void print(int[] arr){
		System.out.println(Arrays.toString(arr));
	}
	
	/**
	 * 交换数组中两个指定下标的数
	 * @param arr
	 * @param i
	 * @param j
	 */
	public static void swap(int[] arr,int i, int j) {
		int t = arr[i];
		arr[i] = arr[j];
		arr[j] = t;
	}
}
