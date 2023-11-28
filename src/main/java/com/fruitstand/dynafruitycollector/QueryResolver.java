package com.fruitstand.dynafruitycollector;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Filters.regex;


@Controller
public class QueryResolver {

    private final MongoTemplate mongoTemplate;

    public QueryResolver(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @QueryMapping
    public String hello() {

        return "Hello World";
    }

    @QueryMapping
    public List<String> collections() {

        List<String> collections = new ArrayList<>();

        Iterable<String> mongoCollections = mongoTemplate.getCollectionNames();

        for (String collectionName : mongoCollections) {
            collections.add(collectionName);
        }

        return collections;
    }

    @QueryMapping
    public List<String> docsByCollectionName(@Argument String collectionName) {
        List<String> documents = new ArrayList<>();

        MongoCollection<Document> collection = mongoTemplate.getCollection(collectionName);
        FindIterable<Document> docs = collection.find();

        for (Document doc : docs) {
            documents.add(doc.toString());
        }

        return documents;
    }

    @QueryMapping
    public List<String> docsBySearchString(@Argument String searchString, @Argument String collectionName, @Argument String fieldToSearch) {
        List<String> documents = new ArrayList<>();

        MongoCollection<Document> collection = mongoTemplate.getCollection(collectionName);

        Document query = new Document("$or", Arrays.asList(new Document(fieldToSearch, searchString)));

        FindIterable<Document> result = collection.find(query);

        for (Document doc : result) {
            documents.add(doc.toString());
        }

        return documents;
    }

    @QueryMapping
    public List<String> docsByRegex(@Argument String regex, @Argument String collectionName, @Argument String fieldToSearch) {
        List<String> documents = new ArrayList<>();

        MongoCollection<Document> collection = mongoTemplate.getCollection(collectionName);

        Document query = new Document("$or", Arrays.asList(regex(fieldToSearch, regex, "i")));

        FindIterable<Document> result = collection.find(query);

        for (Document doc : result) {
            documents.add(doc.toString());
        }

        return documents;
    }

    @QueryMapping


//    docsFindByField(collectionName: String!, fieldToFind: String!): [String]
}
