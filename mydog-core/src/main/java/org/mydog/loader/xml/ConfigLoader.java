package org.mydog.loader.xml;

import java.util.List;
import java.util.Map;

import org.mydog.model.CommentModel;
import org.mydog.model.GeneratorModel;
import org.mydog.model.JdbcConfig;

public interface ConfigLoader {
	
	CommentModel getCommentModel();
	
	
	Map<String,List<GeneratorModel>> getGeneratorModel();
	
	
	public JdbcConfig getJdbcConfig() ;
	
}
