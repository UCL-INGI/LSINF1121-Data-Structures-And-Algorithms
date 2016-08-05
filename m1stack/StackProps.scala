import org.scalacheck.Properties
import org.scalacheck.Prop
import org.scalacheck.Gen.{listOf, alphaStr, numChar}
import java.util.EmptyStackException

object StackProps extends Properties("Stack") {
	property("empty") = Prop.forAll { (el: String) =>
		try {
			val s = new MyStack[String]()
			s.push(el)
			s.pop()
			s.empty()
		} catch {
			case e: Exception => false
		}
	}

	property("not_empty") = Prop.forAll { (el: String) =>
		try {
			val s = new MyStack[String]()
			s.push(el)
			!s.empty()
		} catch {
			case e: Exception => false
		}
	}

	property("peek") = Prop.forAll { (el: String) =>
		try {
			val s = new MyStack[String]()
			s.push(el)
			s.peek()
			!s.empty()
		} catch {
			case e: Exception => false
		}
	}

	property("double_push") = Prop.forAll { (el1: String, el2: String) =>
		try {
			val s = new MyStack[String]()
			val firstPushReturn = s.push(el1)
			val secondPushreturn = s.push(el2)
			(firstPushReturn.equals(el1) && secondPushreturn.equals(el2) && s.pop().equals(el2) && s.peek().equals(el1) && !s.empty())
		} catch {
			case e: Exception => false
		}
	}

	property("multiple_push") = Prop.forAll { (el: String, n: Int) =>
		try {
			val s = new MyStack[String]()
			for (i <- 1 to n%100)
				s.push(el)
			for (i <- 1 to n%100)
				s.pop()
			s.empty()
		} catch {
			case e: Exception => false
		}
	}

	property("pop_exception") =  {
		try {
			val s = new MyStack[String]()
			Prop.throws(classOf[EmptyStackException]) {s.pop()}
		} catch {
			case e: Exception => false
		}
	}

	property("peek_exception") = {
		try {
			val s = new MyStack[String]()
			Prop.throws(classOf[EmptyStackException]) {s.peek()}
		} catch {
			case e: Exception => false
		}
	}

	property("LIFO_order") = Prop.forAll { ( elements: List[String]) =>
		try {
			val s = new MyStack[String]()
    			elements.foreach(el => s.push(el))
    			elements.reverse.forall{_ == s.pop()}
		} catch {
			case e: Exception => false
		}
  	}

	property("int") = Prop.forAll { (el: Int) =>
		try {
			val s = new MyStack[Int]()
			s.push(el)
			(s.pop() == el && s.empty())
		} catch {
			case e: Exception => false
		}
	}

    property("double") = Prop.forAll { (el: Double) =>
		try {
			val s = new MyStack[Double]()
			s.push(el)
			(s.pop() == el && s.empty())
		} catch {
			case e: Exception => false
		}
	}
}
