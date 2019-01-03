/* 百度地图API V2 模块
 * 此模块必须配套使用baidumap_offline_v2_20160822.js对
 * 获取模块的方法：
 * http://api0.map.bdimg.com/getmodules?v=2.0&mod=模块1,模块2
 * 模块名称就是文件名
 * www.xiaoguo123.com 整理
 */
 _jsload2&&_jsload2('pcommon', 'function Xh(a){this.P=a;this.$v=[];this.Jf=p;this.Hl=new bc(p,{Of:"api"});this.rz()}var Yh={none:["-1",""],transit:["0","m_transit.png"],hotel:["1","m_hotel.png"],catering:["2","m_catering.png"],movie:["3","m_movie.png"],transit:["4","m_transit.png"],indoor_scene:["5","m_indoor_scene.png"]};z.Sm(function(a){new Xh(a)}); x.extend(Xh.prototype,{rz:function(){var a=this;pano=a.P;pano.addEventListener("visible_poi_type_changed",function(b){a.e_(b.visiblePOIType)});pano.addEventListener("position_changed",function(){a.Jf&&a.II()})},e_:function(a){this.Jf=a;this.II()},zQ:function(){for(var a=this.$v.length-1;0<=a;a--)this.P.Sb(this.$v[a]);this.$v=[]},II:function(){this.zQ();if("inter"!==pano.Xs()){var a=Yh[this.Jf],b=this.P.ha(),c=this.P.Yb(),d=this;this.Hl.aE(b,200,a[0],function(b){var f=d.P.Yb();if(f===c)for(var g=p, g=p,i=0,k=b.length;i<k;i++)if(g=b[i],15>i||g.panoInfo&&g.panoInfo.panoId==f)g=new Qd(g.position,{icon:F.pa+"panorama/"+a[1],title:g.title,altitude:g.altitude,panoInfo:g.panoInfo}),d.P.Ia(g),d.$v.push(g)})}}});function Zh(a,b){this.P=a;this.B=b;this.Gq=p;this.Hl=new bc(p,{Of:"api"});this.bB=[];this.ds();this.Yl()}z.Sm(function(a){function b(){var b=a.Xs();a.k.indoorSceneSwitchControl==q||"street"==b?c&&c.U():"inter"==b&&(c||(c=new Zh(a,a.B)),c.T_(a.Yb()),c.show())}a.addEventListener("scene_type_changed",b);a.addEventListener("indoor_default_switch_mode_changed",b);var c=p}); x.extend(Zh.prototype,{Yl:function(){this.YP();this.rz()},Jp:function(a){function b(){d.P.Nc({heading:c.heading,pitch:c.pitch});d.P.removeEventListener("position_changed",b)}var c=this.bB[a];this.P.rc(c.panoId);var d=this;this.P.addEventListener("position_changed",b)},next:function(){this.Jp(++this.Gq)},jZ:function(){this.Jp(--this.Gq)},ds:function(){var a=this.uJ=K("div"),b=a.style;b.position="absolute";b.zIndex="2000";b.width="100%";b.top=b.left="0px";this.B.appendChild(a);this.gg=K("a");this.gg.style.left= "2%";this.gg.href="javascript: void(0);";this.gg.className="pano_switch_left";a.appendChild(this.gg);this.Vh=K("a");this.Vh.style.right="2%";this.gg.href="javascript: void(0);";this.Vh.className="pano_switch_right";G()&&(this.Vh.style.height="34px",this.Vh.style.width="34px",this.Vh.style.borderRadius="17px",this.gg.style.height="34px",this.gg.style.width="34px",this.gg.style.borderRadius="17px");a.appendChild(this.Vh);this.gg.style.top=this.Vh.style.top=this.P.gh().height/2-14+"px"},T_:function(a){var b= this,c=this.P.Yb();this.Hl.wx(a,function(a){var e=b.P.Yb();e===c&&(b.bB=a,b.DO(e))})},U:function(){this.Pa=q;this.uJ.style.display="none"},show:function(){this.Pa=o;this.uJ.style.display="block"},mh:t("Pa"),DO:function(a){for(var b=this.bB,c=b.length-1;0<=c;c--)b[c].panoId==a&&(this.Gq=c);x.D.Rb(this.gg,"pano_switch_disable");x.D.Rb(this.Vh,"pano_switch_disable");1===this.Gq?x.D.Ta(this.gg,"pano_switch_disable"):this.Gq==b.length-1&&x.D.Ta(this.Vh,"pano_switch_disable")},YP:function(){var a=this; ha.M(this.gg,"click",function(b){x.D.$s(b.target,"pano_switch_disable")||a.jZ()});ha.M(this.Vh,"click",function(b){x.D.$s(b.target,"pano_switch_disable")||a.next()})},rz:function(){var a=this,b=a.P;b.addEventListener("position_changed",function(){if(a.mh()){var c=b.Yb();a.DO(c)}})}});function $h(a){Md.call(this);this.P=a;this.Hl=new bc(p,{Of:"api"});this.Eq={admission:"",photoDate:"",roadName:"",providerUrl:"",providerName:""};this.Or=p;this.zr=[];this.OA=o;this.qa()}z.Sm(function(a){new $h(a)});x.lang.ta($h,Md,"PanoramaCopyright"); x.extend($h.prototype,{qa:function(){this.B=this.Nj(1900);this.P.Na();this.P.Na().appendChild(this.B);var a=this;this.Hl.kL(function(b){a.zr=b;a.za()});this.ca(this.P);this.P.k.copyrightControlOptions.logoVisible==q&&this.Cx()},za:function(){var a=this.QR(),b=[];b.push(\'<div style="width: 1000px; overflow:hidden;">\');if(this.OA){b.push(\'<a target="_blank" title="\\u5230\\u767e\\u5ea6\\u5730\\u56fe\\u67e5\\u770b\\u6b64\\u533a\\u57df" href=\'+this.P.fL()+\' style="outline:none;float:left;margin-left:3px">\');var c= F.pa+"copyright_logo.png";G()?(c=F.pa+"copyright_logo.png",b.push("<img style=\'border:none;width:68px;height:25px;vertical-align:bottom;\' src=\'"+c+"\' />")):6==x.ea.la?b.push("<div style=\'float: left;cursor:pointer;width:77px;height:32px;filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src="+c+")\'></div>"):b.push("<img style=\'border:none;width:77px;height:32px;vertical-align:bottom;\' src=\'"+c+"\' />");b.push("</a>")}b.push(\'<div style="float:left;\');G()?b.push(\'margin-top:8px;font-family:sans-serif;">\'): (b.push("margin-top:13px;"),x.platform.cM?b.push(\'font-family:sans-serif;">\'):b.push(\'">\'));a.roadName&&b.push(\'<span style="margin-left: 5px;">\'+a.roadName+"</span><span>|</span>");a.providerName&&(a.providerUrl?b.push(\'<span style="margin-left: 5px;">Data \\u00a9</span><span><a style="text-decoration: none;color: rgb(54, 54, 54);" target="_blank" href="\'+a.providerUrl+\'" style="display:inline;">\'+a.providerName+"</a></span>"):b.push(\'<span style="margin-left: 5px;">Data \\u00a9</span><span>\'+a.providerName+ "</span>"));b.push(\'<span style="margin-left: 5px;">\'+a.admission+"</span>");b.push(\'<span style="margin-left: 5px;">\\u62cd\\u6444\\u65e5\\u671f:\'+a.photoDate+"</span>");b.push("</div>");b.push("</div>");this.B.innerHTML=b.join("")},Nj:function(a){var b=K("div"),c=b.style;c.overflow="hidden";c.position="absolute";c.bottom=c.left="0";c.zIndex=a||"0";c.width="100%";c.fontSize="11px";c.height=G()?"27px":"34px";c.f4="none";c.WebkitTextSizeAdjust="none";c.WebkitUserSelect="none";c.visibility="hidden";c.fontFamily= "sans-serif";c.color="rgb(54, 54, 54)";c.lineHeight="20px";return b},ca:function(a){var b=this;a.addEventListener("copyright_changed",function(a){a.copyright&&(b.Or=a.copyright,b.za())});a.addEventListener("visible_changed",function(){b.B.style.visibility=a.$D()?"visible":"hidden"});a.addEventListener("copyright_options_changed",function(){a.k.copyrightControlOptions.logoVisible==q?b.Cx():b.AF()})},QR:function(){if(this.Or){for(var a in this.Eq)this.Eq[a]=this.Or[a];a=this.uX(this.Or.dataProviderIndex); this.Eq.providerName=a.name+this.Or.username;this.Eq.providerUrl=a.url}return this.Eq},uX:function(a){for(var b={name:"",url:"",id:""},c=this.zr.length-1;0<=c;c--)if(this.zr[c].id==a){var b=this.zr[c],d;for(d in b)b[d]=x.trim(this.zr[c][d])}return b},Cx:function(){/baidu\\.com/.test(document.domain)&&(this.OA=q,this.za())},AF:function(){this.OA=o;this.za()}}); ');