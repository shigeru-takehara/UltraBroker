package ultrabroker.util;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.ArrayList;

public class DebugUtil {
  private List<String> lines;
  private String FileName;

  public DebugUtil(String fileName) {
    this.FileName = fileName;
    this.lines = new ArrayList<String>();
  }

  public void addLine(String line) {
    lines.add(line);
  }

  public void WriteToFile() throws Exception {
    FileWriter fileWriter = new FileWriter(FileName);
    PrintWriter printWriter = new PrintWriter(fileWriter);
    for (int i = 0; i < lines.size(); i++) {
      printWriter.println(lines.get(i));
    }
    printWriter.close();
  }

  public void WriteToFile(String line) throws Exception {
    this.addLine(line);
    this.WriteToFile();
  }
}
