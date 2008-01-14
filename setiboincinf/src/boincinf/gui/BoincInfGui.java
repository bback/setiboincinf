/*
  Copyright (C) 2008  SetiBoincInf Project

  This program is free software; you can redistribute it and/or
  modify it under the terms of the GNU General Public License as
  published by the Free Software Foundation; either version 2 of
  the License, or (at your option) any later version.

  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
  General Public License for more details.

  You should have received a copy of the GNU General Public License
  along with this program; if not, write to the Free Software
  Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
*/
package boincinf.gui;

import java.util.*;
import java.util.List;

import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.jface.window.*;
import org.eclipse.swt.*;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

import boincinf.*;
import boincinf.gui.actions.*;
import boincinf.netstat.*;

public class BoincInfGui extends ApplicationWindow {

    private TableViewer hostStatsTableViewer;
    private TableViewer singleHostStatsTableViewer;
    private final ExitAction exit_action;
    private final ConfigAction config_action;
    private final AboutAction about_action;
    private final UpdateWebAction update_web_action;
    private final UpdateDBAction update_db_action;
    private final DeleteHostStatAction deleterow_action;
    private final ImportDBAction import_action;
    private final ExportDBAction export_action;
//    private PreparedStatement listSingleHosts = null;
    private final CreditUpdateOverviewAction creditUpdateOverviewAction;

    private SQLStorage sqlstore = null;

    private Label labAvg14;
    private Label labAvg30;
    private Label labAvgAll;

    private Label labAvg14_grow;
    private Label labAvg30_grow;
    private Label labAvgAll_grow;

    private SashForm sash_form;
    private SashForm sash_form0;
    private SashForm sash_form2;

    Canvas canvas;

    Timer timer;
    TimerUpdateDBWorker timerUpdateDBWorker = null;
    TimerUpdateWebWorker timerUpdateWebWorker = null;

    public BoincInfGui() {
        super(null);

        final SQLStorage store = getStore();
        if( store == null ) {
            throw new RuntimeException("Error on init time, startup cancelled.");
        }

        exit_action = new ExitAction(this, store);
        deleterow_action = new DeleteHostStatAction(this, store);
        config_action = new ConfigAction(this);
        about_action = new AboutAction(this);
        update_web_action = new UpdateWebAction(this, store);
        update_db_action = new UpdateDBAction(this, store);
        import_action = new ImportDBAction(this, store);
        export_action = new ExportDBAction(this, store);
        creditUpdateOverviewAction = new CreditUpdateOverviewAction(this, store);

        addStatusLine();
        addMenuBar();
        //    addToolBar(SWT.FLAT | SWT.WRAP);

        timer = new Timer();
        setupTimers();

        new Thread() { @Override
        public void run() { doStartupUpdates(true); } }.start();

        setBlockOnOpen(true);
        open();
        Display.getCurrent().dispose();
    }

    protected void doStartupUpdates(final boolean async) {
        if( BoincNetStats.getCfg().getBooleanProperty("auto_db_update_enabled") ) {
            try {
                autoDatabaseUpdate(async);
            } catch(final Exception ex) {}
        }
        if( BoincNetStats.getCfg().getBooleanProperty("auto_web_update_enabled") ) {
            try {
                autoWebUpdate(async);
            } catch(final Exception ex) {}
        }
    }

    /**
     * Sets up the timers according to configuration.
     */
    public void setupTimers() {
        setupDatabaseUpdateTimer();
        setupWebUpdateTimer();
    }

    private void setupDatabaseUpdateTimer() {
        if( BoincNetStats.getCfg().getBooleanProperty("auto_db_update_enabled") ) {
            if( timerUpdateDBWorker != null ) {
                return; // already running
            }
            timerUpdateDBWorker = new TimerUpdateDBWorker();
            // schedule timer
            final long timerMinutes = BoincNetStats.getCfg().getIntProperty("auto_db_update_minutes");
            final long timerDelay = timerMinutes * 60 * 1000;
            timer.schedule(timerUpdateDBWorker, timerDelay, timerDelay);
        } else {
            if( timerUpdateDBWorker != null ) {
                timerUpdateDBWorker.cancel();
                timerUpdateDBWorker = null;
            }
        }
    }

