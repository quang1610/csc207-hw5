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
    // if we have preHash...
    if (this.prevHash == null) {
      do {
        this.nonce++;
        // update
        md.update(ByteBuffer.allocate(4).putInt(this.num).array());
        md.update(ByteBuffer.allocate(4).putInt(this.amount).array());
        md.update(ByteBuffer.allocate(8).putLong(this.nonce).array());
        // digest
        this.hash = new Hash(md.digest());
      } while (!this.hash.isValid());
    } else { // if ... else
      // if we don't have preHash...
      do {
        this.nonce++;
        // update
        md.update(ByteBuffer.allocate(4).putInt(this.num).array());
        md.update(ByteBuffer.allocate(4).putInt(this.amount).array());
        md.update(this.prevHash.getData());
        md.update(ByteBuffer.allocate(8).putLong(this.nonce).array());
        // digest
        this.hash = new Hash(md.digest());
      } while (!this.hash.isValid());
    } // else
  }// constructor

  public Block(int num, int amount, Hash prevHash, long nonce)
      throws NoSuchAlgorithmException, Exception {
    this.num = num;
    this.amount = amount;
    this.prevHash = prevHash;
    this.nonce = nonce;

    MessageDigest md = MessageDigest.getInstance("sha-256");
    // update
    md.update(ByteBuffer.allocate(4).putInt(this.num).array());
    md.update(ByteBuffer.allocate(4).putInt(this.amount).array());
    md.update(this.prevHash.getData());
    md.update(ByteBuffer.allocate(8).putLong(this.nonce).array());
    // digest
    Hash temp = new Hash(md.digest());
    // check if the newly made Hash is valid
    if (!temp.isValid()) {
      System.err.println("Invalid hash code: " + temp.toString());
    } else {
      this.hash = temp;
    } // if !temp.isValid
  }

  // Methods

  /**
   * Returns the position of this block in the block chain
   * 
   * @return int, this.num
   */
  public int getNum() {
    return this.num;
  }

  /**
   * Returns the amount in a block
   * 
   * @return int, this.amount
   */
  public int getAmount() {
    return this.amount;
  }

  /**
   * Returns the nonce in a block
   * 
   * @return long, this.nonce
   */
  public long getNonce() {
    return this.nonce;
  }

  /**
   * Returns the hash from the previous block in the block chain
   * 
   * @return Hash, this.prevHash
   */
  public Hash getPrevHash() {
    return this.prevHash;
  }

  /**
   * Returns the hash from a block
   * 
   * @return Hash, this.hash
   */
  public Hash getHash() {
    return this.hash;
  }

  /**
   * Returns a string that includes the blocks number, the amount, the nonce, the hexadecimal
   * representation of the previous hash and this hash. In the following format: Block <num>(Amount:
   * <amt>, Nonce: <nonce>, prevHash: <prevHash>, hash: <hash>)
   * 
   * @return String, formatted string with all information in a block
   */
  public String toString() {
    // only return toString when the block has a Hash of itself.
    if (this.hash != null) {
      // if this is the first block in the blockchain, it does not have prevHash
      if (this.prevHash == null) {
        return "Block " + this.num + "(Amount: " + this.amount + ", Nonce: " + this.nonce
            + ", prevHash: " + "null" + ", hash: " + this.hash.toString() + ")";
      } else {
        // else...
        return "Block " + this.num + "(Amount: " + this.amount + ", Nonce: " + this.nonce
            + ", prevHash: " + this.prevHash.toString() + ", hash: " + this.hash.toString() + ")";
      }
    } else {
      return null;
    }
  }

}
