package nh.graphql.beeradvisor.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Component
public class DbImporter {

  private static final Logger logger = LoggerFactory.getLogger(DbImporter.class);

  private final BeerRepository beerRepository;
  private final ShopRepository shopRepository;

  public DbImporter(BeerRepository beerRepository, ShopRepository shopRepository) {
    this.beerRepository = beerRepository;
    this.shopRepository = shopRepository;
  }

  @PostConstruct
  @Transactional
  public void importDb() {
    importBeerDb();
    importShopDb();
  }

  public void importBeerDb() {
    logger.info("Importing Database");

    final Beer b1 = new Beer("B1", "Barfüßer", "3,80 EUR") //
        .addRating("U1", "R1", "Exceptional!", 4) //
        .addRating("U2", "R7", "Awwwesome!", 4) //
        .addRating("U3", "R9", "Can I order another please?", 5) //
        ;

    final Beer b2 = new Beer("B2", "Frydenlund", "150 NOK") //
        .addRating("U1", "R2", "Very good!", 4) //
        .addRating("U3", "R8", "phenomenal!", 5)
        .addRating("U4", "R15", "Delicate buttery flavor, with notes of sherry and old newsprint", 2)//
        ;

    final Beer b3 = new Beer("B3", "Grieskirchner", "3,20 EUR") //
        .addRating("U1", "R3", "Great taste!", 3) //
        .addRating("U5", "R10", "Tastes moreish", 4) //
        ;

    final Beer b4 = new Beer("B4", "Tuborg", "5,50 EUR") //
        .addRating("U5", "R4", "Try it, you'll love it!", 3) //
        .addRating("U4", "R11", "Hmmmm!!!!", 3) //
        ;

    final Beer b5 = new Beer("B5", "Baltic Tripple", "6,95 EUR") //
        .addRating("U2", "R5", "My favorite!", 4) //
        .addRating("U3", "R12", "Watery mouthfeel and long finish.", 3) //
        ;

    final Beer b6 = new Beer("B6", "Viktoria Bier", "4,20 EUR") //
        .addRating("U4", "R6", "Awwwesome!", 4) //
        .addRating("U2", "R13", "✊...", 5) //
        ;

    beerRepository.save(b1);
    beerRepository.save(b2);
    beerRepository.save(b3);
    beerRepository.save(b4);
    beerRepository.save(b5);
    beerRepository.save(b6);
  }

  public void importShopDb() {
    logger.info("Importing Shop Database");

    if (shopRepository.findAll().size() > 0) {
      logger.info("Shops already imported");
      return;
    }

    try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/shops.csv")))) {
      String line = null;
      int index = 1;
      while ((line = br.readLine()) != null) {
        String[] parts = line.trim().split("\\|");
        Shop shop = new Shop("S" + index++, parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6].split(","));
        shopRepository.addShop(shop);
      }
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

}
