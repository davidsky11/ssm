package com.crm.util.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.crm.domain.system.SysDictionary;

/** 
 * @ClassName	TreeUtils.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年9月29日 下午10:11:39
 * @Version 	V1.0    
 */
public class TreeUtils {

	public static Tree formatDicToTree(SysDictionary dic, boolean checked){
		Tree tree = new Tree();
		tree.setId(dic.getId());
		tree.setName(dic.getEntryvalue());
		tree.setpId(dic.getParentid());
		if(checked){
			tree.setChecked(true);
		}else
			tree.setChecked(false);
		return tree;
	}
	
	/**
	 * @param uncheckedResources
	 * @param checkedResources
	 * @return
	 */
	public static List<Tree> fomatDicToTree(List<SysDictionary> uncheckedDics, Set<SysDictionary> checkedDics){
		List<Tree> trees=new ArrayList<Tree>();
		for(SysDictionary res:uncheckedDics){
			Tree tree=TreeUtils.formatDicToTree(res,false);
			trees.add(tree);
		}
		for(SysDictionary res:checkedDics){
			Tree tree=TreeUtils.formatDicToTree(res,true);
			trees.add(tree);
		}
		return trees;
	}
	
	public static List<Tree> fomatDicToTree(List<SysDictionary> resources){
		List<Tree> trees=new ArrayList<Tree>();
		Tree root=new Tree();
		root.setId(0);
		root.setpId(-1);
		root.setName("字典列表");
		root.setChecked(false);
		
		trees.add(root);
		for(SysDictionary res : resources){
			Tree tree=TreeUtils.formatDicToTree(res,false);
			trees.add(tree);
		}
		return trees;
	}
}
 