class UrlMappings {
    static mappings = {
      "/$controller/$action?/$id?"{
	      constraints {
			 // apply constraints here
		  }
	  }
	  "/tag/$tag" {
          controller = "tag"
          action = "listTagged"
      }
      "/item/byCategory/$category" {
          controller = "item"
          action = "byCategory"
      }
      "/item/byProduct/$product" {
          controller = "item"
          action = "byProduct"
      }
	  "500"(view:'/error')
	}
}
