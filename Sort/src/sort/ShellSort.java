package sort;

/**
 * ϣ������
 * @author asd99
 *
 */
public class ShellSort extends Sort {
	
	public static void main(String[] args) {
		int[] arr = getArray(1000000);
		long start = System.currentTimeMillis();
		Sort(arr);
		long end = System.currentTimeMillis();
		System.out.println(end-start);
	}

	private static void Sort(int[] arr) {
		int flag = -1; 
		for(int i=2;arr.length/i>1;i++){
			int l = arr.length/i;
			if(flag==l) continue; //����Ѿ��Աȹ�,������(��:10/4=3 -> 10/5=3,��������)
			flag = l;
			for(int j=0;l<arr.length;j++,l++){
				if(arr[j]>arr[l]){
					int t = arr[j];
					arr[j] = arr[l];
					arr[l] = t;
				}
			}
		}
	}
	
	
}
