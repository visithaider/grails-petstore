### 2008-04-09: Grails Pet Store 0.2 released ###
The second milestone has been released. Highlights include:

  * Upgraded to Grails 1.0.2, Searchable plugin 0.4, Feeds plugin 1.2
  * Java Pet Store import completely from XML (images are base64-encoded)
  * Address coordinate fetch from Yahoo (turned off in development mode), and Google Maps mashup in item detail view
  * Hibernate second level cache (turned off in development mode)
  * RSS feed with ten latest items
  * Sorting and pagination for browsning by category/product, by tag and search now works correctly. All browsing method use the same view.
  * First example of a taglib
  * Captcha service now session-scoped
  * Command object for handling creation and edit of new items
  * Lots and lots of small improvments

Take a look at the [roadmap](http://code.google.com/p/grails-petstore/issues/list?sort=milestone) to see what lies ahead. There will also be
a live instance of the GPS available within a few weeks. The [Howto](Howto.md) has been updated, please leave a comment or raise an issue if you have any problems.

Note: there is no packaged download - use the "RELEASE\_0\_2" subversion tag.

### 2008-02-07: Welcome to the Grails Pet Store 0.1 ###

This is the first milestone release of the Grails port of the famous Pet Store application.

Features so far:

  * Persistent domain model with validation, closely modeled after the Java Pet Store 2.0
  * Indexed search, using the Compass-backed Searchable plugin
  * Import of the Java Pet Store database to/from XML
  * Image handling (uploading, scaling, captcha generation etc)
  * Pet catalog view with sorting and pagination, browse pets by category or product
  * Browse by tag
  * Edit and create pets

Future releases will have map mashups and of course a shopping cart functionality, as well as numerous small improvements throughout the application.

Track project progress here on Google Code, and keep an eye on [this blog](http://peterbacklund.blogspot.com/) from time to time.

_Grails Pet Store is purely a community project and has no relation to the [G2One](http://www.g2one.com) company._

---

YourKit is kindly supporting open source projects with its full-featured Java Profiler.

YourKit, LLC is creator of innovative and intelligent tools for profiling
Java and .NET applications. Take a look at YourKit's leading software products:

[YourKit Java Profiler](http://www.yourkit.com/java/profiler/index.jsp) and
[YourKit .NET Profiler](http://www.yourkit.com/.net/profiler/index.jsp).