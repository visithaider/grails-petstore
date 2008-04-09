// TODO: should be part of core Grails
class GpsWebUtils {

    static Map toHqlParams(Map params) {
        [offset:(params.offset ?: 0).toInteger(),
         max:(params.max ?: 10).toInteger(),
         sort:params.sort,
         order:params.order]
    }

}