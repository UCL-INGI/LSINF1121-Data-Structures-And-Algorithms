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

	// NB : here we accept the result as a Double or as an Int so that the student doesn't get stucked because of that
	property("div") = Prop.forAll { (x: Int, y: Int) =>
		var interpreter = new Interpreter()
		if (y == 0) // div by 0, must throw the appropriate exception !
			Prop.throws(classOf[ArithmeticException]) { interpreter.interpret(x + " " + y + " div pstack pop") }
		else {
			val result = interpreter.interpret(x + " " + y + " div pstack pop")
			(result.equals((x/y).toString) || result.equals((x.toDouble/y.toDouble).toString))
		}
	}

	property("empty stack") = Prop.forAll { (x: Int) =>
		var interpreter = new Interpreter()
		Prop.throws(classOf[EmptyStackException]) { interpreter.interpret(x + " pop pop pstack") }
	}

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
		result.equals((y-x).toString)
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

	property("def") = {
			var interpreter = new Interpreter()
			val result = interpreter.interpret("/pi 3.141592653 def /radius 42 def pi radius dup mul mul pstack pop")
			result.equals((3.141592653*42*42).toString)
	}
    
	property("generator") = {
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
   	 }

}