/******************************************************************************* 
 * Copyright (c) 2011 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.tools.cdi.seam.solder.core.test.v30;

import junit.extensions.TestSetup;
import junit.framework.Test;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.jboss.tools.common.base.test.validation.TestUtil;
import org.jboss.tools.test.util.JobUtils;
import org.jboss.tools.test.util.ResourcesUtils;

/**
 * @author Viacheslav Kabanovich
 */
public class SeamSolderTestSetup extends TestSetup {

	protected IProject project;
	protected IProject dependentProject;

	public SeamSolderTestSetup(Test test) {
		super(test);
	}

	@Override
	protected void setUp() throws Exception {
		project = ResourcesPlugin.getWorkspace().getRoot().getProject(SeamSolderTest.PROJECT_NAME);
		if(project == null || !project.exists()) {
			project = ResourcesUtils.importProject(SeamSolderTest.PLUGIN_ID, SeamSolderTest.PROJECT_PATH);
			TestUtil._waitForValidation(project);
		}
		dependentProject = ResourcesPlugin.getWorkspace().getRoot().getProject(SeamSolderTest.DEPENDENT_PROJECT_NAME);
		if(dependentProject == null || !dependentProject.exists()) {
			dependentProject = ResourcesUtils.importProject(SeamSolderTest.PLUGIN_ID, SeamSolderTest.DEPENDENT_PROJECT_PATH);
			TestUtil._waitForValidation(dependentProject);
		}
	}

	@Override
	protected void tearDown() throws Exception {
		boolean saveAutoBuild = ResourcesUtils.setBuildAutomatically(false);
		project.delete(true, true, null);
		dependentProject.delete(true, true, null);
		JobUtils.waitForIdle();
		ResourcesUtils.setBuildAutomatically(saveAutoBuild);
	}
}