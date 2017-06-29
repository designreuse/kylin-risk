<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>

<html>
<body>
<div class="container">
    <div style="float:left">
        <div class="search_c" style="width:283px;margin-bottom:30px;">
            <form id="form1" action="${ctxPath}/factor/custFactorQuery"  target="queryResult">
                <iframe name="queryResult" src=""  width="350px" height="510px" scrolling="no" frameborder="0" >
                </iframe>
            </form>
        </div>
    </div>
        <div style="float:right;background-color:#eee; width:450px;height:530px;margin-right: 100px;" >
            <div id="tabs-2" style="padding:0px;">
                <iframe id="factordisplay" name="factorAdd" src=""width="100%" height="550px;" scrolling="yes"  frameborder="0"  >
                </iframe>
            </div>
        </div>
 </div>
</body>
</body>
<div id="siteMeshJavaScript">
    <script type="text/javascript" >
        $(function() {
            $("#form1").submit();

        });
        function refresh(frameId, url){
            $("#"+frameId).attr("src" ,url);
        }
        function formSubmit(){
            $("#form1").submit() ;
        }

        var selectNodeId ;
        function setSelectNodeId(id){
            selectNodeId = id ;
        }
        function getSelectNodeId(){
            if("" == $.trim( selectNodeId )){ return "" ; }
            return selectNodeId ;
        }
        function toMenuModify(){
            var URl = "${ctxPath}/factor/toModifyCustFactor.action?id="+selectNodeId ;
            refresh("factorModify",URl) ;
        }
    </script>
</div>

</html>