<html>
    <head>
        <title>Welcome to Grails Petstore</title>
		<meta name="layout" content="main" />
        <style type="text/css">
            .par {
                margin: 20px;
                width: 600px;
            }
        </style>
    </head>
    <body>
        <h1 style="margin-left:20px">Welcome to the Grails Pet Store ${grailsApplication.metadata['app.version']}</h1>
        <p class="par">
            This a snapshot of the <a href="http://www.grails.org">Grails</a> port of the famous <a href="https://blueprints.dev.java.net/petstore/">Pet Store</a> application.
        </p>
        <p class="par">
            Features so far:
        </p>
        <div class="par">
            <ul>
                <li>Persistent domain model with validation, closely modeled after the Java Pet Store 2.0</li>
                <li>Indexed <a href="${createLink(controller:"item",action:"search",params:[q:"pet"])}">search</a>, using the Compass-backed Searchable plugin</li>
                <li>Import of the Java Pet Store database to/from XML</li>
                <li>Image handling (uploading, scaling, captcha generation etc)</li>
                <li>Pet catalog <a href="${createLink(controller:"item",action:"list",params:[max:10])}">view</a> with sorting and pagination, browse pets by category or product</li>
                <li>Browse <a href="${createLink(controller:"tag",action:"cool")}">by tag</a></li>
                <li>Edit and <a href="${createLink(controller:"item",action:"create")}">create</a> pets</li>
            </ul>
        </div>
        <p class="par">
            Future releases will have map mashups and of course a shopping cart functionality, as well as
            numerous small improvements throughout the application.
        </p>
        <p class="par">
            Track project progress on <a href="http://code.google.com/p/grails-petstore">Google Code</a>,
            and keep an eye on <a href="http://peterbacklund.blogspot.com/">this blog</a> from time to time.
        </p>
        <p class="par">
            <em>
                The imported pet images are imported from the Sun Java Pet Store distribution, and protected by copyright.
                More information <a href="https://blueprints.dev.java.net/petstore/imagecontributors.html">available here</a>.
            </em>
        </p>
        <p class="par">
            <em>Grails Pet Store is purely a community project and has no relation to the <a href="http://www.g2one.com/">G2One</a> company.</em>
        </p>
    </body>
</html>