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
package org.accelerators.activiti.servlet;

import com.vaadin.Application;
import com.vaadin.terminal.gwt.server.AbstractApplicationServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Servlet providing Activiti and Spring integration with Vaadin
 *
 * @author Patrick Oberg
 *
 */
public class SpringApplicationServlet extends AbstractApplicationServlet {

	private static final long serialVersionUID = 1L;
	private WebApplicationContext applicationContext;
    private Class<? extends Application> applicationClass;
    private String applicationBean;

    @SuppressWarnings("unchecked")
	@Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        applicationBean = servletConfig.getInitParameter("applicationBean");
        if (applicationBean == null) {
            throw new ServletException(
                    "ApplicationBean not specified in servlet parameters");
        }

        applicationContext = WebApplicationContextUtils.getWebApplicationContext(
                servletConfig.getServletContext());

        applicationClass = (Class<? extends Application>) applicationContext.getType(applicationBean);
    }

    @Override
    protected Class<? extends Application> getApplicationClass() throws
            ClassNotFoundException {
        return applicationClass;
    }

    @Override
    protected Application getNewApplication(HttpServletRequest request) {
        return (Application) applicationContext.getBean(applicationBean);
    }
}