    private void setupWebUpdateTimer() {
        if( BoincNetStats.getCfg().getBooleanProperty("auto_web_update_enabled") ) {
            if( timerUpdateWebWorker != null ) {
                return; // already running
            }
            timerUpdateWebWorker = new TimerUpdateWebWorker();
            // schedule timer
            long timerMinutes = BoincNetStats.getCfg().getIntProperty("auto_web_update_minutes");
            if( timerMinutes < 30 ) {
                BoincNetStats.out("Warning: minimum of 30 minutes for web update was enforced.");
                timerMinutes = 30;
            }
            final long timerDelay = timerMinutes * 60 * 1000;
            timer.schedule(timerUpdateWebWorker, timerDelay, timerDelay);
        } else {
            if( timerUpdateWebWorker != null ) {
                timerUpdateWebWorker.cancel();
                timerUpdateWebWorker = null;
            }
        }
    }

    class TimerUpdateDBWorker extends TimerTask {
        @Override
        public void run() {
            autoDatabaseUpdate(true);
        }
    }
    class TimerUpdateWebWorker extends TimerTask {
        @Override
        public void run() {
            autoWebUpdate(true);
        }
    }

    public void autoDatabaseUpdate(final boolean async) {
        // update the gui database from the update database
        final String upddbname = BoincNetStats.getCfg().getStrProperty("dbname_update");
        List<HostStats> updEntries = null;
        BoincNetStats.out("Automatic updatedb->guidb transfer begins.");
        try {
            final SQLStorage updstore = SQLStorage.createSQLStore(upddbname);
            updEntries = updstore.getHostStats();
            final Iterator<HostStats> it = updEntries.iterator();
            while(it.hasNext()) {
                final HostStats hs = it.next();
                hs.singleHosts = updstore.retrieveSingleHosts(hs.timestamp);
                hs.sumSingleHosts(); // always compute new sums

                if( BoincNetStats.getCfg().getBooleanProperty("auto_db_update_delete_updatedb_entries") ) {
                    // delete entry from update database
                    updstore.deleteHostStat(hs);
                }

                final int rc = getStore().saveToSQL(hs);
                if( rc != SQLStorage.SAVE_OK ) {
                    it.remove();
                }
            }
            updstore.close();
        } catch(final Exception ex) {
            BoincNetStats.out("Auto-Transfer error: "+ex.toString());
            return;
        }
        if( updEntries.size() == 0 ) {
            return;
        }
        final List<HostStats> finalEntries = updEntries;
        if( async ) {
            getShell().getDisplay().asyncExec(new Runnable() {
                public void run() {
                    for(int x=finalEntries.size()-1; x>=0; x--) {
                        final HostStats hs = finalEntries.get(x);
                        addHostStat(hs); // add to gui
                    }
                }
            });
        } else {
            for(int x=finalEntries.size()-1; x>=0; x--) {
                final HostStats hs = finalEntries.get(x);
                addHostStat(hs); // add to gui
            }
        }
    }

    protected void autoWebUpdate(final boolean async) {
        BoincNetStats.out("Automatic webupdate begins.");
        HostStats hoststats = null;
        try {
            hoststats = BoincNetStats.runUpdate(getStore());
        } catch (final Exception e) {
            BoincNetStats.out(e.toString());
            return;
        }
        if( hoststats == null ) {
            return; // duplicate result suppressed
        }
        final HostStats finalHostatsts = hoststats;
        if( async ) {
            getShell().getDisplay().asyncExec(new Runnable() {
                public void run() {
                    addHostStat(finalHostatsts); // add to gui
                }
            });
        } else {
            addHostStat(finalHostatsts); // add to gui
        }
    }

    @Override
    protected Control createContents(final Composite parent) {

        final SQLStorage store = getStore();
        if( store == null ) {
            throw new RuntimeException("Error on init time, startup cancelled.");
        }

        getShell().setText(BoincNetStats.TITLE+"Viewer "+BoincNetStats.VERSION);

        sash_form = new SashForm(parent, SWT.VERTICAL | SWT.NULL);

        sash_form0 = new SashForm(sash_form, SWT.HORIZONTAL | SWT.NULL);

        hostStatsTableViewer = new TableViewer(sash_form0, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
        hostStatsTableViewer.setContentProvider(new HostStatsTableContentProvider());
        final HostStatsTableLabelProvider lp = new HostStatsTableLabelProvider();
        hostStatsTableViewer.setLabelProvider(lp);
        lp.buildTableColumns(hostStatsTableViewer.getTable());
        hostStatsTableViewer.getTable().setHeaderVisible(true);
        hostStatsTableViewer.getTable().setLinesVisible(true);
        hostStatsTableViewer.setInput(store.getHostStats());

        hostStatsTableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
            public void selectionChanged(final SelectionChangedEvent event) {
                final IStructuredSelection selection = (IStructuredSelection)event.getSelection();
                final HostStats hs = (HostStats)selection.getFirstElement();
                if (hs != null) {
                    updateSingleHostStats(hs.timestamp);
                }
                else {
                    singleHostStatsTableViewer.setInput(null);
                }
                setStatus("Number of items selected is " + selection.size());
            }
        });
        hostStatsTableViewer.getTable().addKeyListener(new KeyListener() {
            public void keyPressed(final KeyEvent e) {}
            public void keyReleased(final KeyEvent e) {
                if( e.keyCode == SWT.DEL ) {
                    deleterow_action.run();
                }
            }});

