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
<script src="${libPath}/pace/pace.min.js"></script>
<script src="${libPath}/jquery-form/jquery.form.js"></script>
<script src="${libPath}/bootbox/bootbox.min.js"></script>
<script src="${libPath}/handlebars/handlebars-v4.0.2.js"></script>

<script type="text/javascript" src="${libPath}/iCheck/js/icheck.min.js"></script>
<script type="text/javascript" src="${libPath}/chosen/chosen.jquery.js"></script>
<script src="${libPath}/jquery-editable/jquery.editable-select.js"></script>
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
        $('div#wrapper').slimscroll({
            alwaysVisible: true,
            size: '10px',
            height: 'auto'
        });

    });
</script>
