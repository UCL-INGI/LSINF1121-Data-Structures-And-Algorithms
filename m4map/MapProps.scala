import org.scalacheck.Properties
import org.scalacheck.Prop
import org.scalacheck.Gen.{listOf, alphaStr, numChar, oneOf, choose}
import java.util.HashMap
import org.scalacheck.Gen
import org.scalacheck.Arbitrary.arbitrary
import math.abs
import math.max
import math.min

object MapProps extends Properties("Map") {
	val debug = true

	property("put") = Prop.forAll { (key: String, value1: Int, value2: Int) =>
		try {
        	if (debug) println("Start put")
			val map = new MyMap[String, Int]()
			map.put(key, value1)
			map.put(key, value2) == value1
		} catch {
			case e: Exception => false
		}
	}

	property("get") = Prop.forAll { (key: String, value: Int) =>
		try {
        	if (debug) println("Start get")
			val map = new MyMap[String, Int]()
			map.put(key, value)
			map.get(key) == value
		} catch {
			case e: Exception => false
		}
	}

	property("containsKey") = Prop.forAll { (key: String, value: Int) =>
		try {
        	if (debug) println("Start containsKey")
			val map = new MyMap[String, Int]()
			map.put(key, value)
			map.containsKey(key)
		} catch {
			case e: Exception => false
		}
	}

	property("containsValue") = Prop.forAll { (key: String, value: Int) =>
		try {
        	if (debug) println("Start containsValue")
			val map = new MyMap[String, Int]()
			map.put(key, value)
			map.containsValue(value)
		} catch {
			case e: Exception => false
		}
	}

	property("entrySet") = Prop.forAll { (key: Int, value: String) =>
		try {
        	if (debug) println("Start entrySet")
			val map = new MyMap[Int, String]()
			map.put(key, value)
			val map2 = new HashMap[Int, String]()
			map2.put(key, value)
			val set = map.entrySet()
			val it = set.iterator()
			val pair = it.next()
			set.size() == 1 && pair.getKey().equals(key) && pair.getValue().equals(value) && !it.hasNext() && 
			set.equals(map2.entrySet())
		} catch {
			case e: Exception => false
		}
	}

	property("entrySet_equals") = Prop.forAll { (key: Int, value: String) =>
		try {
        	if (debug) println("Start entrySet_equals")
			val map = new MyMap[Int, String]()
			map.put(key, value)
			map.put(key+1, value)
			map.put(key+2, value)
			val map2 = new HashMap[Int, String]()
			map2.put(key+2, value)
			map2.put(key, value)
			map2.put(key+1, value)
			map.entrySet().equals(map2.entrySet())
		} catch {
			case e: Exception => false
		}
	}

	property("hashCode") = Prop.forAll { (key: String, value: Int) =>
		try {
        	if (debug) println("Start hashCode")
			val map = new MyMap[String, Int]()
			val map2 = new MyMap[String, Int]()
			map.put(key, value)
			map2.put(key, value)
			val cond = map.hashCode() == map2.hashCode()
			map.put("autre", 42)
			map2.put("autre", 42)
			map.hashCode() == map2.hashCode() && cond
			
		} catch {
			case e: Exception => false
		}
	}

	property("isEmpty") = Prop.forAll { (key: String, value: Double) =>
		try {
        	if (debug) println("Start isEmpty")
			val map = new MyMap[String, Double]()
			val cond = map.isEmpty()
			map.put(key, value)
			!map.isEmpty() && cond
		} catch {
			case e: Exception => false
		}
	}

	property("remove") = Prop.forAll { (key: String, value: Double) =>
		try {
        	if (debug) println("Start remove")
			val map = new MyMap[String, Double]()
			map.put(key, value)
			map.remove(key) == value && map.isEmpty()
		} catch {
			case e: Exception => false
		}
	}

