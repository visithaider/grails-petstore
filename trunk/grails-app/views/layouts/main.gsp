<%@ page import="org.grails.petstore.*" %>

<html>
    <head>
        <title><g:layoutTitle default="Grails Pet Store" /></title>
        <link rel="stylesheet" href="${createLinkTo(dir:'css',file:'main.css')}" />
        <link rel="shortcut icon" href="${createLinkTo(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
        <g:layoutHead />
        <g:javascript library="application" />				
    </head>
    <body>
        <g:form url='[controller: "item", action: "search"]' id="searchableForm" name="searchableForm" method="get">
            <g:textField name="q" value="${params.q}" size="20"/> <input type="submit" value="Search" />
        </g:form>
        <div id="spinner" class="spinner" style="display:none;">
            <img src="${createLinkTo(dir:'images',file:'spinner.gif')}" alt="Spinner" />
        </div>
        <div class="logo"><img src="${createLinkTo(dir:'images',file:'grails_logo.jpg')}" alt="Grails" /></div>
        <div style="font-weight: bold; text-transform: uppercase; letter-spacing: 8px; margin-left: 70px">Pet Store</div>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
            <span class="menuButton"><a class="list" href="${createLinkTo(dir:'item/list')}">Pets</a></span>
            <span class="menuButton"><a class="create" href="${createLinkTo(dir:'item/create')}">New Pet</a></span>
            <span class="menuButton"><a class="list" href="${createLinkTo(dir:'tag/list')}">Tags</a></span>
        </div>
        <g:layoutBody />
    </body>	
</html>