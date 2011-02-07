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

import java.util.HashSet;
import java.util.Set;
import com.vaadin.data.Item;
import com.vaadin.event.Action;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;
import org.accelerators.activiti.admin.AdminApp;
import org.accelerators.activiti.admin.ui.i18n.Messages;

/**
 * Group table
 * 
 * @author Patrick Oberg
 * 
 */
public class GroupTable extends Table implements CloseListener,
		ItemClickListener {

	private static final long serialVersionUID = 1L;

	static final Object[] columnHeaders = new Object[] { "id", "name", "type" };
	private final Action ACTION_EDIT;
	private final Action ACTION_DELETE;
	private final Action[] ACTIONS_UNMARKED;
	private final Action[] ACTIONS_MARKED;
	private final AdminApp app;

	private GroupEditForm form;
	private Window popupWindow;
	private HashSet<Object> markedRows = new HashSet<Object>();

	/**
	 * Creates the group table
	 */
	@SuppressWarnings("serial")
	public GroupTable(AdminApp application) {

		// Set application reference
		this.app = application;

		// Maximize the table width and height
		setSizeFull();

		setPageLength(size());

		// Allow selecting items from the table.
		setSelectable(true);

		// Remove border
		// addStyleName(Reindeer.TABLE_BORDERLESS);

		// Send changes in selection immediately to server.
		setImmediate(true);

		// Disable null selection
		setNullSelectionAllowed(false);

		// Allow column to collapse
		setColumnCollapsingAllowed(true);

		// Allow reordering of columns
		setColumnReorderingAllowed(true);

		// Add table data source
		setContainerDataSource(app.getAdminService().getGroups());

		// Set column headers
		setColumnHeader("id", "Id");
		setColumnHeader("name", "Name");
		setColumnHeader("type", "Type");

		// Set column headers alignment
		setColumnAlignment("id", ALIGN_LEFT);
		setColumnAlignment("name", ALIGN_LEFT);
		setColumnAlignment("type", ALIGN_LEFT);

		// Define the names and data types of columns.
		setVisibleColumns(columnHeaders);

		// Add item click listener for double-click on table row
		addListener((ItemClickListener) this);

		// Context menu actions must be set here to use i18n support
		ACTION_EDIT = new Action(app.getMessage(Messages.Edit));
		ACTION_DELETE = new Action(app.getMessage(Messages.Delete));
		ACTIONS_UNMARKED = new Action[] { ACTION_EDIT, ACTION_DELETE };
		ACTIONS_MARKED = new Action[] { ACTION_EDIT, ACTION_DELETE };

		// Actions (a.k.a context menu)
		addActionHandler(new Action.Handler() {

			public Action[] getActions(Object target, Object sender) {
				if (markedRows.contains(target)) {
					return ACTIONS_MARKED;
				} else {
					return ACTIONS_UNMARKED;
				}
			}

			public void handleAction(Action action, Object sender, Object target) {
				if (ACTION_EDIT.equals(action)) {
					editGroup(target);
				} else if (ACTION_DELETE.equals(action)) {
					deleteGroup(target);
				}
			}
		});

	}

	/**
	 * Edit the group
	 * 
	 * @param itemId
	 *            the BeanItem id of the group
	 */
	private void editGroup(Object itemId) {

		Item item = getItem(itemId);

		// Create form
		if (form == null) {
			form = new GroupEditForm(app);
		}

		if (item != form.getItemDataSource()) {

			// Set double-clicked item
			form.setItemDataSource(item);

			/* Create a new popup window. */
			popupWindow = new Window();
			popupWindow.center();
			popupWindow.setModal(true);
			popupWindow.setWidth("400px");
			popupWindow.addStyleName(Consts.POPUP);

			// Set caption
			popupWindow.setCaption(app.getMessage(Messages.EditGroup));

			// Add form
			popupWindow.addComponent(form);

			// Listen for close events for the window.
			popupWindow.addListener(this);
		}

		// Add the window inside the main window.
		app.getMainWindow().addWindow(popupWindow);

	}

	/**
	 * Delete the group
	 * 
	 * @param target
	 *            the BeanItem id of the group
	 */
	private void deleteGroup(final Object target) {

		// Get user id
		final String id = getItem(target).getItemProperty("id").toString();

		// Get unassigned tasks for the group to be deleted
		Set<String> unassignedTasks = app.getAdminService()
				.getUnassignedTaskIdsByGroup(id);

		// Create pre-formatted confirm dialog message about out deleting the
		// user
		String message = "";

		if (!unassignedTasks.isEmpty()) {

			// One or more tasks will be affected if deleting the group
			message = app.getMessage(Messages.DeleteGroupAffectsTasks)
					+ "<ul><li>" + app.getMessage(Messages.UnassignedTasks)
					+ unassignedTasks + "</li></ul><br/>";

		} else {

			// Info when no task is affected
			message = app.getMessage(Messages.DeleteGroupAffectsNoTasks)
					+ "<br/><br/>";

		}

		ConfirmDialog dialog = new ConfirmDialog(
				app.getMessage(Messages.ConfirmDelete) + id, message,
				app.getMessage(Messages.OkKey),
				app.getMessage(Messages.CancelKey),
				new ConfirmDialog.ConfirmationDialogCallback() {

					@Override
					public void response(boolean ok) {

						// Delete user confirmed?
						if (ok) {

							// Delete user
							app.getAdminService().deleteGroup(id);

							// Remove user from container
							app.getAdminService().getGroups()
									.removeItem(target);

							// Show confirmation
							app.getMainWindow().showNotification(
									app.getMessage(Messages.DeletedGroup) + id);
						}
					}
				});

		// add dialog style
		dialog.addStyleName(Consts.POPUP);

		// Open popup window
		app.getMainWindow().addWindow(dialog);

	}

	/**
	 * Refresh the groups from the data source
	 */
	public void refresh() {

		// Refresh groups
		app.getAdminService().refreshGroups();

		// Add table data source
		setContainerDataSource(app.getAdminService().getGroups());

		// Define the names and data types of columns.
		setVisibleColumns(columnHeaders);

	}

	@Override
	public void itemClick(ItemClickEvent event) {
		if (event.isDoubleClick()) {
			// Open form for editing the group
			editGroup(event.getItemId());
		}
	}

	@Override
	public void windowClose(CloseEvent e) {
		// TODO Auto-generated method stub

	}

}
