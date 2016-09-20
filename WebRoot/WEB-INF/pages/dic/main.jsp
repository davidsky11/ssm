<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	String path = request.getContextPath();
	request.setAttribute("path",path);
%>

<script type="text/javascript">
	rootPath='<%=path%>';
</script>

<style type="text/css">
    ul.rightTools {float:right; display:block;}
    ul.rightTools li{float:left; display:block; margin-left:5px}
</style>

<div class="pageContent" >
    <div class="panelBar" >
        <ul class="toolBar">
            <li><a class="add" href="javascript: addDictionary()" ><span>添加</span></a></li>
            <li><a class="delete" href="javascript: removeDictionary()" ><span>删除</span></a></li>
            <li class="line">line</li>
        </ul>
    </div>
    <div class="tabs" >
        <div class="tabsContent"  >
            <div>
                <div layoutH="38" style="float:left; display:block; overflow:scroll; width:300px; border:solid 1px #CCC; line-height:21px; background:#fff;">
                <div>
                    <label>&nbsp;&nbsp;&nbsp;&nbsp;</label>
                    </div>
                    <table>
                      <tr>
                          <td>
                              <label style="font-size:13px;">&nbsp;&nbsp;字典名称:&nbsp;&nbsp;</label>
                          </td>
                          <td>
                              <input id="inputName" name="inputName" value="" type="hidden"/>
                              <input type="text" id="queryName" name="dic.entryvalue" value="" size="24" postField="keyword" suggestFields="entryvalue" 
                    				suggestUrl="getQueryDic.do" lookupGroup="dic" />&nbsp;&nbsp;
                          </td>
                          <td>
                            <div class="subBar">
                                 <ul>
                                     <li><div class="buttonActive">
                                             <div class="buttonContent">
                                                 <button type="button" onClick="queryDic()">查询</button>
                                             </div>
                                         </div>
                                     </li>
                                 </ul>
                               </div> 
                        </td>
                      </tr>
                  </table>
                  <div class="divider"></div>
                    <ul id="dic_recTree" class="ztree"></ul>
                 </div>
                <div layoutH="38" id="dic_recBox" class="unitBox" style="margin-left:310px;" ></div>    
            </div>
        </div>
        <div class="tabsFooter">
            <div class="tabsFooterContent"></div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //刷新树
    //_self: true(刷新当前选中的节点) false(刷新当前选中的父节点)
    function refreshTree(_self){
        var zTree = $.fn.zTree.getZTreeObj("dic_recTree"),
        nodes = zTree.getSelectedNodes(),
        treeNode = nodes[0];
        if (nodes.length != 0) {
            var leaf = treeNode.isleaf;
            parentNode = treeNode.getParentNode();
            if ("1" == leaf) {
                zTree.reAsyncChildNodes(parentNode,"refresh");
                zTree.selectNode(parentNode);
            } else {
                zTree.reAsyncChildNodes(_self?treeNode:parentNode, "refresh");
                zTree.selectNode(_self?treeNode:parentNode);
            }
        } else {
            getRecDicTree();
        }
        $('#dic_recBox').html('');
    }
    
    //新增修改删除提交后回调，主要用来刷新树
    function refreshDicAjaxDone(json){
    
         DWZ.ajaxDone(json);
       if (json.statusCode == DWZ.statusCode.ok){
           var _self = false;
           if ("_self" == json.callbackType) {
               _self = true; 
           } 
           refreshTree(_self);
       }
    }
    //新增方法
    
    function addDictionary(){
       
        // 选择节点信息取得
        var zTree = $.fn.zTree.getZTreeObj("dic_recTree"),
        nodes = zTree.getSelectedNodes(),
        treeNode = nodes[0];
        var treeNodeId = -1;
        var treeNodelvl = 0;
        if (nodes.length != 0) {
            treeNodeId = treeNode.id;
            if(!isNaN(parseInt(treeNodelvl))){
                treeNodelvl = treeNode.lvl;
            }
        }
        // 调用后台添加方法
        $("#dic_recBox").loadUrl(rootPath+"/addDic" ,{'id': treeNodeId, 'lvl': parseInt(treeNodelvl)+1}, function(){});
        
    }

    //删除方法
    function removeDictionary(){
        var zTree = $.fn.zTree.getZTreeObj("dic_recTree"),
        nodes = zTree.getSelectedNodes(),
        treeNode = nodes[0];
        if (nodes.length == 0) {
             alertMsg.warn("请先选择一个节点");
            return;
        }
     
        alertMsg.confirm("确定要删除吗?", {
            okCall: function(){
                ajaxTodo('removeDic.do?ids='+treeNode.id, refreshDicAjaxDone);
                $('#dic_recBox').html('');
            }
        });
        
    }
    
    //treeId是treeDemo
    function filter(treeId, parentNode, childNodes) {
        if (!childNodes) return null;
        for (var i=0, l=childNodes.length; i<l; i++) {
            if(undefined!=childNodes[i])childNodes[i].name = childNodes[i].name.replace('','');
        }
        return childNodes;
    }
    
    
    var dicSetting = {    
        data: {
            simpleData: {
                enable: true
            }
        },
        async: {
            enable: true,
            url:"dicTreeData.do",
            autoParam:["id", "name"]
        },
        callback: {
            asyncSuccess: zTreeOnAsyncSuccess,//异步加载成功的fun
            asyncError: zTreeOnAsyncError, //加载错误的fun
            onClick: onClick
        }
    };
    function beforeClick(treeId,treeNode){
        if(!treeNode.isParent){
            alertMsg.warn("请选择父节点"); 
            return false;
        }else{
            return true;
        }
    }
    function zTreeOnAsyncError(event, treeId, treeNode){
        alertMsg.error("异步加载失败");
    }
    function zTreeOnAsyncSuccess(event, treeId, treeNode, msg){

    } 
     
    function onClick(event, treeId, treeNode, clickFlag) {
        $("#dic_recBox").loadUrl(rootPath+"/modifyDic.do" ,{'id':treeNode.id}, function(){});
    }    
    
    function zTreeRegisterTarget(event, treeId, treeNode) {
           initUI($('#'+treeId));
    }
        
    $(document).ready(function(){
    	var zNodes =  eval(${dicTreeData});
        $.fn.zTree.init($("#dic_recTree"), dicSetting, zNodes);
    });
    
    function queryDic(){
        var queryname = $("#queryName").val().trim();
        if(!queryname){
        	getRecDicTree();
        }else{
        	getQueryDicTree(queryname);
        }
        
    }
    
    function getQueryDicTree(queryname) {
    	$('#dic_recBox').html('');
        $.ajax({
            type: "POST",
            url: rootPath+"/dicTreeData.do",
            data: {"id":-1,"queryname":queryname,
                "url":"modifyDic.do","target" : "ajax","rel" : "dic_recBox"},
            dataType: "json",
            complete: function(req, status){
                var zNodes =  eval(req.responseText);
                $.fn.zTree.init($("#dic_recTree"), dicSetting, zNodes);
            }
        });
    }
    
    function getRecDicTree() {
        $.ajax({
            type: "POST",
            url: rootPath+"/dicTreeData.do",
            data: {"id":-1,
                "url":"modifyDic.do?","target" : "ajax","rel" : "dic_recBox"},
            dataType: "json",
            complete: function(req, status){
                var zNodes =  eval(req.responseText);
                $.fn.zTree.init($("#dic_recTree"), dicSetting, zNodes);
            }
        });
    }
</script>