        canvas = new Canvas(sash_form0, SWT.NONE);
        canvas.addPaintListener(new PaintListener() {
            public void paintControl(final PaintEvent e) {
                DiagramPainter.canvasPaintControl(e, getStore());
            }
        });

        /////////////////////////////////////
        sash_form2 = new SashForm(sash_form, SWT.VERTICAL | SWT.NULL);
        //	///////////////////////////////////
        singleHostStatsTableViewer = new TableViewer(sash_form2, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
        singleHostStatsTableViewer.setContentProvider(new SingleHostTableContentProvider());
        singleHostStatsTableViewer.getTable().setLinesVisible(true);
        final SingleHostTableLabelProvider slp = new SingleHostTableLabelProvider();
        singleHostStatsTableViewer.setLabelProvider(slp);
        slp.buildTableColumns(singleHostStatsTableViewer.getTable());

        singleHostStatsTableViewer.getTable().setHeaderVisible(true);
        //  ///////////////////////////////////

        final Composite comp = new Composite(sash_form2, SWT.BORDER);
        final GridLayout gl = new GridLayout();
        gl.numColumns = 3;
        comp.setLayout(gl);

        GridData dat;
        Label l;
        final int A_WIDTH = 60;
        final int B_WIDTH = 70;

        l = new Label(comp, SWT.LEFT);
        dat = new GridData();
        dat.widthHint = A_WIDTH;
        l.setLayoutData(dat);
        l.setText("14 day avg.:");

        labAvg14 = new Label(comp, SWT.RIGHT);
        dat = new GridData();
        dat.widthHint = B_WIDTH;
        labAvg14.setLayoutData(dat);

        labAvg14_grow = new Label(comp, SWT.RIGHT);
        dat = new GridData();
        dat.widthHint = B_WIDTH;
        labAvg14_grow.setLayoutData(dat);

        l = new Label(comp, SWT.LEFT);
        dat = new GridData();
        dat.widthHint = B_WIDTH;
        l.setLayoutData(dat);
        l.setText("30 day avg.:");

        labAvg30 = new Label(comp, SWT.RIGHT);
        dat = new GridData();
        dat.widthHint = B_WIDTH;
        labAvg30.setLayoutData(dat);

        labAvg30_grow = new Label(comp, SWT.RIGHT);
        dat = new GridData();
        dat.widthHint = B_WIDTH;
        labAvg30_grow.setLayoutData(dat);

        l = new Label(comp, SWT.LEFT);
        dat = new GridData();
        dat.widthHint = B_WIDTH;
        l.setLayoutData(dat);
        l.setText("Overall avg.:");

        labAvgAll = new Label(comp, SWT.RIGHT);
        dat = new GridData();
        dat.widthHint = B_WIDTH;
        labAvgAll.setLayoutData(dat);

        labAvgAll_grow = new Label(comp, SWT.RIGHT);
        dat = new GridData();
        dat.widthHint = B_WIDTH;
        labAvgAll_grow.setLayoutData(dat);

        //	///////////////////////////////////

        sash_form2.setWeights(new int[] { 70, 30 });

        //	///////////////////////////////////

        final MenuManager menu_manager = new MenuManager();
        hostStatsTableViewer.getTable().setMenu(menu_manager.createContextMenu(hostStatsTableViewer.getTable()));

        menu_manager.add(deleterow_action);

        updateAverages();

        selectFirstHostStat();

        restoreStateFromConfig();

        return sash_form;
    }

    public void updateAverages() {
        if( getStore() == null ) {
            return;
        }
        final SQLStorage.Averages avgs = getStore().getAverages();

        labAvg30.setText(avgs.avg30);
        labAvg30_grow.setText("(" + avgs.avg30_grow+")");
        labAvg14.setText(avgs.avg14);
        labAvg14_grow.setText("(" + avgs.avg14_grow+")");
        labAvgAll.setText(avgs.avgAll);
        labAvgAll_grow.setText("(" + avgs.avgAll_grow+")");

        final List<SQLStorage.CreditsAtTime> cats = getStore().getCreditAtTimeForDayCount(90);
        DiagramPainter.setCreditsAtTimeList(cats);
        canvas.redraw();
    }