	property("size") = Prop.forAll { (key: String, key2: String, value: Double) =>
		try {
			if (debug) println(List(1,2,3).forall{x: Int => x < 4})
			val map = new MyMap[String, Double]()
			val cond = (map.size() == 0)
			map.put(key, value)
			val cond2 = (map.size() == 1)
			map.put(key2, value)
			cond && cond2 && (map.size() == 2 || key.equals(key2))
		} catch {
			case e: Exception => false
		}
	}

	property("collisions") = {
		try {
        	if (debug) println("Start collisions")
			val bigMap = new MyMap[String, Double]()
			var i = 0
			Prop.forAll { (key: String, value: Double) =>
				if (!bigMap.containsKey(key))
					i+=1
				bigMap.put(key, value)
				bigMap.size() == i && bigMap.containsKey(key) && bigMap.containsValue(value) && bigMap.get(key) == value
			}
		} catch {
			case e: Exception => false
		}
	}

	
	val numbersGen = listOf(choose(1, 100))
	property("multiple_operations") = Prop.forAll(numbersGen) { numbers =>
		try {
			val bigMap = new MyMap[Int, Int]()
            if (debug) println("Start multiple_operations")
			numbers.forall { n: Int =>
				bigMap.put(n, -n)
				bigMap.containsKey(n) && bigMap.containsValue(-n) && bigMap.get(n) == -n && !bigMap.isEmpty()
			} &&
			numbers.forall { n: Int =>
				val size = bigMap.size()
				if (bigMap.containsKey(n)) 
					bigMap.remove(n) == -n && bigMap.size() == size-1
				else {
					bigMap.remove(n) // doesn't remove anything
					bigMap.size() == size
				}
			} &&
			bigMap.isEmpty() && bigMap.size() == 0
		} catch {
			case e: Exception => false
		}
	}

	property("put_complexity") = {
		try {
        	if (debug) println("Start put_complexity")
			val qty = 1000000
			val map = new MyMap[Int, Int]()
			val hashMap = new HashMap[Int, Int]()
			val t1 = System.nanoTime()
			for (i <- 1 to qty)
				hashMap.put(i, -i)
			val t2 = System.nanoTime()
			for (i <- 1 to qty)
				map.put(i, -i)
			val t3 = System.nanoTime()
			if (debug) println((t2-t1)/1000000 + " " + (t3-t2)/1000000)
			max(t2-t1, t3-t2) <= 20*min(t2-t1, t3-t2)
		} catch {
			case e: Exception => false
		}
	}

	property("get_complexity") = {
		try {
        	if (debug) println("Start get_complexity")
			val qty = 1000000
			val map = new MyMap[Int, Int]()
			val hashMap = new HashMap[Int, Int]()
			val t1 = System.nanoTime()
			for (i <- 1 to qty)
				hashMap.put(i, -i)
			for (i <- 1 to qty)
				hashMap.get(i)
			val t2 = System.nanoTime()
			for (i <- 1 to qty)
				map.put(i, -i)
			for (i <- 1 to qty)
				map.get(i)
			val t3 = System.nanoTime()
			if (debug) println((t2-t1)/1000000 + " " + (t3-t2)/1000000)
			max(t2-t1, t3-t2) <= 20*min(t2-t1, t3-t2)
		} catch {
			case e: Exception => false
		}
	}

	property("remove_complexity") = {
		try {
        	if (debug) println("Start remove_complexity")
			val qty = 1000000
			val map = new MyMap[Int, Int]()
			val hashMap = new HashMap[Int, Int]()
			val t1 = System.nanoTime()
			for (i <- 1 to qty)
				hashMap.put(i, -i)
			for (i <- 1 to qty)
				hashMap.remove(i)
			val t2 = System.nanoTime()
			for (i <- 1 to qty)
				map.put(i, -i)
			for (i <- 1 to qty)
				map.remove(i)
			val t3 = System.nanoTime()
			if (debug) println((t2-t1)/1000000 + " " + (t3-t2)/1000000)
			max(t2-t1, t3-t2) <= 20*min(t2-t1, t3-t2)
		} catch {
			case e: Exception => false
		}
	}

