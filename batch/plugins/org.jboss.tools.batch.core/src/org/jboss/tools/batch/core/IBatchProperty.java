/*************************************************************************************
 * Copyright (c) 2015 Red Hat, Inc. and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     JBoss by Red Hat - Initial implementation.
 ************************************************************************************/
package org.jboss.tools.batch.core;

import java.util.Collection;

import org.eclipse.jdt.core.IField;
import org.jboss.tools.common.java.IAnnotationDeclaration;
import org.jboss.tools.common.text.ITextSourceReference;

/**
 * 
 * @author Viacheslav Kabanovich
 *
 */
public interface IBatchProperty {

	/**
	 * Returns Inject declaration at Java field.
	 * @return
	 */
	public IAnnotationDeclaration getInjectDeclaration();

	/**
	 * Returns BatchProperty declaration at Java field.
	 * @return
	 */
	public IAnnotationDeclaration getBatchPropertyDeclaration();

	/**
	 * Returns name set by BatchProperty annotation or default name.
	 * @return
	 */
	public String getPropertyName();

	/**
	 * Returns Java field representing this property.
	 * @return
	 */
	public IField getField();

	/**
	 * Returns parent artifact.
	 * @return
	 */
	public IBatchArtifact getArtifact();

	/**
	 * Returns references to this property by its name returned by getPropertyName() method,
	 * scanning all job XML files declared by the current project.
	 * At present, implementation does the scan at every request to avoid it at build.
	 * 
	 * @return
	 */
	public Collection<ITextSourceReference> getReferences();
}
