package objectchaining;

public class ClassTwo {
	public static ClassThree methodTwo(){
		System.out.println("methodTwo");
		return new ClassThree();
	}
}
