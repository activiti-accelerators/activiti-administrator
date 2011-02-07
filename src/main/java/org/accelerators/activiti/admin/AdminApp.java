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
package org.accelerators.activiti.admin;

import java.util.Locale;
import java.util.ResourceBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.vaadin.Application;
import com.vaadin.ui.Window;
import org.accelerators.activiti.admin.service.AdminService;
import org.accelerators.activiti.admin.service.AuthenticationService;
import org.accelerators.activiti.admin.ui.Consts;
import org.accelerators.activiti.admin.ui.LoginView;
import org.accelerators.activiti.admin.ui.ViewManager;
import org.accelerators.activiti.admin.ui.i18n.Messages;

/**
 * Activiti Accelerator Administration Application
 * 
 * @author Patrick Oberg
 * 
 */
@Component(value = "adminApp")
@Scope(value = "session")
public class AdminApp extends Application {

	private static final long serialVersionUID = 1L;

	@Autowired
	AuthenticationService authenticationService;

	@Autowired
	AdminService adminService;

	// Internationalized strings
	ResourceBundle i18nBundle;

	// View manager that handlers different screens in the UI
	ViewManager viewManager;

	// Application main window
	Window mainWindow;

	@Override
	public void init() {

		// Set theme
		setTheme(Consts.THEME);

		// Set default locale
		setLocale(new Locale("en"));

		// Init resource bundle
		final ResourceBundle i18n = ResourceBundle.getBundle(
				Messages.class.getName(), getLocale());

		// Add title
		mainWindow = new Window(i18n.getString(Messages.AppTitle));

		// Set window to full size
		mainWindow.setSizeFull();

		// Set as main window
		setMainWindow(mainWindow);

		// Add window to view manager
		viewManager = new ViewManager(mainWindow);

		// Create the login screen
		viewManager
				.switchScreen(LoginView.class.getName(), new LoginView(this));

	}

	/**
	 * @return the authenticationService
	 */
	public AuthenticationService getAuthenticationService() {
		return authenticationService;
	}

	/**
	 * @return the adminService
	 */
	public AdminService getAdminService() {
		return adminService;
	}

	/**
	 * Returns the view manager used for controlling the different application
	 * views.
	 * 
	 * @return viewManager the view manager
	 */
	public ViewManager getViewManager() {
		// return view manager
		return viewManager;
	}

	@Override
	public void setLocale(Locale locale) {
		super.setLocale(locale);
		i18nBundle = ResourceBundle.getBundle(Messages.class.getName(),
				getLocale());
	}

	/** Returns the bundle for the current locale. */
	public ResourceBundle getBundle() {

		// Set to english by default
		if (i18nBundle == null) {
			setLocale(new Locale("en"));
		}

		// Return resource bundle
		return i18nBundle;
	}

	/**
	 * Returns a localized message from the resource bundle with the current
	 * application locale.
	 **/
	public String getMessage(String key) {
		// return resource bundle
		return i18nBundle.getString(key);
	}

}
