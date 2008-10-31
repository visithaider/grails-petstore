import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

def n = 125
def c = 20

def protocol = "http"
def host = "localhost"
def port = 8080
def path = "/grails-petstore"

def views = [
    "/item/list",
    "/item/list?offset=50&max=10",
    "/item/byCategory/1",
    "/item/byProduct/1",
    "/item/show/1",
    "/item/search?q=cat",
    "/item/create",
    "/tag/cool"
]

def executor = Executors.newFixedThreadPool(c)

def startTime = System.currentTimeMillis()

int size = 0
n.times {
    views.each { view ->
        def url = new URL(protocol, host, port, path + view)
        executor.execute {
            size += url.openConnection().getContent().hashCode()
        }
    }
}
println "Jobs submitted in ${(System.currentTimeMillis() - startTime)/1000.0} s"

executor.shutdown()
executor.awaitTermination(30, TimeUnit.SECONDS)

def stopTime = System.currentTimeMillis()

println """
Grabbed ${n*views.size()} views in ${(stopTime - startTime)/1000.0} s,
which is ${n*views.size()*1000/(stopTime - startTime)} views per second. Size: ${size}"""
