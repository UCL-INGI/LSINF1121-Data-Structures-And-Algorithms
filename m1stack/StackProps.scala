import org.scalacheck.Properties
import org.scalacheck.Prop
import org.scalacheck.Gen.{listOf, alphaStr, numChar}
import java.util.EmptyStackException

object StackProps extends Properties("Stack") {
	property("empty") = Prop.forAll { (el: String) =>
		var s = new MyStack()
		s.push(el)
		s.pop()
		s.empty()
	}

	property("not empty 1") = Prop.forAll { (el: String) =>
		var s = new MyStack()
		s.push(el)
		!s.empty()
	}

	property("not empty 2") = Prop.forAll { (el: String) =>
		var s = new MyStack()
		s.push(el)
		s.peek()
		!s.empty()
	}

	property("double push") = Prop.forAll { (el1: String, el2: String) =>
		var s = new MyStack()
		s.push(el1)
		s.push(el2)
		(s.pop().equals(el2) && s.peek().equals(el1) && !s.empty())
	}

	property("multiple push") = Prop.forAll { (el: String, n: Int) =>
		var s = new MyStack()
		for (i <- 1 to n%100)
			s.push(el)
		for (i <- 1 to n%100)
			s.pop()
		s.empty()
	}

	property("pop exception") =  {
		var s = new MyStack()
		Prop.throws(classOf[EmptyStackException]) {s.pop()}
	}

	property("peek exception") = {
		var s = new MyStack()
		Prop.throws(classOf[EmptyStackException]) {s.peek()}
	}

	property("LIFO order") = Prop.forAll { ( elements: List[String]) =>
    	var s = new MyStack()
    	elements.foreach(el => s.push(el))
    	elements.reverse.forall{_ == s.pop()}
  	}
}

// scalac -cp .:scalacheck.jar StackProps.scala
// scala -cp .:scalacheck.jar StackProps
