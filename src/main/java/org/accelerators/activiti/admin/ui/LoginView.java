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

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.accelerators.activiti.admin.AdminApp;
import org.accelerators.activiti.admin.ui.i18n.Messages;

/**
 * Login view
 * 
 * @author Patrick Oberg
 * 
 */
public class LoginView extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	private final AdminApp app;

	private Button login = new Button("Login");
	private TextField username;
	private PasswordField password;

	@SuppressWarnings("serial")
	public LoginView(AdminApp application) {

		// Set application reference
		this.app = application;

		// Init window caption
		app.getMainWindow().setCaption(app.getMessage(Messages.Title));

		// Set layout to full size
		setSizeFull();
		
		// Set style
		this.setStyleName("login-background");

		// Add header bar
		final HorizontalLayout header = new HorizontalLayout();
		header.setHeight("50px");
		header.setWidth("100%");
		addComponent(header);
		setComponentAlignment(header, Alignment.MIDDLE_CENTER);

		// Setup grid
		GridLayout loginGrid = new GridLayout(1, 2);
		loginGrid.setWidth("250px");
		addComponent(loginGrid);
		setComponentAlignment(loginGrid, Alignment.MIDDLE_CENTER);

		// Add title to header
		GridLayout logoGrid = new GridLayout(1, 1);
		loginGrid.addComponent(logoGrid, 0, 0);
		loginGrid.setComponentAlignment(logoGrid, Alignment.MIDDLE_CENTER);

		Embedded logoImage = new Embedded(null, new ThemeResource(
				"img/login-logo.png")); 
		logoImage.setType(Embedded.TYPE_IMAGE);
		logoImage.addStyleName("login-image");
		logoGrid.addComponent(logoImage, 0, 0);
		logoGrid.setComponentAlignment(logoImage, Alignment.MIDDLE_CENTER);

		// Add field and button layout
		VerticalLayout buttonLayout = new VerticalLayout();
		buttonLayout.setSizeFull();
		buttonLayout.setSpacing(true);
		buttonLayout.setMargin(false);
		buttonLayout.setStyleName("login-form");
		loginGrid.addComponent(buttonLayout, 0, 1);
		loginGrid.setComponentAlignment(buttonLayout, Alignment.MIDDLE_CENTER);

		// Add username field
		username = new TextField(app.getMessage(Messages.Username));
		username.setWidth("100%");
		buttonLayout.addComponent(username);

		// Add password field
		password = new PasswordField(app.getMessage(Messages.Password));
		password.setWidth("100%");
		buttonLayout.addComponent(password);

		// Add Login button
		buttonLayout.addComponent(login);
		buttonLayout.setComponentAlignment(login, Alignment.BOTTOM_RIGHT);

		// Set focus to this component
		username.focus();
		
		// Add shortcut to login button
		login.setClickShortcut(KeyCode.ENTER);

		login.addListener(new Button.ClickListener() {
			public void buttonClick(Button.ClickEvent event) {
				try {

					// Athenticate the user
					authenticate((String) username.getValue(),
							(String) password.getValue());

					// Switch to the main view
					app.getViewManager().switchScreen(MainView.class.getName(),
							new MainView(app));

				} catch (Exception e) {
					getWindow().showNotification(e.toString());
				}
			}
		});
		
		HorizontalLayout footer = new HorizontalLayout();
		footer.setHeight("50px");
		footer.setWidth("100%");
		addComponent(footer);

	}

	/**
	 * Checks the user credentials and authenticates the user if successful.
	 * 
	 * @param username
	 * @param password
	 * @throws Exception
	 */
	public void authenticate(String username, String password) throws Exception {

		if (app.getAuthenticationService() == null) {
			throw new Exception(app.getMessage(Messages.ServiceUnavilable));
		} else if (!app.getAuthenticationService().authenticate(username, password)) {
			throw new Exception(app.getMessage(Messages.FailedLogin));
		} else {

			// Login successful, add user id to session
			app.setUser(username);

		}
	}
}