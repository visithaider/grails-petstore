package gps

class PetstoreTagLib {

    static namespace = "ps"

    // TODO: read directory names from ImageService

    def categoryImage = { attrs ->
        def category = attrs.category
        out << g.createLinkTo(dir:"images/category",file:category.imageUrl)
    }

    def productImage = { attrs ->
        def product = attrs.product
        out << g.createLinkTo(dir:"images/product",file:product.imageUrl)
    }

    def thumbnailImage = { attrs ->
        out << g.createLinkTo(dir:"images/item/thumbnail", file:attrs.imageUrl)
    }
    
    def largeImage = { attrs ->
        out << g.createLinkTo(dir:"images/item/large", file:attrs.imageUrl)
    }

}
