//无限向左慢爬
(function($) {
	$.fn.mackesScrollApp = function(settings) {
		settings = jQuery.extend({
			speed:1,
			itemNum:5
		},
		settings);
		
		var mackesBox=this;
		var _speed=settings.speed;
		var _itemWidth=$(mackesBox).find(".move-list>ul>li:eq(0)").outerWidth();
			_itemWidth=173;
		var _itemLen=$(mackesBox).find(".move-list>ul>li").length;
		var _itemNum=settings.itemNum;
		var _end=Math.ceil(_itemLen/_itemNum)-1;
		var moveBox=$(mackesBox).find('.move-list>ul');
		var _left=0;
		var _width=Math.ceil(_itemWidth)*Math.ceil(_itemLen);
		var index=0;
		//var btn_prev=$(this).find(".prev");
		
		
		
		function mackesScrollApp_init(){
			$(moveBox).css({width:_width}).attr('index',0);
			$(mackesBox).find('.prev').click(function(){
				index--;
				if(index<0){
					index++;
					return false;
				}
				_left=index*Math.ceil(_itemWidth)*_itemNum*-1;
				$(moveBox).animate({left:_left},'normal');
			});
			$(mackesBox).find('.next').click(function(){
				index++;
				if(index>_end){
					index--;
					return false;
				}
				_left=index*Math.ceil(_itemWidth)*_itemNum*-1;
				$(moveBox).animate({left:_left},'normal');
			});
			
		}
		
		return mackesScrollApp_init();
	}
})(jQuery);