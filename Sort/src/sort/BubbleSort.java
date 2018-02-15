package sort;

/**
 * ц╟ещеепР
 * @author asd99
 *
 */
public class BubbleSort extends Sort{
	
	public static void main(String[] args) {
		int[] arr = getArray(100000);
		long start = System.currentTimeMillis();
		Sort(arr);
		long end = System.currentTimeMillis();
		System.out.println(end-start);
	}

	private static void Sort(int[] arr) {
		for(int i=0;i<arr.length;i++){
			for(int j=0;j<arr.length-i-1;j++){
				if(arr[j]>arr[j+1]){
					int t = arr[j];
					arr[j] = arr[j+1];
					arr[j+1] = t;
				}
			}
		}
	}
}
