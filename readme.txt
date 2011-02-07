
Activiti Administrator
==========================================================
Version 1.0.Beta1, February 2011

This software is distributed under the terms of the FSF Lesser Gnu
Public License (see lgpl-3.0.txt). 

Introduction
------------

The goal of this project is to complement the existing Activiti BPM suite with simple and robust reference applications (Accelerators) that can be used to speed up customer implementations.

Each Accelerator is packaged as a standalone web application that can be deployed together with the Activiti BPM suite.

All Accelerators are open source and free to use under the LGPL3 license.

The main objective of the Administrator Accelerator is to provide capabilities to administer users and groups in the Activiti BPM suite.

Features
- Create users
- Edit user details (first name, last name and password)
- Add and remove user groups
- Delete users
- Impact analysis of deleting users
- Create groups
- Edit user details (name and type)
- Add and remove users from groups
- Impact analysis of deleting groups

Architecture
- Built with Vaadin, Spring and Activiti
- Spring dependency injection of Activiti Services
- Vaadin for the UI layer and wrapping of Activiti data in bean containers and bean items

Setup
-----

1. Download the latest version of Activiti http://www.activiti.org/download.html 
2. Setup Activiti by following the Activiti user guide http://activiti.org/userguide/index.html or by following the excellent screencast provided by Joram Barrez http://www.jorambarrez.be/blog/2011/01/24/getting-started-with-activiti/
3. Download the latest version of Administrator http://github.com/activiti-accelerators/activiti-administrator
4. Unzip the Administrator WAR folder (activiti-administrator-1.0.X.zip)
5. Copy the Administrator WAR folder to the webapps folder in Tomcat (activiti-install-dir/apps/apache-tomcat-6.0.29/webapps)
6. Point your browser to: http://localhost:8080/activiti-administrator
7. Login with username: kermit and password: kermit


Happy administration!
/Patrick Öberg, 2011-02-01
