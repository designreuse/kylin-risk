/**
 * 对 jqury datatables的封装
 */
(function (factory) {
    if (typeof define === 'function' && define.amd) {
        // AMD. Register as an anonymous module.
        define(['jquery','datatables'], factory);
    } else if (typeof module === 'object' && module.exports) {
        // Node/CommonJS
        module.exports = factory(require('jquery'));
    } else {
        // Browser globals
        factory(jQuery);
    }
}(function ($) {

    function log() {
        var msg = Array.prototype.join.call(arguments,'');
        if (window.console && window.console.log) {
            window.console.log(msg);
        }
        else if (window.opera && window.opera.postError) {
            window.opera.postError(msg);
        }
    }

    function doDataTables(e) {
        /*jshint validthis:true */
        var options = e.data;
        if (!e.isDefaultPrevented()) { // if event has been canceled, don't proceed
            e.preventDefault();

            var form=$(e.target);
            var method, action, url;

            method = options.type || form.attr('method')||$.ajaxSettings.type;
            action = options.url  || form.attr('action');

            url = (typeof action === 'string') ? $.trim(action) : '';
            url = url || window.location.href || '';
            if (url) {
                // clean url (don't include hash vaue)
                url = (url.match(/^([^#]+)/)||[])[1];
            }

            //get和post请求不同处理
            var formData=form.serializeArray();
            var ajax={
                url:url,
                type: method
            };
            if (method.toUpperCase() == 'GET') {
                url += (url.indexOf('?') >= 0 ? '&' : '?') + jQuery.param(formData);
                ajax.url=url;
            }else {
                var result = {};
                var extend = function (i, element) {
                    var node = result[element.name];
                    // If node with same name exists already, need to convert it to an array as it
                    // is a multi-value field (i.e., checkboxes)
                    if ('undefined' !== typeof node && node !== null) {
                        if ($.isArray(node)) {
                            node.push(element.value);
                        } else {
                            result[element.name] = [node, element.value];
                        }
                    } else {
                        result[element.name] = element.value;
                    }
                };
                $.each(formData, extend);
                ajax.data = result;
            }

            options = $.extend(true, {ajax:ajax}, options);


            if(form[0].tagName.toUpperCase()!=='FORM'){
                log('only support form tag!');
                return;
            }
            var datatable,table=options.table;
            if(table){
                var tableType=(typeof table);
                if(tableType === 'object'){
                    datatable=table;
                }else if(tableType==='string'){
                    datatable=$("#"+table);
                }
            }
            if(!datatable){
                var formTableAttr=form.attr('data-table');
                if(formTableAttr){
                    datatable=$("#"+formTableAttr);
                }
            }
            if(!datatable){
                log("the data-table attribute is not exists,aborting!!!");
                return;
            }

            datatable.dataTable(options);
        }
    }

    $.fn.queryTablesUnbind = function() {
        return this.unbind('submit.query-tables-plugin');
    };

    $.fn.queryTables = function (options) {
        options = options || {};
        return this.queryTablesUnbind().bind('submit.query-tables-plugin', options, doDataTables);
    };


}));