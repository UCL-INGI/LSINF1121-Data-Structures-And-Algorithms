import org.scalacheck.Properties
import org.scalacheck.Prop
import org.scalacheck.Gen.{listOf, alphaStr, numChar}
import java.util.EmptyStackException
import java.util.ArrayList

object InterpreterProps extends Properties("Interpreter") {
	property("add") = Prop.forAll { (x: Int, y: Int) =>
		var interpreter = new Interpreter()
		val result = interpreter.interpret(x + " " + y + " add pstack pop")
		result.equals((x+y).toString)
	}

	property("sub") = Prop.forAll { (x: Int, y: Int) =>
		var interpreter = new Interpreter()
		val result = interpreter.interpret(x + " " + y + " sub pstack pop")
		result.equals((x-y).toString)
	}

	property("mul") = Prop.forAll { (x: Int, y: Int) =>
		var interpreter = new Interpreter()
		val result = interpreter.interpret(x + " " + y + " mul pstack pop")
		result.equals((x*y).toString)
	}

	/*property("div") = Prop.forAll { (x: Int, y: Int) =>
		var interpreter = new Interpreter()
		if (y == 0) // div by 0, must throw the appropriate exception !
			Prop.throws(classOf[ArithmeticException]) { interpreter.interpret(x + " " + y + " div pstack pop") }
		else {
			val result = interpreter.interpret(x + " " + y + " div pstack pop")
			result.equals((x/y).toString)
		}
	}

	property("empty stack") = Prop.forAll { (x: Int) =>
		var interpreter = new Interpreter()
		Prop.throws(classOf[ArithmeticException]) { interpreter.interpret(x + " pop pop") }
	}*/

	property("stack empty") = Prop.forAll { (x: Int, y: Int) =>
		var interpreter = new Interpreter()
		val result = interpreter.interpret(x + " " + y + " add pop pstack")
		result.equals("")
	}

	property("no pstack") = Prop.forAll { (x: Int, y: Int) =>
		var interpreter = new Interpreter()
		val result = interpreter.interpret(x + " " + y + " add pop")
		result.equals("")
	}

	property("mul add") = Prop.forAll { (x: Int, y: Int, z: Int) =>
		var interpreter = new Interpreter()
		val result = interpreter.interpret(x + " " + y + " " + z + " mul add pstack pop")
		result.equals((x+(y*z)).toString)
	}

	property("add mul") = Prop.forAll { (x: Int, y: Int, z: Int) =>
		var interpreter = new Interpreter()
		val result = interpreter.interpret(x + " " + y + " add " + z + " mul pstack pop")
		result.equals(((x+y)*z).toString)
	}

	property("dup with double") = Prop.forAll { (x: Double) =>
		var interpreter = new Interpreter()
		val result = interpreter.interpret(x + " dup mul pstack pop")
		result.equals((x*x).toString)
	}

	property("exch") = Prop.forAll { (x: Int, y: Int) =>
		var interpreter = new Interpreter()
		val result = interpreter.interpret(x + " " + y + " exch sub pstack pop")
		result.equals((x-y).toString)
	}

	property("eq") = Prop.forAll { (x: Int, y: Int) =>
		var interpreter = new Interpreter()
		val result = interpreter.interpret(x + " " + y + " eq pstack pop")
		result.equals((x==y).toString)
	}

	property("ne") = Prop.forAll { (x: Int, y: Int) =>
		var interpreter = new Interpreter()
		val result = interpreter.interpret(x + " " + y + " ne pstack pop")
		result.equals((x!=y).toString)
	}

	property("def") = Prop.forAll { (x: Double) =>
		if (x > 100000 || x < -100000) true
		else {
			var interpreter = new Interpreter()
			val result = interpreter.interpret("/pi 3.141592653 def /radius " + x + " def pi radius dup mul mul pstack pop")
			result.equals((3.141592653*x*x).toString)
		}
	}

}

// scalac -cp .:scalacheck.jar StackProps.scala
// scala -cp .:scalacheck.jar StackProps