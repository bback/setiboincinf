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

import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

import boincinf.*;
import boincinf.util.*;

/**
* This code was generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a
* for-profit company or business) then you should purchase
* a license - please visit www.cloudgarden.com for details.
*/
public class NiceOptionsDialog extends org.eclipse.swt.widgets.Dialog {
	private Button CBdeleteAfterUpdImport;
//	private Button CBupdateWebOnlyIfNewData;
	private Button CBupdateOnlyIfNewData;
	private Text TupdateWebInterval;
	private Label label8;
	private Text TupdateDBinterval;
	private Label label7;
	private Composite composite7;
	private Composite composite6;
	private Button RBupdateWebAuto;
	private Button RBupdateWebManual;
	private Button RBupdateDBauto;
	private Button RBupdateDBmanual;
	private Group group3;
	private Group group2;
	private Composite composite5;
	private TabItem updateTabItem;
	private Text TsocksPort;
	private Label label6;
	private Text TsocksServer;
	private Label label5;
	private Composite composite4;
	private Button RBsocks;
	private Text TproxyPort;
	private Label label4;
	private Text TproxyServer;
	private Label label3;
	private Composite composite3;
	private Button RBproxy;
	private Button RBdirect;
	private Group group1;
	private Composite composite2;
	private Text Taccountid;
	private Label label2;
	private Composite composite1;
	private Button Bcancel;
	private Button Bok;
	private Label label1;
	private TabItem connectionTabItem;
	private TabItem accountTabItem;
	private Composite buttonComposite;
	private TabFolder tabFolder;
	private Shell dialogShell;

    private final Config origConfig;
    private final BoincInfGui gui;

    public NiceOptionsDialog(final BoincInfGui g, final Config cfg) {
        super(g.getShell());
        origConfig = cfg;
        gui = g;
    }

