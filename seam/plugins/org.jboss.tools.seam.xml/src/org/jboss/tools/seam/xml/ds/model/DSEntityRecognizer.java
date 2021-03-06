package org.jboss.tools.seam.xml.ds.model;

import java.io.IOException;

import org.jboss.tools.common.model.loaders.EntityRecognizer;
import org.jboss.tools.common.model.loaders.EntityRecognizerContext;
import org.jboss.tools.common.model.loaders.XMLRecognizerContext;
import org.jboss.tools.common.xml.XMLEntityResolver;
import org.jboss.tools.seam.xml.SeamXMLPlugin;

public class DSEntityRecognizer implements EntityRecognizer, DSConstants {
    static {
        try {
            Class<?> c = DSEntityRecognizer.class;
            XMLEntityResolver.registerPublicEntity(PUBLIC_ID_1_5, c, "/meta/jboss-ds_1_5.dtd"); //$NON-NLS-1$
            XMLEntityResolver.registerPublicEntity(PUBLIC_ID_5_0, c, "/meta/jboss-ds_5_0.dtd"); //$NON-NLS-1$
        } catch (IOException e) {
			SeamXMLPlugin.log(e);
        }
    }

    public DSEntityRecognizer() {}

    public String getEntityName(EntityRecognizerContext context) {
    	String body = context.getBody();
        if(body == null) return null;
		XMLRecognizerContext xml = context.getXMLContext();
		if(xml.isDTD()) {
			String publicId = xml.getPublicId();
			String root = xml.getRootName();
			if(PUBLIC_ID_1_5.equals(publicId)) {
				if("datasources".equals(root)) return ENT_DATASOURCES_FILE;
				if("connection-factories".equals(root)) return ENT_CONNECTION_FACTORIES_FILE;
			}
			if(PUBLIC_ID_5_0.equals(publicId)) {
				if("datasources".equals(root)) return ENT_DATASOURCES_FILE_50_DTD;
				if("connection-factories".equals(root)) return ENT_CONNECTION_FACTORIES_FILE_50_DTD;
			}
		} else {
			if(isDatasourcesSchema(body)) {
				return ENT_DATASOURCES_FILE_50_XSD;
			} else if(isConnectionFactoriesSchema(body)) {
				return ENT_CONNECTION_FACTORIES_FILE_50_XSD;
			}
			
		}

		return null;
	}

    private boolean isDatasourcesSchema(String body) {
    	int i = body.indexOf("<datasources"); //$NON-NLS-1$
    	if(i < 0) return false;
    	int j = body.indexOf(">", i); //$NON-NLS-1$
    	if(j < 0) return false;
    	String s = body.substring(i, j);
    	return s.indexOf("http://www.jboss.org/j2ee/schema/jboss-ds_5_0.xsd") > 0; //$NON-NLS-1$
    }
    
    private boolean isConnectionFactoriesSchema(String body) {
    	int i = body.indexOf("<connection-factories"); //$NON-NLS-1$
    	if(i < 0) return false;
    	int j = body.indexOf(">", i); //$NON-NLS-1$
    	if(j < 0) return false;
    	String s = body.substring(i, j);
    	return s.indexOf("http://www.jboss.org/j2ee/schema/jboss-ds_5_0.xsd") > 0; //$NON-NLS-1$
    }
}
