package org.mydog.core;

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.mydog.loader.xml.XmlConfigLoader;

@Mojo(name = "touch", defaultPhase = LifecyclePhase.PROCESS_SOURCES)
public class MyMojo extends AbstractMojo {

	@Parameter(defaultValue = "${project.build.directory}", property = "outputDir", required = true)
	private File outputDirectory;
	
	@Parameter(property="configXml")
	private String configXml ;

	public void execute() throws MojoExecutionException {
		XmlConfigLoader.getLoader().setSchemaFile(new File(configXml).getAbsolutePath())
		.setOutputDirectory(outputDirectory).load() ;  
		
		MydogDataHanlder.getInstance().generate();
	}
}
