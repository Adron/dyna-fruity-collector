package com.fruitstand.dynafruitycollector;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;


@Controller
public class QueryResolver {

    @QueryMapping
    public String hello() {

        return "Hello World";
    }



}
