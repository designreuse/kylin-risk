$(function () {
    function elementOuterWidth(eles) {
        var sumOuterWidth = 0;
        $(eles).each(function () {
            sumOuterWidth += $(this).outerWidth(!0)
        });
        return sumOuterWidth;
    }

    /**
     * 计算当前tab的位置
     * @param e
     */
    function adapterTabLocation(e) {
        var a = elementOuterWidth($(e).prevAll()), i = elementOuterWidth($(e).nextAll()), n = elementOuterWidth($(".content-tabs").children().not(".J_menuTabs")), s = $(".content-tabs").outerWidth(!0) - n, r = 0;
        if ($(".page-tabs-content").outerWidth() < s)r = 0; else if (i <= s - $(e).outerWidth(!0) - $(e).next().outerWidth(!0)) {
            if (s - $(e).next().outerWidth(!0) > i) {
                r = a;
                for (var o = e; r - $(o).outerWidth() > $(".page-tabs-content").outerWidth() - s;)r -= $(o).prev().outerWidth(), o = $(o).prev()
            }
        } else a > s - $(e).outerWidth(!0) - $(e).prev().outerWidth(!0) && (r = a - $(e).prev().outerWidth(!0));
        $(".page-tabs-content").animate({marginLeft: 0 - r + "px"}, "fast")
    }

    /**
     * 左移tab
     * @returns {boolean}
     */
    function moveTabLeft() {
        var e = Math.abs(parseInt($(".page-tabs-content").css("margin-left"))), a = elementOuterWidth($(".content-tabs").children().not(".J_menuTabs")), i = $(".content-tabs").outerWidth(!0) - a, n = 0;
        if ($(".page-tabs-content").width() < i)return !1;
        for (var s = $(".J_menuTab:first"), r = 0; r + $(s).outerWidth(!0) <= e;)r += $(s).outerWidth(!0), s = $(s).next();
        if (r = 0, elementOuterWidth($(s).prevAll()) > i) {
            for (; r + $(s).outerWidth(!0) < i && s.length > 0;)r += $(s).outerWidth(!0), s = $(s).prev();
            n = elementOuterWidth($(s).prevAll())
        }
        $(".page-tabs-content").animate({marginLeft: 0 - n + "px"}, "fast")
    }

    /**
     *右移tab
     * @returns {boolean}
     */
    function moveTabRight() {
        var e = Math.abs(parseInt($(".page-tabs-content").css("margin-left"))), a = elementOuterWidth($(".content-tabs").children().not(".J_menuTabs")), i = $(".content-tabs").outerWidth(!0) - a, n = 0;
        if ($(".page-tabs-content").width() < i)return !1;
        for (var s = $(".J_menuTab:first"), r = 0; r + $(s).outerWidth(!0) <= e;)r += $(s).outerWidth(!0), s = $(s).next();
        for (r = 0; r + $(s).outerWidth(!0) < i && s.length > 0;)r += $(s).outerWidth(!0), s = $(s).next();
        n = elementOuterWidth($(s).prevAll()), n > 0 && $(".page-tabs-content").animate({marginLeft: 0 - n + "px"}, "fast")
    }

    function addTab() {
        var iframeSrc = $(this).attr("href"), a = $(this).data("index"), i = $.trim($(this).text()), neverOpenTab = true;
        if (!iframeSrc || 0 == $.trim(iframeSrc).length){
            return false;
        }
        $(".J_menuTab").each(function () {
            if($(this).data("id") == iframeSrc){
                neverOpenTab=false;
                if(!$(this).hasClass("active")){
                    $(this).addClass("active").siblings(".J_menuTab").removeClass("active");
                    adapterTabLocation(this);
                    $(".J_mainContent .J_iframe").each(function () {
                        return $(this).data("id") == iframeSrc ? ($(this).show().siblings(".J_iframe").hide(), !1) : void 0
                    });
                    return false;
                }
            }
        });
        if (neverOpenTab) {
            var iframeTab = '<a href="javascript:;" class="active J_menuTab" data-index="tab-'+a+'"   data-id="'
                + iframeSrc + '">' + i + ' <i class="fa fa-times-circle" style="position: relative;top: -9px;right: -9px;"></i></a>';
            var $iframeTab=$(iframeTab);
            $(".J_menuTab").removeClass("active");
            var r = '<iframe class="J_iframe" name="iframe' + a + '" width="100%" height="100%" src="' + iframeSrc + '" frameborder="0" data-id="' + iframeSrc + '" seamless></iframe>';
            var content=$(".J_mainContent");
            content.find("iframe.J_iframe").hide();
            content.append(r);
            var o = layer.load();
            $(".J_mainContent iframe:visible").load(function () {
                layer.close(o)
            });
            $(".J_menuTabs .page-tabs-content").append($iframeTab), adapterTabLocation($(".J_menuTab.active"))


        }
        return false;
    }

    function closeTab() {
        var t = $(this).parents(".J_menuTab").data("id"), a = $(this).parents(".J_menuTab").width();
        if ($(this).parents(".J_menuTab").hasClass("active")) {
            if ($(this).parents(".J_menuTab").next(".J_menuTab").size()) {
                var i = $(this).parents(".J_menuTab").next(".J_menuTab:eq(0)").data("id");
                $(this).parents(".J_menuTab").next(".J_menuTab:eq(0)").addClass("active"), $(".J_mainContent .J_iframe").each(function () {
                    return $(this).data("id") == i ? ($(this).show().siblings(".J_iframe").hide(), !1) : void 0
                });
                var n = parseInt($(".page-tabs-content").css("margin-left"));
                0 > n && $(".page-tabs-content").animate({marginLeft: n + a + "px"}, "fast"), $(this).parents(".J_menuTab").remove(), $(".J_mainContent .J_iframe").each(function () {
                    return $(this).data("id") == t ? ($(this).remove(), !1) : void 0
                })
            }
            if ($(this).parents(".J_menuTab")) {
                var i = $(this).parents(".J_menuTab").prev(".J_menuTab:last").data("id");
                $(this).parents(".J_menuTab").prev(".J_menuTab:last").addClass("active"), $(".J_mainContent .J_iframe").each(function () {
                    return $(this).data("id") == i ? ($(this).show().siblings(".J_iframe").hide(), !1) : void 0
                }), $(this).parents(".J_menuTab").remove(), $(".J_mainContent .J_iframe").each(function () {
                    return $(this).data("id") == t ? ($(this).remove(), !1) : void 0
                })
            }
        } else $(this).parents(".J_menuTab").remove(), $(".J_mainContent .J_iframe").each(function () {
            return $(this).data("id") == t ? ($(this).remove(), !1) : void 0
        }), adapterTabLocation($(".J_menuTab.active"));
        return false;
    }

    function closeOtherTab(excludeTab) {
        $(".page-tabs-content").children("[data-id]").not("[data-index="+excludeTab+"]").each(function () {
            $('.J_iframe[data-id="' + $(this).data("id") + '"]').remove(), $(this).remove()
        }), $(".page-tabs-content").css("margin-left", "0")
    }

    function closeAllTab(){
        $(".page-tabs-content").children("[data-id]").each(function () {
            $('.J_iframe[data-id="' + $(this).data("id") + '"]').remove(), $(this).remove()
        });
        $(".page-tabs-content").children("[data-id]:first").each(function () {
            $('.J_iframe[data-id="' + $(this).data("id") + '"]').show(), $(this).addClass("active")
        });
        $(".page-tabs-content").css("margin-left", "0")
    }

    function activeCurrentTab() {
        if (!$(this).hasClass("active")) {
            var t = $(this).data("id");
            $(".J_mainContent .J_iframe").each(function () {
                return $(this).data("id") == t ? ($(this).show().siblings(".J_iframe").hide(), !1) : void 0
            });
            $(this).addClass("active").siblings(".J_menuTab").removeClass("active");
            adapterTabLocation(this)
        }
    }

    function refesh() {
        var t = $('.J_iframe[data-id="' + $(this).data("id") + '"]'), e = t.attr("src"), a = layer.load();
        t.attr("src", e).load(function () {
            layer.close(a)
        })
    }

    $(".J_menuItem").each(function (t) {
        $(this).attr("data-index") || $(this).attr("data-index", t)
    });
    $(".J_menuItem").on("click", addTab);
    $(".J_menuTabs").on("click", ".J_menuTab i", closeTab);
    $(".J_menuTabs").on("click", ".J_menuTab", activeCurrentTab);
    $(".J_menuTabs").on("dblclick", ".J_menuTab", refesh);
    $(".J_tabLeft").on("click", moveTabLeft);
    $(".J_tabRight").on("click", moveTabRight);
    $(".J_tabCloseOther").on("click", closeOtherTab);
    $(".J_tabCloseAll").on("click", closeAllTab);

    $('div#iframeMenuTabs').contextMenu({
        selector: "a",
        items: {
            "closeOther": {name: "关闭其他"},
            "sep1": "---------",
            "closeAll": {name: "关闭全部"},
            "sep2": "---------",
            "refresh":{name:"刷新"}
        },
        callback: function (itemKey, opt) {
            if(itemKey==='closeOther'){
                closeOtherTab(opt.$trigger.data('index'));
            }else if(itemKey==='closeAll'){
                closeAllTab();
            }else if(itemKey==='refresh'){
                refesh.call(opt.$trigger);
            }
            return true;
        }
    });

});