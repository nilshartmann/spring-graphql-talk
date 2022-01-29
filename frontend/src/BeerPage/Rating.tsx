import * as React from "react";
import Stars from "../components";
import styles from "./Beer.module.css";
import { BeerPageQuery_beer_ratings as BeerRatingData } from "./querytypes/BeerPageQuery";

interface RatingProps {
  rating: BeerRatingData;
}

const Rating = ({ rating: { author, comment, stars } }: RatingProps) => (
  <div className={styles.Rating}>
    <span className={styles.Author}>{author.name}</span>:{" "}
    <span className={styles.Comment}>
      „{comment}“ <Stars stars={stars} />
    </span>
  </div>
);

export default Rating;
