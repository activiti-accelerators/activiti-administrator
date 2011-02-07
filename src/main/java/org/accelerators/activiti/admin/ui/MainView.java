/*
 * BPM Technology Accelerators Development Team, BPM Technology 
 * Accelerators Community and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution 
 * for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.accelerators.activiti.admin.ui;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.Reindeer;
import org.accelerators.activiti.admin.AdminApp;
import org.accelerators.activiti.admin.ui.i18n.Messages;

/**
 * Main view of the application
 * 
 * @author Patrick Oberg
 * 
 */
public class MainView extends VerticalLayout implements ClickListener {

	private static final long serialVersionUID = 1L;

	private AdminApp app;

	// Buttons
	private Button logout = new Button("Logout", (ClickListener) this);

	public MainView(AdminApp application) {

		// Set application
		this.app = application;

		// Setup main layout
		setStyleName(Reindeer.LAYOUT_WHITE);
		setMargin(false);
		setSpacing(false);
		setSizeFull();

		// Add header
		GridLayout header = new GridLayout(2, 1);
		header.setWidth("100%");
		header.setHeight("34px");
		addComponent(header);

		// Add header styles
		header.addStyleName(Consts.HEADER);
		header.addStyleName("header");
		header.setSpacing(true);

		// Add title to header
		GridLayout logoGrid = new GridLayout(1, 1);
		header.addComponent(logoGrid, 0, 0);
		header.setComponentAlignment(logoGrid, Alignment.MIDDLE_LEFT);

		Embedded logoImage = new Embedded(null, new ThemeResource(
				"img/header-logo.png"));
		logoImage.setType(Embedded.TYPE_IMAGE);
		logoImage.addStyleName("header-image");
		logoGrid.addComponent(logoImage, 0, 0);
		logoGrid.setComponentAlignment(logoImage, Alignment.MIDDLE_CENTER);

		// Add logout button to header
		GridLayout logoutGrid = new GridLayout(2, 1);
		Label userLabel = new Label("Signed in as: " + app.getUser().toString());
		userLabel.addStyleName("user");
		logout.setStyleName(Reindeer.BUTTON_LINK);
		logout.addStyleName("logout");
		logoutGrid.addComponent(userLabel, 0, 0);
		logoutGrid.addComponent(logout, 1, 0);
		header.addComponent(logoutGrid, 1, 0);
		header.setComponentAlignment(logoutGrid, Alignment.TOP_RIGHT);

		// Create tab sheet
		TabSheet tabs = new TabSheet();
		tabs.setSizeFull();

		// Add tab styles
		tabs.addStyleName(Reindeer.TABSHEET_BORDERLESS);
		tabs.addStyleName(Reindeer.LAYOUT_WHITE);

		// Add task view tab
		tabs.addTab(new UserTab(app));
		tabs.addTab(new GroupTab(app));

		// Add tab sheet to layout
		addComponent(tabs);
		setExpandRatio(tabs, 1.0F);

		// Add footer text
		Label footerText = new Label(app.getMessage(Messages.Footer));
		footerText.setSizeUndefined();
		footerText.setStyleName(Reindeer.LABEL_SMALL);
		addComponent(footerText);
		setComponentAlignment(footerText, Alignment.BOTTOM_CENTER);

	}

	@Override
	public void buttonClick(ClickEvent event) {

		Button source = event.getButton();

		if (source == logout) {
			app.getMainWindow().getApplication().close();
		}
	}
}
