package src;

import java.io.PrintWriter;
import java.util.Scanner;
import java.security.NoSuchAlgorithmException;
import java.io.*;

public class BlockChainDriver {
  private static BlockChain chain;
  private static PrintWriter pen;
  private static Scanner scanner;
  
  public static void main(String[] args) throws Exception {
    if (args.length != 1) {
      throw new Exception("invalid number of arguments");
    } else {

      int initial = Integer.parseInt(args[0]);

      if (initial <= 0) {
        throw new Exception("Invalid initial amount");
      } else {
        pen = new PrintWriter(System.out, true);
        scanner = new Scanner(System.in);
        chain = new BlockChain(initial);

        // print the chain for the first time
        printChain();
        // start taking user's input
        String cmd = scanner.next();
        while (!cmd.equals("quit")) {
          switch (cmd) {
            case "help":
              printCmdList();
              printChain();
              cmd = scanner.next();
              break;
            case "mine":
              mining();
              printChain();
              cmd = scanner.next();
              break;
            case "append":
              appending();
              printChain();
              cmd = scanner.next();
              break;
            case "remove":
              chain.removeLast();
              printChain();
              cmd = scanner.next();
              break;
            case "check":
              checking();
              printChain();
              cmd = scanner.next();
              break;
            case "report":
              chain.printBalances();
              printChain();
              cmd = scanner.next();
              break;
            case "quit":
              pen.println("Terminating!...");
              break;
            default:
              pen.println("Invalid command, please input again: ");
              cmd = scanner.next();
              break;
          } // switch
        } // while
        pen.flush();
        scanner.close();
        pen.println("Terminate successfully!");
      }
    }
  }

  private static void checking() {
    if (chain.isValidBlockChain()) {
      pen.println("Chain is valid!");
    } else {
      pen.println("Chain is invalid");
    }
  }

  private static void appending() throws Exception, NoSuchAlgorithmException {
    pen.println("Amount transferred? ");
    int amount = scanner.nextInt();
    pen.println("Nonce? ");
    int nonce = scanner.nextInt();

    chain.append(new Block(chain.getSize(), amount, chain.getHash(), nonce));
  }

  private static void mining() throws NoSuchAlgorithmException {
    pen.println("Amount transferred? ");
    int amount = scanner.nextInt();

    pen.println("amount = " + amount + ", nonce: " + chain.mine(amount).getNonce());
  }

  private static void printCmdList() {
    pen.println("Valid commands:");
    pen.println("   mine: discovers the nonce for a given transaction");
    pen.println("   append: appends a new block onto the end of the chain");
    pen.println("   remove: removes the last block from the end of the chain");
    pen.println("   check: checks that the block chain is valid");
    pen.println("   report: reports the balances of Alice and Bob");
    pen.println("   help: prints this list of commands");
    pen.println("   quit: quits the program");
  }

  private static void printChain() {
    pen.println("\n" + "***********************************************************************");
    pen.println(chain.toString());
    pen.println("\n" + "***********************************************************************");
    pen.println("Command? ");
  }
}
