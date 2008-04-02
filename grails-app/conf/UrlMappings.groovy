class UrlMappings {
    static mappings = {
      "/$controller/$action?/$id?"{
	      constraints {
			 // apply constraints here
		  }
	  }
	  "/tag/$tag" {
          controller = "tag"
          action = "byTag"
      }
	  "500"(view:'/error')
	}
}
