package sort;

public class HeapSort extends Sort {
	
	public static void main(String[] args) {
		int[] arr = getArray(10);
		long start = System.currentTimeMillis();
		sort(arr);
		long end = System.currentTimeMillis();
		System.out.println(end-start);
		print(arr);
	}
	
	private static void sort(int[] arr) {
		for(int i=arr.length-1;i>=0;i--){
			createHeap(arr,i);
			swap(arr,0,i);
		}
	}

	private static void createHeap(int[] arr, int border) {
		for(int i=border;i>=0;i--){
			if(i*2+1<=border){
				int c = i*2+1;
				if(c+1<=border && arr[c]<arr[c+1]){
					swap(arr,c,c+1);
				}
				if(arr[i]<arr[c]){
					swap(arr,i,c);
				}
			}
		}
	}
}