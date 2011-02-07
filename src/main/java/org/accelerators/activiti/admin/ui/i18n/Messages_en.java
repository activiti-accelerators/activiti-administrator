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
package org.accelerators.activiti.admin.ui.i18n;

public class Messages_en extends Messages {

	@Override
	public Object[][] getContents() {
		return contents_en;
	}

	static final Object[][] contents_en = {
			{ OkKey, "OK" },
			{ CancelKey, "Cancel" },
			{ Save, "Save" },
			{ Reset, "Reset" },
			{ Close, "Close" },
			{ Edit, "Edit" },
			{ Delete, "Delete" },
			{ Create, "Create" },
			{ Refresh, "Refresh" },
			{ RefreshTable, "Refresh Table" },
			{ ConfirmDelete, "Confirm Delete: " },

			// Application
			{ AppTitle, "activiti-administrator" },

			// LoginScreen
			{ Username, "Username" },
			{ Password, "Password" },
			{ Login, "Login" },
			{ LoginButton, "Login" },

			// Error messages
			{ FailedLogin, "Invalid username or password" },
			{ ServiceUnavilable, "Activiti service is not available" },
			{ UsernameIsMissing, "Username is missing" },
			{ PasswordIsMissing, "Password is missing" },
			{ EmailIsMissing, "Email is missing" },
			{ FirstNameIsMissing, "First name is missing" },
			{ LastNameIsMissing, "Last name is missing" },
			{ EmailFormatError, "Email must contain '@' and have full domain." },
			{
					InvalidUsername,
					"User id must contain 4 to 20 characters long of the following set [a-zA-Z0-9_-]." },

			// Application Header
			{ Title, "Administrator" },
			{ Subtitle, "Business Accelerators for Activiti" },
			{ Logout, "Logout" },
			{ SignedInMessage, "Signed in as:" },

			// Application Footer
			{ Footer,
					"Administrator, Business Accelerators for Activiti, Patrick Oberg, 2011" },

			// Users
			{ Users, "Users" },
			{ EditUser, "Edit User" },
			{ User, "User" },
			{ DeleteUserQuestion, "Delete User? " },
			{ DeletedUser, "Deleted User: " },
			{ DeleteUserAffectsNoTasks,
				"Deleting this user will not effect any existing tasks." },
			{ DeleteUserAffectsTasks,
					"Deleting this user may effect the following task(s): " },
			{ CreateUser, "Create User" },
			{ DeleteUser, "Delete User" },
			{ UserCreatedMessage, "Created User: " },
			{ UserUpdatedMessage, "Updated User: " },
			{ AvailableUsers, "Available Users" },

			// Groups
			{ Groups, "Groups" },
			{ EditGroup, "Edit Group" },
			{ Group, "Groups" },
			{ DeleteGroupQuestion, "Delete Group? " },
			{ DeletedGroup, "Deleted Group: " },
			{ DeleteGroupAffectsNoTasks,
					"Deleting this group will not effect any existing tasks." },
			{ DeleteGroupAffectsTasks,
					"Deleting this group may effect the following task(s): " },
			{ CreateGroup, "Create Group" },
			{ GroupCreatedMessage, "Created Group: " },
			{ GroupUpdatedMessage, "Updated Group: " },
			{ DeleteGroup, "Delete Group" },
			{ AvailableGroups, "Available Groups" },
			{ MemberOfGroups, "Member of Groups" },
			{ Members, "Members" },
			{ GroupMembers, "Group Members" },
			{ GroupNameIsMissing, "Group name is missing" },

			// Tasks
			{ Task, "Task" },
			{ Tasks, "Tasks" },
			{ AssignedTasks, "Assigned Tasks " },
			{ UnassignedTasks, "Unassigned Tasks " },

			// Types
			{ Role, "Role" }, { Team, "Team" }, { Unit, "Unit" },
			{ Assignment, "Assignment" }, { Project, "Project" },
			{ Program, "Program" },
			{ Types, "Types" },

	};
}
