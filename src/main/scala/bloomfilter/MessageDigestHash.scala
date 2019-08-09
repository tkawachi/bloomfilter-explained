package bloomfilter

import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets
import java.security.MessageDigest

class MessageDigestHash(algorithm: String) {
  private def getDigest(bytes: Array[Byte]): Array[Byte] = {
    val md5 = MessageDigest.getInstance(algorithm)
    md5.digest(bytes)
  }
  private def digestInts(s: String, k: Int): Seq[Int] = {
    val bb = ByteBuffer.wrap(getDigest(s.getBytes(StandardCharsets.UTF_8)))
    Seq.tabulate(k)(n => bb.getInt(n * 4))
  }
  def hashes(k: Int): String => Seq[Int] =
    (s: String) => digestInts(s, k)
}
