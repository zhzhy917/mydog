package org.mydog.ui;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import freemarker.cache.TemplateLoader;

public class SpringTemplateLoader implements TemplateLoader {

	protected final Log logger = LogFactory.getLog(getClass());

	private final ResourceLoader resourceLoader;

	private final String templateLoaderPath;

	/**
	 * Create a new SpringTemplateLoader.
	 * 
	 * @param resourceLoader
	 *            the Spring ResourceLoader to use
	 * @param templateLoaderPath
	 *            the template loader path to use
	 */
	public SpringTemplateLoader(ResourceLoader resourceLoader, String templateLoaderPath) {
		this.resourceLoader = resourceLoader;
		if (!templateLoaderPath.endsWith("/")) {
			templateLoaderPath += "/";
		}
		this.templateLoaderPath = templateLoaderPath;
		if (logger.isInfoEnabled()) {
			logger.info("SpringTemplateLoader for FreeMarker: using resource loader [" + this.resourceLoader
					+ "] and template loader path [" + this.templateLoaderPath + "]");
		}
	}

	@Override
	public Object findTemplateSource(String name) throws IOException {
		if (logger.isDebugEnabled()) {
			logger.debug("Looking for FreeMarker template with name [" + name + "]");
		}
		Resource resource = this.resourceLoader.getResource(this.templateLoaderPath + name);
		
		if (!resource.exists()) {
			try {
				 
				resource = new FileSystemResource(new File("").getAbsolutePath() + "/src/main/resources/" + name) ; 
				if (!resource.exists()) {
					return null; 
				}
				return resource;
			} catch (Exception e) {
			}
			return null; 
		}

		return resource;
	}

	@Override
	public Reader getReader(Object templateSource, String encoding) throws IOException {
		Resource resource = (Resource) templateSource;
		try {
			return new InputStreamReader(resource.getInputStream(), "UTF-8" );
		} catch (IOException ex) {
			if (logger.isDebugEnabled()) {
				logger.debug("Could not find FreeMarker template: " + resource);
			}
			throw ex;
		}
	}

	@Override
	public long getLastModified(Object templateSource) {
		Resource resource = (Resource) templateSource;
		try {
			return resource.lastModified();
		} catch (IOException ex) {

			return -1;
		}
	}

	@Override
	public void closeTemplateSource(Object templateSource) throws IOException {
	}

}