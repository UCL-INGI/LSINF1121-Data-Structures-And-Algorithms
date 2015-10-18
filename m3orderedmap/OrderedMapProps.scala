import org.scalacheck.Properties
import org.scalacheck.Prop
import org.scalacheck.Gen.{listOf, alphaStr, numChar, oneOf, choose}
import org.scalacheck.Gen
import scala.collection.mutable.Set
import scala.collection.JavaConversions.mutableSetAsJavaSet

object OrderedMapProps extends Properties("OrderedMap") {

	/* Map ADT */

	property("size") = Prop.forAll { (key: String, value: Set[String]) =>
		try {
			val tree = new SearchTree()
			val size1 = tree.size()
			tree.put(key, mutableSetAsJavaSet(value))
			val size2 = tree.size()
			tree.put(key, mutableSetAsJavaSet(value))
			val size3 = tree.size()
			tree.remove(key)
			tree.size() == 0 && size1 == 0  && size2 == 1 && size3 == 1

		} catch {
			case e: Exception => false
		}
	}

	property("isEmpty") = Prop.forAll { (key: String, value: Set[String]) =>
		try {
			val tree = new SearchTree()
			val e1 = tree.isEmpty()
			tree.put(key, mutableSetAsJavaSet(value))
			val e2 = tree.isEmpty()
			tree.put(key, mutableSetAsJavaSet(value))
			val e3 = tree.isEmpty()
			tree.remove(key)
			tree.isEmpty() && e1  && !e2 && !e3

		} catch {
			case e: Exception => false
		}
	}

	property("put_get_remove") = Prop.forAll { (key: String, value: Set[String]) =>
		try {
			val tree = new SearchTree()
			val result = tree.put(key, mutableSetAsJavaSet(value))
			val result2 = tree.get(key).equals(mutableSetAsJavaSet(value))
			tree.remove(key).equals(mutableSetAsJavaSet(value)) && Option(result) == None && result2 == true
		} catch {
			case e: Exception => false
		}
	}

	property("remove_get_put_put") = Prop.forAll { (key: String, value: Set[String]) =>
		try {
			val tree = new SearchTree()
			val result = tree.remove(key)
			val result2 = tree.get(key)
			val result3 = tree.put(key, mutableSetAsJavaSet(value))
			val result4 = tree.put(key, mutableSetAsJavaSet(value))
			Option(result) == None && Option(result2) == None && Option(result3) == None && result4.equals(mutableSetAsJavaSet(value))
		} catch {
			case e: Exception => false
		}
	}

	property("put_get_remove") = Prop.forAll { (key: String, value: Set[String]) =>
		try {
			val tree = new SearchTree()
			val result = tree.put(key, mutableSetAsJavaSet(value))
			val result2 = tree.get(key).equals(mutableSetAsJavaSet(value))
			tree.remove(key).equals(mutableSetAsJavaSet(value)) && Option(result) == None && result2 == true
		} catch {
			case e: Exception => false
		}
	}

	property("entrySet") = Prop.forAll { (key: String, value: Set[String]) =>
		try {
			val tree = new SearchTree()
			tree.put(key, mutableSetAsJavaSet(value))
			val set = tree.entrySet()
			val it = set.iterator()
			val pair = it.next()
			set.size() == 1 && pair.getKey().equals(key) && pair.getValue().equals(mutableSetAsJavaSet(value)) && !it.hasNext()
		} catch {
			case e: Exception => false
		}
	}

	property("keySet") = Prop.forAll { (key: String, value: Set[String]) =>
		try {
			val tree = new SearchTree()
			tree.put(key, mutableSetAsJavaSet(value))
			val set = tree.keySet()
			val it = set.iterator()
			val myKey = it.next()
			set.size() == 1 && myKey.equals(key) && !it.hasNext()
		} catch {
			case e: Exception => false
		}
	}

	property("values") = Prop.forAll { (key: String, value: Set[String]) =>
		try {
			val tree = new SearchTree()
			tree.put(key, mutableSetAsJavaSet(value))
			val collection = tree.values()
			val it = collection.iterator()
			val mySet = it.next()
			collection.size() == 1 && !it.hasNext() && mySet.equals(mutableSetAsJavaSet(value))
		} catch {
			case e: Exception => false
		}
	}

	/* OrderedMap ADT */

	property("firstEntry") = {
		try {
			val tree = new SearchTree("songs.txt")
			val emptyTree = new SearchTree()
			val pair = tree.firstEntry()
			pair.getKey().equals("AC/DC") && pair.getValue().contains("Back In Black") && pair.getValue().contains("Live Wire") && pair.getValue().size() == 10 && 
			Option(emptyTree.firstEntry()) == None
		} catch {
			case e: Exception => false
		}
	}

	property("lastEntry") = {
		try {
			val tree = new SearchTree("songs.txt")
			val emptyTree = new SearchTree()
			val pair = tree.lastEntry()
			pair.getKey().equals("Zombies") && pair.getValue().contains("Tell Her No") && pair.getValue().size() == 3 && 
			Option(emptyTree.lastEntry()) == None
		} catch {
			case e: Exception => false
		}
	}

