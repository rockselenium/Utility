package objectchaining;
//import static objectchaining.ClassOne.*;

public class TestObjectChaining {
	public static void main(String[] args) {
		ClassOne.methodOne()
		 		.methodTwo()
		 		.methodThree();
				 
//		driver.findElement(By.id("name")).getText().toLowerCase();
//		
//		WebElement name=driver.findElement(By.id("name"));
//		String myName=name.getText();
//		myName.toLowerCase();
		
		
		ClassTwo c2=ClassOne.methodOne();
		ClassThree c3=c2.methodTwo();
		c3.methodThree();
				 
		
		
		
		ClassOne class1=new ClassOne();
		class1.m1()
			  .m2()
			  .m1();
	}
}
