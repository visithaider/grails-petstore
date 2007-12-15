import groovy.sql.*
import groovy.xml.*

// Default Sun Petstore parameters
def url = "jdbc:derby://localhost:1527/petstore"
def username = "APP"
def password = "APP"
def driver = "org.apache.derby.jdbc.ClientDriver"
def sql = Sql.newInstance(url, username, password, driver)

// Tags per item is loaded in advance, for efficiency
def tagQuery = """
	select
        t.tag, i.itemid
    from tag_item ti
	    left join item i on ti.itemid=i.itemid
	    left join tag t on ti.tagid=t.tagid   
"""

def tagmap = [:]

sql.eachRow(tagQuery, {
	list = tagmap[it.itemid]
	if (!list) {
		list = []
		tagmap[it.itemid] = list
	}
	list.add(it.tag)
});

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

xml.petstore() {
    categories() {
        sql.eachRow("select * from category", { c ->
            category() {
                name c.name
                description c.description
                imageurl "images/" + c.imageurl
                products() {
                    sql.eachRow("select * from product where categoryid = ${c.categoryid}", { p ->
                        product() {
                            name p.name
                            description p.description
                            imageurl "images/" + p.imageurl
                        }
                    })
                }
            }
        })
    }
    items() {
        sql.eachRow(itemQuery, { row ->
            item () {
                name row.name
                description row.description
                image row.imageurl
                price row.price
                totalScore row.totalscore
                numberofVotes row.numberofvotes
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
                    tagmap[row.itemid]?.each {
                        tag it
                    }
                }
            }
        }
    )}  // end of items
}

println sw.toString()
