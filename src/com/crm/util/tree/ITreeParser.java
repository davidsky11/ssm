package com.crm.util.tree;

 
public interface ITreeParser {
 
	TreeNode parse(Object data, int dataType);
	TreeNode parse(Object data, int dataType, String url,String classes, String target, String rel);

}
