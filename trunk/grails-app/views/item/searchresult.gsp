<%@ page import="org.springframework.util.ClassUtils" %>
<%@ page import="org.codehaus.groovy.grails.plugins.searchable.SearchableUtils" %>
<%@ page import="org.codehaus.groovy.grails.plugins.searchable.lucene.LuceneUtils" %>
<%@ page import="org.grails.petstore.*" %>

<html>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta name="layout" content="main" />
  </head>
  <body>
  <div class="body">
      <% if (!(params.q?.trim())) { %>
          <h1>Nothing found.</h1>        
      <% } else { %>
          <h1>Search results for "${params.q}"</h1>
          <div class="paginateButtons">
              <g:paginate controller="item" action="search" params="[q: params.q]" total="${itemList.size()}" />
          </div>
          <g:render template="itemListTemplate" model="[itemList:itemList]"/>
          <div class="paginateButtons">
              <g:paginate controller="item" action="search" params="[q: params.q]" total="${itemList.size()}" />
          </div>
      <% } %>
  </div>
  </body>
</html>