    @Override
    protected void handleShellCloseEvent() {
        exit_action.run();
    }

    // called on ^c by BoincNetStats
    public void onShutdown() {
        // Shell is already disposed!!!
//        exit_action.run();
    }

    public void restoreStateFromConfig() {
        final int size_x = BoincNetStats.getCfg().getIntProperty("guistate_app_size_x");
        final int size_y = BoincNetStats.getCfg().getIntProperty("guistate_app_size_y");
        if( size_x > 0 && size_y > 0 ) {
            getShell().setSize(size_x, size_y);
        }
        final int loc_x = BoincNetStats.getCfg().getIntProperty("guistate_app_loc_x");
        final int loc_y = BoincNetStats.getCfg().getIntProperty("guistate_app_loc_y");
        if( loc_x > 0 && loc_y > 0 ) {
            getShell().setLocation(loc_x, loc_y);
        }

        int[] i = new int[2];
        i[0] = BoincNetStats.getCfg().getIntProperty("guistate_app_sash_0");
        i[1] = BoincNetStats.getCfg().getIntProperty("guistate_app_sash_1");
        if( i[0] > 0 && i[1] > 0 ) {
            sash_form.setWeights(i);
        }
        i = new int[2];
        i[0] = BoincNetStats.getCfg().getIntProperty("guistate_app_sash2_0");
        i[1] = BoincNetStats.getCfg().getIntProperty("guistate_app_sash2_1");
        if( i[0] > 0 && i[1] > 0 ) {
            sash_form2.setWeights(i);
        }
        i = new int[2];
        i[0] = BoincNetStats.getCfg().getIntProperty("guistate_app_sash0_0");
        i[1] = BoincNetStats.getCfg().getIntProperty("guistate_app_sash0_1");
        if( i[0] > 0 && i[1] > 0 ) {
            sash_form0.setWeights(i);
        }
    }

    public void saveStateToConfig() {
        final Point size = getShell().getSize();
        BoincNetStats.getCfg().setProperty("guistate_app_size_x", ""+size.x);
        BoincNetStats.getCfg().setProperty("guistate_app_size_y", ""+size.y);

        final Point loc = getShell().getLocation();
        BoincNetStats.getCfg().setProperty("guistate_app_loc_x", ""+loc.x);
        BoincNetStats.getCfg().setProperty("guistate_app_loc_y", ""+loc.y);

        int[] i;
        i = sash_form.getWeights();
        BoincNetStats.getCfg().setProperty("guistate_app_sash_0", ""+i[0]);
        BoincNetStats.getCfg().setProperty("guistate_app_sash_1", ""+i[1]);

        i = sash_form2.getWeights();
        BoincNetStats.getCfg().setProperty("guistate_app_sash2_0", ""+i[0]);
        BoincNetStats.getCfg().setProperty("guistate_app_sash2_1", ""+i[1]);

        i = sash_form0.getWeights();
        BoincNetStats.getCfg().setProperty("guistate_app_sash0_0", ""+i[0]);
        BoincNetStats.getCfg().setProperty("guistate_app_sash0_1", ""+i[1]);

        ((HostStatsTableLabelProvider)hostStatsTableViewer.getLabelProvider()).saveStateToConfig(hostStatsTableViewer.getTable());
        ((SingleHostTableLabelProvider)singleHostStatsTableViewer.getLabelProvider()).saveStateToConfig(singleHostStatsTableViewer.getTable());
    }

    private SQLStorage getStore() {
        if( sqlstore == null ) {
            final String dbname = BoincNetStats.getCfg().getStrProperty("dbname_gui");
            try {
                sqlstore = SQLStorage.createSQLStore(dbname);
            } catch (final Exception e) {
                MessageDialog.openError(getShell(), "Open of database failed", e.toString());
                BoincNetStats.out("Error opening database: " + e.toString());
            }
        }
        return sqlstore;
    }

