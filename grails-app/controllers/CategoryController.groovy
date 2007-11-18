            
class CategoryController {

    def list = {
        [categoryList:Category.listOrderByName()]
    }

    def show = {
        [category:Category.get(params.id)]
    }

}