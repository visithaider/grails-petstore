Current SVN HEAD (0.3-snapshot):

  * Get Grails 1.3.5
  * Get Searchable plugin:

> grails install-plugin searchable 0.5.5

  * Get Feeds plugin:

> grails install-plugin feeds 1.5

  * Go!



These instructions apply to GPS 0.2.

  * Get Grails 1.0.2
  * Get Searchable plugin:

> grails install-plugin searchable 0.4

  * Get Feeds plugin:

> grails install-plugin feeds 1.2

  * Go!

The import of Java Pet Store no longer requires the Java Pet Store on disk, everything is read from the XML export file in src/java.

Development mode has a few things turned off for speed:

  * No coordinate fetching from Yahoo (turn on in GeoCoderService)
  * Only imports 10 items from JPS (production mode imports all 102, change in BootStrap)
  * Uses the same image for all items (production mode imports individual images from JPS, change in SunPetstoreImporterService)
  * Hibernate second-level cache breaks reloading of application ("cache is not alive"), and is turned off in development mode (change in DataSource)

Production mode is default configured for deployment in an environment with a MySQL database exposed in JNDI as ""jdbc/GrailsPetStoreDB" (see DataSource)

GPS 0.2 should require Java 5 or later (tested on WinXP/Java 1.6 and OS X 10.4.11/Java 1.5).
