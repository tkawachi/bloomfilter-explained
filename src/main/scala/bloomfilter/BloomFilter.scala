package bloomfilter

import scala.collection.BitSet

class BloomFilter[A](hashes: A => Seq[Int]) {
  private var bitSet = BitSet.empty
  def add(element: A): Unit = {
    val hashedValues = hashes(element)
    println("-----")
    println(s"Adding $element")
    println(s"hashedValues $hashedValues")
    bitSet = bitSet | hashedValues.toSet
    println(bitSet)
  }

  def contains(element: A): Boolean = {
    val hashedValues = hashes(element)
    println("-----")
    println(s"Look up $element")
    println(s"hashedValues $hashedValues")
    println(bitSet)
    val result = (bitSet & hashedValues.toSet) == hashedValues.toSet
    println(if (result) s"$element exists" else s"$element doesn't exists")
    result
  }
  override def toString: String = bitSet.toString()
}

object BloomFilter {
  def main(args: Array[String]): Unit = {
    val hashes1 = new MessageDigestHash("MD5").hashes(10, 3)
    val filter1 = new BloomFilter[String](hashes1)
    filter1.add("abc")
    filter1.add("Hello, World!")
    filter1.contains("abc")
    filter1.contains("abd")
    filter1.contains("abe") // -> 偽陽性
  }
}
