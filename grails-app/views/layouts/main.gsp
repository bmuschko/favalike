<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
      <meta name="keywords" content="bookmarks, tags, links, favorites">
      <meta name="description" lang="en" content="Manage your favorites easily!">
      <link rel="icon" type="image/ico" href="${createLinkTo(file:'favicon.ico')}">
	  <link rel="stylesheet" type="text/css" href="${createLinkTo(dir:'css',file:'favalike.css')}" />
	  <title>
         Favalike &raquo; <g:layoutTitle default="Manage your favorites easily!" />
      </title>
      <g:layoutHead />
   </head>

   <body>
      <div id="wrapper" class="wrapper">
	     <div id="top_panel" class="top_panel">
		    <div id="top_line" class="top_line">
		    </div>
			<div class="top_panel_content">
			   <div id="logo" class="logo">
			      <a href="/"><img src="${createLinkTo(dir:'images',file:'logo.png')}" style="z-index:999" border="0"/></a>
			   </div>
               <div id="user_info" class="user_info">
			      <g:pageProperty name="page.loginInfo"/>
               </div>
			</div>
	     </div>

		 <div id="main" class="main">
		    <div class="bl">
               <div class="br">
                  <div class="tl">
                     <div class="tr">
                        <div id="main_content">
                           <g:pageProperty name="page.mainContent"/>
                        </div>
			         </div>
                  </div>
               </div>
            </div>
		 </div>
         <div id="footer">
         <span>Favalike version <g:meta name="app.version"/> on Grails <g:meta name="app.grails.version"/></span>
         <span>|</span>
         <span>created by <a href="mailto:benjamin.muschko@gmail.com">Benjamin Muschko</a></span>
         <div style="visibility: hidden;"><a href="http://www.thumbshots.com" target="_blank" title="Thumbnails Previews by Thumbshots">Thumbnails powered by Thumbshots</a></div>
      </div>
      </div>
   </body>
</html>