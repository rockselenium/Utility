package objectchaining;

public class ClassOne {
	
	public static ClassTwo methodOne(){
		System.out.println("methodOne");
		return new ClassTwo();
	}
	
	public ClassOne m1(){
		System.out.println("M1");
		return this;
	}
	
	public ClassOne m2(){
		System.out.println("M2");
		return this;
	}
	
}
