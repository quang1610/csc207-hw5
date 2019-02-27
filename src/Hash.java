package src;

import java.lang.Byte;
import java.util.Arrays;

public class Hash {
  // Fields
  private byte[] data;

  // Constructor
  public Hash(byte[] data) {
    this.data = data;
  }

  // Methods
  /**
   * return the actual hash code as an array of byte
   * 
   * @return byte[], this.data
   */
  public byte[] getData() {
    return this.data;
  }

  /**
   * check if the hash code is valid (the first 3 bytes are 0)
   * 
   * @return true if this.data is valid; false if this.data is not valid
   */
  public boolean isValid() {
    return (this.data[0] == 0 && this.data[1] == 0 && this.data[2] == 0);
  }

  /**
   * return a hexadecimal format of the this.data byte array
   * 
   * @return String, hexadecimal format for this.data
   */
  public String toString() {
    String hashString = new String();
    int temp;

    for (int i = 0; i < this.data.length; i++) {
      temp = Byte.toUnsignedInt(this.data[i]);
      hashString += String.format("%02x", temp);
    }
    return hashString;
  }

  /**
   * check if this Hash is equal to another Hash
   * 
   * @param Object, other.
   * 
   * @return True if other is an instance of Hash, and this.data = other.data, False otherwise
   */
  public boolean equals(Object other) {
    return (other instanceof Hash) && (Arrays.equals(((Hash) other).getData(), this.data));
  }

}
