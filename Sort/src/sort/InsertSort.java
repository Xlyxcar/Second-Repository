package sort;

/**
 * ≤Â»Î≈≈–Ú
 * @author asd99
 *
 */
public class InsertSort extends Sort{
	
	public static void main(String[] args) {
		int[] arr = getArray(1000000);
		long start = System.currentTimeMillis();
		Sort(arr);
		long end = System.currentTimeMillis();
		System.out.println(end-start);
	}

	private static void Sort(int[] arr) {
		for(int i=1;i<arr.length;i++){
			for(int j=i;j>0 && arr[j]<arr[j-1];j--){
				int t = arr[j];
				arr[j] = arr[j-1];
				arr[j-1] = t;
			}
		}
		
	}
}