    public void addHostStat(final HostStats hs) {
        // TODO: add sorted
        // figure out which column is sorted and which direction
        // find position to insert

        // first enhancement: insert sorted to when_taken column
        int insert_pos = -1;
        final ArrayList<HostStats> l = (ArrayList<HostStats>)hostStatsTableViewer.getInput();
        for(int i=0; i<l.size(); i++) {
            final HostStats lsths = l.get(i);
            if( hs.timestamp.after( lsths.timestamp) ) {
                insert_pos = i;
                break;
            }
        }
        if( insert_pos < 0 ) {
            insert_pos = l.size(); // add to end
        }
        l.add(insert_pos, hs); // add to model
        hostStatsTableViewer.insert(hs, insert_pos); // add to view

        updateAverages();
    }
/*
    public void addRow(TableMember member)
    {
        // compute pos to insert and insert node sorted into table
        int insertPos = Collections.binarySearch(rows, member, colComparator);
        if( insertPos < 0 )
        {
            // compute insertion pos
            insertPos = (insertPos+1)*-1;
        }
        else
        {
            // if such an item is already contained in search column,
            // determine last element and insert after
            insertPos =
                Collections.lastIndexOfSubList(rows, Collections.singletonList(rows.get(insertPos)));
            insertPos++; // insert AFTER last

        }
        insertRowAt(member, insertPos);
    }

    public void sortModelColumn(int col, boolean ascending)
    {
        sortColumn(col,ascending);
    }
    private void sortColumn(int col, boolean ascending)
    {
        // sort this column
        colComparator = new ColumnComparator(col, ascending);
        if( rows.size() > 1 )
        {
            Collections.sort(rows, colComparator);
        }
    }

    public class ColumnComparator implements Comparator
    {
        protected int index;
        protected boolean ascending;

        public ColumnComparator(int index, boolean ascending)
        {
            this.index = index;
            this.ascending = ascending;
        }

        // uses implementation in ITableMember or default impl. in abstracttreemodel
        public int compare(Object one, Object two)
        {
            try {
                TableMember oOne = (TableMember)one;
                TableMember oTwo = (TableMember)two;

                if( ascending )
                {
                    return oOne.compareTo(oTwo, index);
                }
                else
                {
                    return oTwo.compareTo(oOne, index);
                }
            }
            catch(Exception e) { }
            return 1;
        }
    }

    public Object getValueAt(int column);
    public int compareTo( TableMember anOther, int tableColumIndex );

*/
    public void reloadHostStats() {
        final SQLStorage store = getStore();
        if( store == null ) {
            return;
        }
        hostStatsTableViewer.setInput(store.getHostStats());
        selectFirstHostStat();
    }


    public void selectFirstHostStat() {
        if (hostStatsTableViewer.getTable().getItemCount() == 0) {
            return;
        }
        hostStatsTableViewer.getTable().setSelection(0);
        final HostStats first = (HostStats)hostStatsTableViewer.getTable().getItem(0).getData();
        if (first != null) {
            updateSingleHostStats(first.timestamp);
            setStatus("Number of items selected is 1");
        }
        else {
            hostStatsTableViewer.setInput(null);
        }
    }

    public static boolean showSpecialIcons = false;

    protected void updateSingleHostStats(final java.sql.Timestamp ts) {
        final SQLStorage store = getStore();
        if( store == null ) {
            return;
        }
        if( hostStatsTableViewer.getTable().getSelectionIndex() == 0 ) {
            // TODO: latest hoststat selected, show special icons
            showSpecialIcons = true;
        } else {
            showSpecialIcons = false;
        }
        singleHostStatsTableViewer.setInput(store.retrieveSingleHosts(ts));
    }

    @Override
    protected MenuManager createMenuManager() {
        final MenuManager bar_menu = new MenuManager("");

        final MenuManager file_menu = new MenuManager("&File");
        final MenuManager edit_menu = new MenuManager("&Edit");
        final MenuManager tool_menu = new MenuManager("&Tools");
        final MenuManager help_menu = new MenuManager("&Help");

        bar_menu.add(file_menu);
        bar_menu.add(edit_menu);
        bar_menu.add(tool_menu);
        bar_menu.add(help_menu);

        file_menu.add(import_action);
        file_menu.add(export_action);
        file_menu.add(new Separator());
        file_menu.add(config_action);
        file_menu.add(new Separator());
        file_menu.add(exit_action);

        tool_menu.add(creditUpdateOverviewAction);

        edit_menu.add(update_web_action);
        edit_menu.add(update_db_action);
        edit_menu.add(new Separator());
        edit_menu.add(deleterow_action);

        help_menu.add(about_action);

        return bar_menu;
    }

    public IStructuredSelection getTableSelection() {
        return (IStructuredSelection) (hostStatsTableViewer.getSelection());
    }

    public void removeTableRow(final Object row) {
        if (row == null) {
            return;
        }
        hostStatsTableViewer.remove(row);
    }

    //  protected ToolBarManager createToolBarManager(int style)
    //  {
    //    ToolBarManager tool_bar_manager = new ToolBarManager(style);
    //    tool_bar_manager.add(deleterow_action);
    //    return tool_bar_manager;
    //  }
}
