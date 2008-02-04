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
	  "500"(view:'/error')
	}
}
