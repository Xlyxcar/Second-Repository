package sort;

/**
 * —°‘Ò≈≈–Ú
 * @author asd99
 *
 */
public class SelectSort extends Sort {
	
	public static void main(String[] args) {
		int[] arr = getArray(100000);
		long start = System.currentTimeMillis();
		Sort(arr);
		long end = System.currentTimeMillis();
		System.out.println(end-start);
	}

	private static void Sort(int[] arr) {
		long count = 0;
		for(int i=0;i<arr.length;i++){
			for(int j=i;j<arr.length;j++){
				if(arr[j]<arr[i]){
					int t = arr[j];
					arr[j] = arr[i];
					arr[i] = t;
					count++;
				}
			}
		}
		System.out.println(count);
	}
	
}
