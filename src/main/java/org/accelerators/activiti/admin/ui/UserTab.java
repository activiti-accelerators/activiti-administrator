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

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;
import org.accelerators.activiti.admin.AdminApp;
import org.accelerators.activiti.admin.ui.i18n.Messages;

/**
 * User tab provides access to user table and user form
 * 
 * @author Patrick Oberg
 * 
 */
public class UserTab extends CustomComponent implements CloseListener,
		ValueChangeListener, ClickListener {

	private static final long serialVersionUID = 1L;

	private final Button create;
	private final Button refresh;
	private final AdminApp app;

	private UserTable table;
	private UserCreateForm form;
	private Window popupWindow;

	public UserTab(AdminApp application) {

		// Set application reference
		this.app = application;

		// Set tab name
		setCaption(app.getMessage(Messages.Users));

		// Add main layout
		VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setSpacing(true);
		layout.setSizeFull();

		// Add toolbar layout
		GridLayout toolbar = new GridLayout(2, 1);
		toolbar.setWidth("100%");
		layout.addComponent(toolbar);

		// Add create button
		create = new Button(app.getMessage(Messages.Create),
				(ClickListener) this);
		create.setDescription(app.getMessage(Messages.CreateUser));
		create.setIcon(new ThemeResource("../runo/icons/16/ok.png"));
		toolbar.addComponent(create, 0, 0);
		toolbar.setComponentAlignment(create, Alignment.TOP_LEFT);

		// Add refresh button
		refresh = new Button(app.getMessage(Messages.Refresh),
				(ClickListener) this);
		refresh.setDescription(app.getMessage(Messages.RefreshTable));
		refresh.setIcon(new ThemeResource("../runo/icons/16/reload.png"));
		toolbar.addComponent(refresh, 1, 0);
		toolbar.setComponentAlignment(refresh, Alignment.TOP_RIGHT);

		// Add table
		table = new UserTable(app);
		table.setSizeFull();
		layout.addComponent(table);

		// Set table to expand
		layout.setExpandRatio(table, 1.0f);

		// Root
		setCompositionRoot(layout);
	}

	@Override
	public void buttonClick(ClickEvent event) {

		Button source = event.getButton();

		if (source == create) {

			/* Create a new popup window. */
			popupWindow = new Window();
			popupWindow.center();
			popupWindow.setModal(true);
			popupWindow.setWidth("400px");
			popupWindow.addStyleName(Consts.POPUP);

			// Set caption
			popupWindow.setCaption(app.getMessage(Messages.CreateUser));

			// Create form
			if (form == null) {
				form = new UserCreateForm(app);
			}
			
			// Init form
			form.setItemDataSource(app.getAdminService().newUser());

			// Add form
			popupWindow.addComponent(form);

			// Add the window inside the main window.
			app.getMainWindow().addWindow(popupWindow);

			// Listen for close events for the window.
			popupWindow.addListener(this);

		} else if (source == refresh) {

			// Refresh table
			table.refresh();
		}
	}

	@Override
	public void windowClose(CloseEvent e) {
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
	}
}
