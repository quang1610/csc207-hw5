import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Block {
  private int num;
  private int amount;
  private Hash prevHash;
  private long nonce;
  private Hash hash;
  
  public Block(int num, int amount, Hash prevHash) throws NoSuchAlgorithmException {
    this.num = num;
    this.amount = amount;
    this.prevHash = prevHash;
    
    MessageDigest md = MessageDigest.getInstance("sha-256");
    if (num == 0) {
      this.nonce = -1;
      do {
        this.nonce++;
        md.update(ByteBuffer.allocate(4).putInt(this.num).array()); 
        md.update(ByteBuffer.allocate(4).putInt(this.amount).array());
        md.update(ByteBuffer.allocate(8).putLong(this.nonce).array());
        this.hash = new Hash(md.digest());
      } while (!this.hash.isValid());
    } else {
      this.nonce = -1;
      do {
        this.nonce++;
        md.update(ByteBuffer.allocate(4).putInt(this.num).array()); 
        md.update(ByteBuffer.allocate(4).putInt(this.amount).array());
        md.update(this.prevHash.getData());
        md.update(ByteBuffer.allocate(8).putLong(this.nonce).array());
        this.hash = new Hash(md.digest());
      } while (!this.hash.isValid());
    }
  }
  
  public Block(int num, int amount, Hash prevHash, long nonce) throws NoSuchAlgorithmException, Exception {
    this.num = num;
    this.amount = amount;
    this.prevHash = prevHash;
    this.nonce = nonce;
    
    MessageDigest md = MessageDigest.getInstance("sha-256");
    md.update(ByteBuffer.allocate(4).putInt(this.num).array()); 
    md.update(ByteBuffer.allocate(4).putInt(this.amount).array());
    md.update(this.prevHash.getData());
    md.update(ByteBuffer.allocate(8).putLong(this.nonce).array());
    this.hash = new Hash(md.digest());
    if(!this.hash.isValid()) {
      throw new Exception("Invalid hash code: " + this.toString()); 
    }
  }
}
