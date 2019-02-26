package src;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Block {
  // Fields
  private int num;
  private int amount;
  private Hash prevHash;
  private long nonce;
  private Hash hash;


  // Constructor
  public Block(int num, int amount, Hash prevHash) throws NoSuchAlgorithmException {
    this.num = num;
    this.amount = amount;
    this.prevHash = prevHash;

    MessageDigest md = MessageDigest.getInstance("sha-256");
    this.nonce = -1;
    if (this.prevHash == null) {
      do {
        this.nonce++;
        //update
        md.update(ByteBuffer.allocate(4).putInt(this.num).array());
        md.update(ByteBuffer.allocate(4).putInt(this.amount).array());
        md.update(ByteBuffer.allocate(8).putLong(this.nonce).array());
        //digest
        this.hash = new Hash(md.digest());
      } while (!this.hash.isValid());
    } else { //if ... else 
      do {
        this.nonce++;
        //update
        md.update(ByteBuffer.allocate(4).putInt(this.num).array());
        md.update(ByteBuffer.allocate(4).putInt(this.amount).array());
        md.update(this.prevHash.getData());
        md.update(ByteBuffer.allocate(8).putLong(this.nonce).array());
        //digest
        this.hash = new Hash(md.digest());
      } while (!this.hash.isValid());
    } //else 
  }

  public Block(int num, int amount, Hash prevHash, long nonce)
      throws NoSuchAlgorithmException, Exception {
    this.num = num;
    this.amount = amount;
    this.prevHash = prevHash;
    this.nonce = nonce;

    MessageDigest md = MessageDigest.getInstance("sha-256");
    //update
    md.update(ByteBuffer.allocate(4).putInt(this.num).array());
    md.update(ByteBuffer.allocate(4).putInt(this.amount).array());
    md.update(this.prevHash.getData());
    md.update(ByteBuffer.allocate(8).putLong(this.nonce).array());
    //digest
    Hash temp = new Hash(md.digest());
    if (!temp.isValid()) {
      System.err.println("Invalid hash code: " + temp.toString());
    } else {
      this.hash = temp;
    }
  }

  // Methods
  public int getNum() {
    return this.num;
  }

  public int getAmount() {
    return this.amount;
  }

  public long getNonce() {
    return this.nonce;
  }

  public Hash getPrevHash() {
    return this.prevHash;
  }

  public Hash getHash() {
    return this.hash;
  }

  public String toString() {
    if(this.prevHash == null) {
      return "Block " + this.num + "(Amount: " + this.amount + ", Nonce: " + this.nonce
          + ", prevHash: " + "null" + ", hash: " + this.hash.toString() + ")";
    } else {
      return "Block " + this.num + "(Amount: " + this.amount + ", Nonce: " + this.nonce
          + ", prevHash: " + this.prevHash.toString() + ", hash: " + this.hash.toString() + ")";
    }
    
  }

}
