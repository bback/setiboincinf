package boincinf;

/*
 * Table example snippet: sort a table by column
 *
 * For a list of all SWT example snippets see
 * http://dev.eclipse.org/viewcvs/index.cgi/%7Echeckout%7E/platform-swt-home/dev.html#snippets
 */
import java.text.*;
import java.util.*;

import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet2 {

public static void main (final String [] args) {
    final Display display = new Display ();
    final Shell shell = new Shell (display);
    shell.setLayout(new FillLayout());
    final Table table = new Table(shell, SWT.BORDER);
    table.setHeaderVisible(true);
    final TableColumn column1 = new TableColumn(table, SWT.NONE);
    column1.setText("Column 1");
    final TableColumn column2 = new TableColumn(table, SWT.NONE);
    column2.setText("Column 2");
    TableItem item = new TableItem(table, SWT.NONE);
    item.setText(new String[] {"a", "3"});
    item = new TableItem(table, SWT.NONE);
    item.setText(new String[] {"b", "2"});
    item = new TableItem(table, SWT.NONE);
    item.setText(new String[] {"c", "1"});
    column1.pack();
    column2.pack();
    column1.addListener(SWT.Selection, new Listener() {
        public void handleEvent(final Event e) {
            // sort column 1
            final TableItem[] items = table.getItems();
            final Collator collator = Collator.getInstance(Locale.getDefault());
            for (int i = 1; i < items.length; i++) {
                final String value1 = items[i].getText(0);
                for (int j = 0; j < i; j++){
                    final String value2 = items[j].getText(0);
                    if (collator.compare(value1, value2) < 0) {
                        final String[] values = {items[i].getText(0), items[i].getText(1)};
                        items[i].dispose();
                        final TableItem item = new TableItem(table, SWT.NONE, j);
                        item.setText(values);
                        break;
                    }
                }
            }
        }
    });
    column2.addListener(SWT.Selection, new Listener() {
        public void handleEvent(final Event e) {
            // sort column 2
            final TableItem[] items = table.getItems();
            final Collator collator = Collator.getInstance(Locale.getDefault());
            for (int i = 1; i < items.length; i++) {
                final String value1 = items[i].getText(1);
                for (int j = 0; j < i; j++){
                    final String value2 = items[j].getText(1);
                    if (collator.compare(value1, value2) < 0) {
                        final String[] values = {items[i].getText(0), items[i].getText(1)};
                        items[i].dispose();
                        final TableItem item = new TableItem(table, SWT.NONE, j);
                        item.setText(values);
                        break;
                    }
                }
            }
        }
    });
    shell.open ();
    while (!shell.isDisposed ()) {
        if (!display.readAndDispatch ()) {
            display.sleep ();
        }
    }
    display.dispose ();
}

}

