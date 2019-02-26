package src;

import java.security.NoSuchAlgorithmException;

public class BlockChain {
  //Fields
  private Node first;
  private Node last;
  private int initialAmount;
  private int aliceBalance;

  //Constructors
  public BlockChain(int initial) throws NoSuchAlgorithmException {
    this.first = new Node(new Block(0, initial, null));
    this.initialAmount = initial;
    this.aliceBalance = initial;
    this.last = this.first;
  }

  //Methods
  public Block mine(int amount) throws NoSuchAlgorithmException {
    return new Block(last.block.getNum() + 1, amount, last.block.getHash());
  }

  public int getSize() {
    return last.block.getNum() + 1;
  }

  //need to check the balance
  public void append(Block blk) {
    
    if (!blk.getPrevHash().equals(this.last.block.getHash())
        || (blk.getNum() != this.last.block.getNum() + 1)
        || blk.getAmount() + this.aliceBalance > this.initialAmount 
        || blk.getAmount() + this.aliceBalance < 0) {
      System.err.println("Invalid block, transaction is denied");;
    } else {
      Node newNode = new Node(blk);

      this.last.nextNode = newNode;
      this.last = this.last.nextNode;
      //temp
      this.aliceBalance += this.last.block.getAmount();
    }
  }

  public boolean removeLast() {
    if (this.getSize() == 1) {
      return false;
    } else {
      Node temp = this.first;

      while (temp.block.getNum() != this.getSize() - 2) {
        temp = temp.nextNode;
      }
      this.aliceBalance -= this.last.block.getAmount();
      this.last = temp;
      this.last.nextNode = null;
      
      return true;
    }
  }

  public Hash getHash() {
    return this.last.block.getHash();
  }

  //need to check the balance
  public boolean isValidBlockChain() {
    int count = 0;
    Node temp = this.first;
    
    if(this.aliceBalance > this.initialAmount || this.aliceBalance < 0) {
      return false;
    }
    
    while (count < this.getSize()) {
      //in case this is the last block
      if (count == this.getSize() - 1) {
        if (this.last.block.getNum() != this.getSize() - 1) {
          return false;
        }
        //other normal block inside the blockchain
      } else if (!temp.block.getHash().equals(temp.nextNode.block.getPrevHash())
          || count != temp.block.getNum()) {
        return false;
      }
      count++;
    }
    return true;
  }
  
  public void printBalances() {
    java.io.PrintWriter pen = new java.io.PrintWriter(System.out, true);
    pen.println("Alice: " + this.aliceBalance + ", Bob:" + (this.initialAmount - this.aliceBalance));
  }
  
  public String toString() {
    String ret = new String();
    
    Node temp = this.first;
    while(temp != null) {
      ret += ("\n" + temp.block.toString());
      temp = temp.nextNode;
    }
    return ret;
  }
}


class Node {
  Block block;
  Node nextNode;

  public Node(Block block) {
    this.block = block;
    this.nextNode = null;
  }
}
