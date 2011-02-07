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
package org.accelerators.activiti.admin.service;

import java.util.Set;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.BeanItem;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;

/**
 * Administration service interface
 * 
 * @author Patrick Oberg
 * 
 */
public interface AdminService {

	BeanContainer<String,Group> getGroups();

	void refreshGroups();

	BeanContainer<String,User> getUsers();

	void refreshUsers();

	Set<String> getGroupMembers(String groupId);

	Set<String> getUserGroups(String userId);

	void updateUserGroups(String user, Set<String> groups);
	
	Set<String> getAssignedTaskIds(String id);
	
	Set<String> getUnassignedTaskIds(String id);
	
	void deleteUser(String id);
	
	void saveUser(Item user, Set<String> groups);
		
	Set<String> getUnassignedTaskIdsByGroup(String id);
	
	void deleteGroup(String id);
		
	void saveGroup(Item group, Set<String> groups);
		
	void updateGroupMembers(String id, Set<String> users);
	
	BeanItem<Group> newGroup();
	
	BeanItem<User> newUser();
	
}
