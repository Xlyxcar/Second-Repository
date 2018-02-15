package sort;

import java.util.Arrays;
/**
 * ����ĸ���
 * ���ڻ�ȡ����ʹ�ӡ����
 * @author asd99
 *
 */
public class Sort {
	/**
	 * ��ȡһ������100,��ֵ0~999������
	 * @return
	 */
	public static int[] getArray(){
		return getArray(100);
	}

	/**
	 * ��ȡһ��ָ������,��ֵ0~����*10-1������
	 * @param len ���鳤��
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
	 * ��ӡ�����ݷ���
	 * @param arr
	 */
	public static void print(int[] arr){
		System.out.println(Arrays.toString(arr));
	}
	
	/**
	 * ��������������ָ���±����
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