	property("ceilingEntry") = {
		try {
			val tree = new SearchTree("songs.txt")
			val emptyTree = new SearchTree()
			val pair = tree.ceilingEntry("Genesis")
			pair.getKey().equals("Genesis") && pair.getValue().contains("Abacab") && pair.getValue().contains("Follow You Follow Me") && pair.getValue().size() == 7 && 
			pair.equals(tree.ceilingEntry("Gen")) && pair.equals(tree.ceilingEntry("Geneee")) && 
			Option(emptyTree.ceilingEntry("Genesis")) == None
		} catch {
			case e: Exception => false
		}
	}

	property("floorEntry") = {
		try {
			val tree = new SearchTree("songs.txt")
			val emptyTree = new SearchTree()
			val pair = tree.floorEntry("Genesis")
			pair.getKey().equals("Genesis") && pair.getValue().contains("Abacab") && pair.getValue().contains("Follow You Follow Me") && pair.getValue().size() == 7 && 
			pair.equals(tree.floorEntry("Genesiss")) && pair.equals(tree.floorEntry("Genzzz")) &&
			Option(emptyTree.floorEntry("Genesis")) == None
		} catch {
			case e: Exception => false
		}
	}

	property("lowerEntry") = {
		try {
			val tree = new SearchTree("songs.txt")
			val emptyTree = new SearchTree()
			val pair = tree.lowerEntry("Georgia Satellites")
			pair.getKey().equals("Genesis") && pair.getValue().contains("Abacab") && pair.getValue().contains("Follow You Follow Me") && pair.getValue().size() == 7 && 
			pair.equals(tree.lowerEntry("Genesiss")) && pair.equals(tree.lowerEntry("Genzzz")) && 
			Option(emptyTree.lowerEntry("Genesis")) == None
		} catch {
			case e: Exception => false
		}
	}

	property("higherEntry") = {
		try {
			val tree = new SearchTree("songs.txt")
			val emptyTree = new SearchTree()
			val pair = tree.higherEntry("Garcia, Jerry")
			pair.getKey().equals("Genesis") && pair.getValue().contains("Abacab") && pair.getValue().contains("Follow You Follow Me") && pair.getValue().size() == 7 && 
			pair.equals(tree.higherEntry("Gen")) && pair.equals(tree.higherEntry("Geneee")) &&
			Option(emptyTree.higherEntry("Genesis")) == None
		} catch {
			case e: Exception => false
		}
	}

	/* Additional methods */

	property("getOrdered") = {
		try {
			val tree = new SearchTree("songs.txt")
			val emptyTree = new SearchTree()
			val tab = tree.getOrdered("Queen")
			tab.length == 10 && tab(0).equals("Bohemian Rhapsody") && tab(2).equals("Fat Bottomed Girls") && tab(3).equals("Keep Yourself Alive") &&
			tab(9).equals("You're My Best Friend") &&
			emptyTree.getOrdered("Queen").length == 0
		} catch {
			case e: Exception => false
		}
	}

	property("entriesBetween") = {
		try {
			val tree = new SearchTree("songs.txt")
			val emptyTree = new SearchTree()
			val list = tree.entriesBetween("D", "Do")
			val it = list.iterator()
			val pair = it.next()
			val pair2 = it.next()
			list.size() == 6 && pair.getKey().equals("Davis Group, Spencer") && pair.getValue().size() == 1 &&
			pair2.getKey().equals("Deep Purple") && pair2.getValue().size() == 6 && 
			it.next().getKey().equals("Def Leppard") &&
			emptyTree.entriesBetween("D", "Do").size() == 0 && tree.entriesBetween("D", "Da").size() == 0
		} catch {
			case e: Exception => false
		}
	}

	property("toString") = {
		try {
            val tree = new SearchTree("songs.txt")
            val s = tree.toString()
			 val it = s.lines
		 	it.next() == "[AC/DC] Back In Black" && it.next() == "[AC/DC] Dirty Deeds" && it.next() == "[AC/DC] Girls Got Rhythm"	
		} catch {
			case e: Exception => false
		}
	}

	// Must be compiled with -J-Xmx2g -J-Xms2g
	property("get_complexity") = {
		try {
			val tree = new SearchTree()
			for (i <- 0 until 100000) {
				tree.put(i.toString(), mutableSetAsJavaSet(Set(i.toString())))
			}
			val stack = new java.util.Stack[String]()
			for (i <- 0 until 100000) {
				stack.push(i.toString())
			}
			val t1 = System.nanoTime()
			val result = tree.get("99888")
			val t2 = System.nanoTime()
			val last = stack.search("111")
			val t3 = System.nanoTime()
			//println((t2-t1)/1000 + " " + (t3-t2)/1000)
			t3-t2 > (t2-t1)/20 // should be log(n) instead of n, we fix the threshold at n/20
		} catch {
			case e: Exception => false
		}
	}

	// Must be compiled with -J-Xmx2g -J-Xms2g
	property("entriesBetween_complexity") = {
		try {
			val tree = new SearchTree()
			for (i <- 0 until 100000) {
				tree.put(i.toString(), mutableSetAsJavaSet(Set(i.toString())))
			}
			val stack = new java.util.Stack[String]()
			for (i <- 0 until 100000) {
				stack.push(i.toString())
			}
			val t1 = System.nanoTime()
			val result = tree.entriesBetween("99997", "99998")
			val t2 = System.nanoTime()
			val last = stack.search("1")
			val t3 = System.nanoTime()
			//println((t2-t1)/1000 + " " + (t3-t2)/1000)
			t3-t2 > (t2-t1)/10 // should be log(n) instead of n, we fix the threshold at n/10
		} catch {
			case e: Exception => false
		}
	}

}