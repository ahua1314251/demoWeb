$(function(){
	$('div.nav').children('ul').children('li').hover(function(){
		$(this).children('ul').stop().slideDown('fast');
	},function(){
		$(this).children('ul').stop().slideUp('fast');
	});
});

//javascript Document
function SuCaiJiaYuan(){
	this.init();
}

SuCaiJiaYuan.prototype = {
	constructor: SuCaiJiaYuan,
	init: function(){		
		this._initBackTop();
	},
	_initBackTop: function(){
		var $backTop = this.$backTop = $('<div class="cbbfixed">'+
						'<a class="r_qq" id="clicent_qq" href="http://wpa.qq.com/msgrd?v=3&uin=1968438152&site=qq&menu=yes" target="_blank"><span></span></a>'+
						'<a class="gotop cbbtn">'+
							'<span class="up-icon"></span>'+
						'</a>'+
					'</div>');
		$('body').append($backTop);
		
		$backTop.click(function(){
			$("html, body").animate({
				scrollTop: 0
			}, 120);
		});
		
		var timmer = null;
		$(window).bind("scroll",function() {
            var d = $(document).scrollTop(),
            e = $(window).height();
            0 < d ? $backTop.css("bottom", "10px") : $backTop.css("bottom", "-200px");
			clearTimeout(timmer);
			timmer = setTimeout(function() {
                clearTimeout(timmer)
            },100);
	   });
	}
	
}
var SuCaiJiaYuan = new SuCaiJiaYuan();


