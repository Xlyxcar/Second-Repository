package sort;

/**
 * ¹é²¢ÅÅÐò
 * @author asd99
 *
 */
public class MergeSort extends Sort {
	
	public static void main(String[] args) {
		int[] arr = getArray(10);
		long start = System.currentTimeMillis();
		sort(arr);
		long end = System.currentTimeMillis();
		System.out.println(end-start);
		print(arr);
	}

	private static void sort(int[] arr) {
		sort(arr,0,arr.length-1);
	}
	private static void sort(int[] arr, int left, int right) {
		System.out.println("left:"+left+" right:"+right);
		int center = (right-left)/2;
		if(center>0){
			sort(arr,left,center+left);
			sort(arr,left+center,right);
			merge(arr,left,center,right);
		}
	}

	private static void merge(int[] arr, int left, int center, int right) {
		int[] temp = new int[arr.length];
		
		int index = left;
		int l = left;
		int c = center;
		int r = right;
		while(left<c && center<r){
			while(arr[left]<arr[center]){
				temp[index++] = arr[left++];
			}
			while(arr[center]<arr[left]){
				temp[index++] = arr[center++];
			}
		}
		System.out.print("step1:");
		print(temp);
		while(left<c){
			temp[index++] = arr[left++];
		}
		while(center<r){
			temp[index++] = arr[center++];
		}
		System.out.print("step2:");
		print(temp);
		
		while(l<index){
			arr[l] = temp[l];
			l++;
		}

		System.out.print("step1:");
		print(arr);
	}
}
