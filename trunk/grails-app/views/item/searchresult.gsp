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
          <div class="paginateButtons paginateTop">
              <g:paginate controller="item" action="search" params="[q: params.q]" total="${itemList.size()}" />
          </div>
          <div class="list">
              <table>
                  <thead>
                      <tr>
                          <th>Image</th>
                          <g:sortableColumn property="name" title="Name" />
                          <%-- TODO: add product, category and tags to index and use _listTemplate here
                          <g:sortableColumn property="product.name" title="Product" />
                          <g:sortableColumn property="product.category.name" title="Category" />
                          <g:sortableColumn property="tags" title="Tags" />
                          --%>
                          <g:sortableColumn property="price" title="Price" />
                      </tr>
                  </thead>
                  <tbody>
                  <g:each in="${itemList}" status="i" var="item">
                      <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                          <td>
                              <g:link controller="item" action="show" id="${item.id}">
                                  <img src="${createLinkTo(dir:'images/item/thumbnail', file:item.imageUrl?.encodeAsHTML())}" alt="" />
                              </g:link>
                          </td>
                          <td>
                              <h2>
                                  <g:link controller="item" action="show" id="${item.id}">${item.name?.encodeAsHTML()}</g:link>
                              </h2>
                              <p>
                                  ${item.description?.encodeAsHTML()}
                              </p>
                          </td>
                          <td>$${item.price?.encodeAsHTML()}</td>
                      </tr>
                  </g:each>
                  </tbody>
              </table>
          </div>
          <div class="paginateButtons paginateTop">
              <g:paginate controller="item" action="search" params="[q: params.q]" total="${itemList.size()}" />
          </div>
      <% } %>
  </div>
  </body>
</html>