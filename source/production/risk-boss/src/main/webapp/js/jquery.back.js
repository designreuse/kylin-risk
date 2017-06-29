/**
 * Created by lina on 2016-9-29.
 */

(function($){
  var backArr = [];

  function pageLoad(that,options){
    var url = options.url;
    if(!url){
      var $that=$(that);
      url=$that.attr("href");
    }
    var divContainer=$("."+options["divContainer"]);
    options._divContainerObject=divContainer;
    //TODO ajax requst
    //请求的结果添加到divContainer
    var $backPage=$("."+options["backPageClsName"]);
    if(!$backPage){
      //执行原生的链接请求
      return;
    }
    $backPage.hide();
    $.get(url,options.data,function(data){
      var $currentPage=$("<div>"+data+"</div>");
      backArr.push({backPage:$backPage,currentPage:$currentPage});
      options._divContainerObject.append($currentPage);
    });
    return false;
  }


  $.ajaxBack=function(options){
    var defaultOptions={
      "goBtnClsName":"ajaxback-requst",
      "divContainer":"divContainer",
      "backPageClsName":"backPageDiv"
    };
    options = $.extend({},defaultOptions,options);
    $("."+options["goBtnClsName"]).click(function(){
      return pageLoad(this,options);
    });
  }
  $.fn.backup=function(callback){
    $(this).click(function(){
      var popObj=backArr.pop();
      if(!popObj){
        console.warn("back page not found");
        return;
      }
      popObj.currentPage.remove();
      popObj.backPage.show();
      callback&& $.isFunction(callback)&&callback();
    })

  }

  $.backquery = function(){
    var popObj=backArr.pop();
    if(!popObj){
      console.warn("back page not found");
      return;
    }
    popObj.currentPage.remove();
    popObj.backPage.show();
    $("form").submit();
  }

  $.ajaxParamBack=function(opts){
    var defaultOptions={
      "divContainer":"divContainer",
      "backPageClsName":"backPageDiv"
    };
    var options = $.extend({},defaultOptions,opts);
    pageLoad(null,options);
  }


})(jQuery)
