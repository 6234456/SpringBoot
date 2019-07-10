package eu.qiou.web


import eu.qiou.aaf4k.util.time.TimeSpan
import kotlinx.html.*
import kotlinx.html.dom.createHTMLTree
import kotlinx.html.dom.document
import kotlinx.html.stream.appendHTML
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.temporal.ChronoUnit
import java.util.concurrent.atomic.AtomicLong

@RestController
class GreetingController {

    val counter = AtomicLong()

    @GetMapping("/greeting")
    fun greeting(@RequestParam(value = "name", defaultValue = "World") name: String) =
        Greeting(counter.incrementAndGet(), "Hello, $name")

    @GetMapping("/account")
    fun trail(@RequestParam(value = "year", defaultValue = "2017") year: Int) = buildString {
        appendHTML(true).html {
            head {
                meta {
                    attributes["lang"] = "cn"
                }
                style {
                    + """
                        * {
                            font-family: Arial;
                        }


                        .stripped  li:nth-child(2n+1){
                            background-color : #c0c0c0;
                            color : white;
                        }

                    """
                }
            }
            body {
                div {
                    select {
                        TimeSpan.forYear(2010).drillDown(1, ChronoUnit.DAYS)
                            .forEach {
                            option {
                                +it.start.toString()
                            }
                        }
                    }
                    ol("stripped"){
                        TimeSpan.forYear(2016).drillDown().forEach {
                            li{
                                attributes["data"] = it.start.toString()
                                +it.toString()
                            }
                        }
                    }
                }
                footer {
                    div {
                        +"qiou.eu - 2019"
                    }
                }
            }
        }
    }

}
