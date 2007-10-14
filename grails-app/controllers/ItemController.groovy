class ItemController {

    GeoCoderService geoCoderService
    CaptchaService captchaService

    def scaffold = Item
    def defaultAction = "create"
    def uploadedDir = "/tmp/petstore/uploaded/"
    def thumbnailDir = "/tmp/petstore/scaled/"

    def create = {
        def captchaString = captchaService.generateCaptchaString(6)
        session.setAttribute("captchaString", captchaString)
        render(view:"edit")
    }

    def save = {
        def item = new Item(address:new Address(), contactInfo:new SellerContactInfo(),product:new Product())
        item.properties = params

        def uploaded = request.getFile("file")
        if (!uploaded.empty) {
            // Generate a new random file name with the suffix intact
            def suffix = ".jpg"
            def newName = new RandomString().getStringfromLong() + suffix

            // Store the uploaded image
            item.imageURL = uploadedDir + newName
            uploaded.transferTo(new File(item.imageURL))

            // Scale and store thumbnail
            item.imageThumbURL = thumbnailDir + newName
            def scaled = new ScalableImage(item.imageURL)
            scaled.keepAspect()
            scaled.resizeWithGraphics(item.imageThumbURL)
        }

        // TODO: set latitude & longitude
        //List gp = geoCoderService.geoCode(item.toString())


        if (item.save()) {
            flash.message = "Saved item with id = " + item.id + ", properties: " + item.properties
            redirect(action:show,id:item.id)
        } else {
            render(view:"edit", model:[item:item])
        }
    }

}