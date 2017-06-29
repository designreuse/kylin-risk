<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<html>
<head>
    <title>规则组查询</title>
</head>
<body >
<div class="divContainer">
  <div class="wrapper wrapper-content animated fadeInRight backPageDiv">
    <div class="ibox-content m-b-sm border-bottom" >
    <form id="ruleForm" action="${ctxPath}/group/queryGroup"   data-table="queryTable">
      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
      <input type="hidden" name="rolename" id="rolename" value="${rolename}"/>
      <div class="row">
        <div class="col-sm-6">
          <div class="form-group">
            <label class="col-sm-2 control-label" >规则组名称</label>
             <div class="col-sm-10">
              <input type="text" id="groupname" name="groupname"  placeholder="规则组名称" class="form-control">
            </div>
          </div>
        </div>
        <div class="col-sm-6">
          <div class="form-group">
            <label class="col-sm-2 control-label" >规则组类型</label>
            <div class="col-sm-10">
              <dict:select dictcode="category" id="grouptype" name="grouptype"  nullLabel="请选择" nullOption="true" cssClass="form-control"></dict:select>
            </div>
          </div>
        </div>
      </div>
    <div class="row query-div">
      <a href="toAjaxAddGroup" class="ajaxback-requst" id="addGroup">
        <button type="button" class="btn btn-w-m btn-primary">添加</button>
      </a>
      <button type="submit" class="btn btn-w-m btn-success">查询</button>
      <button type="reset" class="btn btn-w-m btn-info">重置</button>
    </div>
    <div class="row">
      <div class="col-lg-12">
        <div class="ibox">
          <div class="ibox-content">
            <a href="#">
              <button type="button"  id ="deleteSelected" class="btn-white btn btn-xs">
                <i class="fa fa-trash-o"></i>&nbsp;&nbsp;<strong>删除</strong></button>
            </a>
            <br>
            <table id="queryTable" class="table table-striped table-bordered table-hover" cellspacing="0">
              <thead>
              <tr>
                <th></th>
                <th>规则组名称</th>
                <th>规则组类型</th>
                <th>业务类型</th>
                <th>生效时间</th>
                <th>失效时间</th>
                <th>版本号</th>
                <th>状态</th>
                <th>渠道</th>
                <th>产品</th>
                <th>创建人</th>
                <th>最后修改时间</th>
                <th>操作</th>
              </tr>
              </thead>
            </table>
          </div>
        </div>
      </div>
    </div>
    </form>
      </div>
  </div>

</div>
<script id="checkbox-tpl" type="text/x-handlebars-template">
  <input type="checkbox" name="choose" value="{{id}}"/>
</script>
<script id="sta-tpl" type="text/x-handlebars-template">
  <span class="label {{cssClass}}">{{text}}</span>
