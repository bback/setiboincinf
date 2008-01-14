package boincinf.gui;

/**
 * @author kgraul
 */
public class CreditUpdateData {
    
    public long hostId;
    public String hostName;
    public int daysSinceLastUpdate;
    
    public String getValueAt(int column_index) {
        if (column_index == 0) {
            return ""+hostId;
        }
        if (column_index == 1) {
            return hostName;
        }
        if (column_index == 2) {
            return ""+daysSinceLastUpdate;
        }
        return "*err*";
    }

}
