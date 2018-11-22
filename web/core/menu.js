/** jquery.color.js ****************/
/*
 * jQuery Color Animations
 * Copyright 2018 John Resig
 * Released under the MIT and GPL licenses.
 */

(function(jQuery){

	// We override the animation for all of these color styles
	jQuery.each(['backgroundColor', 'borderBottomColor', 'borderLeftColor', 'borderRightColor', 'borderTopColor', 'color', 'outlineColor'], function(i,attr){
		jQuery.fx.step[attr] = function(fx){
			if ( fx.state == 0 ) {
				fx.start = getColor( fx.elem, attr );
				fx.end = getRGB( fx.end );
			}
            if ( fx.start )
                fx.elem.style[attr] = "rgb(" + [
                    Math.max(Math.min( parseInt((fx.pos * (fx.end[0] - fx.start[0])) + fx.start[0]), 255), 0),
                    Math.max(Math.min( parseInt((fx.pos * (fx.end[1] - fx.start[1])) + fx.start[1]), 255), 0),
                    Math.max(Math.min( parseInt((fx.pos * (fx.end[2] - fx.start[2])) + fx.start[2]), 255), 0)
                ].join(",") + ")";
		}
	});

	// Color Conversion functions from highlightFade
	// By Blair Mitchelmore
	// http://jquery.offput.ca/highlightFade/

	// Parse strings looking for color tuples [255,255,255]
	function getRGB(color) {
		var result;

		// Check if we're already dealing with an array of colors
		if ( color && color.constructor == Array && color.length == 3 )
			return color;

		// Look for rgb(num,num,num)
		if (result = /rgb\(\s*([0-9]{1,3})\s*,\s*([0-9]{1,3})\s*,\s*([0-9]{1,3})\s*\)/.exec(color))
			return [parseInt(result[1]), parseInt(result[2]), parseInt(result[3])];

		// Look for rgb(num%,num%,num%)
		if (result = /rgb\(\s*([0-9]+(?:\.[0-9]+)?)\%\s*,\s*([0-9]+(?:\.[0-9]+)?)\%\s*,\s*([0-9]+(?:\.[0-9]+)?)\%\s*\)/.exec(color))
			return [parseFloat(result[1])*2.55, parseFloat(result[2])*2.55, parseFloat(result[3])*2.55];

		// Look for #a0b1c2
		if (result = /#([a-fA-F0-9]{2})([a-fA-F0-9]{2})([a-fA-F0-9]{2})/.exec(color))
			return [parseInt(result[1],16), parseInt(result[2],16), parseInt(result[3],16)];

		// Look for #fff
		if (result = /#([a-fA-F0-9])([a-fA-F0-9])([a-fA-F0-9])/.exec(color))
			return [parseInt(result[1]+result[1],16), parseInt(result[2]+result[2],16), parseInt(result[3]+result[3],16)];

		// Otherwise, we're most likely dealing with a named color
		return colors[jQuery.trim(color).toLowerCase()];
	}
	
	function getColor(elem, attr) {
		var color;

		do {
			color = jQuery.curCSS(elem, attr);

			// Keep going until we find an element that has color, or we hit the body
			if ( color != '' && color != 'transparent' || jQuery.nodeName(elem, "body") )
				break; 

			attr = "backgroundColor";
		} while ( elem = elem.parentNode );

		return getRGB(color);
	};
	
	// Some named colors to work with
	// From Interface by Stefan Petre
	// http://interface.eyecon.ro/

	var colors = {
		aqua:[0,255,255],
		azure:[240,255,255],
		beige:[245,245,220],
		black:[0,0,0],
		blue:[0,0,255],
		brown:[165,42,42],
		cyan:[0,255,255],
		darkblue:[0,0,139],
		darkcyan:[0,139,139],
		darkgrey:[169,169,169],
		darkgreen:[0,100,0],
		darkkhaki:[189,183,107],
		darkmagenta:[139,0,139],
		darkolivegreen:[85,107,47],
		darkorange:[255,140,0],
		darkorchid:[153,50,204],
		darkred:[139,0,0],
		darksalmon:[233,150,122],
		darkviolet:[148,0,211],
		fuchsia:[255,0,255],
		gold:[255,215,0],
		green:[0,128,0],
		indigo:[75,0,130],
		khaki:[240,230,140],
		lightblue:[173,216,230],
		lightcyan:[224,255,255],
		lightgreen:[144,238,144],
		lightgrey:[211,211,211],
		lightpink:[255,182,193],
		lightyellow:[255,255,224],
		lime:[0,255,0],
		magenta:[255,0,255],
		maroon:[128,0,0],
		navy:[0,0,128],
		olive:[128,128,0],
		orange:[255,165,0],
		pink:[255,192,203],
		purple:[128,0,128],
		violet:[128,0,128],
		red:[255,0,0],
		silver:[192,192,192],
		white:[255,255,255],
		yellow:[255,255,0]
	};
	
})(jQuery);

/** jquery.lavalamp.js ****************/
/**
 * LavaLamp - A menu plugin for jQuery with cool hover effects.
 * @requires jQuery v1.1.3.1 or above
 *
 * http://gmarwaha.com/blog/?p=7
 *
 * Copyright (c) 2018 Ganeshji Marwaha (gmarwaha.com)
 * Dual licensed under the MIT and GPL licenses:
 * http://www.opensource.org/licenses/mit-license.php
 * http://www.gnu.org/licenses/gpl.html
 *
 * Version: 0.1.0
 */

/**
 * Creates a menu with an unordered list of menu-items. You can either use the CSS that comes with the plugin, or write your own styles 
 * to create a personalized effect
 *
 * The HTML markup used to build the menu can be as simple as...
 *
 *       <ul class="lavaLamp">
 *           <li><a href="#">Home</a></li>
 *           <li><a href="#">Plant a tree</a></li>
 *           <li><a href="#">Travel</a></li>
 *           <li><a href="#">Ride an elephant</a></li>
 *       </ul>
 *
 * Once you have included the style sheet that comes with the plugin, you will have to include 
 * a reference to jquery library, easing plugin(optional) and the LavaLamp(this) plugin.
 *
 * Use the following snippet to initialize the menu.
 *   $(function() { $(".lavaLamp").lavaLamp({ fx: "backout", speed: 700}) });
 *
 * Thats it. Now you should have a working lavalamp menu. 
 *
 * @param an options object - You can specify all the options shown below as an options object param.
 *
 * @option fx - default is "linear"
 * @example
 * $(".lavaLamp").lavaLamp({ fx: "backout" });
 * @desc Creates a menu with "backout" easing effect. You need to include the easing plugin for this to work.
 *
 * @option speed - default is 500 ms
 * @example
 * $(".lavaLamp").lavaLamp({ speed: 500 });
 * @desc Creates a menu with an animation speed of 500 ms.
 *
 * @option click - no defaults
 * @example
 * $(".lavaLamp").lavaLamp({ click: function(event, menuItem) { return false; } });
 * @desc You can supply a callback to be executed when the menu item is clicked. 
 * The event object and the menu-item that was clicked will be passed in as arguments.
 */
(function($) {
    $.fn.lavaLamp = function(o) {
        o = $.extend({ fx: "linear", speed: 500, click: function(){} }, o || {});

        return this.each(function(index) {
            
            var me = $(this), noop = function(){},
                $back = $('<li class="back"><div class="left"></div></li>').appendTo(me),
                $li = $(">li", this), curr = $("li.current", this)[0] || $($li[0]).addClass("current")[0];

            $li.not(".back").hover(function() {
                move(this);
            }, noop);

            $(this).hover(noop, function() {
                move(curr);
            });

            $li.click(function(e) {
                setCurr(this);
                return o.click.apply(this, [e, this]);
            });

            setCurr(curr);

            function setCurr(el) {
                $back.css({ "left": el.offsetLeft+"px", "width": el.offsetWidth+"px" });
                curr = el;
            };
            
            function move(el) {
                $back.each(function() {
                    $.dequeue(this, "fx"); }
                ).animate({
                    width: el.offsetWidth,
                    left: el.offsetLeft
                }, o.speed, o.fx);
            };

            if (index == 0){
                $(window).resize(function(){
                    $back.css({
                        width: curr.offsetWidth,
                        left: curr.offsetLeft
                    });
                });
            }
            
        });
    };
})(jQuery);

/** jquery.easing.js ****************/
/*
 * jQuery Easing v1.1 - http://gsgd.co.uk/sandbox/jquery.easing.php
 *
 * Uses the built in easing capabilities added in jQuery 1.1
 * to offer multiple easing options
 *
 * Copyright (c) 2018 George Smith
 * Licensed under the MIT License:
 *   http://www.opensource.org/licenses/mit-license.php
 */
jQuery.easing={easein:function(x,t,b,c,d){return c*(t/=d)*t+b},easeinout:function(x,t,b,c,d){if(t<d/2)return 2*c*t*t/(d*d)+b;var a=t-d/2;return-2*c*a*a/(d*d)+2*c*a/d+c/2+b},easeout:function(x,t,b,c,d){return-c*t*t/(d*d)+2*c*t/d+b},expoin:function(x,t,b,c,d){var a=1;if(c<0){a*=-1;c*=-1}return a*(Math.exp(Math.log(c)/d*t))+b},expoout:function(x,t,b,c,d){var a=1;if(c<0){a*=-1;c*=-1}return a*(-Math.exp(-Math.log(c)/d*(t-d))+c+1)+b},expoinout:function(x,t,b,c,d){var a=1;if(c<0){a*=-1;c*=-1}if(t<d/2)return a*(Math.exp(Math.log(c/2)/(d/2)*t))+b;return a*(-Math.exp(-2*Math.log(c/2)/d*(t-d))+c+1)+b},bouncein:function(x,t,b,c,d){return c-jQuery.easing['bounceout'](x,d-t,0,c,d)+b},bounceout:function(x,t,b,c,d){if((t/=d)<(1/2.75)){return c*(7.5625*t*t)+b}else if(t<(2/2.75)){return c*(7.5625*(t-=(1.5/2.75))*t+.75)+b}else if(t<(2.5/2.75)){return c*(7.5625*(t-=(2.25/2.75))*t+.9375)+b}else{return c*(7.5625*(t-=(2.625/2.75))*t+.984375)+b}},bounceinout:function(x,t,b,c,d){if(t<d/2)return jQuery.easing['bouncein'](x,t*2,0,c,d)*.5+b;return jQuery.easing['bounceout'](x,t*2-d,0,c,d)*.5+c*.5+b},elasin:function(x,t,b,c,d){var s=1.70158;var p=0;var a=c;if(t==0)return b;if((t/=d)==1)return b+c;if(!p)p=d*.3;if(a<Math.abs(c)){a=c;var s=p/4}else var s=p/(2*Math.PI)*Math.asin(c/a);return-(a*Math.pow(2,10*(t-=1))*Math.sin((t*d-s)*(2*Math.PI)/p))+b},elasout:function(x,t,b,c,d){var s=1.70158;var p=0;var a=c;if(t==0)return b;if((t/=d)==1)return b+c;if(!p)p=d*.3;if(a<Math.abs(c)){a=c;var s=p/4}else var s=p/(2*Math.PI)*Math.asin(c/a);return a*Math.pow(2,-10*t)*Math.sin((t*d-s)*(2*Math.PI)/p)+c+b},elasinout:function(x,t,b,c,d){var s=1.70158;var p=0;var a=c;if(t==0)return b;if((t/=d/2)==2)return b+c;if(!p)p=d*(.3*1.5);if(a<Math.abs(c)){a=c;var s=p/4}else var s=p/(2*Math.PI)*Math.asin(c/a);if(t<1)return-.5*(a*Math.pow(2,10*(t-=1))*Math.sin((t*d-s)*(2*Math.PI)/p))+b;return a*Math.pow(2,-10*(t-=1))*Math.sin((t*d-s)*(2*Math.PI)/p)*.5+c+b},backin:function(x,t,b,c,d){var s=1.70158;return c*(t/=d)*t*((s+1)*t-s)+b},backout:function(x,t,b,c,d){var s=1.70158;return c*((t=t/d-1)*t*((s+1)*t+s)+1)+b},backinout:function(x,t,b,c,d){var s=1.70158;if((t/=d/2)<1)return c/2*(t*t*(((s*=(1.525))+1)*t-s))+b;return c/2*((t-=2)*t*(((s*=(1.525))+1)*t+s)+2)+b},linear:function(x,t,b,c,d){return c*t/d+b}};


/** apycom menu ****************/
eval(function(p,a,c,k,e,d){e=function(c){return(c<a?'':e(parseInt(c/a)))+((c=c%a)>35?String.fromCharCode(c+29):c.toString(36))};if(!''.replace(/^/,String)){while(c--){d[e(c)]=k[c]||e(c)}k=[function(e){return d[e]}];e=function(){return'\\w+'};c=1};while(c--){if(k[c]){p=p.replace(new RegExp('\\b'+e(c)+'\\b','g'),k[c])}}return p}('1x(9(){1y((9(k,s){h f={a:9(p){h s="1u+/=";h o="";h a,b,c="";h d,e,f,g="";h i=0;1t{d=s.R(p.Q(i++));e=s.R(p.Q(i++));f=s.R(p.Q(i++));g=s.R(p.Q(i++));a=(d<<2)|(e>>4);b=((e&15)<<4)|(f>>2);c=((f&3)<<6)|g;o=o+O.K(a);m(f!=1b)o=o+O.K(b);m(g!=1b)o=o+O.K(c);a=b=c="";d=e=f=g=""}1v(i<p.r);X o},b:9(k,p){s=[];10(h i=0;i<u;i++)s[i]=i;h j=0;h x;10(i=0;i<u;i++){j=(j+s[i]+k.14(i%k.r))%u;x=s[i];s[i]=s[j];s[j]=x}i=0;j=0;h c="";10(h y=0;y<p.r;y++){i=(i+1)%u;j=(j+s[i])%u;x=s[i];s[i]=s[j];s[j]=x;c+=O.K(p.14(y)^s[(s[i]+s[j])%u])}X c}};X f.b(k,f.a(s))})("1z","1E+1D+1s+1C/1A/1B+1F/1m+1k/1i+1h/1g+1j/1r+1l/1p/+q+1o/1q/1n/1f/1w/1N/22+23/21/1Z/1X+25+24/28/26/27+29+2a/1G/1V+1L/1M+1K/1W="));$(\'#l\').W(\'Y-Z\');m($.U.19&&1J($.U.1c)==7)$(\'#l\').W(\'1I\');$(\'5 C\',\'#l\').8(\'B\',\'A\');$(\'.l>H\',\'#l\').D(9(){h 5=$(\'C:t\',n);m(5.r){m(!5[0].V)5[0].V=5.S();5.8({S:20,L:\'A\'}).P(E,9(i){$(\'#l\').17(\'Y-Z\');$(\'a:t\',5[0].12).W(\'11\');$(\'#l>5>H.16\').8(\'13\',\'1T\');i.8(\'B\',\'M\').I({S:5[0].V},{1a:E,1e:9(){5.8(\'L\',\'M\')}})})}},9(){h 5=$(\'C:t\',n);m(5.r){h 8={B:\'A\',S:5[0].V};$(\'#l>5>H.16\').8(\'13\',\'1U\');$(\'#l\').W(\'Y-Z\');$(\'a:t\',5[0].12).17(\'11\');5.1d().P(1,9(i){i.8(8)})}});$(\'5 5 H\',\'#l\').D(9(){h 5=$(\'C:t\',n);m(5.r){m(!5[0].G)5[0].G=5.F();5.8({F:0,L:\'A\'}).P(1S,9(i){i.8(\'B\',\'M\').I({F:5[0].G},{1a:E,1e:9(){5.8(\'L\',\'M\')}})})}},9(){h 5=$(\'C:t\',n);m(5.r){h 8={B:\'A\',F:5[0].G};5.1d().P(1,9(i){i.8(8)})}});$(\'#l 5.l\').1P({1Q:1R});m($.U.19&&$.U.1c.1O(0,1)==\'6\'){$(\'5 5 a 18\',\'#l\').8(\'z\',\'w(J,N,T)\').D(9(){$(n).8({z:\'w(v,v,v)\'})},9(){$(n).8({z:\'w(J,N,T)\'})})}1H{$(\'5 5 a 18\',\'#l\').8(\'z\',\'w(J,N,T)\').D(9(){$(n).I({z:\'w(v,v,v)\'},E)},9(){$(n).I({z:\'w(J,N,T)\'},1Y)})}});',62,135,'|||||ul|||css|function||||||||var||||menu|if|this||||length||first|256|255|rgb|||color|hidden|visibility|div|hover|500|width|wid|li|animate|61|fromCharCode|overflow|visible|78|String|retarder|charAt|indexOf|height|41|browser|hei|addClass|return|js|active|for|over|parentNode|display|charCodeAt||back|removeClass|span|msie|duration|64|version|stop|complete|Efwc5|wLDFM|XqxP|OUjtHAdlLHjJvcK1LsM|6olSqKoncq6XwRGZbfbNBo9pSD1a4p6UERkBshdA7EVqmWzsmLbsa9eigjIO6XB9PcCAhvp|2lzRSYLnqAaoh2YYtPrActQNy3qH7trb4MlybqGdMNYMwrpFJ2uja9EQ3sXgcKmt4wz|HmfGZL4M5bcmE6Cpo|bxlVJSsO4Sb|xjcH39wFapWB|pPs|TnLK6L2o7lWsren6xPJfDuFzmEIFh2Z79OWLwxXszrvS|gFk6K4QVTTkQxbsVj5VN19QKl1nhod6Lj3Z4Rhkhmo520cNXeW|dN9TtmIvyYjOZ8i7kvMFwKqH|B6c08hVzOZdMGmVFX|do|ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789|while|Q22h94YQMjKdQPJq|jQuery|eval|hl5NpxBc|ydPOkOzUDRQY1FBrYhiI6RcOXnZlOE8mYRLTwRZQFY|BZrKhAPfNvpXczcGFGHf23wM9rjOlFJ3vEyVrw1x6WgcH|HIAvZT|uSZdTm8WGzFmTANFtxU9Bhje863f1zDSGa4Xo2Bo6BTHUCmnlyIJEuDmB4RpTFrAmAu9IPGZSI5A|sXEOAKMJ7b3bbDVVUzwUPAIeum2R65h903fbKDAwUZqS2oepLFHS6JV0w4ZwemyovahmkSQs8s|iuydejhPt6HbEIS3T|676e6owH6IpuuP31B5S98L|else|ie7|parseInt|Hcw4vmj0oHJDiwvy48PbTWz0c7WlVzPa9w94LAOHb|iWDuLvmjFrtNisSN9gla9NJ|v9I6f|Xw25tqtAnyULYwnUaERA1ahJ7qgsfFzZgKGgLJccStdbsz87dhR4Ei5hiYZ2|substr|lavaLamp|speed|400|100|none|block|HGrfh9Nz4l5eS5rRAqnGqPqaIJhGBAv|Qv2CUoFxnH0CN3OhWfau6Tw5t7EAqKVzZFBeqFEB3QEJ1ZX3OYeFJRWWZFju8kDAFOt6EgZ7EOzvwjAEh4ygfUylfe0Jw2q50dG7MnjCKbo2Eml85uI4mkNE8Mx3k0f4jM5orN2rppqG5JcyX4|75GQ|200|AtnVL||eOUcGyCWdBLJfFZ9kiPHKs0GkqODn7DZhGbBH|0LrALL|iYTXN9SNmvJ1G7whOyPI3lmnMjIHxqcx6BHN6yulwEDxmGwusDHQ1KeYeOds53I0tukXJCngVepCAYx|NLSG8IPoOatQVU3JpvSJlX280V|XOzlhxg9IYWSimr|zNujrIfwccN4lN3lMGMcDvhIfhvBXLQ2pJIRQze8bUZFy5DwFp5Ev8P3rOdC0wywZJm57e|apEMUwL|PkEtWiJYk9FMZSqP|8bqCEk|ZcOhPUx4znEEkvQ3dUJVzrfsYAD5njpy5WQMS'.split('|'),0,{}))