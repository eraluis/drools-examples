package droolscourse.util;

public class OutputDisplay {
	
	public void showText(String someText) {
		long time=System.currentTimeMillis();
		System.out.println(someText + " - Fired at time: " +time);
	}

}
