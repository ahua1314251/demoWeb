(function($) {
	$.fn.mackesFocus = function(settings) {
		settings = jQuery.extend({
			intervalTime: 5,
			moveSpeedTime: 400
		},
		settings);
		var box=this;
		var _intervalTime=settings.intervalTime;
		_intervalTime=Math.ceil(_intervalTime*1000);
		var _moveSpeed=settings.moveSpeedTime;
		var moveBox=$(box).children('.pics');
		var btnBox=$(box).children('.btns');
		var picWidth=$(moveBox).children('li').width();
		var total=$(moveBox).children('li').length;
		var totalWidth=Math.ceil(picWidth*total);
		var moveIndex=0;
		var myTime;
		
		function move_show(index,resetTime){
			var _left=Math.ceil(picWidth*index)*-1;
			$(btnBox).children('li').eq(index).addClass('current').siblings().removeClass('current');
			$(moveBox).stop().animate({left:_left},_moveSpeed,'',function(){
				if(resetTime==1){
					myTime=setInterval(function(){
							moveIndex++;
							if(moveIndex==total){
								moveIndex=0;
							}
							move_show(moveIndex,0);
							
						},_intervalTime);
				}	
			});
			
		}
		
		
		function mackesFocus_init() {
			var _m_l=Math.ceil(Math.ceil($(btnBox).width())/2)*-1;
			$(btnBox).css({marginLeft:_m_l});
			$(moveBox).css({width:totalWidth});
			
			var new_width=$(document).width();
			picWidth=new_width;
			$(moveBox).children('li').css({width:new_width,float:'left'});
			totalWidth=Math.ceil(total*new_width);
			$(moveBox).css({width:totalWidth});
			
			$(window).resize(function(){
				var new_width=$(document).width();
				if(new_width<857){
					new_width=857;
				}
				var _left=Math.ceil(new_width*moveIndex)*-1;
				totalWidth=Math.ceil(total*new_width);
				picWidth=new_width;
				$(moveBox).children('li').css({width:new_width,float:'left'});
				$(moveBox).css({width:totalWidth,left:_left});
			});
			
			myTime=setInterval(function(){
				moveIndex++;
				if(moveIndex==total){
					moveIndex=0;
				}
				move_show(moveIndex,0);
				
			},_intervalTime);
			$(btnBox).children('li').mouseenter(function(){
				if(myTime){
					clearInterval(myTime);
				}
				moveIndex=$(this).index();
				move_show(moveIndex,1);
			});
			
		};
		return mackesFocus_init()
	}
})(jQuery);