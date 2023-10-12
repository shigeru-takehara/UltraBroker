package ultrabroker.net;

import java.util.ArrayList;

public class Message extends ArrayList<String> {
  
  public String toString() {
    StringBuffer result = new StringBuffer();
    for(int i=0; i<this.size(); i++) {
      result.append(this.get(i));
      if (i < this.size() - 1) {
        result.append("\n");
      }
    }
    return result.toString();
  }
  
}
