package ultrabroker.service.support;

import java.util.ArrayList;
import java.util.List;

public class WorkerInfo {
  public static final String PORT_NUMBER_PARAM = "{PORT_NUMBER}";

  private String id;
  private List<String> commandStringList;
  private String workingDirectory;
  private boolean portNumberExistInCommandLine;

  public String getId() {
    if (id == null)
      id = "";
    return id;
  }

  public void setId(String iD) {
    id = iD;
  }

  public boolean portNumberExistInCommandLine() {
    return this.portNumberExistInCommandLine;
  }

  protected void setPortNumberExistInCommandLine(boolean val) {
    this.portNumberExistInCommandLine = val;
  }

  public List<String> getCommandStringList() {
    return commandStringList;
  }

  public List<String> getCommandStringListWithPortNumber(int port) {
    List<String> result = new ArrayList<>();
    for (int i = 0; i < commandStringList.size(); i++) {
      if (commandStringList.get(i).indexOf(PORT_NUMBER_PARAM) > -1) {
        result.add(commandStringList.get(i).replace(PORT_NUMBER_PARAM, Integer.toString(port)));
      } else {
        result.add(this.commandStringList.get(i));
      }
    }
    return result;
  }

  public void setCommandStringList(String... strings) {
    List<String> list = new ArrayList<String>();
    for (String val : strings) {
      if (val == null)
        continue;
      if (val.indexOf(PORT_NUMBER_PARAM) > -1) {
        this.setPortNumberExistInCommandLine(true);
      }
      list.add(val);
    }
    if (!this.portNumberExistInCommandLine) {
      list.add(PORT_NUMBER_PARAM);
    }
    this.setCommandStringList(list);
  }

  public void setCommandStringList(List<String> commandStringList) {
    this.commandStringList = commandStringList;
  }

  public String getWorkingDirectory() {
    if (workingDirectory == null)
      workingDirectory = "";
    return workingDirectory;
  }

  public void setWorkingDirectory(String workingDirectory) {
    this.workingDirectory = workingDirectory;
  }

  public boolean validate() {
    boolean result = false;
    if (getId().length() > 0 && getCommandStringList().size() > 0 && getWorkingDirectory().length() > 0) {
      result = true;
    }
    return result;
  }

  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append("Id:" + this.getId());
    for (int i = 0; i < commandStringList.size(); i++) {
      sb.append(", CommandLine" + (i + 1) + ":" + commandStringList.get(i));
    }
    sb.append(", WorkingDirectory:" + this.workingDirectory);
    return sb.toString();

  }
}