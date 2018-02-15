package sort;

import java.util.Scanner;
/**
 * 基数排序
 * @author asd99
 *
 */
public class RadixSort extends Sort{
	
	public static void main(String[] args) {
		int[] arr = getArray(1000000);
		long start = System.currentTimeMillis();
		Sort(arr);
		long end = System.currentTimeMillis();
		System.out.println(end-start);
	}

	private static void Sort(int[] arr) {
//		Scanner scan = new Scanner(System.in);
//		System.out.println("请数组中输入最高位数(9=1;99=2;999=3;...):");
//		int digit = scan.nextInt();
		
//		LeastSigSort(arr, 3);
		MostSigSort(arr,3,0,arr.length);
	}
	/** 
	 * 最低位优先
	 * @param arr 数组
	 * @param dig 最大位数
	 */
	private static void LeastSigSort(int[] arr, int dig) {
		int[][] a = new int[10][arr.length];
		int[] b = new int[10];
		int n = 1;
		int q = (int) Math.pow(10, dig-1);
		while(n<=q){
			for(int i=0;i<arr.length;i++){
				int m = (arr[i]/n)%10;
				a[m][b[m]++] = arr[i];
			}
			
			int o = 0;
			for(int i=0;i<10;i++){
				for(int j=0;b[i]>0;j++,b[i]--){
					arr[o++] = a[i][j];
				}
			}
			n *= 10;
		}
		
	}
	
	/**
	 * 最高位优先
	 * @param arr 数组
	 * @param dig 最大位数
	 * @param start 数组起始下标
	 * @param len 数组长度
	 */
	private static void MostSigSort(int[] arr, int dig,int start,int len) {
		int[][] a = new int[10][len];
		int[] b = new int[10];
		int q = (int) Math.pow(10, dig-1);
		
		for(int i=start,j=0;j<len;i++,j++){
			int m = (arr[i]/q)%10;
			a[m][b[m]++] = arr[i];
		}

		int o = start;
		for(int i=0;i<10;i++){
			if(b[i]>0) {
				int m = o;
				int l = b[i];
				for(int j=0;b[i]>0;j++,b[i]--){
					arr[o++] = a[i][j];
				}
				if(dig-1>0 && l>1) MostSigSort(arr,dig-1,m,l);
			}
		}
	}
	
	
}
