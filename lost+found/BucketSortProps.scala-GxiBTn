import org.scalacheck.Properties
import org.scalacheck.Prop
import org.scalacheck.Gen.{listOf, alphaStr, numChar, oneOf, choose}
import org.scalacheck.Gen
import math.{min, max}

object BucketSortProps extends Properties("BucketSort") {

	property("trivial") = {
		try {
			val array: Array[Int] = Array(1, 2, 3)
			val result = BucketSort.sort(array, 0)
			result.deep == array.deep

		} catch {
			case e: Exception => false
		}
	}

	property("empty_tab") = {
		try {
			val array: Array[Int] = Array()
			val result = BucketSort.sort(array, 0)
			result.deep == array.deep

		} catch {
			case e: Exception => false
		}
	}

	property("one_element_tab") = Prop.forAll { (w: Int) =>
		try {
			val x = if (w == Int.MinValue) Int.MaxValue else if (w >= 0) w else -w
			val array: Array[Int] = Array(x)
			val result = BucketSort.sort(array, 0)
			result.deep == array.deep

		} catch {
			case e: Exception => false
		}
	}

	val posNum = Gen.posNum[Int]
	val posNum2 = Gen.posNum[Int]
	property("digit_larger_than_number") = Prop.forAll(posNum, posNum2) { (x, y) =>
		try {
			val array: Array[Int] = Array(x%100, y%100)
			val result = BucketSort.sort(array, 9)
			result.deep == array.deep

		} catch {
			case e: Exception => false
		}
	}

	val genIntArray = Gen.containerOf[Array, Int](Gen.posNum[Int])
	property("random_arrays") = Prop.forAll(genIntArray) { array =>
		try {
			val digit = 0
			val result = BucketSort.sort(array, digit)
			result.deep == array.sortWith(getDigit(_, digit) < getDigit(_, digit)).deep

		} catch {
			case e: Exception => false
		}
	}

	val genDigit = Gen.choose(0, 9)
	property("random_digit") = Prop.forAll(genDigit) { digit =>
		try {
			val array = Array(125456, 49625, 485, 0, 3, 5198, 56982136, 489456325, 1235)
			val result = BucketSort.sort(array, digit)
			result.deep == array.sortWith(getDigit(_, digit) < getDigit(_, digit)).deep

		} catch {
			case e: Exception => false
		}
	}

	property("random_arrays_and_digits") = Prop.forAll(genIntArray, genDigit) { (array, digit) =>
		try {
			val result = BucketSort.sort(array, digit)
			result.deep == array.sortWith(getDigit(_, digit) < getDigit(_, digit)).deep

		} catch {
			case e: Exception => false
		}
	}

	// Must be compiled with -J-Xmx2g -J-Xms2g
	val largeArray = Gen.containerOfN[Array, Int](10000000, Gen.posNum[Int])
	property("linear_complexity") = {
		try {
			var large = Array(5)
			largeArray.sample match {
				case Some(a) => large = a
				case None => println("BUG")
			}
			val t1 = System.nanoTime()
			val myResult = MyBucketSort.sort(large, 0)
			val t2 = System.nanoTime()
			val result = BucketSort.sort(large, 0)
			val t3 = System.nanoTime()
			//println((t2-t1)/1000 + " " + (t3-t2)/1000)
			max(t2-t1, t3-t2) <= 10*min(t2-t1, t3-t2)
		} catch {
			case e: Exception => false
		}
	}

	property("tab_not_modified") = Prop.forAll(genIntArray) { array =>
		try {
			val digit = 1
			val oldArray = Array.ofDim[Int](array.length)
			Array.copy(array, 0, oldArray, 0, array.length)
			val result = BucketSort.sort(array, digit)
			array.deep == oldArray.deep

		} catch {
			case e: Exception => false
		}
	}

	/* Get digit at position 'pos' out of 'number' */
	def getDigit(number: Int, pos: Int): Int = {
		var i = pos
		var n = number
		while (i > 0) {
			n = n/10
			i-=1
		}
		n % 10;
	}
}