	/**
	* Opens the Dialog Shell.
	* Auto-generated code - any changes you make will disappear.
	*/
	public void open(){
		try {
			preInitGUI();

			final Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
			dialogShell.setText(getText());
			tabFolder = new TabFolder(dialogShell,SWT.NULL);
			accountTabItem = new TabItem(tabFolder,SWT.NULL);
			composite1 = new Composite(tabFolder,SWT.NULL);
			label2 = new Label(composite1,SWT.NULL);
			Taccountid = new Text(composite1,SWT.BORDER);
			connectionTabItem = new TabItem(tabFolder,SWT.NULL);
			composite2 = new Composite(tabFolder,SWT.NULL);
			group1 = new Group(composite2,SWT.NULL);
			RBdirect = new Button(group1,SWT.RADIO| SWT.LEFT);
			RBproxy = new Button(group1,SWT.RADIO| SWT.LEFT);
			composite3 = new Composite(group1,SWT.NULL);
			label3 = new Label(composite3,SWT.NULL);
			TproxyServer = new Text(composite3,SWT.BORDER);
			label4 = new Label(composite3,SWT.NULL);
			TproxyPort = new Text(composite3,SWT.BORDER);
			RBsocks = new Button(group1,SWT.RADIO| SWT.LEFT);
			composite4 = new Composite(group1,SWT.NULL);
			label5 = new Label(composite4,SWT.NULL);
			TsocksServer = new Text(composite4,SWT.BORDER);
			label6 = new Label(composite4,SWT.NULL);
			TsocksPort = new Text(composite4,SWT.BORDER);
			updateTabItem = new TabItem(tabFolder,SWT.NULL);
			composite5 = new Composite(tabFolder,SWT.NULL);
			group2 = new Group(composite5,SWT.NULL);
			RBupdateDBmanual = new Button(group2,SWT.RADIO| SWT.LEFT);
			RBupdateDBauto = new Button(group2,SWT.RADIO| SWT.LEFT);
			composite7 = new Composite(group2,SWT.NULL);
			label7 = new Label(composite7,SWT.NULL);
			TupdateDBinterval = new Text(composite7,SWT.BORDER);
			CBdeleteAfterUpdImport = new Button(group2,SWT.CHECK| SWT.LEFT);
			group3 = new Group(composite5,SWT.NULL);
			RBupdateWebManual = new Button(group3,SWT.RADIO| SWT.LEFT);
			RBupdateWebAuto = new Button(group3,SWT.RADIO| SWT.LEFT);
			composite6 = new Composite(group3,SWT.NULL);
			label8 = new Label(composite6,SWT.NULL);
			TupdateWebInterval = new Text(composite6,SWT.BORDER);
			CBupdateOnlyIfNewData = new Button(composite5,SWT.CHECK| SWT.LEFT);
			buttonComposite = new Composite(dialogShell,SWT.NULL);
			label1 = new Label(buttonComposite,SWT.NULL);
			Bok = new Button(buttonComposite,SWT.PUSH| SWT.CENTER);
			Bcancel = new Button(buttonComposite,SWT.PUSH| SWT.CENTER);

			dialogShell.setText("Options");
			dialogShell.setSize(new org.eclipse.swt.graphics.Point(450,400));

			final GridData tabFolderLData = new GridData();
			tabFolderLData.verticalAlignment = GridData.FILL;
			tabFolderLData.horizontalAlignment = GridData.FILL;
			tabFolderLData.widthHint = -1;
			tabFolderLData.heightHint = -1;
			tabFolderLData.horizontalIndent = 0;
			tabFolderLData.horizontalSpan = 1;
			tabFolderLData.verticalSpan = 1;
			tabFolderLData.grabExcessHorizontalSpace = true;
			tabFolderLData.grabExcessVerticalSpace = true;
			tabFolder.setLayoutData(tabFolderLData);
			tabFolder.setSize(new org.eclipse.swt.graphics.Point(432,343));

			accountTabItem.setControl(composite1);
			accountTabItem.setText("Account");


			final GridData label2LData = new GridData();
			label2LData.verticalAlignment = GridData.CENTER;
			label2LData.horizontalAlignment = GridData.BEGINNING;
			label2LData.widthHint = -1;
			label2LData.heightHint = -1;
			label2LData.horizontalIndent = 0;
			label2LData.horizontalSpan = 1;
			label2LData.verticalSpan = 1;
			label2LData.grabExcessHorizontalSpace = false;
			label2LData.grabExcessVerticalSpace = false;
			label2.setLayoutData(label2LData);
			label2.setText("Account ID");

			final GridData TaccountidLData = new GridData();
			TaccountidLData.verticalAlignment = GridData.CENTER;
			TaccountidLData.horizontalAlignment = GridData.BEGINNING;
			TaccountidLData.widthHint = 225;
			TaccountidLData.heightHint = -1;
			TaccountidLData.horizontalIndent = 0;
			TaccountidLData.horizontalSpan = 1;
			TaccountidLData.verticalSpan = 1;
			TaccountidLData.grabExcessHorizontalSpace = false;
			TaccountidLData.grabExcessVerticalSpace = false;
			Taccountid.setLayoutData(TaccountidLData);
			final GridLayout composite1Layout = new GridLayout(2, true);
			composite1.setLayout(composite1Layout);
			composite1Layout.marginWidth = 10;
			composite1Layout.marginHeight = 10;
			composite1Layout.numColumns = 2;
			composite1Layout.makeColumnsEqualWidth = false;
			composite1Layout.horizontalSpacing = 15;
			composite1Layout.verticalSpacing = 5;
			composite1.layout();

			connectionTabItem.setControl(composite2);
			connectionTabItem.setText("Connection");


			final GridData group1LData = new GridData();
			group1LData.verticalAlignment = GridData.FILL;
			group1LData.horizontalAlignment = GridData.FILL;
			group1LData.widthHint = -1;
			group1LData.heightHint = -1;
			group1LData.horizontalIndent = 0;
			group1LData.horizontalSpan = 1;
			group1LData.verticalSpan = 1;
			group1LData.grabExcessHorizontalSpace = true;
			group1LData.grabExcessVerticalSpace = true;
			group1.setLayoutData(group1LData);
			group1.setText("Connection");

			final GridData RBdirectLData = new GridData();
			RBdirectLData.verticalAlignment = GridData.CENTER;
			RBdirectLData.horizontalAlignment = GridData.BEGINNING;
			RBdirectLData.widthHint = 118;
			RBdirectLData.heightHint = 16;
			RBdirectLData.horizontalIndent = 0;
			RBdirectLData.horizontalSpan = 2;
			RBdirectLData.verticalSpan = 1;
			RBdirectLData.grabExcessHorizontalSpace = false;
			RBdirectLData.grabExcessVerticalSpace = false;
			RBdirect.setLayoutData(RBdirectLData);
			RBdirect.setText("Direct connection");
			RBdirect.setSize(new org.eclipse.swt.graphics.Point(118,16));
			RBdirect.addSelectionListener( new SelectionAdapter() {
				@Override
                public void widgetSelected(final SelectionEvent evt) {
					RBdirectWidgetSelected(evt);
				}
			});

			final GridData RBproxyLData = new GridData();
			RBproxyLData.verticalAlignment = GridData.CENTER;
			RBproxyLData.horizontalAlignment = GridData.BEGINNING;
			RBproxyLData.widthHint = 112;
			RBproxyLData.heightHint = 16;
			RBproxyLData.horizontalIndent = 0;
			RBproxyLData.horizontalSpan = 1;
			RBproxyLData.verticalSpan = 1;
			RBproxyLData.grabExcessHorizontalSpace = false;
			RBproxyLData.grabExcessVerticalSpace = false;
			RBproxy.setLayoutData(RBproxyLData);
			RBproxy.setText("Use proxy server");
			RBproxy.setSize(new org.eclipse.swt.graphics.Point(112,16));
			RBproxy.addSelectionListener( new SelectionAdapter() {
				@Override
                public void widgetSelected(final SelectionEvent evt) {
					RBproxyWidgetSelected(evt);
				}
			});

			final GridData composite3LData = new GridData();
			composite3LData.verticalAlignment = GridData.CENTER;
			composite3LData.horizontalAlignment = GridData.BEGINNING;
			composite3LData.widthHint = -1;
			composite3LData.heightHint = -1;
			composite3LData.horizontalIndent = 0;
			composite3LData.horizontalSpan = 1;
			composite3LData.verticalSpan = 1;
			composite3LData.grabExcessHorizontalSpace = false;
			composite3LData.grabExcessVerticalSpace = false;
			composite3.setLayoutData(composite3LData);

			final GridData label3LData = new GridData();
			label3LData.verticalAlignment = GridData.CENTER;
			label3LData.horizontalAlignment = GridData.BEGINNING;
			label3LData.widthHint = -1;
			label3LData.heightHint = -1;
			label3LData.horizontalIndent = 0;
			label3LData.horizontalSpan = 1;
			label3LData.verticalSpan = 1;
			label3LData.grabExcessHorizontalSpace = false;
			label3LData.grabExcessVerticalSpace = false;
			label3.setLayoutData(label3LData);
			label3.setText("Proxy server");

			final GridData TproxyServerLData = new GridData();
			TproxyServerLData.verticalAlignment = GridData.CENTER;
			TproxyServerLData.horizontalAlignment = GridData.BEGINNING;
			TproxyServerLData.widthHint = 175;
			TproxyServerLData.heightHint = -1;
			TproxyServerLData.horizontalIndent = 0;
			TproxyServerLData.horizontalSpan = 1;
			TproxyServerLData.verticalSpan = 1;
			TproxyServerLData.grabExcessHorizontalSpace = false;
			TproxyServerLData.grabExcessVerticalSpace = false;
			TproxyServer.setLayoutData(TproxyServerLData);
			TproxyServer.setText("text1");

			final GridData label4LData = new GridData();
			label4LData.verticalAlignment = GridData.CENTER;
			label4LData.horizontalAlignment = GridData.BEGINNING;
			label4LData.widthHint = -1;
			label4LData.heightHint = -1;
			label4LData.horizontalIndent = 0;
			label4LData.horizontalSpan = 1;
			label4LData.verticalSpan = 1;
			label4LData.grabExcessHorizontalSpace = false;
			label4LData.grabExcessVerticalSpace = false;
			label4.setLayoutData(label4LData);
			label4.setText("Proxy port");

			final GridData TproxyPortLData = new GridData();
			TproxyPortLData.verticalAlignment = GridData.CENTER;
			TproxyPortLData.horizontalAlignment = GridData.BEGINNING;
			TproxyPortLData.widthHint = 175;
			TproxyPortLData.heightHint = -1;
			TproxyPortLData.horizontalIndent = 0;
			TproxyPortLData.horizontalSpan = 1;
			TproxyPortLData.verticalSpan = 1;
			TproxyPortLData.grabExcessHorizontalSpace = false;
			TproxyPortLData.grabExcessVerticalSpace = false;
			TproxyPort.setLayoutData(TproxyPortLData);
			TproxyPort.setText("pport");
			final GridLayout composite3Layout = new GridLayout(2, true);
			composite3.setLayout(composite3Layout);
			composite3Layout.marginWidth = 15;
			composite3Layout.marginHeight = 5;
			composite3Layout.numColumns = 2;
			composite3Layout.makeColumnsEqualWidth = false;
			composite3Layout.horizontalSpacing = 15;
			composite3Layout.verticalSpacing = 7;
			composite3.layout();

			final GridData RBsocksLData = new GridData();
			RBsocksLData.verticalAlignment = GridData.CENTER;
			RBsocksLData.horizontalAlignment = GridData.BEGINNING;
			RBsocksLData.widthHint = -1;
			RBsocksLData.heightHint = -1;
			RBsocksLData.horizontalIndent = 0;
			RBsocksLData.horizontalSpan = 1;
			RBsocksLData.verticalSpan = 1;
			RBsocksLData.grabExcessHorizontalSpace = false;
			RBsocksLData.grabExcessVerticalSpace = false;
			RBsocks.setLayoutData(RBsocksLData);
			RBsocks.setText("Use socks server");
			RBsocks.addSelectionListener( new SelectionAdapter() {
				@Override
                public void widgetSelected(final SelectionEvent evt) {
					RBsocksWidgetSelected(evt);
				}
			});

			final GridData composite4LData = new GridData();
			composite4LData.verticalAlignment = GridData.CENTER;
			composite4LData.horizontalAlignment = GridData.BEGINNING;
			composite4LData.widthHint = -1;
			composite4LData.heightHint = -1;
			composite4LData.horizontalIndent = 0;
			composite4LData.horizontalSpan = 1;
			composite4LData.verticalSpan = 1;
			composite4LData.grabExcessHorizontalSpace = false;
			composite4LData.grabExcessVerticalSpace = false;
			composite4.setLayoutData(composite4LData);

			final GridData label5LData = new GridData();
			label5LData.verticalAlignment = GridData.CENTER;
			label5LData.horizontalAlignment = GridData.BEGINNING;
			label5LData.widthHint = -1;
			label5LData.heightHint = -1;
			label5LData.horizontalIndent = 0;
			label5LData.horizontalSpan = 1;
			label5LData.verticalSpan = 1;
			label5LData.grabExcessHorizontalSpace = false;
			label5LData.grabExcessVerticalSpace = false;
			label5.setLayoutData(label5LData);
			label5.setText("Socks server");

			final GridData TsocksServerLData = new GridData();
			TsocksServerLData.verticalAlignment = GridData.CENTER;
			TsocksServerLData.horizontalAlignment = GridData.BEGINNING;
			TsocksServerLData.widthHint = 175;
			TsocksServerLData.heightHint = -1;
			TsocksServerLData.horizontalIndent = 0;
			TsocksServerLData.horizontalSpan = 1;
			TsocksServerLData.verticalSpan = 1;
			TsocksServerLData.grabExcessHorizontalSpace = false;
			TsocksServerLData.grabExcessVerticalSpace = false;
			TsocksServer.setLayoutData(TsocksServerLData);
			TsocksServer.setText("sserver");

			final GridData label6LData = new GridData();
			label6LData.verticalAlignment = GridData.CENTER;
			label6LData.horizontalAlignment = GridData.BEGINNING;
			label6LData.widthHint = -1;
			label6LData.heightHint = -1;
			label6LData.horizontalIndent = 0;
			label6LData.horizontalSpan = 1;
			label6LData.verticalSpan = 1;
			label6LData.grabExcessHorizontalSpace = false;
			label6LData.grabExcessVerticalSpace = false;
			label6.setLayoutData(label6LData);
			label6.setText("Socks port");

			final GridData TsocksPortLData = new GridData();
			TsocksPortLData.verticalAlignment = GridData.CENTER;
			TsocksPortLData.horizontalAlignment = GridData.BEGINNING;
			TsocksPortLData.widthHint = 175;
			TsocksPortLData.heightHint = 14;
			TsocksPortLData.horizontalIndent = 0;
			TsocksPortLData.horizontalSpan = 1;
			TsocksPortLData.verticalSpan = 1;
			TsocksPortLData.grabExcessHorizontalSpace = false;
			TsocksPortLData.grabExcessVerticalSpace = false;
			TsocksPort.setLayoutData(TsocksPortLData);
			TsocksPort.setText("sport");
			TsocksPort.setSize(new org.eclipse.swt.graphics.Point(175,14));
			final GridLayout composite4Layout = new GridLayout(2, true);
			composite4.setLayout(composite4Layout);
			composite4Layout.marginWidth = 15;
			composite4Layout.marginHeight = 5;
			composite4Layout.numColumns = 2;
			composite4Layout.makeColumnsEqualWidth = false;
			composite4Layout.horizontalSpacing = 15;
			composite4Layout.verticalSpacing = 7;
			composite4.layout();
			final GridLayout group1Layout = new GridLayout(1, true);
			group1.setLayout(group1Layout);
			group1Layout.marginWidth = 5;
			group1Layout.marginHeight = 5;
			group1Layout.numColumns = 1;
			group1Layout.makeColumnsEqualWidth = true;
			group1Layout.horizontalSpacing = 5;
			group1Layout.verticalSpacing = 5;
			group1.layout();
			final GridLayout composite2Layout = new GridLayout(1, true);
			composite2.setLayout(composite2Layout);
			composite2Layout.marginWidth = 5;
			composite2Layout.marginHeight = 5;
			composite2Layout.numColumns = 1;
			composite2Layout.makeColumnsEqualWidth = false;
			composite2Layout.horizontalSpacing = 5;
			composite2Layout.verticalSpacing = 5;
			composite2.layout();

			updateTabItem.setControl(composite5);
			updateTabItem.setText("Updating");


			final GridData group2LData = new GridData();
			group2LData.verticalAlignment = GridData.FILL;
			group2LData.horizontalAlignment = GridData.FILL;
			group2LData.widthHint = -1;
			group2LData.heightHint = -1;
			group2LData.horizontalIndent = 0;
			group2LData.horizontalSpan = 1;
			group2LData.verticalSpan = 1;
			group2LData.grabExcessHorizontalSpace = false;
			group2LData.grabExcessVerticalSpace = true;
			group2.setLayoutData(group2LData);
			group2.setText("Update from update database");

			final GridData RBupdateDBmanualLData = new GridData();
			RBupdateDBmanualLData.verticalAlignment = GridData.CENTER;
			RBupdateDBmanualLData.horizontalAlignment = GridData.BEGINNING;
			RBupdateDBmanualLData.widthHint = -1;
			RBupdateDBmanualLData.heightHint = -1;
			RBupdateDBmanualLData.horizontalIndent = 0;
			RBupdateDBmanualLData.horizontalSpan = 1;
			RBupdateDBmanualLData.verticalSpan = 1;
			RBupdateDBmanualLData.grabExcessHorizontalSpace = false;
			RBupdateDBmanualLData.grabExcessVerticalSpace = false;
			RBupdateDBmanual.setLayoutData(RBupdateDBmanualLData);
			RBupdateDBmanual.setText("Manual update");
			RBupdateDBmanual.addSelectionListener( new SelectionAdapter() {
				@Override
                public void widgetSelected(final SelectionEvent evt) {
					RBupdateDBmanualWidgetSelected(evt);
				}
			});

			final GridData RBupdateDBautoLData = new GridData();
			RBupdateDBautoLData.verticalAlignment = GridData.CENTER;
			RBupdateDBautoLData.horizontalAlignment = GridData.BEGINNING;
			RBupdateDBautoLData.widthHint = -1;
			RBupdateDBautoLData.heightHint = -1;
			RBupdateDBautoLData.horizontalIndent = 0;
			RBupdateDBautoLData.horizontalSpan = 1;
			RBupdateDBautoLData.verticalSpan = 1;
			RBupdateDBautoLData.grabExcessHorizontalSpace = false;
			RBupdateDBautoLData.grabExcessVerticalSpace = false;
			RBupdateDBauto.setLayoutData(RBupdateDBautoLData);
			RBupdateDBauto.setText("Automatic update");
			RBupdateDBauto.addSelectionListener( new SelectionAdapter() {
				@Override
                public void widgetSelected(final SelectionEvent evt) {
					RBupdateDBautoWidgetSelected(evt);
				}
			});

			final GridData composite7LData = new GridData();
			composite7LData.verticalAlignment = GridData.FILL;
			composite7LData.horizontalAlignment = GridData.FILL;
			composite7LData.widthHint = -1;
			composite7LData.heightHint = -1;
			composite7LData.horizontalIndent = 15;
			composite7LData.horizontalSpan = 1;
			composite7LData.verticalSpan = 1;
			composite7LData.grabExcessHorizontalSpace = false;
			composite7LData.grabExcessVerticalSpace = true;
			composite7.setLayoutData(composite7LData);

			final GridData label7LData = new GridData();
			label7LData.verticalAlignment = GridData.CENTER;
			label7LData.horizontalAlignment = GridData.BEGINNING;
			label7LData.widthHint = -1;
			label7LData.heightHint = -1;
			label7LData.horizontalIndent = 0;
			label7LData.horizontalSpan = 1;
			label7LData.verticalSpan = 1;
			label7LData.grabExcessHorizontalSpace = false;
			label7LData.grabExcessVerticalSpace = false;
			label7.setLayoutData(label7LData);
			label7.setText("Interval (minutes)");

			final GridData TupdateDBintervalLData = new GridData();
			TupdateDBintervalLData.verticalAlignment = GridData.CENTER;
			TupdateDBintervalLData.horizontalAlignment = GridData.BEGINNING;
			TupdateDBintervalLData.widthHint = -1;
			TupdateDBintervalLData.heightHint = -1;
			TupdateDBintervalLData.horizontalIndent = 0;
			TupdateDBintervalLData.horizontalSpan = 1;
			TupdateDBintervalLData.verticalSpan = 1;
			TupdateDBintervalLData.grabExcessHorizontalSpace = false;
			TupdateDBintervalLData.grabExcessVerticalSpace = false;
			TupdateDBinterval.setLayoutData(TupdateDBintervalLData);
			TupdateDBinterval.setText("text1");
			TupdateDBinterval.setDoubleClickEnabled(false);
			final GridLayout composite7Layout = new GridLayout(2, true);
			composite7.setLayout(composite7Layout);
			composite7Layout.marginWidth = 5;
			composite7Layout.marginHeight = 5;
			composite7Layout.numColumns = 2;
			composite7Layout.makeColumnsEqualWidth = false;
			composite7Layout.horizontalSpacing = 15;
			composite7Layout.verticalSpacing = 5;
			composite7.layout();

			final GridData CBdeleteAfterUpdImportLData = new GridData();
			CBdeleteAfterUpdImportLData.verticalAlignment = GridData.CENTER;
			CBdeleteAfterUpdImportLData.horizontalAlignment = GridData.BEGINNING;
			CBdeleteAfterUpdImportLData.widthHint = -1;
			CBdeleteAfterUpdImportLData.heightHint = -1;
			CBdeleteAfterUpdImportLData.horizontalIndent = 0;
			CBdeleteAfterUpdImportLData.horizontalSpan = 2;
			CBdeleteAfterUpdImportLData.verticalSpan = 1;
			CBdeleteAfterUpdImportLData.grabExcessHorizontalSpace = false;
			CBdeleteAfterUpdImportLData.grabExcessVerticalSpace = false;
			CBdeleteAfterUpdImport.setLayoutData(CBdeleteAfterUpdImportLData);
			CBdeleteAfterUpdImport.setText("Delete entries from update database after import");
			final GridLayout group2Layout = new GridLayout(1, true);
			group2.setLayout(group2Layout);
			group2Layout.marginWidth = 5;
			group2Layout.marginHeight = 5;
			group2Layout.numColumns = 1;
			group2Layout.makeColumnsEqualWidth = true;
			group2Layout.horizontalSpacing = 5;
			group2Layout.verticalSpacing = 5;
			group2.layout();

			final GridData group3LData = new GridData();
			group3LData.verticalAlignment = GridData.FILL;
			group3LData.horizontalAlignment = GridData.FILL;
			group3LData.widthHint = -1;
			group3LData.heightHint = -1;
			group3LData.horizontalIndent = 0;
			group3LData.horizontalSpan = 1;
			group3LData.verticalSpan = 1;
			group3LData.grabExcessHorizontalSpace = false;
			group3LData.grabExcessVerticalSpace = true;
			group3.setLayoutData(group3LData);
			group3.setText("Update from website");
			group3.setSize(new org.eclipse.swt.graphics.Point(416,117));

			final GridData RBupdateWebManualLData = new GridData();
			RBupdateWebManualLData.verticalAlignment = GridData.CENTER;
			RBupdateWebManualLData.horizontalAlignment = GridData.BEGINNING;
			RBupdateWebManualLData.widthHint = -1;
			RBupdateWebManualLData.heightHint = -1;
			RBupdateWebManualLData.horizontalIndent = 0;
			RBupdateWebManualLData.horizontalSpan = 1;
			RBupdateWebManualLData.verticalSpan = 1;
			RBupdateWebManualLData.grabExcessHorizontalSpace = false;
			RBupdateWebManualLData.grabExcessVerticalSpace = false;
			RBupdateWebManual.setLayoutData(RBupdateWebManualLData);
			RBupdateWebManual.setText("Manual update");
			RBupdateWebManual.addSelectionListener( new SelectionAdapter() {
				@Override
                public void widgetSelected(final SelectionEvent evt) {
					RBupdateWebManualWidgetSelected(evt);
				}
			});

			final GridData RBupdateWebAutoLData = new GridData();
			RBupdateWebAutoLData.verticalAlignment = GridData.CENTER;
			RBupdateWebAutoLData.horizontalAlignment = GridData.BEGINNING;
			RBupdateWebAutoLData.widthHint = -1;
			RBupdateWebAutoLData.heightHint = -1;
			RBupdateWebAutoLData.horizontalIndent = 0;
			RBupdateWebAutoLData.horizontalSpan = 1;
			RBupdateWebAutoLData.verticalSpan = 1;
			RBupdateWebAutoLData.grabExcessHorizontalSpace = false;
			RBupdateWebAutoLData.grabExcessVerticalSpace = false;
			RBupdateWebAuto.setLayoutData(RBupdateWebAutoLData);
			RBupdateWebAuto.setText("Automatic update");
			RBupdateWebAuto.addSelectionListener( new SelectionAdapter() {
				@Override
                public void widgetSelected(final SelectionEvent evt) {
					RBupdateWebAutoWidgetSelected(evt);
				}
			});

			final GridData composite6LData = new GridData();
			composite6LData.verticalAlignment = GridData.FILL;
			composite6LData.horizontalAlignment = GridData.FILL;
			composite6LData.widthHint = -1;
			composite6LData.heightHint = -1;
			composite6LData.horizontalIndent = 15;
			composite6LData.horizontalSpan = 1;
			composite6LData.verticalSpan = 1;
			composite6LData.grabExcessHorizontalSpace = false;
			composite6LData.grabExcessVerticalSpace = true;
			composite6.setLayoutData(composite6LData);
			composite6.setSize(new org.eclipse.swt.graphics.Point(391,65));

			final GridData label8LData = new GridData();
			label8LData.verticalAlignment = GridData.CENTER;
			label8LData.horizontalAlignment = GridData.BEGINNING;
			label8LData.widthHint = -1;
			label8LData.heightHint = -1;
			label8LData.horizontalIndent = 0;
			label8LData.horizontalSpan = 1;
			label8LData.verticalSpan = 1;
			label8LData.grabExcessHorizontalSpace = false;
			label8LData.grabExcessVerticalSpace = false;
			label8.setLayoutData(label8LData);
			label8.setText("Interval (minutes)");

			final GridData TupdateWebIntervalLData = new GridData();
			TupdateWebIntervalLData.verticalAlignment = GridData.CENTER;
			TupdateWebIntervalLData.horizontalAlignment = GridData.BEGINNING;
			TupdateWebIntervalLData.widthHint = -1;
			TupdateWebIntervalLData.heightHint = -1;
			TupdateWebIntervalLData.horizontalIndent = 0;
			TupdateWebIntervalLData.horizontalSpan = 1;
			TupdateWebIntervalLData.verticalSpan = 1;
			TupdateWebIntervalLData.grabExcessHorizontalSpace = false;
			TupdateWebIntervalLData.grabExcessVerticalSpace = false;
			TupdateWebInterval.setLayoutData(TupdateWebIntervalLData);
			TupdateWebInterval.setText("text2");
			final GridLayout composite6Layout = new GridLayout(2, true);
			composite6.setLayout(composite6Layout);
			composite6Layout.marginWidth = 5;
			composite6Layout.marginHeight = 5;
			composite6Layout.numColumns = 2;
			composite6Layout.makeColumnsEqualWidth = false;
			composite6Layout.horizontalSpacing = 15;
			composite6Layout.verticalSpacing = 5;
			composite6.layout();
			final GridLayout group3Layout = new GridLayout(1, true);
			group3.setLayout(group3Layout);
			group3Layout.marginWidth = 5;
			group3Layout.marginHeight = 5;
			group3Layout.numColumns = 1;
			group3Layout.makeColumnsEqualWidth = true;
			group3Layout.horizontalSpacing = 5;
			group3Layout.verticalSpacing = 5;
			group3.layout();

			final GridData CBupdateOnlyIfNewDataLData = new GridData();
			CBupdateOnlyIfNewDataLData.verticalAlignment = GridData.CENTER;
			CBupdateOnlyIfNewDataLData.horizontalAlignment = GridData.BEGINNING;
			CBupdateOnlyIfNewDataLData.widthHint = -1;
			CBupdateOnlyIfNewDataLData.heightHint = -1;
			CBupdateOnlyIfNewDataLData.horizontalIndent = 7;
			CBupdateOnlyIfNewDataLData.horizontalSpan = 2;
			CBupdateOnlyIfNewDataLData.verticalSpan = 1;
			CBupdateOnlyIfNewDataLData.grabExcessHorizontalSpace = false;
			CBupdateOnlyIfNewDataLData.grabExcessVerticalSpace = false;
			CBupdateOnlyIfNewData.setLayoutData(CBupdateOnlyIfNewDataLData);
			CBupdateOnlyIfNewData.setText("Don't store unchanged data");
			final GridLayout composite5Layout = new GridLayout(1, true);
			composite5.setLayout(composite5Layout);
			composite5Layout.marginWidth = 5;
			composite5Layout.marginHeight = 5;
			composite5Layout.numColumns = 1;
			composite5Layout.makeColumnsEqualWidth = true;
			composite5Layout.horizontalSpacing = 5;
			composite5Layout.verticalSpacing = 5;
			composite5.layout();

			final GridData buttonCompositeLData = new GridData();
			buttonCompositeLData.verticalAlignment = GridData.CENTER;
			buttonCompositeLData.horizontalAlignment = GridData.FILL;
			buttonCompositeLData.widthHint = -1;
			buttonCompositeLData.heightHint = -1;
			buttonCompositeLData.horizontalIndent = 0;
			buttonCompositeLData.horizontalSpan = 1;
			buttonCompositeLData.verticalSpan = 1;
			buttonCompositeLData.grabExcessHorizontalSpace = true;
			buttonCompositeLData.grabExcessVerticalSpace = false;
			buttonComposite.setLayoutData(buttonCompositeLData);

			final GridData label1LData = new GridData();
			label1LData.verticalAlignment = GridData.CENTER;
			label1LData.horizontalAlignment = GridData.FILL;
			label1LData.widthHint = -1;
			label1LData.heightHint = -1;
			label1LData.horizontalIndent = 0;
			label1LData.horizontalSpan = 1;
			label1LData.verticalSpan = 1;
			label1LData.grabExcessHorizontalSpace = true;
			label1LData.grabExcessVerticalSpace = false;
			label1.setLayoutData(label1LData);
			label1.setText(" ");

			final GridData BokLData = new GridData();
			BokLData.verticalAlignment = GridData.CENTER;
			BokLData.horizontalAlignment = GridData.END;
			BokLData.widthHint = -1;
			BokLData.heightHint = -1;
			BokLData.horizontalIndent = 0;
			BokLData.horizontalSpan = 1;
			BokLData.verticalSpan = 1;
			BokLData.grabExcessHorizontalSpace = false;
			BokLData.grabExcessVerticalSpace = false;
			Bok.setLayoutData(BokLData);
			Bok.setText("OK");
			Bok.addSelectionListener( new SelectionAdapter() {
				@Override
                public void widgetSelected(final SelectionEvent evt) {
					BokWidgetSelected(evt);
				}
			});

			final GridData BcancelLData = new GridData();
			BcancelLData.verticalAlignment = GridData.CENTER;
			BcancelLData.horizontalAlignment = GridData.END;
			BcancelLData.widthHint = -1;
			BcancelLData.heightHint = -1;
			BcancelLData.horizontalIndent = 0;
			BcancelLData.horizontalSpan = 1;
			BcancelLData.verticalSpan = 1;
			BcancelLData.grabExcessHorizontalSpace = false;
			BcancelLData.grabExcessVerticalSpace = false;
			Bcancel.setLayoutData(BcancelLData);
			Bcancel.setText("Cancel");
			Bcancel.addSelectionListener( new SelectionAdapter() {
				@Override
                public void widgetSelected(final SelectionEvent evt) {
					BcancelWidgetSelected(evt);
				}
			});
			final GridLayout buttonCompositeLayout = new GridLayout(3, true);
			buttonComposite.setLayout(buttonCompositeLayout);
			buttonCompositeLayout.marginWidth = 5;
			buttonCompositeLayout.marginHeight = 5;
			buttonCompositeLayout.numColumns = 3;
			buttonCompositeLayout.makeColumnsEqualWidth = false;
			buttonCompositeLayout.horizontalSpacing = 5;
			buttonCompositeLayout.verticalSpacing = 5;
			buttonComposite.layout();
			final GridLayout dialogShellLayout = new GridLayout(1, true);
			dialogShell.setLayout(dialogShellLayout);
			dialogShellLayout.marginWidth = 5;
			dialogShellLayout.marginHeight = 5;
			dialogShellLayout.numColumns = 1;
			dialogShellLayout.makeColumnsEqualWidth = true;
			dialogShellLayout.horizontalSpacing = 5;
			dialogShellLayout.verticalSpacing = 5;
			dialogShell.layout();
			final Rectangle bounds = dialogShell.computeTrim(0, 0, 450,400);
			dialogShell.setSize(bounds.width, bounds.height);
			postInitGUI();
			dialogShell.open();
			final Display display = dialogShell.getDisplay();
			while (!dialogShell.isDisposed()) {
				if (!display.readAndDispatch()) {
                    display.sleep();
                }
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
	/** Add your pre-init code in here 	*/
	public void preInitGUI(){
	}

	/** Add your post-init code in here 	*/
	public void postInitGUI(){
        loadValuesFromConfig();
        centerOnParent();
	}

    protected void centerOnParent() {
        final int loc_x = gui.getShell().getLocation().x + 25;
        final int loc_y = gui.getShell().getLocation().y + 25;
        dialogShell.setLocation(loc_x, loc_y);
    }

	/** Auto-generated event handler method */
	protected void BokWidgetSelected(final SelectionEvent evt){
        saveValuesToConfig();
        dialogShell.close();
        dialogShell.dispose();
	}

	/** Auto-generated event handler method */
	protected void BcancelWidgetSelected(final SelectionEvent evt){
        dialogShell.close();
        dialogShell.dispose();
	}

	/** Auto-generated event handler method */
	protected void RBdirectWidgetSelected(final SelectionEvent evt){
		TproxyServer.setEnabled(false);
        TproxyPort.setEnabled(false);
        TsocksServer.setEnabled(false);
        TsocksPort.setEnabled(false);
	}

	/** Auto-generated event handler method */
	protected void RBproxyWidgetSelected(final SelectionEvent evt){
        TproxyServer.setEnabled(true);
        TproxyPort.setEnabled(true);
        TsocksServer.setEnabled(false);
        TsocksPort.setEnabled(false);
	}

	/** Auto-generated event handler method */
	protected void RBsocksWidgetSelected(final SelectionEvent evt){
        TproxyServer.setEnabled(false);
        TproxyPort.setEnabled(false);
        TsocksServer.setEnabled(true);
        TsocksPort.setEnabled(true);
	}

	/** Auto-generated event handler method */
	protected void RBupdateDBmanualWidgetSelected(final SelectionEvent evt){
        TupdateDBinterval.setEnabled(false);
	}

	/** Auto-generated event handler method */
	protected void RBupdateDBautoWidgetSelected(final SelectionEvent evt){
        TupdateDBinterval.setEnabled(true);
	}

	/** Auto-generated event handler method */
	protected void RBupdateWebManualWidgetSelected(final SelectionEvent evt){
		TupdateWebInterval.setEnabled(false);
	}

	/** Auto-generated event handler method */
	protected void RBupdateWebAutoWidgetSelected(final SelectionEvent evt){
        TupdateWebInterval.setEnabled(true);
	}

    protected void loadValuesFromConfig() {
        final Config c = this.origConfig;
        Taccountid.setText( c.getStrProperty("accountid") );

        CBupdateOnlyIfNewData.setSelection(c.getBooleanProperty("update_dont_store_unchanged_data"));

        if( c.getBooleanProperty("auto_db_update_enabled") ) {
            RBupdateDBauto.setSelection(true);
            RBupdateDBautoWidgetSelected(null);
        } else {
            RBupdateDBmanual.setSelection(true);
            RBupdateDBmanualWidgetSelected(null);
        }
        TupdateDBinterval.setText(""+c.getIntProperty("auto_db_update_minutes"));
        CBdeleteAfterUpdImport.setSelection(c.getBooleanProperty("auto_db_update_delete_updatedb_entries"));

        if( c.getBooleanProperty("auto_web_update_enabled") ) {
            RBupdateWebAuto.setSelection(true);
            RBupdateWebAutoWidgetSelected(null);
        } else {
            RBupdateWebManual.setSelection(true);
            RBupdateWebManualWidgetSelected(null);
        }
        TupdateWebInterval.setText(""+c.getIntProperty("auto_web_update_minutes"));

        if( c.getBooleanProperty("connection_use_proxy") ) {
            RBproxy.setSelection(true);
            RBproxyWidgetSelected(null);
        } else if( c.getBooleanProperty("connection_use_socks") ) {
            RBsocks.setSelection(true);
            RBsocksWidgetSelected(null);
        } else {
            RBdirect.setSelection(true);
            RBdirectWidgetSelected(null);
        }
        TproxyServer.setText( c.getStrProperty("connection_proxy_server") );
        TproxyPort.setText( ""+c.getIntProperty("connection_proxy_port") );
        TsocksServer.setText( c.getStrProperty("connection_socks_server") );
        TsocksPort.setText( ""+c.getIntProperty("connection_socks_port") );
    }

    protected void saveValuesToConfig() {
        final Config c = this.origConfig;
        c.setProperty("accountid", Taccountid.getText());

        c.setProperty("update_dont_store_unchanged_data", ""+CBupdateOnlyIfNewData.getSelection());

        c.setProperty("auto_db_update_enabled", ""+RBupdateDBauto.getSelection());
        c.setProperty("auto_db_update_minutes", TupdateDBinterval.getText());
        c.setProperty("auto_db_update_delete_updatedb_entries", ""+CBdeleteAfterUpdImport.getSelection());

        c.setProperty("auto_web_update_enabled", ""+RBupdateWebAuto.getSelection());
        c.setProperty("auto_web_update_minutes", TupdateWebInterval.getText());

        if( RBdirect.getSelection() ) {
            c.setProperty("connection_use_proxy", ""+false);
            c.setProperty("connection_use_socks", ""+false);
        } else if( RBproxy.getSelection() ) {
            c.setProperty("connection_use_proxy", ""+true);
            c.setProperty("connection_use_socks", ""+false);
        } else if( RBsocks.getSelection() ) {
            c.setProperty("connection_use_proxy", ""+false);
            c.setProperty("connection_use_socks", ""+true);
        }
        c.setProperty("connection_socks_server", TsocksServer.getText());
        c.setProperty("connection_socks_port", TsocksPort.getText());
        c.setProperty("connection_proxy_server", TproxyServer.getText());
        c.setProperty("connection_proxy_port", TproxyPort.getText());

        if( c.saveConfig() ) {
            BoincNetStats.out("Options saved.");
        } else {
            BoincNetStats.out("Error saving options!");
        }

        // update running parts
        c.setProxiesFromConfig();
        gui.setupTimers();
    }
}
