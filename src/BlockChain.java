package src;

import java.security.NoSuchAlgorithmException;

public class BlockChain {
  // Fields
  private Node first;
  private Node last;
  private int initialAmount;

  // Constructors
  public BlockChain(int initial) throws NoSuchAlgorithmException {
    this.first = new Node(new Block(0, initial, null));
    this.last = this.first;
    this.initialAmount = initial;
  }

  // Methods

  /**
   * Returns a block with valid hash from the input amount of transaction
   * 
   * @param int, amount
   * 
   * @return Block, a newly created block
   * 
   * @throws NoSuchAlgorithmException
   */
  public Block mine(int amount) throws NoSuchAlgorithmException {
    return new Block(last.block.getNum() + 1, amount, last.block.getHash());
  }

  /**
   * Returns the number of blocks in a block chain
   * 
   * @return int, size of block chain
   */
  public int getSize() {
    return last.block.getNum() + 1;
  }

  /**
   * Checks if a given block is valid (valid block number, valid previous hash, and has a hash of
   * itself). If so, we append the block to the end of the chain. If not valid, we deny transaction.
   * 
   * @param Block, blk
   */
  public void append(Block blk) {
    if (blk.getHash() == null || !blk.getPrevHash().equals(this.last.block.getHash())
        || (blk.getNum() != this.last.block.getNum() + 1)) {
      System.err.println("Invalid block, transaction is denied");;
    } else {
      Node newNode = new Node(blk);

      this.last.nextNode = newNode;
      this.last = this.last.nextNode;
    }
  }

  /**
   * Removes the last block in a block chain.
   * 
   * @return boolean, false if the block chain only has one element, true if the size of the block
   *         chain is greater than 1.
   */
  public boolean removeLast() {
    if (this.getSize() == 1) {
      return false;
    } else {
      Node temp = this.first;

      // get the block before the last block, store that in temp
      while (temp.block.getNum() != this.getSize() - 2) {
        temp = temp.nextNode;
      }

      // remove the last block
      this.last = temp;
      this.last.nextNode = null;

      return true;
    }
  }

  /**
   * Returns the hash of the last block in the block chain
   * 
   * @return Hash, last block's hash
   */
  public Hash getHash() {
    return this.last.block.getHash();
  }

  /**
   * Checks if the block chain is valid (meaning tracing aliceBalance so check that at anytime 0 <=
   * aliceBalance <= initialAmount), the number of each block is incrementing 1 by 1 from first to
   * last, and the prevHash of current block matches the hash of the previous block
   * 
   * @return boolean, True if the above conditions hold, false otherwise
   */
  public boolean isValidBlockChain() {
    int count = 0;
    Node temp = this.first;
    int aliceBalance = 0;

    // go through the linked list till the final block
    while (count < this.getSize()) {

      // in case this is the last block
      if (count == this.getSize() - 1) {
        if (this.last.block.getNum() != this.getSize() - 1) {
          return false;
        }
        // other normal block inside the blockchain
      } else if (!temp.block.getHash().equals(temp.nextNode.block.getPrevHash())
          || count != temp.block.getNum()) {
        return false;
      } // else if

      // check if the transaction amount is reasonable.
      aliceBalance += temp.block.getAmount();
      if (aliceBalance < 0 || aliceBalance > this.initialAmount) {
        return false;
      }

      temp = temp.nextNode;
      count++;
    }
    return true;
  }

  /**
   * Prints the current account balance for Alice and Bob
   */
  public void printBalances() {
    int aliceBalance = this.first.block.getAmount();
    int bobBalance = 0;
    Node temp = this.first.nextNode;

    // calculate the balance of Alice and Bob
    while (temp != null) {
      aliceBalance += temp.block.getAmount();
      bobBalance -= temp.block.getAmount();

      temp = temp.nextNode;
    }

    java.io.PrintWriter pen = new java.io.PrintWriter(System.out, true);
    pen.println("Alice: " + aliceBalance + ", Bob:" + bobBalance);
  }

  /**
   * Return the string format for all the blocks in the chain, using the following format for each
   * block (on its own line): Block <num>(Amount: <amt>, Nonce: <nonce>, prevHash: <prevHash>, hash:
   * <hash>)
   * 
   * @return String, string format of block chain
   */
  public String toString() {
    String ret = new String();

    Node temp = this.first;
    while (temp != null) {
      ret += ("\n" + temp.block.toString());
      temp = temp.nextNode;
    }
    return ret;
  }
}


// Class Node
// To hold block, as well as reference to next block in the chain
class Node {
  // fields
  Block block;
  Node nextNode;

  // Constructor
  public Node(Block block) {
    this.block = block;
    this.nextNode = null;
  }
}
