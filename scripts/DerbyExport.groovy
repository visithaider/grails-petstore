import groovy.sql.Sql
import groovy.xml.MarkupBuilder

// Default Sun Petstore parameters
def url = "jdbc:derby://localhost:1527/petstore"
def username = "APP"
def password = "APP"
def driver = "org.apache.derby.jdbc.ClientDriver"
def sql = Sql.newInstance(url, username, password, driver)

// path to Java Pet Store images
def pathToImages = "c:/javapetstore-2.0-ea5/web/"

// Name of output file
def outputFile = "src/java/sun_petstore_export.xml"

// Item tags
def tagQuery = """
	select
        t.tag
    from tag_item ti
	    left join tag t on ti.tagid=t.tagid
    where ti.itemid = ?
"""

// All items, with category, address, product and contact info joined
def itemQuery = """
	select
        i.itemid,i.name,i.description,
        i.imageurl,i.price,i.totalscore,i.numberofvotes,
        p.name as p_name,
        p.description as p_description,
        p.imageurl as p_imageurl,
        c.name as c_name,
        c.description as c_description,
        c.imageurl as c_imageurl,
        a.street1,a.street2,a.city,a.state,a.zip,
        sci.firstname,sci.lastname,sci.email
    from item i
	    left outer join product p on p.productid=i.productid
	    left outer join category c on c.categoryid=p.categoryid
	    left outer join address a on i.address_addressid=a.addressid
	    left outer join sellercontactinfo sci on i.contactinfo_contactinfoid=sci.contactinfoid
"""

def sw = new StringWriter()
def xml = new MarkupBuilder(sw)
def imgBaseDir = new File(pathToImages)

// Helper to encode images using base64
def toBase64 = { imageUrl ->
    def bytes = new File(imgBaseDir, imageUrl).readBytes()
    bytes.encodeBase64()
}

xml.petstore() {
    categories() {
        sql.eachRow("select * from category", { c ->
            category() {
                name c.name
                description c.description
                imageurl c.imageurl
                image toBase64("images/${c.imageurl}")
                products() {
                    sql.eachRow("select * from product where categoryid = ${c.categoryid}", { p ->
                        product() {
                            name p.name
                            description p.description
                            imageurl p.imageurl
                            image toBase64("images/${p.imageurl}")
                        }
                    })
                } // end of products
            }
        })
    } // end of categories
    items() {
        sql.eachRow(itemQuery, { row ->
            item () {
                name row.name
                description row.description
                imageurl row.imageurl
                image toBase64(row.imageurl)
                price row.price
                totalScore row.totalscore
                numberOfVotes row.numberofvotes
                product row.p_name
                contactInfo() {
                    firstName row.firstname
                    lastName row.lastname
                    email row.email
                }
                address()  {
                    street1 row.street1
                    street2 row.street2
                    city row.city
                    zip row.zip
                    state row.state
                }
                tags() {
                    sql.eachRow(tagQuery, [row.itemid], {
                        tag it.TAG
                    })
                }
            }
        }
    )} // end of items
}

def result = new File(outputFile)
result.write(sw.toString())
println "\n* * * Wrote export to ${outputFile}. Size: ${(int) (result.size() / 1024)} kilobytes. * * *\n"
