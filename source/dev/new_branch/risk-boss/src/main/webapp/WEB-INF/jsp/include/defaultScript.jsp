<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>

<script src="${libPath}/jquery/jquery-2.1.4.js"></script>
<script src="${libPath}/slimscroll/jquery.slimscroll.min.js"></script>
<script src="${libPath}/jquery/jquery-migrate-1.2.1.min.js"></script>
<script src="${libPath}/bootstrap/js/bootstrap.min.js"></script>
<script src="${libPath}/parsley/parsley.js"></script>
<script src="${libPath}/parsley/zh_cn.js"></script>
<script src="${libPath}/parsley/validators.js"></script>

<script src="${libPath}/My97DatePicker/WdatePicker.js"></script>
<script src="${libPath}/moment/moment.min.js"></script>
<script src="${libPath}/jquery-form/jquery.form.js"></script>
<script src="${libPath}/bootbox/bootbox.min.js"></script>
<script src="${libPath}/handlebars/handlebars-v4.0.2.js"></script>

<script type="text/javascript" src="${libPath}/iCheck/js/icheck.min.js"></script>
<script type="text/javascript" src="${libPath}/chosen/chosen.jquery.js"></script>
<script src="${libPath}/jquery-editable/jquery.editable-select.js"></script>
<%--<link href="${libPath}/mCustomerScroll/jquery.mCustomScrollbar.css" rel="stylesheet" type="text/css" />
<script src="${libPath}/mCustomerScroll/jquery.mousewheel.min.js"></script>
<script src="${libPath}/mCustomerScroll/jquery.mCustomScrollbar.concat.min.js"></script>--%>
<script>
    $(document).ready(function () {
        $('.i-checks').each(function(){
            var $this=$(this);
            var $checkboxClass=$this.attr('checkboxClass');
            var $radioClass=$this.attr('radioClass');
            if($checkboxClass){
                $this .iCheck({
                    checkboxClass: $checkboxClass
                });
            }
            if($radioClass){
                $this.iCheck({
                    radioClass: $radioClass
                });
            }
        });


        var config = {
            '.chosen-select'           : {},
            '.chosen-select-deselect'  : {allow_single_deselect:true},
            '.chosen-select-no-single' : {disable_search_threshold:10},
            '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
            '.chosen-select-width'     : {width:"95%"}
        }
        for (var selector in config) {
            $(selector).chosen(config[selector]);
        }
    /*   $('div#wrapper').slimscroll({
            alwaysVisible: true,
            size: '10px',
            height: 'auto'
        });*/
        /*$('div.scrollWrapper').mCustomScrollbar({
            horizontalScroll:false,
            setHeight:"100%",
            autoDraggerLength:true,
            alwaysShowScrollbar:0,
            theme:"3d-thick",
            scrollInertia:400,
            advanced:{ updateOnBrowserResize:true } ,     //根据百分比为自适应布局 调整浏览器上滚动条的大小 值:true,false 设置 false 如果你的内容块已经被固定大小
            advanced:{ updateOnContentResize:true },     //自动根据动态变换的内容调整滚动条的大小 值:true,false 设置成 true 将会不断的检查内容的长度并且据此改变滚动条大小 建议除非必要不要设置成 true 如果页面中有很多滚动条的时候 它有可能会产生额外的移出 你可以使用 update 方法来替代这个功能
            advanced:{ autoExpandHorizontalScroll:true }, //自动扩大水平滚动条的长度 值:true,false 设置 true 你可以根据内容的动态变化自动调整大小 可以看Demo
            advanced:{ autoScrollOnFocus:false }
        });*/
    });
</script>
