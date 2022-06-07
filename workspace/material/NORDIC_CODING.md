1. Schema mit Beer definieren
2. Query mit beer(beerId: ID!) und beers
3. Schema erweitern um Rating:
    - id comment stars
4. User (id name login) hinzufügen
   - Am Rating givenBy: User! hinzufügen
   - givenBy mit UserService.getUser (blocking) implementieren
5. Mutation addRating implementieren
   - AddRatingInput mit beerId, userId, comment, stars
   - PreAuthorize(hasRole('USER_ROLE'))
   - Tokena us BeerAdvisorApplication  
6. Optimierung
   - QUERY:   query {   beer(beerId: "B1") @trace {   ratings {   givenBy { id name login }   }   }   }
   - @trace hinzufügen
   - Im UserController slowDown auf true setzten
   - Optimierung 1: Mono!
7. DataLoader
    - Query: queries {   beer(beerId: "B1") @trace {   ratings {   givenBy { id name login }   }   }   }
      - Verwendung: 
      - 
        - Registry:
          registry.forTypePair(String.class, User.class).registerBatchLoader(
           (keys, env) -> {
           log.info("loading users with keys {}", keys);
           return userService.findUsersWithIds(keys);
           }
           );


record AddRatingInput(String userId, String beerId, String comment, Integer stars) {}

@MutationMapping
public Rating addRating(@Argument AddRatingInput ratingInput) {
return beerAdvisorService.addRating(ratingInput.userId(),
ratingInput.beerId(),
ratingInput.comment(),
ratingInput.stars());
}




## Erstes Schema:

```graphql
type Beer  {
    id: ID!
    name: String!
    price: String!
}

type Query {
    beer(beerId: ID!): Beer
    beers: [Beer!]!
}
```