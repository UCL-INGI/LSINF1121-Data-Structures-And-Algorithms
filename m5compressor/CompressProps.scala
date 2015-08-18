import org.scalacheck.Properties
import org.scalacheck.Prop
import org.scalacheck.Gen.{listOf, alphaStr, numChar, oneOf, choose}
import java.util.HashMap
import org.scalacheck.Gen
import org.scalacheck.Arbitrary.arbitrary
import scala.tools.nsc.io.File
import scala.io.Source.fromFile

object CompressProps extends Properties("Compress") {

	property("basic_file_equality") = {
		try {
			val s = compress_decompress("hello world")
			s == "hello world\n"

		} catch {
			case e: Exception => false
		}
	}

	property("random_files_equality") = Prop.forAll { (el: String) =>
		try {
			val s = compress_decompress(el)
			s == el || s == el + "\n"

		} catch {
			case e: Exception => false
		}
	}

	val strGen = Gen.listOfN(1000, Gen.alphaChar).map(_.mkString)

	property("long_files_equality") = Prop.forAll(strGen) { str =>
		try {
			val s = compress_decompress(str)
			s == str + "\n"

		} catch {
			case e: Exception => false
		}
	}

	property("compression_length") = {
		try {
			var str = ""
			for (i <- 1 to 10000)
				str += "a"
			val s = compress_decompress(str)

			val compressed = new java.io.File("./data/compressed.txt")
			val fileSize = compressed.length
			//println(fileSize)
			//println((new java.io.File("./data/input.txt")).length)
			s == str + "\n" && fileSize < 1350 // length of my solution is 1313

		} catch {
			case e: Exception => false
		}
	}

	property("compression_length_2") = {
		try {
			var str = ""
			for (i <- 1 to 10000)
				str += "01"
			val s = compress_decompress(str)

			val compressed = new java.io.File("./data/compressed.txt")
			val fileSize = compressed.length
			//println(fileSize)
			//println((new java.io.File("./data/input.txt")).length)
			s == str + "\n" && fileSize < 3900 // length of my solution is 3853

		} catch {
			case e: Exception => false
		}
	}

	def compress_decompress(content: String): String = {

		File("./input.txt").writeAll(content)
		Compress.main(Array("./input.txt", "./compressed.txt"))
		Decompress.main(Array("./compressed.txt", "./output.txt"))
		val result = fromFile("./output.txt").mkString

		result
	}
}