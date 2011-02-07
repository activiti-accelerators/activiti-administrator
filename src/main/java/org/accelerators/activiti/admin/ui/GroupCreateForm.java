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

import java.util.Set;
import com.vaadin.data.Item;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.AbstractSelect.Filtering;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import org.accelerators.activiti.admin.AdminApp;
import org.accelerators.activiti.admin.ui.i18n.Messages;

/**
 * Form for creating groups
 * 
 * @author Patrick Oberg
 * 
 */
@SuppressWarnings("unchecked")
public class GroupCreateForm extends Form implements ClickListener {

	private static final long serialVersionUID = 1L;
	private static final String[] visibleItems = new String[] { "id", "name",
			"type", "users" };

	private final Button create;
	private final Button close;
	private final Button reset;
	private final ComboBox groupTypes;;
	private final String[] types;
	private final AdminApp app;

	private TwinColSelect members;

	/**
	 * 
	 * @param app
	 */
	public GroupCreateForm(AdminApp application) {

		// Set application reference
		this.app = application;

		// Enable buffering so that commit() must be called for the form.
		setWriteThrough(false);

		// Set the form to act immediately on user input.
		setImmediate(true);

		// Set form size
		setSizeFull();

		// Setup footer layout
		HorizontalLayout footer = new HorizontalLayout();
		footer.setSpacing(true);
		footer.setWidth("100%");
		footer.setVisible(true);

		// Add footer
		setFooter(footer);

		// Init buttons
		create = new Button(app.getMessage(Messages.Create),
				(ClickListener) this);
		close = new Button(app.getMessage(Messages.Close), (ClickListener) this);
		reset = new Button(app.getMessage(Messages.Reset), this, "discard");

		// Set button grid
		GridLayout grid = new GridLayout(3, 1);
		grid.addComponent(create, 0, 0);
		grid.addComponent(reset, 1, 0);
		grid.addComponent(close, 2, 0);
		grid.setSpacing(true);

		// Add grid to footer
		footer.addComponent(grid);

		// Right align buttons in footer
		footer.setComponentAlignment(grid, Alignment.BOTTOM_RIGHT);

		// Init group types
		types = new String[] { app.getMessage(Messages.Assignment),
				app.getMessage(Messages.Program),
				app.getMessage(Messages.Project),
				app.getMessage(Messages.Role), app.getMessage(Messages.Team),
				app.getMessage(Messages.Unit) };

		// Create combo box for group types
		groupTypes = new ComboBox("type");

		groupTypes.setFilteringMode(Filtering.FILTERINGMODE_STARTSWITH);
		for (int i = 0; i < types.length; i++) {
			groupTypes.addItem(types[i]);
		}

		// Propagate changes directly
		groupTypes.setImmediate(true);

		// Allow adding new group types
		groupTypes.setNewItemsAllowed(true);

		// Get available users
		members = new TwinColSelect(app.getMessage(Messages.Members), app
				.getAdminService().getUsers());

		// Set column headers
		members.setLeftColumnCaption(app.getMessage(Messages.AvailableUsers));
		members.setRightColumnCaption(app.getMessage(Messages.GroupMembers));

		// Propagate changes directly
		members.setImmediate(true);

		// Set max width
		members.setWidth("100%");

		// Field factory for over riding how fields are created
		setFormFieldFactory(new DefaultFieldFactory() {

			private static final long serialVersionUID = 1L;

			@Override
			public Field createField(Item item, Object propertyId,
					Component uiContext) {

				if (propertyId.equals("type")) {
					groupTypes.setWidth("100%");
					groupTypes.setRequired(false);
					groupTypes.setCaption(app.getMessage(Messages.Types));
					return groupTypes;
				}

				Field field = super.createField(item, propertyId, uiContext);

				if (propertyId.equals("id")) {
					TextField tf = (TextField) field;

					// Do not display "null" to the user when the field is empty
					tf.setNullRepresentation("");

					// Set as required field
					tf.setRequired(true);

					// Set error message
					tf.setRequiredError("Id is missing");

					// Set read only
					tf.setReadOnly(false);

				} else if (propertyId.equals("name")) {
					TextField tf = (TextField) field;

					// Do not display "null" to the user when the field is empty
					tf.setNullRepresentation("");

					// Set as required field
					tf.setRequired(true);

					// Set error message
					tf.setRequiredError(app
							.getMessage(Messages.GroupNameIsMissing));

				}

				field.setWidth("100%");
				return field;
			}
		});
	}

	@Override
	public void setItemDataSource(Item item) {

		if (item != null) {

			// Set data source
			super.setItemDataSource(item);

			// Ensure that the fields are shown in correct order
			setVisibleItemProperties(visibleItems);

			// Attach non-bean fields
			attachField("members", members);

			// No members selected per default
			members.setValue(null);

			// Set focus on field
			getField("id").focus();

		} else {
			super.setItemDataSource(null);
		}

	}

	@Override
	public void buttonClick(ClickEvent event) {

		// Get source event
		Button source = event.getButton();

		if (source == create) {

			/* If the given input is not valid there is no point in continuing */
			if (!isValid()) {
				return;
			}

			// Commit the form
			commit();

			try {

				// Create group
				app.getAdminService().saveGroup(getItemDataSource(),
						(Set<String>) members.getValue());

				// Show notification
				app.getMainWindow().showNotification(
						app.getMessage(Messages.GroupCreatedMessage)
								+ getItemDataSource().getItemProperty("id")
										.toString());

				// Clear data source
				setItemDataSource(null);

				// Clear groups
				members.setValue(null);

				// Close window
				app.getMainWindow().removeWindow(getWindow());

			} catch (InvalidValueException e) {
				// Failed to commit. The validation errors are
				// automatically shown to the user.
			}
		} else if (source == close) {

			// Clear data source
			setItemDataSource(null);

			// Clear groups
			members.setValue(null);

			// Close window
			app.getMainWindow().removeWindow(getWindow());
		}
	}

}
