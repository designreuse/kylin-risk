<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <sitemesh:write property='meta' />
    <title><sitemesh:write property='title' /></title>
    <%@ include file="/WEB-INF/jsp/include/defaultHeader.jsp" %>
    <!-- Data Tables -->
    <link href="${libPath}/datatables/css/dataTables.bootstrap.css" rel="stylesheet">
    <link href="${libPath}/datatables/css/dataTables.responsive.css" rel="stylesheet">
    <link href="${libPath}/datatables/css/dataTables.tableTools.min.css" rel="stylesheet">
    <sitemesh:write property='head' />

</head>
<body>
<noscript>${noScript}</noscript>
<sitemesh:write property='body' />

<%@ include file="/WEB-INF/jsp/include/defaultScript.jsp" %>


<!-- Data Tables -->
<script src="${libPath}/datatables/jquery.dataTables.js"></script>
<script src="${libPath}/datatables/dataTables.bootstrap.js"></script>
<script src="${libPath}/datatables/dataTables.responsive.js"></script>
<script src="${libPath}/datatables/dataTables.tableTools.min.js"></script>

<script src="${libPath}/inspinia/inspinia.js"></script>

<script type="text/javascript">
    $.extend( $.fn.dataTable.defaults, {
        "searching":false,
        "ordering":false,
        "processing": true,
        "lengthChange":false,
        "pageLength":10,
        "destroy":true,
        "language": {
            "url": "${libPath}/datatables/Chinese.lang"
        }
    });
</script>
<script src="${libPath}/arg/arg-1.3.1.min.js"></script>
<script src="${jsPath}/jquery.query.datatables.js"></script>

<sitemesh:write property='div.siteMeshJavaScript' />
<script type="text/javascript">
    $(function(){
        if(Arg.query()['_initQuery']){
            var form=$('form');
            form&&form.submit();
        }
    });
</script>
</body>
</html>