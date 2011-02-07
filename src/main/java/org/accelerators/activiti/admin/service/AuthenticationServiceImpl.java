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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.activiti.engine.IdentityService;

/**
 * Provides access to the activiti authentication service.
 * 
 * @author Patrick Oberg
 * 
 */
@Component(value = "authenticationService")
@Scope(value = "singleton")
public class AuthenticationServiceImpl implements AuthenticationService {

	private static final long serialVersionUID = 1L;

	@Autowired
	private IdentityService identityService;

	/**
	 * Checks the user credentials and authenticates the user if successful
	 * 
	 * @return <tt>true</tt> only if the authentication was successful
	 */
	public boolean authenticate(String username, String password) {

		if (getIdentityService() != null) {

			// Check user
			if (!getIdentityService().checkPassword(username, password)) {

				// Login failed
				return false;
			} else {
				// Authenticate the session
				getIdentityService().setAuthenticatedUserId(username);

				// Login successful
				return true;
			}
		} else {
			// Login failed
			return false;
		}
	}
	
	/**
	 * Get activiti identity service
	 * 
	 * @return the identityService
	 */
	private IdentityService getIdentityService() {
		return identityService;
	}

}