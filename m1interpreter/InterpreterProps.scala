import org.scalacheck.Properties
import org.scalacheck.Prop
import org.scalacheck.Gen.{listOf, alphaStr, numChar}
import java.util.EmptyStackException
import java.util.ArrayList

object InterpreterProps extends Properties("Interpreter") {
	property("add") = Prop.forAll { (x: Int, y: Int) =>
		try {
			var interpreter = new Interpreter()
			val result = interpreter.interpret(x + " " + y + " add pstack pop")
			result.equals((x+y).toString)
		} catch {
			case e: Exception => false
		}
	}

	property("sub") = Prop.forAll { (x: Int, y: Int) =>
		try {
			var interpreter = new Interpreter()
			val result = interpreter.interpret(x + " " + y + " sub pstack pop")
			result.equals((x-y).toString)
		} catch {
			case e: Exception => false
		}
	}

	property("mul") = Prop.forAll { (x: Int, y: Int) =>
		try {
			var interpreter = new Interpreter()
			val result = interpreter.interpret(x + " " + y + " mul pstack pop")
			result.equals((x*y).toString)
		} catch {
			case e: Exception => false
		}
	}

	// NB : here we accept the result as a Double or as an Int so that the student doesn't get stucked because of that
	property("div") = Prop.forAll { (x: Int, y: Int) =>
		try {
			var interpreter = new Interpreter()
			if (y == 0) // div by 0, must throw the appropriate exception !
				Prop.throws(classOf[ArithmeticException]) { interpreter.interpret(x + " " + y + " div pstack pop") }
			else {
				val result = interpreter.interpret(x + " " + y + " div pstack pop")
				(result.equals((x/y).toString) || result.equals((x.toDouble/y.toDouble).toString))
			}
		} catch {
			case e: Exception => false
		}
	}

	property("empty_stack_exception") = Prop.forAll { (x: Int) =>
		try {
			var interpreter = new Interpreter()
			Prop.throws(classOf[EmptyStackException]) { interpreter.interpret(x + " pop pop pstack") }
		} catch {
			case e: Exception => false
		}
	}

	property("pstack_stack_empty") = Prop.forAll { (x: Int, y: Int) =>
		try {
			var interpreter = new Interpreter()
			val result = interpreter.interpret(x + " " + y + " add pop pstack")
			result.equals("")
		} catch {
			case e: Exception => false
		}
	}

	property("no_pstack") = Prop.forAll { (x: Int, y: Int) =>
		try {
			var interpreter = new Interpreter()
			val result = interpreter.interpret(x + " " + y + " add pop")
			result.equals("")
		} catch {
			case e: Exception => false
		}
	}

	property("mul_add") = Prop.forAll { (x: Int, y: Int, z: Int) =>
		try {
			var interpreter = new Interpreter()
			val result = interpreter.interpret(x + " " + y + " " + z + " mul add pstack pop")
			result.equals((x+(y*z)).toString)
		} catch {
			case e: Exception => false
		}
	}

	property("add_mul") = Prop.forAll { (x: Int, y: Int, z: Int) =>
		try {
			var interpreter = new Interpreter()
			val result = interpreter.interpret(x + " " + y + " add " + z + " mul pstack pop")
			result.equals(((x+y)*z).toString)
		} catch {
			case e: Exception => false
		}
	}

	// Removed because most student would fail because of
	// rounding errors (double/float, ...)
	/*property("dup_with_double") = Prop.forAll { (x: Double) =>
		try {
			var interpreter = new Interpreter()
			val result = interpreter.interpret(x + " dup mul pstack pop")
			result.equals((x*x).toString)
		} catch {
			case e: Exception => false
		}
	}*/

	property("exch") = Prop.forAll { (x: Int, y: Int) =>
		try {
			var interpreter = new Interpreter()
			val result = interpreter.interpret(x + " " + y + " exch sub pstack pop")
			result.equals((y-x).toString)
		} catch {
			case e: Exception => false
		}
	}

	property("eq") = Prop.forAll { (x: Int, y: Int) =>
		try {
			var interpreter = new Interpreter()
			val result = interpreter.interpret(x + " " + y + " eq pstack pop")
			result.equals((x==y).toString)
		} catch {
			case e: Exception => false
		}
	}

	property("ne") = Prop.forAll { (x: Int, y: Int) =>
		try {
			var interpreter = new Interpreter()
			val result = interpreter.interpret(x + " " + y + " ne pstack pop")
			result.equals((x!=y).toString)
		} catch {
			case e: Exception => false
		}
	}

	property("def") = {
		try {
			var interpreter = new Interpreter()
			val result = interpreter.interpret("/pi 3 def /radius 42 def pi radius dup mul mul pstack pop")
			result.equals((3*42*42).toString)
		} catch {
			case e: Exception => false
		}
	}

	// Removed because the result of the test relied too much on RNG-esus
	/*property("generator") = {
		try {
			val g = new GeneratorPostScript(42)
			var b = true
			for (i <- 1 to 100 if b) {
				val s = g.generate(20)
				//println("Generation : " + s)
				var interpreter = new Interpreter()
				val myInterpreter = new MyInterpreter()
				if (Prop.throws(classOf[Exception]) { (new Interpreter()).interpret(s) } && Prop.throws(classOf[Exception]) { (new MyInterpreter()).interpret(s) }) {
					// Ok
				}
				else if (Prop.throws(classOf[Exception]) { (new Interpreter()).interpret(s) } || Prop.throws(classOf[Exception]) { new MyInterpreter().interpret(s) }) {
					b = false // Missing exception or unwanted exception
				}
				else {
					val result = interpreter.interpret(s)
					val myResult = myInterpreter.interpret(s)
					if (!result.equals(myResult)) {
						b = false
					}
				}
			}
			b
		} catch {
			case e: Exception => false
		}
	}*/

}
