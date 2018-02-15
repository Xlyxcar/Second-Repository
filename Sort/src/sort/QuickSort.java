package sort;

/**
 * øÏÀŸ≈≈–Ú
 * @author asd99
 *
 */
public class QuickSort extends Sort {
	
	public static void main(String[] args) {
		int[] arr = getArray(10000000);
		long start = System.currentTimeMillis();
		Sort(arr);
		long end = System.currentTimeMillis();
		System.out.println(end-start);
	}

	private static void Sort(int[] arr) {
		quickSort(arr,0,arr.length-1);
	}

	private static void quickSort(int[] arr, int left, int right) {
		int radix = left;
		int destra = right;
		while(left<right){
			while(arr[right]>=arr[radix] && right>left){
				right--;
			}
			while(arr[left]<=arr[radix] && left<right){
				left++;
			}
			int t = arr[left];
			arr[left] = arr[right];
			arr[right] = t;
		}
		int t = arr[left];
		arr[left] = arr[radix];
		arr[radix] = t;
		
		if(radix < left-1 && left+1 < destra){
			quickSort(arr,radix,left-1);
			quickSort(arr,left+1,destra);
		}
	}
	
}
