<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>

<html>
<head>
	<link href="${libPath}/ztree/css/zTreeStyle/metro.css" rel="stylesheet" type="text/css"/>
	<style type="text/css">
		.ztree li button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
	</style>
</head>
<body>	
<div class="" style="overflow-y:auto;height:547px;width:320px;">
	<div id="tree" style="position:relative;left:20px;width:300px;max-height:450px;overflow-y:auto;">
		<ul id="menuTree" class="ztree"></ul>
	</div>
	<div style="position:relative;height:5px;"></div>
	<div id="addNewNode" style="position:relative;left:20px;cursor:pointer;width:150px;margin-top:15px">
		<button type="button" class="fa fa-plus-circle" title="增加节点" ></button>
		增加子节点
	</div>
</div>

</body>
<div id="siteMeshJavaScript">
	<script src="${libPath}/ztree/js/jquery.ztree.core-3.5.js"></script>
	<script src="${libPath}/ztree/js/jquery.ztree.excheck-3.5.js"></script>
	<script src="${libPath}/ztree/js/jquery.ztree.exedit-3.5.js"></script>
	<script type="text/javascript" >

		var setting = {
			view: {
				/*removeHoverDom: removeHoverDom,*/
				selectedMulti: false
			},
			edit: {
				enable: true,
				editNameSelectAll: true,
				drag:{
					prev : false,
					inner : false,
					next :false
				 }
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				beforeEditName: beforeEditName,
				beforeRemove: beforeRemove,
				onClick:queryOne
			}
		};
		//菜单节点 JSON格式
		var sampleNodes = ${menuTreeJSONString} ;
		var log, className = "dark";
		function beforeEditName(treeId, treeNode) {
			var id = treeNode.id ;
			var URl = "${ctxPath}/menu/toMenuModify.action?id="+id ;
			changeTheFrameSrcValue("mennudisplay",URl) ;
			return false;
		}
		function queryOne(event, treeId, treeNode) {
			var id = treeNode.id ;
			var URl = "${ctxPath}/menu/toMenuModify.action?id="+id ;
			changeTheFrameSrcValue("mennudisplay",URl) ;
			return false;
		}
		function beforeRemove(treeId, treeNode) {
			className = (className === "dark" ? "":"dark");
			var zTree = $.fn.zTree.getZTreeObj("menuTree");
			zTree.selectNode(treeNode);
			if(treeNode.isParent){
				bootbox.alert("请先删除"+treeNode.name+"节点下的子菜单");
				return false;
			}else{
				bootbox.confirm("确认删除菜单 -- " + treeNode.name + " 吗？",function(res){
							if(res==true){
								deleteMenu(treeNode.id);
								return true;
							}else{
								window.parent.formSubmit();
							}
						}
				);
			}
		}

		function removeHoverDom(treeId, treeNode) {
		}
		function selectAll() {
			var zTree = $.fn.zTree.getZTreeObj("menuTree");
			zTree.setting.edit.editNameSelectAll =  $("#selectAll").attr("checked");
		}
		var csrfToken="${_csrf.token}";
		var csrfHeaderName="${_csrf.headerName}";
		function deleteMenu(id){
			$.ajax( {
				type : "POST",
				beforeSend: function(xhr) {
				  xhr.setRequestHeader(csrfHeaderName, csrfToken);
				},
				url : "${ctxPath}/api/1/menu/menuDelete",
				data : "id="+id,
				dataType: "text",
				success : function(data, textStatus, jqXHR) {
                    if(textStatus=='success'){
						bootbox.alert("删除菜单成功！", function () {
							window.parent.formSubmit();
						});
					}else{
						bootbox.alert("删除菜单失败！", function () {
							window.parent.formSubmit();
						});
					}
				}
			});
			window.parent.refresh("mennudisplay","")
		}

		$(function(){
			var tree=$.fn.zTree.init($("#menuTree"), setting, sampleNodes);
			tree.expandAll(true);
			/**添加节点**/
			$("#addNewNode").click(function addMenu(){
				var parentNodes  = tree.getSelectedNodes();
                var parentNode=parentNodes[0];
				if(!parentNode){
					bootbox.alert("请选中一个节点！") ;
					return false ;
				}
				var parentId = parentNode.id ;
				var level = parentNode.menulevel;
				var URl = "${ctxPath}/menu/toAddMenu.action?targetParentId="+parentId+"&targetParentLevel="+level+"&parentNodeName="+parentNode.name;
				changeTheFrameSrcValue("mennudisplay",URl) ;
			}) ;
		});

		function changeTheFrameSrcValue(frameId,questURL){
			window.parent.refresh(frameId, questURL);
		}
	</script>

</div>
</html>
