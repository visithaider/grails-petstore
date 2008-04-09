<html>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta name="layout" content="main" />
  </head>
  <body>
  <div class="body">
      <% if (!(params.q?.trim())) { %>
          <h1>Nothing found.</h1>        
      <% } else {
          def from = params.offset?.toInteger() ?: 0
          def to = from + itemList.size()
          %>
          <h1>Results ${from + 1} to ${to} of ${total} for "${params.q}"</h1>
          <div class="paginateButtons paginateTop">
              <g:paginate controller="item" action="search" params="[q: params.q]" total="${total}" />
          </div>
          <g:render template="itemListTemplate" model="[itemList:itemList]"/>
          <div class="paginateButtons paginateTop">
              <g:paginate controller="item" action="search" params="[q: params.q]" total="${total}" />
          </div>
      <% } %>
  </div>
  </body>
</html>