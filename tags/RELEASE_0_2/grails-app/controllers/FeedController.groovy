import groovy.xml.MarkupBuilder

class FeedController {

    def buildRssContent(item) {
        def sw = new StringWriter()
        new MarkupBuilder(sw).div() {
            img(src:ps.thumbnailImage(item:item))
            p(item.description)
        }
        sw.toString()
    }

    def rss = {
        render(feedType:"rss",feedVersion:"2.0") {
            title = "Grails Pet Store"
            link = g.createLink(action:"list")
            description = "The ten most recent pets from the Grails Pet Store"
            Item.list(max:10,sort:"lastUpdated",order:"desc").each { item ->
                entry(item.name) {
                    publishedDate = item.lastUpdated
                    link = g.createLink(action:"show",id:item.id)
                    buildRssContent(item)
                }
            }
        }
    }

}