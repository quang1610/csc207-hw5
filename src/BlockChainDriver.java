package src;

import java.io.PrintWriter;
import java.util.Scanner;
import java.security.NoSuchAlgorithmException;

// the main class that holds the main function
public class BlockChainDriver {
  // Fields
  private static BlockChain chain;
  private static PrintWriter pen;
  private static Scanner scanner;

  // Methods

  /**
   * the main function that run the program. It will print out the entire block chain and ask the
   * user for command. If the command is not valid, the program will ask the user to re-input. The
   * program will terminate when the user enters "quit"
   * 
   * @param String[], args. Expected length to be 1. args[0] is the initial amount of the entire
   *        system. it should be able to convert into an integer.
   * 
   * @throws Exception
   */
  public static void main(String[] args) throws Exception {
    // if the length of args is not 1, throw an error...
    if (args.length != 1) {
      throw new Exception("invalid number of arguments");
    } else {
      // parse the integer and store in initial.
      int initial = Integer.parseInt(args[0]);

      // if the initial is invalid (initial <= 0), throws error.
      if (initial <= 0) {
        throw new Exception("Invalid initial amount");
      } else {
        // init the static fields...
        pen = new PrintWriter(System.out, true);
        scanner = new Scanner(System.in);
        chain = new BlockChain(initial);

        // print the chain for the first time
        printChain();
        // start taking user's input
        String cmd = scanner.next();

        // depend on the input cmd, choose the appropriate operation...
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

        // clean up
        pen.flush();
        scanner.close();
        pen.println("Terminate successfully!");
      }
    }
  }

  /**
   * check if the chain is valid and print out the appropriate message.
   */
  private static void checking() {
    if (chain.isValidBlockChain()) {
      pen.println("Chain is Valid!");
    } else {
      pen.println("Chain is INVALID!");
    }
  }

  /**
   * appending: ask user for amount and nonce, then create a new block with the given information
   * and append to the block chain. If the block is not valid (mismatch preHash, incorrect num,
   * does not have a hash of itself), the transaction is denied.
   * 
   * @throws Exception
   * @throws NoSuchAlgorithmException
   */
  private static void appending() throws Exception, NoSuchAlgorithmException {
    pen.println("Amount transferred? ");
    int amount = scanner.nextInt();
    pen.println("Nonce? ");
    int nonce = scanner.nextInt();

    chain.append(new Block(chain.getSize(), amount, chain.getHash(), nonce));
  }

  /**
   * mining: ask user for the transaction amount, then find the valid nonce for the next block to
   * add to the chain. Once the nonce is found, print it to the screen. This method does not add the
   * new block to the chain.
   * 
   * @throws NoSuchAlgorithmException
   */
  private static void mining() throws NoSuchAlgorithmException {
    pen.println("Amount transferred? ");
    int amount = scanner.nextInt();

    pen.println("amount = " + amount + ", nonce: " + chain.mine(amount).getNonce());
  }

  /**
   * print out the list of valid commands.
   */
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

  /**
   * print out the current block chain.
   */
  private static void printChain() {
    pen.println("\n" + "***********************************************************************");
    pen.println(chain.toString());
    pen.println("\n" + "***********************************************************************");
    pen.println("Command? ");
  }
}
