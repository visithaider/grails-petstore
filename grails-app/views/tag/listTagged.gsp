<html>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta name="layout" content="main" />
  </head>
  <body>
  <div class="body">
      <% if (itemList.isEmpty()) { %>
          <h1>No items are tagged with "${params.tag}".</h1>
      <% } else { %>
          <h1>${itemList.size()} item${itemList.size() == 1 ? "" : "s"} are tagged "${params.tag}"</h1>
          <div class="paginateButtons paginateTop">
              <g:paginate controller="tag" action="listTagged" params="[tag: params.tag]" total="${itemList.size()}" />
          </div>
          <g:render template="/item/itemListTemplate" model="[itemList:itemList]"/>
          <div class="paginateButtons paginateBottom">
              <g:paginate controller="tag" action="listTagged" params="[tag: params.tag]" total="${itemList.size()}" />
          </div>
      <% } %>
  </div>
  </body>
</html>