</script>
<script id="oper-tpl" type="text/x-handlebars-template">
  <div class="btn-group">
    {{#each func}}
    {{#isRight this.text this.status this.isexecute}}
    <button type="button" class="btn-white btn btn-xs" onclick="{{this.fn}}"><i class="fa {{this.cssClass}}"></i>&nbsp;&nbsp;{{this.text}}</button>
    {{else}}
    {{/isRight}}
    {{/each}}

  </div>
</script>
<script id="channel-tpl" type="text/x-handlebars-template">
  {{#with groupChannels}}
  {{#each this}}
  <p>{{channelname}}</p>
  {{/each}}
  {{/with}}
</script>
<script id="product-tpl" type="text/x-handlebars-template">
  {{#with groupChannels}}
  {{#each this}}
  <p>{{productname}}</p>
  {{/each}}
  {{/with}}
</script>

</body>
<div id="siteMeshJavaScript">
  <script src="${libPath}/layer/layer.min.js"></script>
  <script type="text/javascript">
    var csrfToken = "${_csrf.token}";
    var csrfHeaderName = "${_csrf.headerName}";
    var checkboxSource   = $("#checkbox-tpl").html();
    var checkBoxTemplate = Handlebars.compile(checkboxSource);
    var operTpl = $("#oper-tpl").html();
    var operTemplate = Handlebars.compile(operTpl);
    var statusSource   = $("#sta-tpl").html();
    var statusTemplate = Handlebars.compile(statusSource);
    var channelSource   = $("#channel-tpl").html();
    var channelTemplate = Handlebars.compile(channelSource);
    var productSource   = $("#product-tpl").html();
    var productTemplate = Handlebars.compile(productSource);

    Handlebars.registerHelper("isRight",function(v1,v2,v3,options){
      var rolename = $("#rolename").val();
      if(v1=="删除"&&(rolename!="MODIFIER"&&rolename!="SUPERMODIFIER")){
        return options.inverse(this);
      }else if(v1=="生成版本"&&v3!="00"){
        return options.inverse(this);
      }else{
        return options.fn(this);
      }
    });

    $(function(){
      $.ajaxBack();

      $("#deleteSelected").click(function(){

        var selectIds = "";
        var result = $("input[name='choose']:checked");
        if (result.length == 0) {
          bootbox.alert("请选择数据");
        } else {
          for(var i=0;i<result.length;i++){
            selectIds=selectIds+result[i].value+",";
          }
          selectIds = selectIds.substr(0,selectIds.length-1);
          deleteGroup(selectIds);
        }
      });
    });


    function getDetail(id){
      $.ajaxParamBack({"url":"${ctxPath}/group/toAjaxqueryGroupDetail","data":"id="+id});
     }
    function deleteGroup(ids){
      if (confirm("确定删除？")) {
      $.ajax({
        type: "POST",
        beforeSend: function (xhr) {
          xhr.setRequestHeader(csrfHeaderName, csrfToken);
        },
        url: "${ctxPath}/api/1/group/deleteGroup",
        data: "id=" + ids,
        dataType: "json",
        success: function (data, textStatus, jqXHR) {
          if (data.code==200) {
            bootbox.alert("删除成功！",function(){
              $('#ruleForm').submit();
            });
          } else {
            bootbox.alert("删除失败！");
          }
        }
      });
      }
    }
    function generatingVersion(id,isexecute){
      if(isexecute!="00"){
        bootbox.alert("规则组状态不符合");
      }else{
        bootbox.confirm("确认生成规则组版本吗？",function(res){
                  if(res==true){
                    execuGeneratingVersion(id);
                  }
                }
        );

      }
    }

    function execuGeneratingVersion(id){
      var ii = layer.load();
      $.ajax({
        type: "POST",
        beforeSend: function (xhr) {
          xhr.setRequestHeader(csrfHeaderName, csrfToken);
        },
        url: "${ctxPath}/api/1/group/generatingVersion",
        data: "id=" + id,
        dataType: "json",
        success: function (data, textStatus, jqXHR) {
          layer.close(ii);
          if (data.code==200) {
            bootbox.alert("生成版本成功！",function(){
              $('#ruleForm').submit();
            });
          } else {
            bootbox.alert("生成版本失败，请检查子规则！");
          }
        }
      });
    }
    $(function(){
      $('form#ruleForm').queryTables({
        "pageLength":10,
        "columns": [
          {"data": "id"},
          {"data": "groupname"},
          {"data": "grouptype"},
          {"data": "servicetype"},
          {"data": "effdate"},
          {"data": "expdate"},
          {"data": "version"},
          {"data": "status"},
          {"data": null},
          {"data": null},
          {"data": "createopername"},
          {"data": "updatetime"},
          {"data": null}
        ],columnDefs:[
          {
            targets:0,
            render:function(data, type, row, meta){
              return checkBoxTemplate({"id":data});
            }
          },
          {
            targets:3,
            render:function(data, type, row, meta){
              var dataMap={"01":{"cssClass":"label-default",text:"基本信息"},"02":{"cssClass":"label-default",text:"订单信息"},
                "03":{"cssClass":"label-default",text:"全国培训机构总部"},"04":{"cssClass":"label-default",text:"全国培训机构分支"},
                "05":{"cssClass":"label-default",text:"大型机构总部"},"06":{"cssClass":"label-default",text:"大型机构分支"},
                "07":{"cssClass":"label-default",text:"中小型机构"},"08":{"cssClass":"label-default",text:"测试业务"},
                "10":{"cssClass":"label-default",text:"客户准入标准"},"14":{"cssClass":"label-default",text:"打分修正规则"},
                "11":{"cssClass":"label-default",text:"机构客户准入标准"},"12":{"cssClass":"label-default",text:"客户选择必要标准"},
                "13":{"cssClass":"label-default",text:"客户选择次要标准"}
              };
              var map=dataMap[data];
              return map?statusTemplate(map):'';
            }
          },
          {
            targets: 2,
            render:function(data, type, row, meta){
              var dataMap={"scorerule":{"cssClass":"label-primary",text:"评分规则"},"logicrule":{"cssClass":"label-danger",text:"逻辑规则"}};
              var map=dataMap[data];
              return map?statusTemplate(map):'';
            }
          },
          {
            targets:7,
            render:function(data, type, row, meta){
              var dataMap={"00":{"cssClass":"label-primary",text:"有效"},"01":{"cssClass":"label-danger",text:"无效"}};
              var map=dataMap[data];
              return map?statusTemplate(map):'';
            }
          },
          {
            targets:[8],
            render:function(data, type, row, meta){
              return channelTemplate({"groupChannels":row.groupChannels});
            }
          },
          {
            targets:[9],
            render:function(data, type, row, meta){
              return productTemplate({"groupChannels":row.groupChannels});
            }
          },
          {
            targets:11,
            render:function(data, type, row, meta){
              return moment(data, "YYYY-MM-DD HH:mm:ss.SSS").format('YYYY-MM-DD HH:mm:ss');
            }
          },
          {
            targets:12,
            render: function (data, type, row, meta) {
              var context =
              {
                func: [
                  {"text": "编辑", "fn": "getDetail("+row.id+")", "cssClass": "fa-wrench"},
                  {"text": "删除", "fn": "deleteGroup("+row.id+")", "cssClass": "fa-pencil",
                    "isexecute":row.isexecute},
                  {"text": "生成版本", "fn": "generatingVersion('"+row.id+"','"+row.isexecute+"')",
                    "cssClass": "fa-tasks","isexecute":row.isexecute,"status":row.status}
                ]
              };
              var html = operTemplate(context);
              return html;
            }
          }
        ]
      });
    });
  </script>

</div>
</html>