	property("contains_key_complexity") = {
		try {
        	if (debug) println("Start contains_key complexity")
			val qty = 1000000
			val map = new MyMap[Int, Int]()
			val hashMap = new HashMap[Int, Int]()
			val t1 = System.nanoTime()
			for (i <- 1 to qty)
				hashMap.put(i, -i)
			for (i <- 1 to qty)
				hashMap.containsKey(i)
			val t2 = System.nanoTime()
			for (i <- 1 to qty)
				map.put(i, -i)
			for (i <- 1 to qty)
				map.containsKey(i)
			val t3 = System.nanoTime()
			if (debug) println((t2-t1)/1000000 + " " + (t3-t2)/1000000)
			max(t2-t1, t3-t2) <= 20*min(t2-t1, t3-t2)
		} catch {
			case e: Exception => false
		}
	}

	property("contains_value_complexity") = {
		try {
        	if (debug) println("Start contains_value complexity")
			val qty = 10000
			val map = new MyMap[Int, Int]()
			val hashMap = new HashMap[Int, Int]()
			val t1 = System.nanoTime()
			for (i <- 1 to qty)
				hashMap.put(i, -i)
			for (i <- 1 to qty)
				hashMap.containsValue(-i)
			val t2 = System.nanoTime()
			for (i <- 1 to qty)
				map.put(i, -i)
			for (i <- 1 to qty)
				map.containsValue(-i)
			val t3 = System.nanoTime()
			if (debug) println((t2-t1)/1000000 + " " + (t3-t2)/1000000)
			max(t2-t1, t3-t2) <= 20*min(t2-t1, t3-t2)
		} catch {
			case e: Exception => false
		}
	}

	property("entry_set_complexity") = {
		try {
        	if (debug) println("Start entry_set_complexity")
			val qty = 1000000
			val map = new MyMap[Int, Int]()
			val hashMap = new HashMap[Int, Int]()
			val t1 = System.nanoTime()
			for (i <- 1 to qty)
				hashMap.put(i, -i)
			hashMap.entrySet()
			val t2 = System.nanoTime()
			for (i <- 1 to qty)
				map.put(i, -i)
			map.entrySet()
			val t3 = System.nanoTime()
			if (debug) println((t2-t1)/1000000 + " " + (t3-t2)/1000000)
			max(t2-t1, t3-t2) <= 20*min(t2-t1, t3-t2)
		} catch {
			case e: Exception => false
		}
	}

	property("is_empty_complexity") = {
		try {
        	if (debug) println("Start is_empty_complexity")
			val qty = 1000000
			val map = new MyMap[Int, Int]()
			val hashMap = new HashMap[Int, Int]()
			val t1 = System.nanoTime()
			for (i <- 1 to qty) {
				hashMap.put(i, -i)
				hashMap.isEmpty()
			}
			val t2 = System.nanoTime()
			for (i <- 1 to qty) {
				map.put(i, -i)
				map.isEmpty()
			}
			val t3 = System.nanoTime()
			if (debug) println((t2-t1)/1000000 + " " + (t3-t2)/1000000)
			max(t2-t1, t3-t2) <= 20*min(t2-t1, t3-t2)
		} catch {
			case e: Exception => false
		}
	}

	property("size_complexity") = {
		try {
        	if (debug) println("Start size_complexity")
			val qty = 1000000
			val map = new MyMap[Int, Int]()
			val hashMap = new HashMap[Int, Int]()
			val t1 = System.nanoTime()
			for (i <- 1 to qty) {
				hashMap.put(i, -i)
				hashMap.size()
			}
			val t2 = System.nanoTime()
			for (i <- 1 to qty) {
				map.put(i, -i)
				map.size()
			}
			val t3 = System.nanoTime()
			if (debug) println((t2-t1)/1000000 + " " + (t3-t2)/1000000)
			max(t2-t1, t3-t2) <= 20*min(t2-t1, t3-t2)
		} catch {
			case e: Exception => false
		}
	}
}