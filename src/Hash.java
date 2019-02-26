package src;

import java.lang.Byte;
import java.util.Arrays;

public class Hash {
  private byte[] data;

  public Hash(byte[] data) {
    this.data = data;
  }

  public byte[] getData() {
    return this.data;
  }

  public boolean isValid() {
    return (this.data[0] == 0 && this.data[1] == 0 && this.data[2] == 0);
  }

  public String toString() {
    String hashString = new String();
    int temp;
   
    for (int i = 0; i < this.data.length; i++) {
      temp = Byte.toUnsignedInt(this.data[i]);
      hashString += String.format("%02x", temp);
    }
    return hashString;
  }
  
  
  public boolean equals(Object other) {
    return (other instanceof Hash) && (Arrays.equals(((Hash) other).getData(), this.data));
  }
  
}
