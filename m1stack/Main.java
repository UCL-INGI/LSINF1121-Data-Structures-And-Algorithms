public class Main {

    public static void main(String[] args) {
	MyStack s = new MyStack(); 
	s.push("a"); 
	if (s.pop().equals("a") && s.empty())
		System.out.println("OK"); 
    }

}
